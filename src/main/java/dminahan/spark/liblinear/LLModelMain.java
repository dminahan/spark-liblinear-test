package dminahan.spark.liblinear;

import de.bwaldvogel.liblinear.*;
//import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.ForeachFunction;
import org.apache.spark.sql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LLModelMain implements Serializable {

   public JavaSparkContext context;
   public SQLContext sqlContext=null;
   
   private static final Logger LOGGER=LoggerFactory.getLogger(LLModelMain.class);
   private static SolverType defaultSolverType=SolverType.L2R_LR;
   private static double defaultBias=-1; //Means we will not have a bias feature entry
   private static double defaultCostOfContraints=1.0;
   private static double epsilon=0.01;
   
   //Label set to use
   private static double[] fullLables={0,1,1,0,1};
   
   //Small Label set to use
   private static double[] smallLabels={0,1,1};
   
   //All Features to use in models
   private static FeatureNode[] instance1={new FeatureNode(2,0.1),new FeatureNode(3,0.2)};
   private static FeatureNode[] instance2={new FeatureNode(2,0.2),new FeatureNode(3,0.3),new FeatureNode(4,-1.2)};
   private static FeatureNode[] instance3={new FeatureNode(1,0.4)};
   private static FeatureNode[] instance4={new FeatureNode(2,0.1),new FeatureNode(4,1.4),new FeatureNode(5,0.5)};
   private static FeatureNode[] instance5={new FeatureNode(1,-0.1),new FeatureNode(2,-0.2),new FeatureNode(3,0.1), new FeatureNode(4,1.1), new FeatureNode(5,0.1)};
   private static FeatureNode[][] fullTrainingSet={instance1,instance2,instance3,instance4,instance5};

   private static FeatureNode[][] smallTrainingSet={instance1,instance2,instance3};

 
public static void main(String args) {
   LLModelMain modelMain= new LLModelMain();
   modelMain.init();
   modelMain.run(modelMain.sqlContext.sparkSession());
}

public void init() {
   String master="local[*]";
   SparkConf conf =new SparkConf()
                .setAppName(LLModelMain.class.getName())
                .setMaster(master);
    
   context=new JavaSparkContext(conf);
   sqlContext=new SQLContext(context);
}

public void run(SparkSession sparkSession) {
    LLModel llModel=new LLModel();

   Parameter parameter = new Parameter(defaultSolverType, defaultCostOfContraints, epsilon);
   Model model=createModel(parameter, 5, 5, defaultBias, fullLables, fullTrainingSet);
   llModel=LibLinearUtility.convertToLLModel(model);

   UserModel userModel=new UserModel();
   userModel.setName("user1");
   userModel.setModel(llModel);

   List<UserModel> listOfModels=new ArrayList<UserModel>();
   listOfModels.add(userModel);

    //1. Enoder by bean
    Encoder<UserModel> encoder=Encoders.bean(UserModel.class);
    Dataset<UserModel> dataset=sparkSession.createDataset(listOfModels, encoder);
    dataset.printSchema();
    dataset.show(false);

    dataset.write().mode(SaveMode.Overwrite).format("parquet").save("testModels.parquet");
   Dataset<UserModel> parquetModels=sparkSession.read().load("testModels.parquet").as(encoder);
   parquetModels.show(false);
   parquetModels.printSchema();

//for(ModelUser record: records) {
//  System.out.println(“ModelUser is: “+ record.toString());
//}

   parquetModels.createOrReplaceTempView("models");
   Dataset<Row> llModels=sparkSession.sql("SELECT model FROM models");
   Dataset<Model> models=llModels.as(Encoders.bean(Model.class));
   models.foreach((ForeachFunction<Model>) row-> LOGGER.warn("model is: " + row.toString()));

//parquetModels.foreach((ForeachFunction<ModelUser>)row -> LOGGER.warn(“Read in LLModel is: “ + row.toString()));

   List<ScoringInput> scoresInputList=generateScoringDataSet();
   Encoder<ScoringInput> scoringEncoder=Encoders.bean(ScoringInput.class);
   Dataset<ScoringInput> datasetScores=sparkSession.createDataset(scoresInputList, scoringEncoder);

   Dataset<Row> joinedDS=datasetScores.crossJoin(dataset);
  //For each try, resulted in class cast exception
//joinedDS.foreach((ForeachFunction<Row>) row -> LOGGER.warn(“Joined Dataset LLModel is: “ + row.<ModelUser>.getAs(“value”).getLLModel().toString()));
   joinedDS.show(false);
   joinedDS.printSchema();

   joinedDS.foreach((ForeachFunction<Row>) row -> LOGGER.warn("row vector is: " + row.getAs("vector")));
   joinedDS.foreach((ForeachFunction<Row>)row -> {
      //LOGGER.warn(""+((Row)row.getAs("llModel")).getAs("model").getAs("bias"));
   });
   
   Dataset<CombinedScoringInput> scoringCombined=joinedDS.as(Encoders.bean(CombinedScoringInput.class));
   scoringCombined.printSchema();
   scoringCombined.show(false);
   scoringCombined.foreach((ForeachFunction<CombinedScoringInput>)row -> {
      LLModel dsModel=row.getModel();
      Model libLinearModel=LibLinearUtility.convertFromLLModel(dsModel);
      double[]vectors=row.getVector();
      ArrayList<Feature> features=new ArrayList<Feature>();
      for(int i=0; i<vectors.length;i++) {
         FeatureNode feature=new FeatureNode(i+1, vectors[i]);
         features.add(feature);
      }
      
      double[] probabilities=new double[dsModel.getNrClass()];
      Feature[] featureSize=new Feature[features.size()];
      featureSize = features.toArray(featureSize);

      System.out.println("predict output is: " + Linear.predictProbability(libLinearModel, featureSize, probabilities));
      System.out.println("probablities are: " + Arrays.toString(probabilities));
   });

}
 

public static Model createModel(Parameter parameterIn, int trainingSize, int featureSize, double bias, double[] labels, Feature[][] features) {
  Problem modelProblem=new Problem();
  modelProblem.bias=bias;
  modelProblem.l=trainingSize;
  modelProblem.n=featureSize;
  modelProblem.y=labels;
  modelProblem.x=features;

  Model trainingModel=Linear.train(modelProblem,parameterIn);
  return trainingModel;
}

 

  public static List<ScoringInput> generateScoringDataSet() {
    List<ScoringInput> listOfScoringVectors=new ArrayList<>();
    int scores=10;
    ScoringInput input=null;

    Random r = new Random();
    for(int i=0; i<scores; i++) {
      input=new ScoringInput();
      input.setId("id"+i);
      input.setMemberId("memberId"+i);
      int vectorSize=5;
       
      ArrayList<Double> vectors=new ArrayList<Double>();
       for(int j=0; j<vectorSize; j++) {
          double d = r.nextDouble();
          vectors.add(d);
       }
       Double[] vectorArray=((Double[])vectors.toArray(new Double[vectors.size()]));
       //Double[] vectorArray=((Double[])vectors.toArray(new Double(vectors.size())));
       input.setVector(ArrayUtils.toPrimitive(vectorArray));
       listOfScoringVectors.add(input);
    }
    return listOfScoringVectors;
  }
}

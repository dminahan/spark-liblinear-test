package dminahan.spark.liblinear;

import de.bwaldvogel.liblinear.*;
import org.apache.commons.lang.ArrayUtils;
import org.apache.spark.sql.java.JavaSparkContext;
import org.apache.spark.api.java.function.ForeachFuntion;
import org.apache.spark.sql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LLModelmain implements Serializable {

   public JavaSparkContext context;
   public SQLContext sqlContext=null;
   
   private static final Logger LOGGER=LoggerFactory.getLogger(LLModelMain.class);
   private static Solvertype defaultSolverType=SolverType.L2R_LR;
   private static double defaultBias=-1; //Means we will not have a bias feature entry
   private static double defaultCostOfContraints=1.0;
   private static double epsilon=0.01;
   
   //Label set to use
   private static double[] fullLables={0,1,1,0,1};
   
   //Small Label set to use
   private static double[] smallLabels={0,1,1};
   
   //All Features to use in models
   private static FeatureNode[] instance1={new FeatureNode(2,0.1), new FeatureNode(3,0.2};
   
      
}

package de.bwaldvogel.liblinear;

import dminahan.spark.liblinear.LLModel;

import java.util.List;
import java.util.ArrayList;

public class LibLinearUtility {

   public static LLModel convertToLLModel(Model model) {
      Linear.disableDebugOutput();
      LLModel llModel=new LLModel();
      llModel.setBias(model.bias);
      llModel.setNrClass(model.nr_class);
      llModel.setNrFeature(model.nr_feature);
      llModel.setLabels(model.label);
      llModel.setFeatureWeights(model.w);
      llModel.setSolverType(model.solverType.name());
      return llModel;
   }

   public static Model convertFromLLModel(LLModel llModel){
      Linear.disableDebugOutput();
      Model model=new Model();
      model.bias=llModel.getBias();
      model.nr_class=llModel.getNrClass();
      model.nr_feature=llModel.getNrFeature();
      model.solverType=SolverType.valueOf(llModel.getSolverType());
      model.label=llModel.getLabels();
      model.w=llModel.getFeatureWeights();
      return model;
   }

   /**
    * Convert a list of LibLinear Models to a list of the DTO LLModels
    */
   public static List<LLModel> convertToLlModels(List<Model> models) {
      //List<LLModel> dtoModels=new ArrayList<~>();
      List<LLModel> dtoModels=new ArrayList<LLModel>();

      for(Model model: models){
        LLModel dtoModel=convertToLLModel(model);
        dtoModels.add(dtoModel);
      }
      return dtoModels;
   }
}

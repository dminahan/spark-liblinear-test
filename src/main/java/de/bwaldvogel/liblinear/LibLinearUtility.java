package de.bwaldvogel.liblinear;

import dminahan.spark.liblinear.LLModel;

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
}

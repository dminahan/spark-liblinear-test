package dminahan.spark.liblinear;

import java.io.Serializable;
import java.util.Arrays;

public class LLModel implements Serializable {
   //private transient static final Charset LIBLINEAR_MODEL_ENCODING=Charset.forName("UTF-8");

   private double bias=-1;
   private int nrClass=0;
   private int nrFeature=0;

   private int[] labels;
   private double[] featureWeights;

   private String solverType="L2R_LR";

   public double getBias() {
      return this.bias;
   }

   public void setBias(double bias) {
      this.bias=bias;
   }

   public int getNrClass(){
      return this.nrClass;
   }

   public void setNrClass(int nrClass) {
      this.nrClass=nrClass;
   }

   public int getNrFeature(){
      return this.nrFeature;
   }

   public void setNrFeature(int nrFeature) {
      this.nrFeature=nrFeature;
   }

   public int[] getLabels() {
      return this.labels;
   }

   public void setLabels(int[] labels){
	   this.labels= Arrays.copyOf(labels, labels.length);
   }


   public double[] getFeatureWeights() {
      return this.featureWeights;
   }

   public void setFeatureWeights(double[] weights) {
      this.featureWeights=Arrays.copyOf(weights, weights.length);
   }

public String getSolverType() {
	return solverType;
}

public void setSolverType(String solverType) {
	this.solverType = solverType;
}
	
public boolean equals(Object obj) {
	if(this==obj) {
		return true;
	} else if(obj==null){
		return false;
	} else if(this.getClass() != obj.getClass()) {
		return false;
	} else {
		LLModel otherLLModel=(LLModel)obj;
		if((this.getBias()!=otherLLModel.getBias()) || (this.getNrClass()!=otherLLModel.getNrClass()) || 
		   (this.getNrFeature()!=otherLLModel.getNrFeature()) ||(!Arrays.equals(this.getLabels(), otherLLModel.getLabels())) ||
	           (!Arrays.equals(this.getFeatureWeights(),otherLLModel.getFeatureWeights())) || !this.getSolverType().equals(otherLLModel.getSolverType())) {
			   return false;
		   }
		    return true;
	}
}
		    
public String toString() {
	StringBuffer modelBuffer = new StringBuffer();
	modelBuffer.append("Model.toString(), bias=").append(this.getBias());
	modelBuffer.append(", nrClass=").append(this.getNrClass());
	modelBuffer.append(", nrFeature=").append(this.getNrFeature());
	modelBuffer.append(", labels=").append(Arrays.toString(this.getLabels()));
	modelBuffer.append(", featureWeights=").append(Arrays.toString(this.getFeatureWeights()));
	if(this.getSolverType()!=null) {
		modelBuffer.append(", solverType=").append(this.getSolverType());
	} else {
		modelBuffer.append(", solverType is null");
	}
	return modelBuffer.toString();
}	    
   
}

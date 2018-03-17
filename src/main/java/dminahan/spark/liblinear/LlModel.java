package dminahan.spark.liblinear;

import java.io.Serializable;

public class LLModel implements Serializable {

   private double bias=-1;
   private int nrClass=0;
   private int nrFeature=0;

   private double[] labels;
   private double[] featureWeights;`

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

   public double[] getLabels() {
      return this.labels;
   }

   public void setLabels(double[] labels){
      Arrays.copyOf(this.labels, labels);
   }


   public double[] getFeatureWeights() {
      return this.featureWeights;
   }

   public void setFeatureWeights(double[] weights) {
      Arrays.copyOf(this.featureWeights, weights);
   }
}

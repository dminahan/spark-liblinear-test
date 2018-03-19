package dminahan.spark.liblinear;

import java.io.Serializable;

public class UserModel implements Serializable {

   private String name;
   private double timestamp;
   private LLModel model;

   private String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name=name;
   }

   public double getTimestamp(){
      return this.timestamp;
   }

   public void setTimestamp(double timestamp) {
      this.timestamp=timestamp;
   }

   public LLModel getModel() {
      return model;
   }

   public void setModel(LLModel model){
      this.model=model;
   }
}

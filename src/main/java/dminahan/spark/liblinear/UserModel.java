package dminahan.spark.liblinear;

import java.io.Serializable;

public class UserModel implements Serializable {

private static final long serialVersionUID = 1946360051697544747L;
   private String name;
   private double timestamp;
   private LLModel model;

   public String getName() {
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

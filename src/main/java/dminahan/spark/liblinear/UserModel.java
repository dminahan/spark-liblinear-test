package dminahan.spark.liblinear;

import java.io.Serializable;

public class UserModel implements Serializable {

   private String user;
   private double timestamp;
   private LLModel model;

   private String getUser() {
      return this.user;
   }

   public void setUser(String user) {
      this.user=user;
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

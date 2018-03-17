package dminahan.spark.liblinear;

public class ScoringInput {
   private String id;
   private String memberId;
   private double vector;
   private LLModel model;
   private String user;
   private double timestamp;

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id=id;
   }

   public String getMemberId() {
      return this.memberId;
   }

   public void setMemberId(String memberId) {
      this.memberId=memberId;
   }

   public double getVector() {
      return this.vector;
   }

   public void setVector(double vector) {
      this.vector=vector;
   }

   public LLModel getModel() {
      return this.model;
   }

   public void setModel(LLModel model) {
      this.model=model;
   }

   public String getUser(){
      return this.user;
   }

   public void setUser(String user) {
      this.user=user;
   }

   public double getTimestamp() {
      return this.timestamp;
   }

   public void setTimestamp(double timestamp) {
      this.timestamp=timestamp;
   }
}

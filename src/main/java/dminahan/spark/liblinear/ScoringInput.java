package dminahan.spark.liblinear;

public class ScoringInput {
   private String id;
   private String memberId;
   private double vector;

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
}

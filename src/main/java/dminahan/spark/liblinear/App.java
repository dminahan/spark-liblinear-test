package dminahan.spark.liblinear;

import org.apache.spark.sql.SparkSession;

/**
 * Main runnable spark class
 */
public class App {

   public static void main(String[] args) {
      SparkSession spark = SparkSession.builder().appName("LibLinear Test").getOrCreate();
      LLModelMain modelMain=new LLModelMain();
      modelMain.run(spark);
   }
}

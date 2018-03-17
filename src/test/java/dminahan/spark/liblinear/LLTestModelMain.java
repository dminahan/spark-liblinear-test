package dminahan.spark.liblinear;


Public class LLTestModelMainTest extends JavaDatasetSuiteBase implements Serializable {

    public void test() {
          new LLTestModelMain().run(spark());
    }
}

package dminahan.spark.liblinear;

import java.io.Serializable;

import com.holdenkarau.spark.testing.JavaDatasetSuiteBase;

public class LLTestModelMain extends JavaDatasetSuiteBase implements Serializable {

    public void test() {
          new LLModelMain().run(spark());
    }
}

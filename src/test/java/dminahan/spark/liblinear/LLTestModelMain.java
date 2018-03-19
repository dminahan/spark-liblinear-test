package dminahan.spark.liblinear;

import java.io.Serializable;

import org.junit.Test;

import com.holdenkarau.spark.testing.JavaDatasetSuiteBase;

public class LLTestModelMain extends JavaDatasetSuiteBase implements Serializable {

	@Test
    public void test() {
          new LLModelMain().run(spark());
    }
}

package com.bluevortexflare.securevoip;


import lombok.Getter;
import lombok.Setter;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class TestArray {

    @Getter
    @Setter
    private class TestClass {
        String a = null;
        String b = null;
    }

    private class Worker {

        private void work(List<TestClass> list) {
            for (var a : list) {
                a.setA("A");
                a.setB("B");
            }
        }

    }

    @Test
    void testArray() {
        List<TestClass> testArray = new ArrayList<>();
        testArray.add(new TestClass());
        Worker worker = new Worker();
        worker.work(testArray);

        Assertions.assertThat(testArray.get(0)
                                       .getA()
                                       .equals("A")).isTrue();
    }
}

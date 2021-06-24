package org.candyteam.lgw.framework;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Data;
import org.candyteam.lgw.annotation.Test;

/**
 * 一个单元测试类的抽象
 */
@Data
public class TestClass {

    private final Class<?> clazz;
    private final List<Method> methodsForTest = new ArrayList<>();

    public TestClass(Class<?> clazz) {
        this.clazz = clazz;
        scanTestMethods(clazz);
    }

    protected void scanTestMethods(Class<?> clazz) {
        for (Method eachMethod : clazz.getDeclaredMethods()) {
            List<? extends Annotation> allAnnos = Arrays.asList(eachMethod.getAnnotations());
            if (allAnnos.contains(Test.class)) {
                methodsForTest.add(eachMethod);
            }
        }
    }
}


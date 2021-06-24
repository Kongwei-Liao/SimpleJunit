package org.candyteam.lgw.runner.builder;

import org.candyteam.lgw.runner.Runner;

public class SimpleJunitBuilder extends RunnerBuilder {
    @Override
    public Runner runnerForClass(Class<?> testClass) throws Throwable {
        return new SimpleJunit(testClass);
    }
}


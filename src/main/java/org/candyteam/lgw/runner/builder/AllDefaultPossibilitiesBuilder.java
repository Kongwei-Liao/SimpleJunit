package org.candyteam.lgw.runner.builder;

import org.candyteam.lgw.runner.Runner;

import java.util.Arrays;
import java.util.List;

/**
 * 策略模式
 */
public class AllDefaultPossibilitiesBuilder extends RunnerBuilder {

    private final boolean canUseSuiteMethod;

    public AllDefaultPossibilitiesBuilder() {
        canUseSuiteMethod = true;
    }

    @Override
    public Runner runnerForClass(Class<?> testClass) throws Throwable {

        List<RunnerBuilder> builders = Arrays.asList(
                simpleJunitBuilder()
                // XxxxBuilder(), YyyyBuilder(), ZzzzBuilder()
        );

        for (RunnerBuilder each : builders) {
            Runner runner = each.safeRunnerForClass(testClass);
            if (runner != null) {
                return runner;
            }
        }

        return null;
    }



    protected SimpleJunitBuilder simpleJunitBuilder() {
        return new SimpleJunitBuilder();
    }
}

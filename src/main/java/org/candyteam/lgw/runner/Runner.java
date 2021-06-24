package org.candyteam.lgw.runner;

import org.candyteam.lgw.framework.TestClass;
import org.candyteam.lgw.runner.notification.RunNotifier;

import java.util.Objects;

public class Runner {

    private final TestClass testClass;

    public Runner(TestClass testClass) {
        if (Objects.nonNull(testClass)) {
           this.testClass = testClass;
        } else {
            throw new RuntimeException();
        }
        validate();
    }

    // 验证测试目标的正确性
    private void validate() {
//        validatePublicVoidNoArgMethods(BeforeClass.class, true, errors);
//        validatePublicVoidNoArgMethods(AfterClass.class, true, errors);
//        validateClassRules(errors);
//        applyValidators(errors);
    }

    /*
     * (non-Javadoc)
     * @see org.junit.runner.Describable#getDescription()
     */
    public Description getDescription() {
        Class<?> clazz = testClass.getClazz();
        Description description;

        if (clazz == null || !clazz.getName().equals(getName())) {
            description = Description.createSuiteDescription(getName(), getRunnerAnnotations());
        } else {
            description = Description.createSuiteDescription(clazz, getRunnerAnnotations());
        }

        return description;
    }

    public void run(RunNotifier notifier) {

    }

    public int testCount() {
        return getDescription().testCount();
    }
}

package org.candyteam.lgw.runner;

import lombok.SneakyThrows;
import org.candyteam.lgw.framework.TestClass;
import org.candyteam.lgw.framework.TestResult;
import org.candyteam.lgw.runner.notification.RunListener;
import org.candyteam.lgw.runner.notification.RunNotifier;

import static java.lang.Thread.currentThread;

/**
 * 1、从命令行读测试类名，加载测试类
 * 2、扫描@Test注解的测试方法
 */
public class Core {

    private final RunNotifier notifier = new RunNotifier();

    public static void main(String[] args) {
        // CommandLineParseResult.parse(args);
        TestResult result = new Core().runMain(args);
        System.exit(result.wasSuccessful() ? 0 : 1);
    }

    @SneakyThrows
    TestResult runMain(String[] args) {
        // CommandLineParseResult commandLineParseResult = CommandLineParseResult.parse(args);
        TestClass testClass = new TestClass(getClass(args[1], this.getClass()));
        Runner runner = new Runner(testClass);
        return run(runner);
    }

    public TestResult run(Runner runner) {
        TestResult result = new TestResult();

        RunListener listener = result.createListener();
        notifier.addListener(listener);

        try {
            notifier.fireTestRunStarted(runner.getDescription());
            runner.run(notifier);
            notifier.fireTestRunFinished(result);
        } finally {
            removeListener(listener);
        }
        return result;
    }

    public void removeListener(RunListener listener) {
        notifier.removeListener(listener);
    }

    public static Class<?> getClass(String className, Class<?> callingClass) throws ClassNotFoundException {
        ClassLoader classLoader = currentThread().getContextClassLoader();
        return Class.forName(className, true, classLoader == null ? callingClass.getClassLoader() : classLoader);
    }
}

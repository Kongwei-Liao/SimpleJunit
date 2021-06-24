package org.candyteam.lgw.framework;

import org.candyteam.lgw.runner.Description;
import org.candyteam.lgw.runner.notification.RunListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class TestResult {

    private final AtomicLong runTime;
    private final AtomicLong startTime;
    private final CopyOnWriteArrayList<Failure> failures;

    protected List<TestListener> fListeners = new ArrayList();

    public TestResult() {
        this.runTime = new AtomicLong();
        this.startTime = new AtomicLong();
    }

    public RunListener createListener() {
        return new Listener();
    }

    @RunListener.ThreadSafe
    private class Listener extends RunListener {
        @Override
        public void testRunStarted(Description description) throws Exception {
            startTime.set(System.currentTimeMillis());
        }

        @Override
        public void testRunFinished(TestResult result) throws Exception {
            long endTime = System.currentTimeMillis();
            runTime.addAndGet(endTime - startTime.get());
        }
    }


    public boolean wasSuccessful() {
        return getFailureCount() == 0;
    }

    public int getFailureCount() {
        return failures.size();
    }
}

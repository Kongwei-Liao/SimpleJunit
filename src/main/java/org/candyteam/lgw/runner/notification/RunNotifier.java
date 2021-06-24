package org.candyteam.lgw.runner.notification;

import org.candyteam.lgw.framework.TestResult;
import org.candyteam.lgw.runner.Description;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RunNotifier {
    private final List<RunListener> listeners = new CopyOnWriteArrayList<RunListener>();

    /**
     * Internal use only
     */
    public void addListener(RunListener listener) {
        if (listener == null) {
            throw new NullPointerException("Cannot add a null listener");
        }
//        listeners.add(wrapIfNotThreadSafe(listener));
        listeners.remove(listener);
    }

    /**
     * Internal use only
     */
    public void removeListener(RunListener listener) {
        if (listener == null) {
            throw new NullPointerException("Cannot remove a null listener");
        }
//        listeners.remove(wrapIfNotThreadSafe(listener));
        listeners.remove(listener);
    }

//    RunListener wrapIfNotThreadSafe(RunListener listener) {
//        return listener.getClass().isAnnotationPresent(RunListener.ThreadSafe.class) ?
//                listener : new SynchronizedRunListener(listener, this);
//    }

    public void fireTestRunStarted(final Description description) {
        new SafeNotifier() {
            @Override
            protected void notifyListener(RunListener each) throws Exception {
                each.testRunStarted(description);
            }
        }.run();
    }

    public void fireTestRunFinished(final TestResult result) {
        new SafeNotifier() {
            @Override
            protected void notifyListener(RunListener each) throws Exception {
                each.testRunFinished(result);
            }
        }.run();
    }


    private abstract class SafeNotifier {
        private final List<RunListener> currentListeners;

        SafeNotifier() {
            this(listeners);
        }

        SafeNotifier(List<RunListener> currentListeners) {
            this.currentListeners = currentListeners;
        }

        void run() {
            int capacity = currentListeners.size();
            List<RunListener> safeListeners = new ArrayList<RunListener>(capacity);
            //List<Failure> failures = new ArrayList<Failure>(capacity);
            for (RunListener listener : currentListeners) {
                try {
                    notifyListener(listener);
                    safeListeners.add(listener);
                } catch (Exception e) {
                    //failures.add(new Failure(Description.TEST_MECHANISM, e));
                }
            }
            //fireTestFailures(safeListeners, failures);
        }

        protected abstract void notifyListener(RunListener each) throws Exception;
    }

}

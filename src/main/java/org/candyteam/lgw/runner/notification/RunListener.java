package org.candyteam.lgw.runner.notification;

import org.candyteam.lgw.framework.TestResult;
import org.candyteam.lgw.runner.Description;

import java.lang.annotation.*;

public class RunListener {

    public void testRunStarted(Description description) throws Exception {
    }

    public void testRunFinished(TestResult result) throws Exception {
    }

    @Documented
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ThreadSafe {
    }
}

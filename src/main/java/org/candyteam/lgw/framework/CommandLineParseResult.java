package org.candyteam.lgw.framework;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.currentThread;

/**
 * 抽象为处理 Core 中主方法的参数的处理结果
 *
 * 实现阶段性目标：
 *    1、假定主方法的入参全是目标测试类的的类名；—— 6月20日 TODO 1
 */
@Data
public class CommandLineParseResult {

    private final List<Class<?>> targetTestClasses = new ArrayList<Class<?>>();

    CommandLineParseResult() {};

    public static CommandLineParseResult parse(String[] args) {
        CommandLineParseResult result = new CommandLineParseResult();
        result.parseArgs(args);
        return result;
    }

    // TODO：1 原则上主方法中的 args ，并没有完全是Class名这条限定
    private void parseArgs(String[] args) {
        parseParameters(args);
    }

    // TODO：1 这里指的args是 Core中main方法中的测试目标类
    void parseParameters(String[] args) {
        for (String arg : args) {
            try {
                targetTestClasses.add(getClass(arg, CommandLineParseResult.class));
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("Class not found: " + arg + ".", e);
            }
        }
    }

    public static Class<?> getClass(String className, Class<?> callingClass) throws ClassNotFoundException {
        ClassLoader classLoader = currentThread().getContextClassLoader();
        return Class.forName(className, true, classLoader == null ? callingClass.getClassLoader() : classLoader);
    }

}

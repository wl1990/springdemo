package com.test.springdemo.proxy;

import com.test.springdemo.proxy.cglib.CglibProxyTest;
import com.test.springdemo.proxy.jdkproxy.JdkDynamicProxyTest;

import java.util.LinkedHashMap;
import java.util.Map;

public class ProxyPerformanceTest {
    private static void runWithoutMonitor(Target target, int runCount) {
        for (int i = 0; i < runCount; i++) {
            target.test(i);
        }
    }

    private static void runWithMonitor(Target target, int runCount, String tag) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < runCount; i++) {
            target.test(i);
        }
        long end = System.currentTimeMillis();
        System.out.println("[" + tag + "] Total Time:" + (end - start) + "ms");
    }

    private static void runTest(int repeatCount, int runCount, Map<String, Target> tests) {
        System.out.println(
                String.format("\n===== run test : [repeatCount=%s] [runCount=%s] [java.version=%s] =====",
                        repeatCount, runCount, System.getProperty("java.version")));
        for (int i = 0; i < repeatCount; i++) {
            System.out.println(String.format("\n--------- test : [%s] ---------", (i + 1)));
            for (String key : tests.keySet()) {
                runWithMonitor(tests.get(key), runCount, key);
            }
        }
    }

    public static void main(String[] args) {
        Target nativeTest = new TargetImpl();
        Target dynamicProxy = JdkDynamicProxyTest.newProxyInstance(nativeTest);
        Target cglibProxy = CglibProxyTest.newProxyInstance(TargetImpl.class);
        int preRunCount = 10000;
        runWithoutMonitor(nativeTest, preRunCount);
        runWithoutMonitor(cglibProxy, preRunCount);
        runWithoutMonitor(dynamicProxy, preRunCount);

        //执行测试
        Map<String, Target> tests = new LinkedHashMap<String, Target>();
        tests.put("Native   ", nativeTest);
        tests.put("Dynamic  ", dynamicProxy);
        tests.put("Cglib    ", cglibProxy);
        int repeatCount = 3;
        int runCount = 100000;
        runTest(repeatCount, runCount, tests);
        runCount = 500000;
        runTest(repeatCount, runCount, tests);

    }
}

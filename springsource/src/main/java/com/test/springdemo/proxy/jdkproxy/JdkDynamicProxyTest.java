package com.test.springdemo.proxy.jdkproxy;

import com.test.springdemo.proxy.Target;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkDynamicProxyTest implements InvocationHandler {
    private Target target;
    public JdkDynamicProxyTest(Target target){
        this.target=target;
    }

    public static Target newProxyInstance(Target target) {
        return (Target) Proxy.newProxyInstance(JdkDynamicProxyTest.class.getClassLoader(),new Class<?>[]{Target.class}
        ,new JdkDynamicProxyTest(target));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        System.out.println("proxy = [" + proxy.getClass() + "], method = [" + method + "], args = [" + args + "]");
        return method.invoke(target,args);
    }

}

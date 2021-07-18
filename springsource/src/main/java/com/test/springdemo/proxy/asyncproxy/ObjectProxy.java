package com.test.springdemo.proxy.asyncproxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ObjectProxy implements MethodInterceptor {
    private Object target;
    public ObjectProxy(Object target){
        this.target=target;
    }
    public Object instanceProxy(){
        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setClassLoader(target.getClass().getClassLoader());
        enhancer.setCallback(this);
        return enhancer.create();
    }
    @Override
    public Object intercept(Object object, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        AsyncWork asyncWork=new AsyncWork(target,method,objects);
        ProxyThreadPool proxyThreadPool=new ProxyThreadPool();
        proxyThreadPool.addWork(asyncWork);
        return null;
    }
}

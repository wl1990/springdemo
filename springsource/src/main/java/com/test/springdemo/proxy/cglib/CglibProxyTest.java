package com.test.springdemo.proxy.cglib;

import com.test.springdemo.proxy.Target;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxyTest implements MethodInterceptor {

    private CglibProxyTest(){

    }

    public static<T extends Target> Target newProxyInstance(Class<T> targetInstanceClazz){
        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(targetInstanceClazz);
        enhancer.setCallback(new CglibProxyTest());
        return (Target) enhancer.create();
    }


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
//        System.out.println("o = [" + o.getClass() + "], method = [" + method + "], objects = [" + objects + "], methodProxy = [" + methodProxy + "]");
        return methodProxy.invokeSuper(o,objects);
    }


}

package com.test.springdemo.jvm.clazzloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyTest {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        MyClassLoaderParentFirst myClassLoaderParentFirst=new MyClassLoaderParentFirst();
//        Class testAClass=myClassLoaderParentFirst.findClass("com.test.springdemo.jvm.clazzloader.TestA");
        MyClassLoaderParentFirst myClassLoaderParentFirst=new MyClassLoaderParentFirst(Thread.currentThread().getContextClassLoader());
        Class testAClass=myClassLoaderParentFirst.loadClass("com.test.springdemo.jvm.clazzloader.TestA");
        Method mainMethod=testAClass.getDeclaredMethod("main",String[].class);
        mainMethod.invoke(null,new Object[]{args});
    }
}

package com.test.springdemo.jvm.clazzloader;

public class TestB {
    public void hello(){
        System.out.println("testB--"+this.getClass().getClassLoader());
    }
}

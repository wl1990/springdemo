package com.test.springdemo.jvm.clazzloader;

public class TestA {
    public void hello(){
        System.out.println("testA--"+this.getClass().getClassLoader());
        TestB testB=new TestB();
        testB.hello();
    }

    public static void main(String[] args) {
        TestA testA=new TestA();
        testA.hello();
    }
}

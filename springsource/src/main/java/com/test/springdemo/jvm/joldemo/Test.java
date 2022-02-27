package com.test.springdemo.jvm.joldemo;

import org.openjdk.jol.info.ClassLayout;

public class Test {
    public static void main(String[] args) {
        A a=new A();
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
        B b=new B();
        System.out.println(ClassLayout.parseInstance(b).toPrintable());
        C c=new C();
        System.out.println(ClassLayout.parseInstance(c).toPrintable());
        int[] aa=new int[0];
        System.out.println(ClassLayout.parseInstance(aa).toPrintable());
        User user=new User();
        System.out.println(ClassLayout.parseInstance(user).toPrintable());
    }

}

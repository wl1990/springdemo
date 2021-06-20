package com.test.springdemo.jvm.clazzmem;

import org.openjdk.jol.vm.VM;

/**
 * class 内存结构
 */
public class ClazzStruct {
    public static void main(String[] args) {
        System.out.println(VM.current().details());
    }
}

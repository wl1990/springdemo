package com.test.springdemo.jvm.oom;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class HeapOOM {
    public static void main(String[] args) {
        List<HeapOOM> list=new ArrayList<>();
        while(true){
            list.add(new HeapOOM());
        }
    }

}

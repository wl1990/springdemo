package com.test.springdemo.jvm.oom;

public class StackOOM {
    static int index=0;
    public static void test(){
        System.out.println("test="+index++);
        test();
    }
    public static void dontStop(){
        for(;;){
            System.out.println(Thread.currentThread().getName());
        }
    }

    public static void curTest(){
        for(;;){
            new Thread(()->{
                dontStop();
            }).start();
        }
    }

    public static void main(String[] args) {
//        test();
        curTest();
    }
}

package com.test.springdemo.jvm.clazzmem;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

public class User {
    // -XX:BiasedLockingStartupDelay=0 这个参数
    public static void main(String[] args) throws InterruptedException {
//        User user=new User();
//        System.out.println(ClassLayout.parseInstance(user).toPrintable());
//        biasedLockTest();
        TimeUnit.SECONDS.sleep(5);
        lightLockTest();
//        weightLockTest();
    }

    /**
     * 偏向锁
     */
    public static void biasedLockTest(){
        User user=new User();
        synchronized (user){
            System.out.println(ClassLayout.parseInstance(user).toPrintable());
        }
        System.out.println(ClassLayout.parseInstance(user).toPrintable());
        synchronized (user){
            System.out.println(ClassLayout.parseInstance(user).toPrintable());
        }
    }
    /**
     * 轻量级锁
     */
    public static void lightLockTest() throws InterruptedException {
        User user=new User();
        synchronized (user){
            System.out.println("mian--"+ClassLayout.parseInstance(user).toPrintable());
        }
        Thread thread=new Thread(()->{
            synchronized (user) {
                System.out.println("thread--" + ClassLayout.parseInstance(user).toPrintable());
            }
        });
        thread.start();
        thread.join();
        System.out.println("end--"+ClassLayout.parseInstance(user).toPrintable());
    }

    /**
     * 重量级锁
     */
    public static void weightLockTest(){
        User user = new User();
        new Thread(() -> {
            synchronized (user) {
                System.out.println("--THREAD1--:" + ClassLayout.parseInstance(user).toPrintable());
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            synchronized (user) {
                System.out.println("--THREAD2--:" + ClassLayout.parseInstance(user).toPrintable());
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}

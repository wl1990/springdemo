package com.test.springdemo.currentdemo.synchronize;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SynLockTest {
    static SynLockTest synLockTest=new SynLockTest();
    public static void main(String[] args) throws InterruptedException {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                synLockTest.syn();
            }
        });
        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                synLockTest.test();
                synLockTest.syn1();
            }
        });
        Thread thread2=new Thread(new Runnable() {
            @Override
            public void run() {
                synLockTest.syn2();
                synLockTest.syn4();
            }
        });
        thread.start();
        Thread.sleep(10);
        thread1.start();
        Thread.sleep(10);
        thread2.start();

    }
    public synchronized void syn(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("class synchronized");
    }

    public synchronized void syn1(){
        System.out.println("other class synchronized");
    }
    public  void syn2(){
        synchronized (SynLockTest.class){
            System.out.println("SynLockTest.class");
        }
    }

    public  static synchronized void syn3(){
        System.out.println("static synchronized method");
    }

    public void syn4(){
        synchronized (synLockTest){
            System.out.println("syn synLockTest instance");
        }
    }

    public void test(){
        System.out.println("no synchronized");
    }

    public static synchronized void staticSyn(){
        System.out.println("class synchronized");
    }
    private static Lock lock=new ReentrantLock();
    public void lockTest(){
        lock.lock();
        try{
            System.out.println("lock syn");
        }finally{
            lock.unlock();
        }
    }
}

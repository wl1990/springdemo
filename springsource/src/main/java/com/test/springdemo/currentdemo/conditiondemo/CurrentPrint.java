package com.test.springdemo.currentdemo.conditiondemo;

import sun.misc.Unsafe;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CurrentPrint {
    private Lock lock=new ReentrantLock();
    private Condition proCon=lock.newCondition();
    private Condition cliCon=lock.newCondition();
    private int simple=0;
    private static Object object=new Object();

    public synchronized  void synPrpducer() throws InterruptedException {
        while(true){
            while(simple>=1){
                wait();
            }
            simple++;
            System.out.println("synchronized produce");
            notifyAll();
        }
    }
    public synchronized void synConsumer() throws InterruptedException {
        while(true){
            while(simple<1){
                wait();
            }
            simple--;
            System.out.println("synchronized consumer");
            notifyAll();
        }
    }


    public void singleConditionProduce() throws InterruptedException {
        lock.lock();
        try {
            while (true) {
                while (simple >= 1) {
                    System.out.println("produce await");
                    proCon.await();
                }
                simple++;
                System.out.println("single produce");
                proCon.signal();

            }
        }finally{
            lock.unlock();
        }
    }

    public void singleConConsumer() throws InterruptedException {
        lock.lock();
        try{
            while(true){
                while(simple<1){
                    System.out.println("consumer await");
                    proCon.await();
                }
                simple--;
                System.out.println("single consumer");
                proCon.signal();
            }
        }finally{
            lock.unlock();
        }
    }
    public void product() throws InterruptedException {
        lock.lock();
        try {
            while (true) {
                System.out.println("start produce");
                if (simple >= 1) {
                    proCon.await();
                }
                simple++;
                Thread.sleep(100);
                System.out.println("product");
                cliCon.signal();
            }
        }finally{
            lock.unlock();
        }
    }

    public void consumer() throws InterruptedException {
        lock.lock();
        try {
            while (true) {
                System.out.println("start consumer");
                if (simple <= 0) {
                    cliCon.await();
                }
                simple--;
                System.out.println("consumer");
                proCon.signal();
            }
        }finally{
            lock.unlock();
        }
    }





}

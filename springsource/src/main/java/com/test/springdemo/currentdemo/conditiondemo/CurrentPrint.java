package com.test.springdemo.currentdemo.conditiondemo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CurrentPrint {
    private Lock lock=new ReentrantLock();
    private Condition proCon=lock.newCondition();
    private Condition cliCon=lock.newCondition();

    private int simple=0;

    public void product() throws InterruptedException {
        lock.lock();
        try {
            while (true) {
                if (simple >= 1) {
                    proCon.await();
                }
                simple++;
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

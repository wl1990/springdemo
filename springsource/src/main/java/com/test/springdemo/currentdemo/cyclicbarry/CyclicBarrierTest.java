package com.test.springdemo.currentdemo.cyclicbarry;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
    static int n=5;
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier=new CyclicBarrier(n, new Runnable() {
            @Override
            public void run() {
                System.out.println("all get it");
            }
        });
        for(int i=0;i<n;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for(int i=0;i<3;i++) {
                            Thread.sleep(1000);
                            System.out.println(Thread.currentThread().getName() + " the point");
                            cyclicBarrier.await();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}

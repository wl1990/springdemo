package com.test.springdemo.currentdemo.synch;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

public class SynchronizedDemo {
    ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("demo-pool-%d").build();
    ExecutorService singleThreadPool = new ThreadPoolExecutor(5, 10,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    private static SynchronizedDemo synchronizedDemo=new SynchronizedDemo();
    public synchronized static void  staticSynTest() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        System.out.println("--static synchronized---");
    }
    public synchronized static void  staticSynTest1() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        System.out.println("--static synchronized 1---");
    }

    public static void synclazz() throws InterruptedException {
        synchronized (SynchronizedDemo.class){
            TimeUnit.SECONDS.sleep(5);
            System.out.println("-***-static synclazz ---");
        }
    }

    public  static void synInstance() throws InterruptedException {
        synchronized (synchronizedDemo){
            TimeUnit.SECONDS.sleep(5);
            System.out.println("-<<<-static instance ---");
        }
    }

    public synchronized  void synTest() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        System.out.println("----syntest----");
    }


    public static SynchronizedDemo instance(){
        if(synchronizedDemo==null){
            synchronized (synchronizedDemo){
                if(synchronizedDemo==null){
                    synchronizedDemo=new SynchronizedDemo();
                }
            }
        }
        return synchronizedDemo;
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedDemo synDemo=new SynchronizedDemo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SynchronizedDemo.staticSynTest();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SynchronizedDemo.staticSynTest1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synDemo.synTest();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SynchronizedDemo.synclazz();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SynchronizedDemo.synInstance();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        TimeUnit.SECONDS.sleep(20);
        System.out.println("main end");
    }

}

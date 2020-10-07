package com.test.springdemo.currentdemo.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantRWDemo {
    private static ReadWriteLock readWriteLock=new ReentrantReadWriteLock(false);
    private static final Lock readLock=readWriteLock.readLock();
    private static final Lock writeLock=readWriteLock.writeLock();
    private static List<String> list=new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        deadLock();
    }
    public static void deadLock(){
        readLock.lock();
        System.out.printf("read lock");
        writeLock.lock();
        System.out.println("block thread");

    }
    public static void mulwr() throws InterruptedException {
        new Thread(()->{
            try {
                write();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        TimeUnit.SECONDS.sleep(4);
        for(int i=0;i<4;i++){
            new Thread(()->{
                try {
                    read();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        TimeUnit.SECONDS.sleep(30);
    }
    public static void writeRead(){
        readLock.lock();
        try{
            if(list.size()<=0){
                readLock.unlock();
                writeLock.lock();
                try{
                    list.add("write data");
                }finally {
                    writeLock.unlock();
                }
                System.out.println("write end");
                readLock.lock();
            }
        }finally {
            readLock.unlock();
        }
        System.out.println("read end");
    }
    public static void write() throws InterruptedException {
        writeLock.lock();
        try{
            list.add("write data");
            System.out.printf(String.format("thread=%s write data",Thread.currentThread().getName()));
            TimeUnit.SECONDS.sleep(1);
        }finally{
            writeLock.unlock();
        }
    }

    public static void read() throws InterruptedException {
        readLock.lock();
        try{
            for(String s:list){
                System.out.println(String.format("thread=%s read data",Thread.currentThread().getName()));
            }
            TimeUnit.SECONDS.sleep(5);
        }finally{
            readLock.unlock();
        }
    }

}

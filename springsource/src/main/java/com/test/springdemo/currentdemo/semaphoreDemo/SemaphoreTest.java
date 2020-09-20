package com.test.springdemo.currentdemo.semaphoreDemo;

import java.util.concurrent.*;

public class SemaphoreTest {
  /*  ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("demo-pool-%d").build();
    ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    singleThreadPool.execute(()-> System.out.println(Thread.currentThread().getName()));
    singleThreadPool.shutdown();*/
    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore=new Semaphore(5);
        for(int i=0;i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        System.out.println(Thread.currentThread().getName()+" start task");
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName()+" task step2");
                        semaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }).start();

        }
    }
}

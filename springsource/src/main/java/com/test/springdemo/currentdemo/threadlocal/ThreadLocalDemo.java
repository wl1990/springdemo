package com.test.springdemo.currentdemo.threadlocal;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.Random;
import java.util.concurrent.*;

public class ThreadLocalDemo {
    ThreadFactory sThreadFactory=new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build();
    ScheduledExecutorService executorService=new ScheduledThreadPoolExecutor(1,
              sThreadFactory);

//    ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
//            .setNameFormat("demo-pool-%d").build();
//
//    //Common Thread Pool
//    ExecutorService pool = new ThreadPoolExecutor(5, 200,
//            0L, TimeUnit.MILLISECONDS,
//            new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    ThreadFactory namedThreadFactory=new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
    ExecutorService pool=new ThreadPoolExecutor(5,20,0L,TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024),namedThreadFactory,new ThreadPoolExecutor.AbortPolicy());
    private ExecutorService executors= Executors.newCachedThreadPool();

    public  void test() throws InterruptedException {
        pool.execute(()->{
            try{
            MyThreadLocal.assign("122","123");
            TimeUnit.SECONDS.sleep(new Random().nextInt(5));
            System.out.println(String.format("thread name=%s,user=%s",Thread.currentThread().getName(),MyThreadLocal.getUserId()));
        }catch(Exception e){
                System.out.println("exception="+e);
        }
        });
        pool.execute(()->{
            try{
                MyThreadLocal.assign("123","123");
                TimeUnit.SECONDS.sleep(new Random().nextInt(5));
                System.out.println(String.format("thread name=%s,user=%s",Thread.currentThread().getName(),MyThreadLocal.getUserId()));
            }catch(Exception e){
                System.out.println("exception="+e);
            }     });
        pool.execute(()->{
            try{
                MyThreadLocal.assign("124","123");
                TimeUnit.SECONDS.sleep(new Random().nextInt(5));
                System.out.println(String.format("thread name=%s,user=%s",Thread.currentThread().getName(),MyThreadLocal.getUserId()));
            }catch(Exception e){
                System.out.println("exception="+e);
            }   });
        pool.execute(()->{
            try{
                MyThreadLocal.assign("125","123");
                TimeUnit.SECONDS.sleep(new Random().nextInt(5));
                System.out.println(String.format("thread name=%s,user=%s",Thread.currentThread().getName(),MyThreadLocal.getUserId()));
            }catch(Exception e){
                System.out.println("exception="+e);
            }
        });
            TimeUnit.SECONDS.sleep(10);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadLocalDemo localDemo=new ThreadLocalDemo();
        localDemo.test();
    }

}

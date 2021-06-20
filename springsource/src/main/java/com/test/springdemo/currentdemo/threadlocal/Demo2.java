package com.test.springdemo.currentdemo.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Demo2 {
    public static ExecutorService threadPool = Executors.newFixedThreadPool(16);
    public  ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = new ThreadLocal();

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 100; i++) {
            int finalI = i;
            threadPool.submit(() -> {
                String data = new Demo2().date(finalI);
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                }catch (Exception e){

                }
                System.out.println(data+"]="+Thread.currentThread());
            });
        }
        threadPool.shutdown();
    }

    private String date(int seconds){
        Date date = new Date(1000 * seconds);
//        SimpleDateFormat dateFormat = ThreadSafeFormater.dateFormatThreadLocal.get();
        SimpleDateFormat dateFormat = new ThreadSafeFormater().getThreadLocal().get();
        return dateFormat.format(date)+"]"+dateFormat.hashCode();
    }
}
class ThreadSafeFormater{
//    public  static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("mm:ss"));
    public ThreadLocal<SimpleDateFormat> getThreadLocal(){
        return ThreadLocal.withInitial(() -> new SimpleDateFormat("mm:ss"));
    }
}

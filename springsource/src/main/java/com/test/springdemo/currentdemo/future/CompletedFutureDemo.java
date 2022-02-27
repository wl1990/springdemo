package com.test.springdemo.currentdemo.future;

import com.sun.xml.internal.ws.util.CompletedFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompletedFutureDemo {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service= Executors.newSingleThreadExecutor();
        CompletableFuture future=CompletableFuture.supplyAsync(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("supplyAsync " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello ";
        },service).thenAccept(s->{
            System.out.println("--result-"+s+" world");
        });
        System.out.println(Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(4);
        System.out.println("---end---");
    }

}

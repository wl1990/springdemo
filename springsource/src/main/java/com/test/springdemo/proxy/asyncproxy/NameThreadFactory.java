package com.test.springdemo.proxy.asyncproxy;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NameThreadFactory implements ThreadFactory {
    private final AtomicInteger atomicInteger=new AtomicInteger(1);

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r,"thread-"+atomicInteger.getAndIncrement());
    }
}

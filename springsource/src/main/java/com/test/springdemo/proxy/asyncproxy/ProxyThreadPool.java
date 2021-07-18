package com.test.springdemo.proxy.asyncproxy;

import java.util.PriorityQueue;
import java.util.concurrent.*;

public class ProxyThreadPool {
    private static BlockingQueue<Runnable> priorityQueue;
    private static ExecutorService executorService=null;
    static{
        init();
    }
    private static void init(){
        priorityQueue=new PriorityBlockingQueue<>(10000);
        executorService=new ThreadPoolExecutor(2, 2, 0, TimeUnit.SECONDS, priorityQueue
                ,new NameThreadFactory(),new RejectedExecutionHandler(){

            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                return ;
            }
        });
    }
    public void addWork(AsyncWork asyncWork){
        System.out.println("asyncWork = " + asyncWork);
        ProxyThread proxyThread=new ProxyThread(asyncWork);
        executorService.execute(proxyThread);
    }
}

package com.test.springdemo.currentdemo.future;

import java.util.Random;
import java.util.concurrent.*;

public class FutureDemo {
    private class Task implements Callable<String> {

        @Override
        public String call() throws Exception {
            Random rand = new Random();

            TimeUnit.SECONDS.sleep(rand.nextInt(5));

            return Thread.currentThread().getName();
        }
    }

    /**
     * 会阻塞main 线程
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void fdemo() throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService executorService= Executors.newCachedThreadPool();
        Long a=System.currentTimeMillis();
        for(int i=0;i<5;i++) {
            Callable<String> task=new Task();
            FutureTask futureTask = new FutureTask(task);
            executorService.submit(futureTask);
            System.out.println("result start --time="+(System.currentTimeMillis()-a));
            String result= (String) futureTask.get(8,TimeUnit.SECONDS);
            System.out.println("result="+result+"--time="+(System.currentTimeMillis()-a));
        }
    }

    /**
     * 不会阻塞主线程
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void myfdemo() throws ExecutionException, InterruptedException {
        ExecutorService executorService= Executors.newCachedThreadPool();
        for(int i=0;i<5;i++) {
            Callable<String> task=new Task();
            MyFutureTask myFutureTask = new MyFutureTask(task);
            executorService.submit(myFutureTask);
        }
    }

    private class MyFutureTask extends FutureTask<String>{

        public MyFutureTask(Callable<String> callable) {
            super(callable);
        }

        @Override
        protected void done() {
            try {
                System.out.println(get()+" 执行完成！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        FutureDemo demo=new FutureDemo();
        demo.fdemo();
//        demo.myfdemo();
        System.out.println("---main--");
        TimeUnit.SECONDS.sleep(10);
    }



}

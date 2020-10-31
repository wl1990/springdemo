package com.test.springdemo.throwabledemo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExceptionDemo {
    private static ExecutorService service = Executors.newCachedThreadPool();
    public static void exceptionDemo(){
        List<String> ips=new ArrayList<>();
        ips.add("1");
        ips.add("2");
        ips.add("3");
        List<Future<Boolean>> lists = new ArrayList<>();
        for (int i=0;i<3;i++) {
            Future<Boolean> fb = service.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return noticeNginx();
                }
            });
            lists.add(fb);
        }
        for(Future<Boolean> future:lists){
            try {
                boolean result=future.get(3, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }

        }

    }

    private static Boolean noticeNginx() {
        boolean result=nginxOpRequest();
        if(result){
            return true;
        }
        System.out.println("notice nginx");
        return false;
    }

    private static boolean nginxOpRequest() {
        for (int i = 1; i <= 3; i++) {
            try {
                System.out.println("nginx i="+i);
                throw new FileNotFoundException("");
            } catch (IOException e) {
                System.out.println("exception "+e.toString());
            } finally {
                System.out.println("print finally");
            }
        }
        return false;
    }
}

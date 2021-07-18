package com.test.springdemo.proxy.asyncproxy;

import java.lang.reflect.InvocationTargetException;

public class ProxyThread implements Runnable,Comparable<ProxyThread>{
    private AsyncWork asyncWork;
    public ProxyThread(AsyncWork asyncWork){
        this.asyncWork=asyncWork;
    }
    @Override
    public void run() {
        try {
            asyncWork.callback();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int compareTo(ProxyThread o) {
        return 0;
    }
}

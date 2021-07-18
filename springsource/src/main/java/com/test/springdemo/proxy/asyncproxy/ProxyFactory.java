package com.test.springdemo.proxy.asyncproxy;

public class ProxyFactory {
    public Object getProxy(Object object){
        ObjectProxy objectProxy=new ObjectProxy(object);
        return objectProxy.instanceProxy();
    }
}

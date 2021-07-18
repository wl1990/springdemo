package com.test.springdemo.proxy.asyncproxy;

public class DemeTest {
    public static void main(String[] args) {
        ProxyFactory proxyFactory=new ProxyFactory();
        DemeTest demeTest= (DemeTest) proxyFactory.getProxy(new DemeTest());
        demeTest.test();
    }
    public void test(){
        System.out.println("1234");
    }
}

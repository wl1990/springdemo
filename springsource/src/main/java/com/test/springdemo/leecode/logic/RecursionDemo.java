package com.test.springdemo.leecode.logic;

/**
 * 递归优化
 */
public class RecursionDemo {
    public static int function(int m,int n){
        if(n==0){
            return 1;
        }
        return function(m,n-1)*m;
    }

    public static int function1(int m,int n){
        if(n==0){
            return 1;
        }
        int t=function1(m,n/2);
        if((n&1)==1){
            return t*t*m;
        }else{
            return t*t;
        }
    }
}

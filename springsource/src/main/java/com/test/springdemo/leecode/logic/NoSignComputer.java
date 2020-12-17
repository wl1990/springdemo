package com.test.springdemo.leecode.logic;

public class NoSignComputer {
    // 不用+的加法
    public static int computer(int a,int b){
        int c=a;
        int d=b;
        // 有进位
        while((a&b)!=0){
            c=a^b;
            d=(a&b)<<1;
            a=c;
            b=d;
        }
        return a|b;
    }

    public static void main(String[] args) {
        int result=computer(123456789,123456789);
        System.out.println(result);
    }
}

package com.test.springdemo.leecode.tree;

public enum Scanner {
    ADD("add"),;
    private String code;
    Scanner(String code){
        this.code=code;
    }

    public static void main(String[] args) {
        int a=2;
        int b=3;
        a=a^b;
        b=a^b;
        a=a^b;
        System.out.printf("a="+a+" b="+b);
    }
}

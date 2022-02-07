package com.test.springdemo.leecode.logic;

import java.util.ArrayList;
import java.util.List;

public class RingBufferDemo<T> {
    private int start=0;
    private int end=0;
    private int size=0;
    private int cap=10;
    private Object[] ring=new Object[cap];

    public RingBufferDemo(){};

    public  void write(T e) throws Exception {
        if(size>=cap){
            throw new Exception("异常");
        }
        if(end>=cap){
            end=0;
        }
        ring[end++]=e;
        size++;
    }

    public T read() throws Exception{
        if(size<=0){
            size=0;
            return null;
        }
        if(start>=cap){
            start=0;
        }
        size--;
        return (T) ring[start++];

    }

    public int getSize() {
        return size;
    }

    public static void main(String[] args) throws Exception {
        RingBufferDemo<String> bufferDemo=new RingBufferDemo<>();
        bufferDemo.write("1");
        bufferDemo.write("2");
        bufferDemo.write("3");
        bufferDemo.write("4");
        bufferDemo.write("5");
        System.out.println("size="+bufferDemo.getSize());
        int size=bufferDemo.getSize();
        for(int i=0;i<size;i++){
            System.out.println("---"+bufferDemo.read());
        }

    }
}

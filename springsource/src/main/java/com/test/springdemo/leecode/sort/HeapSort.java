package com.test.springdemo.leecode.sort;

import java.util.ArrayList;

/**
 * 堆排序
 * @param <E>
 */
public class HeapSort<E extends Object> {

    private static int[] heap=new int[16];
    static int size=0;

    private  static void maxtopHeap(int value){
        heap[size++]=value;
        int parent=(size-2)/2;
        int child=size-1;
        while(parent>=0){
            if(heap[parent]<heap[child]){
                heap[parent]=heap[parent]^heap[child];
                heap[child]=heap[parent]^heap[child];
                heap[parent]=heap[parent]^heap[child];
                child=parent;
                parent=(parent-1)/2;
            }else{
                break;
            }
        }
    }

    public static void printHead(){
        for(int i=0;i<heap.length;i++){
            System.out.println("--"+heap[i]);
        }
    }


    public static void main(String[] args) {
        maxtopHeap(5);
        maxtopHeap(10);
        maxtopHeap(2);
        maxtopHeap(21);
        maxtopHeap(3);
        printHead();

    }



}

package com.test.springdemo.leecode.sort;

import java.util.ArrayList;

public class HeapSort<E extends Object> {

    private int[] heap=new int[16];
    int size=0;

    private void maxtopHeap(int value){
        heap[size++]=value;
        int parent=(size-2)/2;
        int child=size-1;
        while(parent>0){
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




}

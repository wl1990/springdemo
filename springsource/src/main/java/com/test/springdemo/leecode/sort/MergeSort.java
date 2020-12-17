package com.test.springdemo.leecode.sort;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Random;

/**
 * 归并排序
 */
public class MergeSort {

    public static void merge(int[] queue,int start,int end){
        if(end<=start){
            return;
        }
        int mid=(start+end)/2;
        merge(queue,start,mid);
        merge(queue,mid+1,end);
        sort(queue,start,mid,end);


    }

    private static void sort(int[] queue, int start, int mid, int end) {
        if(start==end){
            return;
        }
        int[] mqueue=new int[end-start+1];
        if(queue[mid+1]>queue[mid]){
            return;
        }
        int size=0;
        int firstindex=start;
        int secondindex=mid+1;
        while(firstindex<=mid && secondindex<=end){
            while(firstindex<=mid && queue[firstindex]<queue[secondindex]){
                mqueue[size++]=queue[firstindex];
                firstindex++;
            }
            while(secondindex<=end && queue[secondindex]<queue[firstindex]){
                mqueue[size++]=queue[secondindex];
                secondindex++;
            }
        }
        while(firstindex<=mid){
            mqueue[size++]=queue[firstindex++];
        }
        while(secondindex<=end){
            mqueue[size++]=queue[secondindex++];
        }
        for(int i=0;i<size;i++){
            queue[start+i]=mqueue[i];
        }
    }

    public static void main(String[] args) {
        int[] queue=new int[16];
        for(int i=0;i<queue.length;i++){
            queue[i]=i+new Random().nextInt(100);
            System.out.println(queue[i]);
        }
        System.out.println("-------------------");
        merge(queue,0,queue.length-1);
        for(int i=0;i<queue.length;i++){
            System.out.println(queue[i]);
        }
    }
}

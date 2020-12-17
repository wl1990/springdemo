package com.test.springdemo.leecode.sort;

/**
 * 快排
 */
public class QuickSort {
    public static void quicksort(int[] source,int left,int right){
        if(source==null || left>=right){
            return;
        }
        int temp=source[left];
        int head=left;
        int tail=right;
        while(head<tail){
            while(head<tail && source[tail]>temp){
                tail--;
            }
            if(head<tail){
                source[head]=source[tail];
            }
            while(head<tail && source[head]<=temp){
                head++;
            }
            if(head<tail){
                source[tail]=source[head];
            }
        }
        source[head]=temp;
        quicksort(source,left,head-1);
        quicksort(source,head+1,right);
    }

    public static void main(String[] args) {
        int[] source=new int[]{1,34,5,66,21,3,2,33};
        quicksort(source,0,source.length-1);
        for (int i=0;i<source.length;i++){
            System.out.println(source[i]);
        }
    }

}

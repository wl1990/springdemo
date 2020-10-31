package com.test.springdemo.leecode.sort;

/**
 * 冒泡排序
 */
public class BubbleSort {
    public static void bubbleSort(int[] values){
        for(int i=0;i<values.length-1;i++){
            int temp=0;
            for(int j=0;j<values.length-1-i;j++){
                if(values[j]>values[j+1]){
                    temp++;
                    values[j]=values[j]^values[j+1];
                    values[j+1]=values[j]^values[j+1];
                    values[j]=values[j]^values[j+1];
                }
            }
            if(temp==0){
                System.out.println("i="+i);
                break;
            }
        }
    }

    public static void main(String[] args) {
        int[] v=new int[]{1,3,4,5,6,22};
        bubbleSort(v);
        for(int i=0;i<v.length;i++){
            System.out.println(""+v[i]);
        }
    }

}

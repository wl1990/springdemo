package com.test.springdemo.leecode.sort;

/**
 * 选择排序
 */
public class SelectSort {
    public static void select_sort(int[] values){
        for(int i=0;i<values.length-1;i++){
            int temp=i;
            for(int j=i+1;j<values.length;j++){
                if(values[temp]>values[j]){
                    temp=j;
                }
            }
            if(temp!=i){
                values[i]=values[i]^values[temp];
                values[temp]=values[i]^values[temp];
                values[i]=values[i]^values[temp];
            }
        }
    }

    public static void main(String[] args) {
        int[] v=new int[]{44,33,4,5,6,22};
        select_sort(v);
        for(int i=0;i<v.length;i++){
            System.out.println(""+v[i]);
        }
    }
}

package com.test.springdemo.leecode.sort;

/**
 * 插入排序
 */
public class InsertSort {
    public static void insertSort(int[] values){
        for(int i=1;i<values.length;i++){
            int temp=i;
            int tempValue=values[i];
            for(int j=i;j>0;j--){
                if(tempValue<values[j-1]){
                    values[j]=values[j-1];
                    temp=j-1;
                }
            }
            if(temp!=i){
                values[temp]=tempValue;
            }
        }
    }

    public static void main(String[] args) {
        int[] v=new int[]{13,3,41,5,66,22};
        insertSort(v);
        for(int i=0;i<v.length;i++){
            System.out.println(""+v[i]);
        }
    }

}

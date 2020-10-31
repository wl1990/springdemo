package com.test.springdemo.leecode.sort;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ShellSort {
    public static void shellSort(int[] values){
        for(int step=values.length/2;step>0;step=step/2){
            for(int i=step;i<values.length;i+=step){
                int temp=i;
                int tempValue=values[i];
                for(int j=i;j>=step;j-=step){
                    if(tempValue<values[j-step]){
                        values[j]=values[j-step];
                        temp=j-step;
                    }
                }
                if(temp!=i){
                    values[temp]=tempValue;
                }
            }
        }
    }

    public static void main(String[] args) {
        Set<String> set=new HashSet<>();
        set.add(null);
        if(set.size()==1){
            for(String s:set){
                if(s==null){
                    System.out.println("is null");
                }
            }
        }
       /* int[] v=new int[]{13,3,41,5,66,22};
        shellSort(v);
        for(int i=0;i<v.length;i++){
            System.out.println(""+v[i]);
        }*/
    }
}

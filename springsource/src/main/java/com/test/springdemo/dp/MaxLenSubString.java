package com.test.springdemo.dp;

import java.util.Arrays;

public class MaxLenSubString {
    // 循环
    public static String maxlen(String maxString,String minString){
        String max=maxString.length()>minString.length()? maxString:minString;
        String min=max.equals(maxString)?minString:maxString;
        if(max.contains(min)){
            return min;
        }
        for(int i=0;i<min.length()-1;i++){
            for(int j=min.length();j>i;j--){
                String subStr=min.substring(i,j);
                if(max.contains(subStr)){
                    return subStr;
                }

            }
        }
        return "null";
    }

    // LCS 算法
    public static String lcsSubString(String maxString,String minString){
        String max=maxString.length()>minString.length()? maxString:minString;
        String min=max.equals(maxString)?minString:maxString;
        if(max.contains(min)){
            return min;
        }
        int rowlen=min.length();
        int collen=max.length();
        int[][] cover=new int[rowlen][collen];
        for(int i=0;i<rowlen;i++){
            for(int j=0;j<collen;j++){
                if(min.charAt(i)==max.charAt(j)){
                    int prerow=i==0?0:i-1;
                    int precol=j==0?0:j-1;
                    cover[i][j]=cover[prerow][precol]+1;
                }
            }
        }
        for(int i=0;i<rowlen;i++) {
            for (int j = 0; j < collen; j++) {
                System.out.print(cover[i][j]+" ");

            }
            System.out.println("\n");
        }
        int temprow=-1;
        int tempcol=-1;
        int maxsub=0;
        for(int i=0;i<rowlen;i++) {
            for (int j = 0; j < collen; j++) {
                if(cover[i][j]>maxsub){
                    maxsub=cover[i][j];
                    temprow=i;
                    tempcol=j;
                }
            }
        }

        if(maxsub>0){
            return min.substring(temprow-maxsub+1,temprow+1);
        }else{
            return "null";
        }



    }

    public static void main(String[] args) {
        String m="1234ghkdfgyui56";
        String n="asdaghkdfgyui";
//        System.out.println(maxlen(m,n));
        System.out.println(lcsSubString(m,n));
    }

}

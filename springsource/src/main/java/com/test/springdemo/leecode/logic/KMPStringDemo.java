package com.test.springdemo.leecode.logic;

import org.springframework.util.StringUtils;

public class KMPStringDemo {
    public static int indexof(String source,String pattern){
        int index=-1;
        if(StringUtils.isEmpty(source) || StringUtils.isEmpty(pattern)){
            return index;
        }
        int slength=source.length();
        int s=0;
        int patternlength=pattern.length();
        int p=0;
        while(s<slength && p<patternlength){
            if(source.charAt(s)==pattern.charAt(p)){
                s++;
                p++;
            }else{
                s=s-p+1;
                p=0;
            }
        }
        if(p==patternlength){
            index=s-p;
        }
        return index;
    }
    private static int[] getnnext(String pattern){
        int[] next=new int[pattern.length()];
        next[0]=-1;
        next[1]=0;
        int max=0;
        for(int i=2;i<pattern.length()-1;i++){
            if(pattern.charAt(max)==pattern.charAt(i-1)){
                max=max+1;
            }
            next[i]=max;
        }
        return next;
    }

    public static int kmpIndexof(String source,String pattern){
        int index=-1;
        if(StringUtils.isEmpty(source) || StringUtils.isEmpty(pattern)){
            return index;
        }
        int[] indexs=getnnext(pattern);
        int slength=source.length();
        int s=0;
        int patternlength=pattern.length();
        int p=0;
        while(s<slength && p<patternlength){
            if(p==-1|| source.charAt(s)==pattern.charAt(p)){
                s++;
                p++;
            }else{
                p=indexs[p];
            }
        }
        if(p==patternlength){
            index=s-p;
        }
        return index;
    }



    public static void main(String[] args) {
        String s="ababababca";
        String p="abababca";
        System.out.println(kmpIndexof(s,p));

    }
}

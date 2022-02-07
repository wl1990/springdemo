package com.test.springdemo.dp;


import org.apache.commons.lang3.StringUtils;

public class StringSameLength {
    public static int maxLen(String s1,String s2){
        if(StringUtils.isEmpty(s1) || StringUtils.isEmpty(s2)){
            return 0;
        }
        int max=0;
        int[][] dp=new int[s1.length()+1][s2.length()+1];
        for(int i=1;i<=s1.length();i++){
            for(int j=1;j<=s2.length();j++){
                if(s1.charAt(i-1)==s2.charAt(j-1)){
                    dp[i][j]=dp[i-1][j-1]+1;
                }else{
                    dp[i][j]=0;
                }
                max=Math.max(max,dp[i][j]);
            }
        }
        printdp(dp);
        return max;
    }

    public static int maxLenSecond(String s1,String s2){
        if(StringUtils.isEmpty(s1) || StringUtils.isEmpty(s2)){
            return 0;
        }
        int max=0;
        int[] dp=new int[s2.length()+1];
        for(int i=1;i<=s1.length();i++){
            for(int j=s2.length();j>0;j--){
                if(s1.charAt(i-1)==s2.charAt(j-1)){
                    dp[j]=dp[j-1]+1;
                }else{
                    dp[j]=0;
                }
                max=Math.max(max,dp[j]);
            }
        }
        return max;
    }



    private static void printdp(int[][] dp) {
        for(int i=0;i<dp.length;i++){
            for(int j=0;j<dp[i].length;j++){
                System.out.print(dp[i][j]+" ");
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) {
//        System.out.println("args = [" + maxLen("eplm","people") + "]");
        System.out.println("args = [" + maxLenSecond("eplm","people") + "]");
    }
}

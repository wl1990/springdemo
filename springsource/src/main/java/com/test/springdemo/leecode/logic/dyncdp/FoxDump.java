package com.test.springdemo.leecode.logic.dyncdp;

public class FoxDump {
    private static int[] paths=new int[]{2,3,1,1,4};

    public static void main(String[] args) {
        System.out.println(candump());
    }

    public static boolean dpDump(){
        int range=paths[0];
        for(int i=1;i<paths.length;i++){
            if(i-1+paths[i-1]>=i){
                range=Math.max(i,range);
            }
            if(range>=(paths.length-1)){
                return true;
            }
        }

        return false;

    }

    public static boolean candump(){
        Long a=System.currentTimeMillis();
        boolean result= dfs(paths.length-1);
        System.out.println(System.currentTimeMillis()-a);
        return result;

    }
    public static boolean dfs(int endpoint){
        if(endpoint==0){
            return true;
        }
        for(int from=endpoint-1;from>=0;from--){
            if(paths[from]>=(endpoint-from)){
                if(dfs(from)){
                    return true;
                }
            }
        }
        return false;
    }
}

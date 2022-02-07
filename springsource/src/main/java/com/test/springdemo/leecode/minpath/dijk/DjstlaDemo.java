package com.test.springdemo.leecode.minpath.dijk;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DjstlaDemo {
    private static int[][] grap=new int[][]{
            { 0,   1,   4,  -1,  -1,  -1},
            {1,   0,   2,   7,    5,  -1},
            {4,   2,   0,  -1,    1,  -1},
            {-1,  7,  -1,   0,    3,    2},
            {-1,  5,    1,   3,   0,    6},
            {-1, -1,  -1,   2,   6,    0}};
    private static  Map<String,Integer> nodePath=new HashMap<>();
    private static Set<Integer> nodeSet=new HashSet<>();
    static {
        nodeSet.add(new Integer(0));
        nodeSet.add(new Integer(1));
        nodeSet.add(new Integer(2));
        nodeSet.add(new Integer(3));
        nodeSet.add(new Integer(4));
        nodeSet.add(new Integer(5));
    }

    private static int finEnd=5;

    public static void calu(int end,int path){
        String key=null;
        int tempend=end;
        int tenpMinPath=100000;
        Set<Integer> temp=new HashSet<>();
        for(int i=0;i<grap.length;i++){
            if(nodeSet.contains(new Integer(i)) && grap[i][end]>0){
                int newpath=path+grap[i][end];
                key=i+"_"+finEnd;
                if(newpath<tenpMinPath){
                    tenpMinPath=newpath;
                    tempend=i;
                }
                temp.add(new Integer(i));
                if(!nodePath.containsKey(key)){
                    nodePath.put(key,newpath);
                }else{
                    if(newpath<nodePath.get(key)){
                        nodePath.put(key,newpath);
                    }
                }

            }
        }
        System.out.println("tempend="+tempend);
        nodeSet.remove(new Integer(tempend));
        for(Integer tend:temp){
            calu(tend,nodePath.get(key));
        }
    }

    public static void main(String[] args) {
        System.out.println("==========");
        calu(5,0);
        nodePath.forEach((k,v)->{
            System.out.println(k+":"+v);
        });
        System.out.println("-----");
    }
}

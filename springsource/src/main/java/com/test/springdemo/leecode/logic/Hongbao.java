package com.test.springdemo.leecode.logic;

import java.util.*;

/**
 * 红包算法
 */
public class Hongbao {
    private static Set<Integer> pointSet=new TreeSet<>();
    /**
     * 线段切割
     */
    public static List<Integer> splitLine(int price,int num) throws Exception {
        if(price<1 || num<1){
            throw new Exception("price <1");
        }
        Random random=new Random();
        while(pointSet.size()<num-1){
            pointSet.add(random.nextInt(price-1));
        }
        pointSet.add(price);
        List<Integer> packets=new ArrayList<>();
        int pre=0;
        for(int point:pointSet){
            packets.add(point-pre);
            pre=point;
        }
        return packets;
    }
}

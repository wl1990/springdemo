package com.test.springdemo.bitmap;

import java.util.LinkedList;
import java.util.List;

public class BitMapListUtil {
    private static final int offset_bit=28;
    private static final int max_size=1<<28;
    private static final Long max_ip=IPUtil.ipToLong("255.255.255.255");
    public static void main(String[] args) {

    }
    public synchronized static List<byte[]> createListByte(){
        List<byte[]> byteList = new LinkedList<>();
        int size = getListSize() + 1;
        for (int i = 0; i < size; i++) {
            byte[] tempByte = BitMapUtil.create(max_size);
            byteList.add(tempByte);
        }
        return byteList;
    }

    public static void delete(List<byte[]>byteList, long num){
        int index= getListIndex(num);
        int tempNum=getNewNum(num,index);
        byte[] tempByte=byteList.get(index);
        BitMapUtil.clear(tempByte,tempNum);
    }
    public static void add(List<byte[]>byteList, long num){
        int index= getListIndex(num);
        int tempNum=getNewNum(num,index);
        byte[] tempByte=byteList.get(index);
        BitMapUtil.add(tempByte,tempNum);
    }
    public static int getNewNum(long num,int index){
        int tempNum= (int) (num-index*max_size);
        return tempNum;
    }
    public static int getListIndex(long num){
        int index= (int) (num>>offset_bit);
        return index;
    }
    public static boolean contain(List<byte[]>byteList,long num){
        int index= getListIndex(num);
        int tempNum= getNewNum(num,index);
        byte[] tempByte=byteList.get(index);
        return BitMapUtil.contains(tempByte,tempNum);
    }


    public static int getListSize(){
        int size= (int) (max_ip>>offset_bit);
        return size;
    }
}

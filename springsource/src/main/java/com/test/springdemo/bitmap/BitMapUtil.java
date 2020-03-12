package com.test.springdemo.bitmap;


import java.util.LinkedList;
import java.util.List;


public class BitMapUtil {
    public static void main(String[] args) {
        Long ipl=IPUtil.ipToLong("255.255.255.255");
        System.out.println("ipl = [" + ipl + "]");
        int maxint=1<<28;
        System.out.println("maxint = [" + maxint + "]");
        maxint=maxint>>3;
        System.out.println("maxint = [" + maxint + "]");
        maxint=maxint>>10;
        maxint=maxint>>10;
        System.out.println("maxint = [" + maxint + "]");
       /* byte[] b1=create(maxint);
        add(b1,9);
        System.out.println("args = [" + contains(b1,9) + "]");*/
       List<byte[]> list=new LinkedList<>();

    }

    public static byte[] create(int n){
        byte[] bits = new byte[getIndex(n) + 1];
        return bits;
    }

    public static void add(byte[] bits, int num){
        bits[getIndex(num)] |= 1 << getPosition(num);
    }

    public static boolean contains(byte[] bits, int num){
        return (bits[getIndex(num)] & 1 << getPosition(num)) != 0;
    }

    public static int getIndex(int num){
        return num >> 3;
    }
    public static int getPosition(int num){
        return num & 0x07;
    }

    public static void clear(byte[] bits, int num){
        bits[getIndex(num)] &= ~(1 << getPosition(num));
    }

    public static void showByte(byte b){
        byte[] array = new byte[8];
        for(int i = 7; i >= 0; i--){
            array[i] = (byte)(b & 1);
            b = (byte)(b >> 1);
        }

        for (byte b1 : array) {
            System.out.print(b1);
            System.out.print(" ");
        }

        System.out.println();
    }
}

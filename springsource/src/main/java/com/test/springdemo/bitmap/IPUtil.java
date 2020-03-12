package com.test.springdemo.bitmap;

public class IPUtil {

    public static long ipToLong(String strIp) {
        long[] ip = new long[4];
        int position1 = strIp.indexOf(".");
        int position2 = strIp.indexOf(".", position1 + 1);
        int position3 = strIp.indexOf(".", position2 + 1);
        ip[0] = Long.parseLong(strIp.substring(0, position1));
        ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
        ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
        ip[3] = Long.parseLong(strIp.substring(position3 + 1));
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    public static String longToIP(long longIp) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(longIp >>> 24));
        sb.append(".");
        sb.append(String.valueOf((longIp & 16777215L) >>> 16));
        sb.append(".");
        sb.append(String.valueOf((longIp & 65535L) >>> 8));
        sb.append(".");
        sb.append(String.valueOf(longIp & 255L));
        return sb.toString();
    }
}

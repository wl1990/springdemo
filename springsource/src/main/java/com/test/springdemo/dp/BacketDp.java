package com.test.springdemo.dp;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BacketDp {
    public static class ProjectValue{
        private int cap;
        private int value;
        public ProjectValue(int cap,int value){
            this.cap=cap;
            this.value=value;
        }

        public int getCap() {
            return cap;
        }

        public void setCap(int cap) {
            this.cap = cap;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    /**
     * 各背包无限
     * @param cap
     * @param list
     * @return
     */
    public static int calValue(int cap,List<ProjectValue> list){
        int[] pValue=new int[cap+1];
        Arrays.fill(pValue,0);
        for(int i=0;i<pValue.length;i++){
            for(ProjectValue projectValue:list){
                int tempCap=projectValue.getCap();
                if(i-tempCap<0){
                    continue;
                }
                int tempValue=projectValue.getValue();
                int lastIndex=i-tempCap;
                int lastMaxValue=0;
                for(int j=lastIndex;j>0;j--){
                    if(pValue[j]>0){
                        lastMaxValue=pValue[j];
                        break;
                    }
                }
                pValue[i]=Math.max(pValue[i],tempValue+lastMaxValue);
            }
        }
        return pValue[cap];
    }

    public static void main(String[] args) {
        List<ProjectValue> list=new ArrayList<>();
        for(int i=1;i<11;i++){
            ProjectValue projectValue=new ProjectValue(i+10,i*10);
            list.add(projectValue);
        }
        System.out.println("list = [" + JSON.toJSONString(list) + "]");
        System.out.println("max value = [" + calValue(100,list) + "]");
    }

}

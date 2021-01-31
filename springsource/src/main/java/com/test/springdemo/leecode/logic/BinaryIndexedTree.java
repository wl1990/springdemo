package com.test.springdemo.leecode.logic;

public class BinaryIndexedTree {
    private int[] bitArr;
    public BinaryIndexedTree(int[] list){
        bitArr=new int[list.length+1];
        for(int i=0;i<list.length;i++){
            bitArr[i+1]=list[i];
        }
        for(int i=1;i<bitArr.length;i++){
            int j=i+(i&(-i));
            if(j<bitArr.length){
                bitArr[j]+=bitArr[i];
            }
        }
    }

    public void update(int index,int delta){
        index=index+1;
        while(index<bitArr.length){
            this.bitArr[index]+=delta;
            index=index+(index&(-index));
        }
    }

    public int prefixSum(int idx){
        idx+=1;
        int result=0;
        while(idx>0){
            result+=this.bitArr[idx];
            idx=idx-(idx&(-idx));
        }
        return result;
    }

    public int rangeSum(int from_idx, int to_idx) {
        return prefixSum(to_idx) - prefixSum(from_idx - 1);
    }

    public static void main(String[] args) {

    }
}

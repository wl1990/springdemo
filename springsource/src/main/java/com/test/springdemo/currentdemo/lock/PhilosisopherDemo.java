package com.test.springdemo.currentdemo.lock;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PhilosisopherDemo {
    static int chopstic_num=5;
    private static Object lock=new Object();
    private static Chopstic[] chopstics=new Chopstic[chopstic_num];
    private static AtomicInteger eater=new AtomicInteger(0);
    private static Philosisopher[] philosisophers=new Philosisopher[chopstic_num];

    private static Lock reentrantLock=new ReentrantLock();

    /**
     * 奇偶位哲学家拿筷子顺序不同
     * @throws Exception
     */
    public void noOrderGetChopstics() throws Exception {
        while(eater.get()==chopstic_num){
            TimeUnit.SECONDS.sleep(1);
        }
        eater.getAndAdd(1);
        int temp=-1;
        temp=getFreephilosisopher();
        if(temp==-1){
            throw new Exception("------exception all eat---");
        }
        philosisophers[temp].setState(0,1);
        System.out.println(Thread.currentThread().getName()+"--哲学家 "+temp+" 开始拿筷子 ");
        int left=temp;
        int right=left==chopstic_num-1 ? 0:left+1;
        if((left&1)==0){
            left=left^right;
            right=left^right;
            left=left^right;
        }
        getchopandeat(temp,left,right);
    }

    /**
     * 限制哲学家用餐人数
     * @throws Exception
     */
    public  void limitNum() throws Exception {
        while (eater.get() == chopstic_num - 1) {
            TimeUnit.SECONDS.sleep(1);
        }
        eater.getAndAdd(1);
        int temp=-1;
        temp=getFreephilosisopher();
        if(temp==-1){
            throw new Exception("------exception all eat---");
        }
        philosisophers[temp].setState(0,1);
        System.out.println(Thread.currentThread().getName()+"--哲学家 "+temp+" 开始拿筷子 "+philosisophers[temp].getState().get());
        int left=temp;
        int right=left==chopstic_num-1 ? 0:left+1;
        getchopandeat(temp,left,right);


    }

    private void getchopandeat(int phinum,int left, int right) throws InterruptedException {
        synchronized (chopstics[left]){
            System.out.println(Thread.currentThread().getName()+" 哲学家 "+phinum+" 拿到第一根筷子 ="+left);
            TimeUnit.MILLISECONDS.sleep(10);
            synchronized (chopstics[right]){
                System.out.println(Thread.currentThread().getName()+" 哲学家 "+phinum+" 拿到第二根筷子 ="+right);
                TimeUnit.MILLISECONDS.sleep(2000);
                System.out.println(Thread.currentThread().getName()+" 哲学家 "+phinum+" 吃完东西了");
                philosisophers[phinum].setState(1,0);
                eater.getAndAdd(-1);
            }
        }
    }

    private int getFreephilosisopher() {
        for(int i=0;i<philosisophers.length;i++){
            if(philosisophers[i].getState().get()==1){
                continue;
            }
            return i;
        }
        return -1;
    }

    public static void main(String[] args) throws Exception {
        PhilosisopherDemo philosisopherDemo=new PhilosisopherDemo();
        philosisopherDemo.inti();
        for(int i=0;i<chopstic_num;i++){
            new Thread(()->{
                try {

                    for(int j=0;j<200;j++) {
//                        philosisopherDemo.limitNum();
                        philosisopherDemo.noOrderGetChopstics();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            System.out.println("----start thread---");
        }
        TimeUnit.SECONDS.sleep(1000);
    }

    private void inti() {
        for(int i=0;i<chopstic_num;i++){
            chopstics[i]=new Chopstic();
            philosisophers[i]=new Philosisopher();
        }
    }

    private class Philosisopher{
        private AtomicInteger state=new AtomicInteger(0);

        public AtomicInteger getState() {
            return state;
        }

        public void setState(int expect,int state) {
            this.state.compareAndSet(expect,state);
        }
    }
    private class Chopstic{
        private int index=0;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}

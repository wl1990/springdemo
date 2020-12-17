package com.test.springdemo.currentdemo.atomic;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferDemo {
    public final static AtomicReference<Student> atomicStudent=new AtomicReference<>();

    public static void main(String[] args) {
        final Student student=new Student("a",1);
        final Student student1=new Student("b",2);
        atomicStudent.set(student);
        for(int i=0;i<10;i++){
            new Thread(()->{
                try {
                    TimeUnit.MILLISECONDS.sleep(new Random().nextInt(30));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(atomicStudent.compareAndSet(student,student1)){
                    System.out.println(Thread.currentThread().getId()+"change value");
                    System.out.println(atomicStudent.get().getName()+":"+atomicStudent.get().getAge());
                }else{
                    System.out.println(Thread.currentThread().getId() + "Failed");
                }


            }).start();
        }
    }
}

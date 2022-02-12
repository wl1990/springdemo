package com.test.springdemo.currentdemo.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeDemo {

    public static void main(String[] args) {
        Node node=new Node();
        boolean flag=node.casNext(null,new Node());
        System.out.println(flag);
    }

    private static class Node{
        volatile Node next;

        boolean casNext(Node cmp,Node val){
            return UNSAFE.compareAndSwapObject(this,nextOffset,cmp,val);
        }
        private static final sun.misc.Unsafe UNSAFE;
        private static final long nextOffset;
        static{
            try {
                UNSAFE = getUnsafe();
                Class<?> k = Node.class;
                nextOffset = UNSAFE.objectFieldOffset(k.getDeclaredField("next"));
            }catch (Exception e){
                throw new Error(e);
            }
        }

        public static Unsafe getUnsafe(){
            try{
                Field f=Unsafe.class.getDeclaredField("theUnsafe");
                f.setAccessible(true);
                return (Unsafe) f.get(null);
            }catch(Exception e){
                return null;
            }

        }
    }


}

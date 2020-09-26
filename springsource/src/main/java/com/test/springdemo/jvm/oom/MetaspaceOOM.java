package com.test.springdemo.jvm.oom;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;

public class MetaspaceOOM extends ClassLoader{
    public static List<Class<?>> createClass() throws InterruptedException {
        List<Class<?>> classes=new ArrayList<>();
        for(int i=0;i<100*10000;i++){
            Thread.sleep(1);
            ClassWriter cw=new ClassWriter(0);
            cw.visit(Opcodes.V1_1,Opcodes.ACC_PUBLIC,"Class"+i,null,"java/lang/Object",null);
            MethodVisitor mw=cw.visitMethod(Opcodes.ACC_PUBLIC,"<init>","()V",null,null);
            mw.visitVarInsn(Opcodes.ALOAD,0);
            mw.visitMethodInsn(Opcodes.INVOKESPECIAL,"java/lang/Object","<init>","()V");
            mw.visitInsn(Opcodes.RETURN);
            mw.visitMaxs(1,1);
            mw.visitEnd();
            MetaspaceOOM test=new MetaspaceOOM();
            byte[] code=cw.toByteArray();
            Class<?> exampleClass=test.defineClass("Class"+i,code,0,code.length);
            classes.add(exampleClass);
        }
        return classes;
    }
    private static String str="test";
    public static void main(String[] args) throws InterruptedException {
        createClass();
        List<String> list=new ArrayList<>();
//        while(true){
//            String str2=str+str;
//            str=str2;
//            str.intern();
//        }
    }
}

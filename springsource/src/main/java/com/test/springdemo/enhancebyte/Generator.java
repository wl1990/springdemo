package com.test.springdemo.enhancebyte;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Generator {
    public static void main(String[] args) throws IOException {
        ClassReader classReader=new ClassReader("com.test.springdemo.enhancebyte.Base");
        ClassWriter classWriter=new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassVisitor classVisitor=new MyClassVisitor(classWriter);
        classReader.accept(classVisitor,ClassReader.SKIP_DEBUG);
        byte[] data=classWriter.toByteArray();
        File f=new File("D:\\workspace\\springdemo\\springsource\\target\\classes\\com\\test\\springdemo\\enhancebyte\\Base.class");
        FileOutputStream fout=new FileOutputStream(f);
        fout.write(data);
        fout.close();
        System.out.println("now generator cc success!!!!!");
    }
}

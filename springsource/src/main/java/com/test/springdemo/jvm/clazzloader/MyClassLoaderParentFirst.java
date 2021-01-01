package com.test.springdemo.jvm.clazzloader;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MyClassLoaderParentFirst extends ClassLoader{

    private ClassLoader jdkClassLoader;

    private Map<String,String> classPathMap=new HashMap<>();
    public MyClassLoaderParentFirst(){
        classPathMap.put("com.test.springdemo.jvm.clazzloader.TestA","D:\\workspace\\springdemo\\springsource\\target\\classes\\com\\test\\springdemo\\jvm\\clazzLoader\\TestA.class");
        classPathMap.put("com.test.springdemo.jvm.clazzloader.TestB","D:\\workspace\\springdemo\\springsource\\target\\classes\\com\\test\\springdemo\\jvm\\clazzLoader\\TestB.class");
    }
    public MyClassLoaderParentFirst(ClassLoader jdkClassLoader){
        this.jdkClassLoader=jdkClassLoader;
        classPathMap.put("com.test.springdemo.jvm.clazzloader.TestA","D:\\workspace\\springdemo\\springsource\\target\\classes\\com\\test\\springdemo\\jvm\\clazzLoader\\TestA.class");
        classPathMap.put("com.test.springdemo.jvm.clazzloader.TestB","D:\\workspace\\springdemo\\springsource\\target\\classes\\com\\test\\springdemo\\jvm\\clazzLoader\\TestB.class");
    }

    @Override
    protected Class<?> loadClass(String name,boolean resolve) throws ClassNotFoundException{
        Class result=null;
        try{
            result=jdkClassLoader.loadClass(name);
        }catch (Exception e){

        }
        if(result!=null){
            return result;
        }
        String classPath=classPathMap.get(name);
        File file=new File(classPath);
        if(!file.exists()){
            throw new ClassNotFoundException();
        }
        byte[] classBytes=getClassBytes(file);
        if(classBytes==null || classBytes.length==0){
            throw new ClassNotFoundException();
        }
        return defineClass(null,classBytes,0,classBytes.length);
    }

//    @Override
//    public Class<?> findClass(String name) throws ClassNotFoundException{
//        String classPath=classPathMap.get(name);
//        File file=new File(classPath);
//        if(!file.exists()){
//            throw new ClassNotFoundException();
//        }
//        byte[] classBytes=getClassBytes(file);
//        if(classBytes==null || classBytes.length==0){
//            throw new ClassNotFoundException();
//        }
//        return defineClass(null,classBytes,0,classBytes.length);
//    }

    private byte[] getClassBytes(File file) {
        try(InputStream ins=new FileInputStream(file);
        ByteArrayOutputStream baos=new ByteArrayOutputStream()){
            byte[] bytes=new byte[4096];
            int byteNumRead=0;
            while((byteNumRead=ins.read(bytes))!=-1){
                baos.write(bytes,0,byteNumRead);
            }
            return baos.toByteArray();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return new byte[]{};
    }


}

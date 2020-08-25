package com.test.springdemo.ThreadStackTrace;

public class StackTracedemo {
    public static void demo(){
        StackTraceElement[] strackTraceElements=Thread.currentThread().getStackTrace();
        int len=strackTraceElements.length;
        for(int i=0;i<len;i++){
            StackTraceElement stackTraceElement=strackTraceElements[i];
            String className=stackTraceElement.getClassName();
            String methodName=stackTraceElement.getMethodName();
            String fileName=stackTraceElement.getFileName();
            int lineNumber=stackTraceElement.getLineNumber();
            System.out.println("fileName="+fileName+
                    ",className="+className+",methodName="+methodName+",lineNumber="+lineNumber);
        }

    }

    public static void main(String[] args) {
        demo();
    }
}

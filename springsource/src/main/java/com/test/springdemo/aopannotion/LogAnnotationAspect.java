package com.test.springdemo.aopannotion;

import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import sun.management.MethodInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
@ComponentScan(basePackages = "com.test.springdemo.aopannotion")
@Component
@Aspect
public class LogAnnotationAspect {
    @Pointcut("execution(* *(..))")
    public void point(){


    }
/*   @Before("point()")
    public void before(){
       System.out.println("before");
   }*/

   @After(value="point() && @annotation(logAnnotation)",argNames = "joinPoint,logAnnotation")
    public void after(JoinPoint joinPoint,LogAnnotation logAnnotation) throws ClassNotFoundException, NotFoundException {
       Object[] args = joinPoint.getArgs();
       String  classType = joinPoint.getTarget().getClass().getName();
       Class<?> clazz = Class.forName(classType);
       String clazzName = clazz.getName();
       System.out.println("targetName = [" + clazzName + "]");
       String methodName = joinPoint.getSignature().getName();
       //获取参数名称和值
       Map<String,Object > nameAndArgs = this.getFieldsName(this.getClass(), clazzName, methodName,args);
       if(nameAndArgs == null || nameAndArgs.size()<=0){
           return ;
       }
       for(Map.Entry<String,Object> entry: nameAndArgs.entrySet()){
           Object object = entry.getValue();
           if(isPrimite(object.getClass())){
               System.out.println("key = [" + entry.getKey() + "], value = [" + object + "]");
           }else{
               System.out.println("key = [" + entry.getKey() + "], not simple value = [" + object + "]");
           }
           if(object.getClass().isAssignableFrom(HttpServletRequest.class)
                   || object.getClass().isAssignableFrom(HttpServletResponse.class)){
               System.out.println("key = [" + entry.getKey() + "], value = [" + object + "]");
           }
       }

       System.out.println("after");
   }

    private Map<String,Object> getFieldsName(Class<? extends LogAnnotationAspect> aClass, String clazzName, String methodName, Object[] args) throws NotFoundException {
        Map<String,Object > map=new HashMap<String,Object>();
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(aClass);
        pool.insertClassPath(classPath);

        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        javassist.bytecode.MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            return null;
        }
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < cm.getParameterTypes().length; i++){
            //paramNames即参数名
            map.put( attr.variableName(i + pos),args[i]);
        }
        return map;
    }

    private boolean isPrimite(Class<?> clazz){
        return clazz.isPrimitive() || clazz == String.class;
    }
}

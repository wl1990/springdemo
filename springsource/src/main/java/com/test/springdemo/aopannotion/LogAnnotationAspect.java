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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import sun.management.MethodInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
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
       // 获取注解
       MethodSignature signature = (MethodSignature) joinPoint.getSignature();
       Method method = signature.getMethod();
       Annotation[][] methodAnnotations = method.getParameterAnnotations();
       for(int i=0;i<methodAnnotations.length;i++){
           Annotation[] paramAnnotion = methodAnnotations[i];
           for(int j = 0; j < paramAnnotion.length; j++){
               if(paramAnnotion[j] instanceof TargetType){
                   System.out.println("i = "+i+" j="+j+ "param annotion="+ paramAnnotion[j]);
                   System.out.println(" param value="+ args[i]);
               }
           }
       }
     /*  ServletWebRequest servletContainer = (ServletWebRequest)RequestContextHolder.getRequestAttributes();
       HttpServletRequest request = servletContainer.getRequest();*/
       HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
       System.out.println("ip1 = [" + getIpAddress(request) + "], contextpath = [" + request.getServletPath() + "]"+"url="+request.getRequestURL());
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

    public static String getIpAddress(HttpServletRequest request) {
                String ip = request.getHeader("x-forwarded-for");
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                        ip = request.getHeader("Proxy-Client-IP");
                    }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                         ip = request.getHeader("WL-Proxy-Client-IP");
                    }
              if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                         ip = request.getHeader("HTTP_CLIENT_IP");
                     }
                 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                     }
                 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                         ip = request.getRemoteAddr();
                     }
                 return ip;
             }
}

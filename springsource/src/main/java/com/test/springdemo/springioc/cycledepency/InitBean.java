package com.test.springdemo.springioc.cycledepency;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class InitBean {
    private static Map<String, Object> cacheMap = new HashMap<>(2);
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class[] clazz={A.class,B.class};
        for(Class c:clazz){
            getBean(c);
        }
        System.out.println(getBean(B.class).getA() == getBean(A.class));
        System.out.println(getBean(A.class).getB() == getBean(B.class));
    }

    private static <T> T getBean(Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String beanName=clazz.getSimpleName().toLowerCase();
        if(cacheMap.containsKey(beanName)){
            return (T) cacheMap.get(beanName);
        }
        Object object=clazz.getDeclaredConstructor().newInstance();
        cacheMap.put(beanName,object);
        Field[] fields=object.getClass().getDeclaredFields();
        for(Field f:fields){
            f.setAccessible(true);
            Class<?> fClass=f.getType();
            String fname=fClass.getSimpleName().toLowerCase();
            f.set(object,cacheMap.containsKey(fname) ? cacheMap.get(fname):getBean(fClass));
        }
        return (T) object;
    }

}

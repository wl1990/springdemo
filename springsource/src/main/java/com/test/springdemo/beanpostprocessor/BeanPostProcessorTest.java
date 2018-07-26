package com.test.springdemo.beanpostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.lang.reflect.Field;

public class BeanPostProcessorTest implements BeanPostProcessor,BeanFactoryAware {
    private ConfigurableListableBeanFactory beanFactory;
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String s) throws BeansException {
        try {
            return autowiredImplement(bean);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;

    }

    private Object autowiredImplement(Object bean) throws IllegalAccessException {
        for(Field field : bean.getClass().getDeclaredFields()){
            Autowired value = field.getAnnotation(Autowired.class);
            if(null!=value){
                Object obj = beanFactory.getBean(field.getType());
                field.setAccessible(true);
                field.set(bean,obj);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String s) throws BeansException {
        return bean;
    }
}

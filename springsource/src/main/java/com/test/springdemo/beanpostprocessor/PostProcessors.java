package com.test.springdemo.beanpostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import java.util.Iterator;

/**
 * BeanFactoryPostProcessor demo
 */
public class PostProcessors implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        // 获取容器的注册接口
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) configurableListableBeanFactory;
        // beandefinition
        GenericBeanDefinition definition = new GenericBeanDefinition();
        // 设置要添加的类
        definition.setBeanClass(Pc.class);
        registry.registerBeanDefinition("postBean",definition);
        Iterator<String> iterable = configurableListableBeanFactory.getBeanNamesIterator();
        while (iterable.hasNext()){
            String name = iterable.next();
            Object obj = configurableListableBeanFactory.getBean(name);
            System.out.println("configurableListableBeanFactory = [" + obj + "]");
        }

    }
}

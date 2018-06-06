package com.test.springdemo.beanfactorypostprocessor;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by 王大雷 on 2018/6/5.
 */
public class PropertyConfigurerDemo {
    public static void main(String[] args) {
        ConfigurableListableBeanFactory bf=new XmlBeanFactory(new ClassPathResource("BeanFactory.xml"));
        BeanFactoryPostProcessor bfpp= (BeanFactoryPostProcessor) bf.getBean("bfpp");
        bfpp.postProcessBeanFactory(bf);
        System.out.println(bf.getBean("simpleBean"));
    }
}

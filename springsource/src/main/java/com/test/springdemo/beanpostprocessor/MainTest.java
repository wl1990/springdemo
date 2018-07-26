package com.test.springdemo.beanpostprocessor;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("BeanPostProcess.xml");
        Pc pc = context.getBean(Pc.class);
        System.out.println(pc);
    }
}

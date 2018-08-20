package com.test.springdemo.aopannotion;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class MainTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(LogAnnotationAspect.class);
        LogController logController = annotationConfigApplicationContext.getBean(LogController.class);
        Map<String,String> map = new HashMap<>();
        map.put("code1","code1");
        map.put("code2","code2");
        logController.getLog("wl","info",map);
    }
}

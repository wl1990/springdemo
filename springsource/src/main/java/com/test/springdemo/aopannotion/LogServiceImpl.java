package com.test.springdemo.aopannotion;

import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl {
    public void getInfo(){
        System.out.println("--service info--");
    }
}

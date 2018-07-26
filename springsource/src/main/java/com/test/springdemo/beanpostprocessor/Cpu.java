package com.test.springdemo.beanpostprocessor;

import org.springframework.beans.factory.annotation.Autowired;

public class Cpu {
    private String brand;
    @Autowired
    private Pc belong;

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }
}

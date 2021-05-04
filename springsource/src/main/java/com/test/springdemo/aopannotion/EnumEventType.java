package com.test.springdemo.aopannotion;

public enum EnumEventType {
    account_operator(1,"账号操作"),
    resume_operator(2,"简历操作"),
    other(20,"其他");


    private Integer code;
    private String name;

    EnumEventType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static EnumEventType getEnumByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (EnumEventType type : EnumEventType.values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        EnumEventType enumEventType=EnumEventType.resume_operator;
        switch(enumEventType){
            case account_operator:
                System.out.println("-----");
            case resume_operator:
                System.out.println("--****--");
            default:
                System.out.println("--&&&&&&--");
        }
    }

}

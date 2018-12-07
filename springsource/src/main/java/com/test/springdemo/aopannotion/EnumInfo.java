package com.test.springdemo.aopannotion;

public enum EnumInfo {
    Positive(0, "正向"),
    Negative(1, "负向");

    private Integer code;
    private String name;

    EnumInfo(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static EnumInfo getEnumByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (EnumInfo type : EnumInfo.values()) {
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

}

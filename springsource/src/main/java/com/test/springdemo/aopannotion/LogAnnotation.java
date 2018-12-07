package com.test.springdemo.aopannotion;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    String[] values() default {};
    EnumInfo name() default EnumInfo.Positive;
    EnumEventType eventType() default EnumEventType.other;


}

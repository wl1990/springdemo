package com.test.springdemo.aopannotion;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetType {
    String value() default "";

}

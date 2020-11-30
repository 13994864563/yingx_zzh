package com.baizhi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)     //只在方法上生效
@Retention(RetentionPolicy.RUNTIME)   //运行时生效
public @interface AddLog {

    String value() default "";

}

package com.dra.annotation;


import java.lang.annotation.*;

/**
 * 本注解可以标记方法，类
 * 主要负责标记公开的请求
 * @author com.dra
 * @version 1.0.0
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Public {
}

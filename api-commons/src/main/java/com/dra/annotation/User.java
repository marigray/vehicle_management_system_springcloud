package com.dra.annotation;

import java.lang.annotation.*;

/**
 * 普通用户可以访问的接口
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface User {
}

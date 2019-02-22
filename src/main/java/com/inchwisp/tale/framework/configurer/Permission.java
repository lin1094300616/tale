package com.inchwisp.tale.framework.configurer;

import java.lang.annotation.*;

/**
 * @Author MSI
 * @Description 自定义注解，用于判断权限
        * @Date 2019/1/7 14:07
        * @Param
 * @return
         **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Permission {
    String value();
}

package com.shaun.commons.annotation;

import java.lang.annotation.*;

/**
 * @author luotao
 * @Description
 * @Date Created on 2017/11/21.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)//生命周期运行时保存
@Target({ElementType.METHOD})//注解只用于方法
public @interface BussinessLog {
    String module() default "";//模块名

    String methoed() default "";//操作方法
}

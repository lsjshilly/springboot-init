package com.lsj.usercenter.aop;



import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Operator {

    /**
     * 操作名称
     * @return 操作名称
     */
    String value() default "";


    /***
     * 是否记录操作日志
     * @return 是否及记录日志
     */
    boolean auditEnabled() default true;

    /**
     * 任意角色符合
     * @return 【】
     */
    String[] anyRoles() default {};

    /***
     * 必须包含角色
     * @return 【】
     */
    String[] mustRoles() default{};


}

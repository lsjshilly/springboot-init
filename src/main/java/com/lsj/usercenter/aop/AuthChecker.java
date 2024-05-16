package com.lsj.usercenter.aop;



import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthChecker {


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

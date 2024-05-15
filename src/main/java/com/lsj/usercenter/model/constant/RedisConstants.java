package com.lsj.usercenter.model.constant;

public class RedisConstants {

    /**
     * 登录验证码前缀
     */
    public static final String LOGIN_USER_CODE_PREFIX = "login:code:";
    /**
     * 验证码过期时间
     */
    public static final long LOGIN_USER_CODETTL = 2L;
    /**
     * 登录token前缀
     */
    public static final String LOGIN_USER_TOKEN_PREFIX = "login:token:";
    /**
     * 登录token过期时间
     */
    public static final long LOGIN_USER_TOKEN_TTL = 30L;
}

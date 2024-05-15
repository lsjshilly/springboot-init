package com.lsj.usercenter.utils;

public class RegexPatterns {

    /**
     * 手机号正则
     */
    public static final String PHONE_REGEX = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$";


    /**
     * 邮箱正则
     */
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    /**
     * 用户名正则 4-32长度，支持中英文、数字、下划线、中划线、@、.
     */
    public static final String USERNAME_REGEX = "^[\\u4e00-\\u9fa5a-zA-Z0-9_-]{4,32}$";


    /**
     * 密码正侧 8-32位 支持中应为、数字、下划线、中划线及特殊符号@.#$%&*!
     */
    public static final String PASSWORD_REGEX = "^[\\u4e00-\\u9fa5a-zA-Z0-9_@.#$%&*!-]{8,32}$";

}

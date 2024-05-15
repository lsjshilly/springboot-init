package com.lsj.usercenter.utils;

import org.apache.commons.lang3.StringUtils;

public class RegexUtils {


    /**
     * 检查提供的电话号码是否不符合预定义的电话号码模式。
     *
     * @param phone 需要验证的电话号码。
     * @return 如果电话号码与预定义的电话号码模式不匹配，则返回true；否则返回false。
     */
    public static boolean invalidphone(String phone) {
        // 使用预定义的电话号码正则表达式检查电话号码是否匹配
        return mismatch(RegexPatterns.PHONE_REGEX, phone);
    }


    /**
     * 检查提供的电子邮件地址是否不符合标准电子邮件格式。
     *
     * @param email 待验证的电子邮件地址。
     * @return 如果电子邮件地址不符合预定义的电子邮件正则表达式格式，则返回true；否则返回false。
     */
    public static boolean invalidEmail(String email) {
        // 使用正则表达式模式对电子邮件地址进行匹配，检查是否不匹配
        return mismatch(RegexPatterns.EMAIL_REGEX, email);
    }

    /**
     * 检查提供的用户名是否不符合规定的格式。
     *
     * @param username 待检查的用户名字符串。
     * @return 返回一个布尔值，如果用户名与预定义的正则表达式不匹配，则返回true；否则返回false。
     */
    public static boolean invalidUsername(String username) {
        // 使用正则表达式检查用户名格式是否合规
        return mismatch(RegexPatterns.USERNAME_REGEX, username);
    }


    /**
     * 检查提供的密码是否不符合预定的密码规则。
     *
     * @param password 待检查的密码字符串。
     * @return 返回一个布尔值，如果密码不符合规则，则返回true；否则返回false。
     */
    public static boolean invalidPassword(String password) {
        // 使用正则表达式检查密码是否符合预定格式
        return mismatch(RegexPatterns.PASSWORD_REGEX, password);
    }


    private static boolean mismatch(String regex, String content) {
        if (StringUtils.isBlank(content)) {
            return true;
        }

        return !content.matches(regex);
    }

}

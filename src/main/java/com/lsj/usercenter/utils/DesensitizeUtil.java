package com.lsj.usercenter.utils;

public class DesensitizeUtil {
    // 对邮箱进行脱敏，保留邮箱前缀的前两个字符和后缀
    public static String desensitizeEmail(String email) {
        String regex = "^(.{2}).*(@.*)$";
        return email.replaceAll(regex, "$1****$2");
    }

    // 对电话号码进行脱敏，保留前三位和后四位
    public static String desensitizePhone(String phone) {
        String regex = "(\\d{3}).*(\\d{4})";
        return phone.replaceAll(regex, "$1****$2");
    }
}

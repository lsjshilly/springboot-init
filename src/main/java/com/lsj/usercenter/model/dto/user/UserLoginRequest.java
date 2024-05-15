package com.lsj.usercenter.model.dto.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequest {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 登录方式
     */
    private String loginType;


    /**
     * 电话
     */
    private String phone;

    /**
     * y验证码
     */
    private String code;

}

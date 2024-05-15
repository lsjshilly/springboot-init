package com.lsj.usercenter.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateRequest {

    /**
     * Id
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 权限
     */
    private String roles;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 标签
     */
    private String tags;

    /**
     * 地址
     */
    private String city;

    /**
     * 个人介绍
     */
    private String description;

    /**
     * 生日
     */
    private Date birthday;

}

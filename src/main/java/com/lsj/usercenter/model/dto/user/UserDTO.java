package com.lsj.usercenter.model.dto.user;

import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {

    private Long id;

    /**
     * 用户名
     */
    private String username;


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

}

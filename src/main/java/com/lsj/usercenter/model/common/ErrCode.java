package com.lsj.usercenter.model.common;


import lombok.Getter;

@Getter
public enum ErrCode {

    /**
     * 成功
     */
    SUCCESS(100001, ""),
    /**
     * 系统内部错误
     */
    SYSTEM_ERROR(100002, "内部服务错误"),
    /**
     * 数据库错误
     */
    DATABASE_ERROR(100101, "数据库异常"),
    /**
     * 校验错误
     */
    VALIDATION_ERROR(100201, "参数校验错误"),

    /**
     * 不存在
     */
    ERR_PAGE_NOT_FOUND(100202, "资源不存在"),
    /**
     * 未登录
     */
    ERR_NOT_LOGIN(100203, "用户未登录"),

    /**
     * 无权限
     */
    ERR_ACCESS_DENEY(100204, "权限不足"),


    /**
     * 头像上传失败
     */
    ERR_UPLODA_AVATAR(110101, "头像上传失败"),

    /**
     * 登录失败
     */
    ERR_LOGIN_ERROR(110102, "登录失败"),

    /**
     * 注册 失败
     */
    ERR_REGISTER_ERROR(110103, "注册失败");

    private final int code;
    private final String message;


    ErrCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

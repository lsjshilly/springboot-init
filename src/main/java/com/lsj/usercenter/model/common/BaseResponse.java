package com.lsj.usercenter.model.common;


import lombok.Data;

@Data
public class BaseResponse {

    /**
     * 错误码
     */
    private int code;
    /**
     * 错误信息
     */
    private String message;
    /**
     * 响应数据
     */
    private Object data;


    public BaseResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public static BaseResponse success() {
        return new BaseResponse(ErrCode.SUCCESS.getCode(), "", null);
    }


    public static BaseResponse success(Object data) {
        return new BaseResponse(ErrCode.SUCCESS.getCode(), "", data);
    }


    public static BaseResponse error(int code, String errmsg) {
        return new BaseResponse(code, errmsg, null);
    }

    public static BaseResponse error(ErrCode errCode) {
        return new BaseResponse(errCode.getCode(), errCode.getMessage(), null);
    }


    public static BaseResponse error(ErrCode errCode, String errmsg) {
        return new BaseResponse(errCode.getCode(), errmsg, null);
    }


}

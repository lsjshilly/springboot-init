package com.lsj.usercenter.model.domain;


import lombok.Data;

import java.util.List;

@Data
public class Operation {

    /**
     * 方法签名
     */
    private String methodSignature;

    /**
     * 操作名称
     */
    private String name;


    /**
     * 记录日志
     */
    private boolean auditEnabled;

    /**
     * 任意角色符合
     */
    private List<String> anyRoles;

    /**
     * 必须包含角色
     */
    private List<String> mustRoles;


    public Operation(String methodSignature, String name) {
        this.methodSignature = methodSignature;
        this.name = name;
    }
}

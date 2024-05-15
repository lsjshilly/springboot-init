package com.lsj.usercenter.model.domain;

import cn.hutool.core.lang.UUID;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class OperationLog {

    /**
     * 日志ID
     */
    private String id;
    /**
     * 操作名称
     */
    private String operationName;
    /**
     * 操作资源名称
     */
    private String resource;
    /**
     * 客户端
     */
    private String client;
    /**
     * 用户标识
     */
    private String userId;
    /**
     * 执行结果
     */
    private boolean failed;
    /**
     * 错误码
     */
    private int errCode;
    /**
     * cuowu信息
     */
    private String errMsg;
    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;


    public OperationLog(String operationName, String client) {
        this.operationName = operationName;
        this.client = client;
        this.startTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.id = UUID.fastUUID().toString(true);
    }


    void finish() {
        synchronized (this) {
            if (this.endTime == null) {
                this.endTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            } else {
                throw new IllegalStateException("Operation is already finished.");
            }
        }
    }


    public void success() {
        finish();
        this.failed = false;
    }


    public void failed(int errCode, String errMsg) {
        finish();
        this.failed = true;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }


    public boolean isFinished() {
        return this.endTime != null;
    }

}

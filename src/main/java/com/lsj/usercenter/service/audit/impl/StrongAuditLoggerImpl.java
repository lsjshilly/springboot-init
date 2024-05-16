package com.lsj.usercenter.service.audit.impl;

import com.lsj.usercenter.model.common.BaseResponse;
import com.lsj.usercenter.model.common.ErrCode;
import com.lsj.usercenter.model.domain.OperationLog;
import com.lsj.usercenter.model.domain.RequestContext;
import com.lsj.usercenter.model.common.BusinessExecption;
import com.lsj.usercenter.service.audit.AuditAdapter;
import com.lsj.usercenter.service.audit.AuditLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StrongAuditLoggerImpl implements AuditLogger {

    private final RequestContext context;

    private final AuditAdapter auditAdapter;

    private OperationLog operationLog;


    public StrongAuditLoggerImpl(RequestContext context, AuditAdapter auditAdapter) {
        this.context = context;
        this.auditAdapter = auditAdapter;
    }

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        if (ip == null) {
            return "127.0.0.1";
        }
        return ip;
    }

    @Override
    public void auditTry() {
        String client = getClientIp(context.getRequest());
        String operationName = context.getOperation().getName();
        this.operationLog = new OperationLog(operationName, client);
    }

    @Override
    public void authenticateFailed(BaseResponse result) {
        this.operationLog.failed(result.getCode(), result.getMessage());
        this.auditAdapter.submit(this.operationLog);
    }

    @Override
    public void auditFinished(Object result) {
        if (result instanceof BaseResponse re) {
            if (re.getCode() != ErrCode.SUCCESS.getCode()) {
                this.operationLog.failed(re.getCode(), re.getMessage());
            } else {
                this.operationLog.success();
            }
        }
        this.auditAdapter.submit(this.operationLog);
    }

    @Override
    public void auditFailed(Throwable e) {
        if (e instanceof BusinessExecption be) {
            this.operationLog.failed(be.getCode(), be.getMessage());
        } else {
            this.operationLog.failed(ErrCode.SYSTEM_ERROR.getCode(), e.getMessage());
        }
        log.error("Operation execute failed, reauest url:{}", context.getRequest().getRequestURL(), e);
        this.auditAdapter.submit(this.operationLog);
    }
}

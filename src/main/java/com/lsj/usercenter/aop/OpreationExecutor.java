package com.lsj.usercenter.aop;

import com.lsj.usercenter.model.common.BaseResponse;
import com.lsj.usercenter.model.common.ErrCode;
import com.lsj.usercenter.model.domain.RequestContext;
import com.lsj.usercenter.model.execption.BusinessExecption;
import com.lsj.usercenter.service.audit.AuditAdapter;
import com.lsj.usercenter.service.audit.AuditLogger;
import com.lsj.usercenter.service.audit.impl.StrongAuditLoggerImpl;
import com.lsj.usercenter.service.auth.impl.SimpleAuthenticator;
import org.aspectj.lang.ProceedingJoinPoint;

public class OpreationExecutor {

    private final RequestContext context;

    private final ProceedingJoinPoint joinPoint;

    private final AuditLogger auditLogger;


    public OpreationExecutor(RequestContext context, AuditAdapter auditAdapter, ProceedingJoinPoint joinPoint) {
        this.context = context;
        this.joinPoint = joinPoint;
        this.auditLogger = new StrongAuditLoggerImpl(context, auditAdapter);
    }

    public Object execute() {

        SimpleAuthenticator authenticator = new SimpleAuthenticator();
        // 开始记录日志
        this.auditLogger.auditTry();

        try {
            // 认证
            BaseResponse validate = authenticator.validate(context);
            if (validate != null) {
                this.auditLogger.authenticateFailed(validate);
                return validate;
            }

            BaseResponse authenticate = authenticator.authenticate(context);
            if (authenticate != null) {
                this.auditLogger.authenticateFailed(authenticate);
                return authenticate;
            }

            Object proceed = this.joinPoint.proceed();
            this.auditLogger.auditFinished(proceed);
            return proceed;


        } catch (BusinessExecption e) {
            this.auditLogger.auditFailed(e);
            throw e;

        } catch (Throwable e) {
            this.auditLogger.auditFailed(e);
            throw new BusinessExecption(ErrCode.SYSTEM_ERROR, e.getMessage());
        }

    }
}

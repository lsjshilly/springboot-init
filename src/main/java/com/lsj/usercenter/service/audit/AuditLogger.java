package com.lsj.usercenter.service.audit;

import com.lsj.usercenter.model.common.BaseResponse;

public interface AuditLogger {

    void auditTry();


    void authenticateFailed(BaseResponse result);


    void auditFinished(Object result);


    void auditFailed(Throwable e);

}

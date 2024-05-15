package com.lsj.usercenter.service.audit;

import com.lsj.usercenter.model.domain.OperationLog;

public interface AuditAdapter {



    void submit(OperationLog operationLog);
}

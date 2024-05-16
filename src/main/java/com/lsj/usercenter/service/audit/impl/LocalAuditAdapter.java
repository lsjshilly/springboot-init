package com.lsj.usercenter.service.audit.impl;

import com.lsj.usercenter.model.domain.OperationLog;
import com.lsj.usercenter.service.audit.AuditAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class LocalAuditAdapter implements AuditAdapter {
    @Override
    public void submit(OperationLog operationLog) {
        String result = operationLog.isFinished() ? (operationLog.isFailed() ? "Failed" : "Success") : "Running";
        log.info("##### StartTime:{},Client:{}, User:{}, Operation:{}, Errcode:{}, ErrMsg:{}, EndTime:{}, Result:{}",
        operationLog.getStartTime(),
                operationLog.getClient(),
                operationLog.getUserId(),
                operationLog.getOperationName(),
                operationLog.getErrCode(),
                operationLog.getErrMsg(),
                operationLog.getEndTime(),
                result
        );

    }
}

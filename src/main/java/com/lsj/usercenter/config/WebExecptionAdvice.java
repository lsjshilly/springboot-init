package com.lsj.usercenter.config;


import com.lsj.usercenter.model.common.BaseResponse;
import com.lsj.usercenter.model.execption.BusinessExecption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.lsj.usercenter.model.common.ErrCode.SYSTEM_ERROR;

@RestControllerAdvice
@Slf4j
public class WebExecptionAdvice {


    @ExceptionHandler(BusinessExecption.class)
    public BaseResponse handler(BusinessExecption e) {
        log.error("#error occured: code {} message {} ", e.getCode(), e.getMessage());
        return BaseResponse.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse handlerbadRequest(RuntimeException e) {
        log.error("#error occured: message {}", e.getMessage());
        return BaseResponse.error(SYSTEM_ERROR.getCode(), e.getMessage());
    }
}

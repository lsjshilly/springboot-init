package com.lsj.usercenter.service.auth;

import com.lsj.usercenter.model.common.BaseResponse;
import com.lsj.usercenter.model.domain.RequestContext;

public interface Authenticator {

    BaseResponse validate(RequestContext ctx);


    BaseResponse authenticate(RequestContext ctx);
}

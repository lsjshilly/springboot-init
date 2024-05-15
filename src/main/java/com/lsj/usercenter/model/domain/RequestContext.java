package com.lsj.usercenter.model.domain;

import com.lsj.usercenter.model.dto.user.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

import static com.lsj.usercenter.model.constant.HttpHeader.AUTHORIZATION_HEADER;


@Getter
public class RequestContext {
    /**
     * request 请求
     */
    private final HttpServletRequest request;
    /**
     * token 认证
     */
    @Setter
    private String token;

    /***
     * 用户信息
     */
    @Setter
    private UserDTO userDTO;

    /**
     * 操作信息
     */
    @Setter
    private Operation operation;

    private RequestContext(HttpServletRequest request) {
        this.request = request;
    }

    public static RequestContext build(HttpServletRequest request) {
        RequestContext context = new RequestContext(request);
        String authToken = request.getHeader(AUTHORIZATION_HEADER);
        context.setToken(authToken);
        return context;
    }

}

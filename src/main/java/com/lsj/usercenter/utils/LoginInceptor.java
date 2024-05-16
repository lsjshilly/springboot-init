package com.lsj.usercenter.utils;

import com.lsj.usercenter.model.common.ErrCode;
import com.lsj.usercenter.model.dto.user.UserDTO;
import com.lsj.usercenter.model.common.BusinessExecption;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class LoginInceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserDTO userDTO = UserHodler.get();

        if (userDTO == null) {
            log.info("用户未登录，请求被拦截：{}", request.getRequestURL());
            throw new BusinessExecption(ErrCode.ERR_NOT_LOGIN, "用户未登录");
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHodler.remove();
    }
}

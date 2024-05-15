package com.lsj.usercenter.utils;

import cn.hutool.json.JSONUtil;
import com.lsj.usercenter.model.dto.user.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.TimeUnit;

import static com.lsj.usercenter.model.constant.RedisConstants.LOGIN_USER_TOKEN_PREFIX;
import static com.lsj.usercenter.model.constant.RedisConstants.LOGIN_USER_TOKEN_TTL;

@Slf4j
public class RefreshTokenInceptor implements HandlerInterceptor {

    private final StringRedisTemplate stringRedisTemplate;

    public RefreshTokenInceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (StringUtils.isBlank(token)) {
            log.error("token is empty");
            return true;
        }

        String userDtoStr = stringRedisTemplate.opsForValue().get(LOGIN_USER_TOKEN_PREFIX + token);

        if (StringUtils.isBlank(userDtoStr)) {
            log.error("token is expired:{}", token);
            return true;
        }

        UserDTO userDTO = JSONUtil.toBean(userDtoStr, UserDTO.class);

        if (userDTO == null) {
            log.error("token is invalid:{}", token);
            return true;
        }

        UserHodler.save(userDTO);
        // 刷新token过期时间
        stringRedisTemplate.expire(LOGIN_USER_TOKEN_PREFIX + token, LOGIN_USER_TOKEN_TTL, TimeUnit.MINUTES);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}

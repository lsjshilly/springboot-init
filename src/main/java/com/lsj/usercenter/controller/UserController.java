package com.lsj.usercenter.controller;

import com.lsj.usercenter.aop.Operator;
import com.lsj.usercenter.model.common.BaseResponse;
import com.lsj.usercenter.model.dto.user.UserLoginRequest;
import com.lsj.usercenter.model.dto.user.UserRegisterRequest;
import com.lsj.usercenter.service.user.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-center/user")
public class UserController {


    @Resource
    private UserService userService;


    @Operator(value = "UserLogin")
    @PostMapping("/login")
    public BaseResponse login(@RequestBody UserLoginRequest userLoginRequest) {
        return userService.doLogin(userLoginRequest);
    }


    @PostMapping("/register")
    @Operator(value = "UserRegister")
    public BaseResponse register(@RequestBody UserRegisterRequest userRegisterRequest) {
        return userService.register(userRegisterRequest);
    }


    @GetMapping("/me")
    @Operator(value = "GetCurrentUser")
    public BaseResponse getCurrentUser() {
        return null;
    }

  
}

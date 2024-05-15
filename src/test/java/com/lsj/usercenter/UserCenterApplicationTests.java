package com.lsj.usercenter;

import com.lsj.usercenter.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserCenterApplicationTests {

    @Autowired
    private UserService userService;



}

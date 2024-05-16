package com.lsj.usercenter.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsj.usercenter.model.common.BaseResponse;
import com.lsj.usercenter.model.dto.user.UserDTO;
import com.lsj.usercenter.model.dto.user.UserLoginRequest;
import com.lsj.usercenter.model.dto.user.UserLoginResult;
import com.lsj.usercenter.model.dto.user.UserRegisterRequest;
import com.lsj.usercenter.model.entity.User;

/**
 * @author liushijie
 * @description 针对表【tb_user】的数据库操作Service
 * @createDate 2024-05-07 23:21:15
 */
public interface UserService extends IService<User> {

    UserLoginResult doLogin(UserLoginRequest userLoginRequest);

    UserDTO register(UserRegisterRequest userRegisterRequest);

    UserDTO getLoginUser(long userId);
}

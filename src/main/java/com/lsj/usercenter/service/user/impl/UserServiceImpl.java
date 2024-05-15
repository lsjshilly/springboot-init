package com.lsj.usercenter.service.user.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsj.usercenter.mapper.UserMapper;
import com.lsj.usercenter.model.common.BaseResponse;
import com.lsj.usercenter.model.common.ErrCode;
import com.lsj.usercenter.model.dto.user.UserDTO;
import com.lsj.usercenter.model.dto.user.UserLoginRequest;
import com.lsj.usercenter.model.dto.user.UserRegisterRequest;
import com.lsj.usercenter.model.entity.User;
import com.lsj.usercenter.service.user.UserService;
import com.lsj.usercenter.utils.RegexUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.lsj.usercenter.model.constant.RedisConstants.*;
import static com.lsj.usercenter.model.constant.UserConstant.*;

/**
 * @author liushijie
 * @description 针对表【tb_user】的数据库操作Service实现
 * @createDate 2024-05-15 22:46:02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {


    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public BaseResponse doLogin(UserLoginRequest userLoginRequest) {
        if (ACCOUNT_LOGIN_TYPE.equals(userLoginRequest.getLoginType())) {
            return accountLogin(userLoginRequest);
        } else if (PHONE_LOGIN_TYPE.equals(userLoginRequest.getLoginType())) {
            return phoneLogin(userLoginRequest);
        } else {
            return BaseResponse.error(ErrCode.ERR_LOGIN_ERROR, "登录类型错误");
        }
    }

    @Override
    public BaseResponse register(UserRegisterRequest userRegisterRequest) {
        if (RegexUtils.invalidUsername(userRegisterRequest.getUsername())) {
            return BaseResponse.error(ErrCode.ERR_LOGIN_ERROR, "用户名不合法，仅支持中英文、数字、下划线、中划线、@及.，4-32长度");
        }
        if (RegexUtils.invalidPassword(userRegisterRequest.getPassword())) {
            return BaseResponse.error(ErrCode.ERR_LOGIN_ERROR, "密码不合法，仅中应为、数字、下划线、中划线及特殊符号@.#$%&*!，8-32长度");
        }

        User existUser = query().eq("username", userRegisterRequest.getUsername()).one();
        if (existUser != null) {
            return BaseResponse.error(ErrCode.ERR_LOGIN_ERROR, "用户名已存在");
        }

        String encryptPassword = DigestUtils.md5Hex(SALT + userRegisterRequest.getPassword());

        User user = new User();
        user.setUsername(userRegisterRequest.getUsername());
        user.setPassword(encryptPassword);
        user.setNickname("user_" + RandomUtil.randomString(10));
        save(user);
        return BaseResponse.success();
    }

    @Override
    public BaseResponse getUserDetail(long id) {
        return null;
    }


    private BaseResponse phoneLogin(UserLoginRequest userLoginRequest) {
        if (RegexUtils.invalidphone(userLoginRequest.getPhone())) {
            return BaseResponse.error(ErrCode.ERR_LOGIN_ERROR, "无效的手机号");
        }

        User existUser = query().eq("phone", userLoginRequest.getPhone()).one();

        if (existUser == null) {
            return BaseResponse.error(ErrCode.ERR_LOGIN_ERROR, "手机号未注册");
        }


        String code = stringRedisTemplate.opsForValue().get(LOGIN_USER_CODE_PREFIX + userLoginRequest.getPhone());

        if (StringUtils.isBlank(code) || !code.equals(userLoginRequest.getCode())) {
            return BaseResponse.error(ErrCode.ERR_LOGIN_ERROR, "验证码错误");
        }


        String token = UUID.randomUUID().toString(true);

        saveInRedis(existUser, token);

        return BaseResponse.success(token);

    }

    private void saveInRedis(User existUser, String token) {
        UserDTO userDTO = BeanUtil.toBean(existUser, UserDTO.class);

        stringRedisTemplate.opsForValue().set(LOGIN_USER_TOKEN_PREFIX + token,
                JSONUtil.toJsonStr(userDTO)
        );
        stringRedisTemplate.expire(LOGIN_USER_TOKEN_PREFIX + token, LOGIN_USER_TOKEN_TTL, TimeUnit.MINUTES);
    }

    private BaseResponse accountLogin(UserLoginRequest userLoginRequest) {
        if (RegexUtils.invalidUsername(userLoginRequest.getUsername())) {
            return BaseResponse.error(ErrCode.ERR_LOGIN_ERROR, "无效的用户名");
        }
        if (RegexUtils.invalidPassword(userLoginRequest.getPassword())) {
            return BaseResponse.error(ErrCode.ERR_LOGIN_ERROR, "无效的密码");
        }


        String encryptPassword = DigestUtils.md5Hex(SALT + userLoginRequest.getPassword());

        User existUser = query().eq("username", userLoginRequest.getUsername())
                .eq("password", encryptPassword)
                .one();

        if (existUser == null) {
            return BaseResponse.error(ErrCode.ERR_LOGIN_ERROR, "用户名或密码错误");
        }

        String token = UUID.randomUUID().toString(true);

        saveInRedis(existUser, token);

        return BaseResponse.success(token);
    }
}





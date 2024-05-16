package com.lsj.usercenter.service.user.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsj.usercenter.mapper.UserMapper;
import com.lsj.usercenter.model.common.BaseResponse;
import com.lsj.usercenter.model.common.BusinessExecption;
import com.lsj.usercenter.model.common.ErrCode;
import com.lsj.usercenter.model.dto.user.UserDTO;
import com.lsj.usercenter.model.dto.user.UserLoginRequest;
import com.lsj.usercenter.model.dto.user.UserLoginResult;
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
    public UserLoginResult doLogin(UserLoginRequest userLoginRequest) {
        if (ACCOUNT_LOGIN_TYPE.equals(userLoginRequest.getLoginType())) {
            return accountLogin(userLoginRequest);
        } else if (PHONE_LOGIN_TYPE.equals(userLoginRequest.getLoginType())) {
            return phoneLogin(userLoginRequest);
        } else {
            throw new BusinessExecption(ErrCode.ERR_LOGIN_ERROR, "登录类型错误");
        }
    }

    @Override
    public UserDTO register(UserRegisterRequest userRegisterRequest) {
        if (RegexUtils.invalidUsername(userRegisterRequest.getUsername())) {
            throw new BusinessExecption(ErrCode.ERR_REGISTER_ERROR, "用户名不合法，仅支持中英文、数字、下划线、中划线、@及.，4-32长度");
        }
        if (RegexUtils.invalidPassword(userRegisterRequest.getPassword())) {
            throw new BusinessExecption(ErrCode.ERR_REGISTER_ERROR, "密码不合法，仅中应为、数字、下划线、中划线及特殊符号@.#$%&*!，8-32长度");
        }

        User existUser = query().eq("username", userRegisterRequest.getUsername()).one();
        if (existUser != null) {
            throw new BusinessExecption(ErrCode.ERR_REGISTER_ERROR, "用户名已存在");
        }

        String encryptPassword = DigestUtils.md5Hex(SALT + userRegisterRequest.getPassword());

        User user = new User();
        user.setUsername(userRegisterRequest.getUsername());
        user.setPassword(encryptPassword);
        user.setNickname("user_" + RandomUtil.randomString(10));
        boolean saved = save(user);
        if (!saved) {
            throw new BusinessExecption(ErrCode.ERR_REGISTER_ERROR, "数据库异常");
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        return userDTO;
    }

    @Override
    public UserDTO getLoginUser(long userId) {
        if (userId <=0) {
            return null;
        }
        User user = getById(userId);
        return BeanUtil.copyProperties(user, UserDTO.class);
    }


    private UserLoginResult phoneLogin(UserLoginRequest userLoginRequest) {
        if (RegexUtils.invalidphone(userLoginRequest.getPhone())) {
            throw new BusinessExecption(ErrCode.ERR_LOGIN_ERROR, "无效的手机号");
        }

        User existUser = query().eq("phone", userLoginRequest.getPhone()).one();

        if (existUser == null) {
            throw new BusinessExecption(ErrCode.ERR_LOGIN_ERROR, "手机号未注册");
        }


        String code = stringRedisTemplate.opsForValue().get(LOGIN_USER_CODE_PREFIX + userLoginRequest.getPhone());

        if (StringUtils.isBlank(code) || !code.equals(userLoginRequest.getCode())) {
            throw new BusinessExecption(ErrCode.ERR_LOGIN_ERROR, "验证码错误");
        }


        String token = UUID.randomUUID().toString(true);

        saveInRedis(existUser, token);

        return new UserLoginResult(token, "");

    }

    private void saveInRedis(User existUser, String token) {

        stringRedisTemplate.opsForValue().set(LOGIN_USER_TOKEN_PREFIX + token,existUser.getId().toString()

        );
        stringRedisTemplate.expire(LOGIN_USER_TOKEN_PREFIX + token, LOGIN_USER_TOKEN_TTL, TimeUnit.MINUTES);
    }

    private UserLoginResult accountLogin(UserLoginRequest userLoginRequest) {
        if (RegexUtils.invalidUsername(userLoginRequest.getUsername())) {
            throw new BusinessExecption(ErrCode.ERR_LOGIN_ERROR, "无效的用户名");
        }
        if (RegexUtils.invalidPassword(userLoginRequest.getPassword())) {
            throw new BusinessExecption(ErrCode.ERR_LOGIN_ERROR, "无效的密码");
        }


        String encryptPassword = DigestUtils.md5Hex(SALT + userLoginRequest.getPassword());

        User existUser = query().eq("username", userLoginRequest.getUsername())
                .eq("password", encryptPassword)
                .one();

        if (existUser == null) {
            throw new BusinessExecption(ErrCode.ERR_LOGIN_ERROR, "用户名或密码错误");
        }

        String token = UUID.randomUUID().toString(true);

        saveInRedis(existUser, token);

        return new UserLoginResult(token, "");
    }
}





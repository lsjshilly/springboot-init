package com.lsj.usercenter.utils;

import com.lsj.usercenter.model.dto.user.UserDTO;

public class UserHodler {
    private static final ThreadLocal<UserDTO> threadLocal = new ThreadLocal<>();

    public static void save(UserDTO userDTO) {
        threadLocal.set(userDTO);
    }

    public static UserDTO get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}

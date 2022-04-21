package com.memory.glowingmemory.services;

import com.memory.glowingmemory.pojo.LoginUser;

/**
 * @author zc
 */
public interface LoginService {

    int register(LoginUser user);

    LoginUser getUser(LoginUser user);
}

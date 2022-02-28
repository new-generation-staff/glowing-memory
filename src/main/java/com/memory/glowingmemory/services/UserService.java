package com.memory.glowingmemory.services;

import com.memory.glowingmemory.pojo.User;

import java.util.Map;

/**
 * @author zc
 */
public interface UserService {

    User getUserById(Integer id);

    int register(User user);
}

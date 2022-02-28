package com.memory.glowingmemory.services.servicesImpl;

import com.memory.glowingmemory.mapper.UserMapper;
import com.memory.glowingmemory.pojo.User;
import com.memory.glowingmemory.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zc
 */
@Service("UserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public int register(User user) {
        return userMapper.insert(user);
    }
}

package com.memory.glowingmemory.controller;

import com.memory.glowingmemory.pojo.User;
import com.memory.glowingmemory.services.UserService;
import com.memory.glowingmemory.util.common.Result;
import com.memory.glowingmemory.util.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author zc
 */
@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    //http://localhost:8083/user/getUserById?id=3
    @GetMapping("/getUserById")
    public Result getUserById(Integer id) {
        User user = userService.getUserById(id);
        return Result.result(ResultCode.SUCCESS, user);
    }

    //http://localhost:8083/user/getUserById2/2
    @GetMapping("/getUserById2/{id}")
    public Result getUserById2(@PathVariable("id") Integer id) {
        User user = userService.getUserById(id);
        return Result.result(ResultCode.SUCCESS, user);
    }

    @PostMapping("/register")
    public Result register(User user) {
        int i = userService.register(user);
        return Result.result(ResultCode.SUCCESS);
    }
}

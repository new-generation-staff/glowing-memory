package com.memory.glowingmemory.controller;

import com.memory.glowingmemory.interfaces.AvoidRepeatableCommit;
import com.memory.glowingmemory.pojo.LoginUser;
import com.memory.glowingmemory.services.LoginService;
import com.memory.glowingmemory.utils.common.Constants;
import com.memory.glowingmemory.utils.common.Result;
import com.memory.glowingmemory.utils.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 登录相关的操作
 *
 * @author zc
 */
@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("register")
    //防止重复提交
    @AvoidRepeatableCommit(timeout = 10000)
    public Result register(LoginUser user) {
        try {
            //查询数据库中登录名是否存在
            int i = loginService.countByUsername(user);
            if (i > 0) {
                return Result.result(ResultCode.REGISTER_FAILURE);
            }
            //todo 异步提交（先校验参数）
            loginService.register(user);
            return Result.result(ResultCode.SUCCESS);
        } catch (Exception e) {
            return Result.result(ResultCode.ERROR);
        }
    }

    @PostMapping("login")
    public Result login(LoginUser user, HttpSession session) {
        //todo 添加登录次数校验   Result.result(ResultCode.LOGIN_OVERRUN);
        LoginUser loginUser = loginService.getUser(user);
        if (loginUser != null) {
            log.info("set session :{}", user);
            // 在sesssion中存储用户信息
            session.setAttribute(Constants.FONT_SESSION, user);
            //设置session过期时间为30s 默认是1800s，指的是在不进行任何操作的情况下，超时时间，（即若处于操作时间期间的话，则自动延长超时时间）
            session.setMaxInactiveInterval(30);
        }
        return Result.result(ResultCode.SUCCESS);
    }

    @PostMapping("logout")
    public Result Logout(HttpSession session) {
        //用户登出后将session置空
        session.invalidate();
        return Result.result(ResultCode.SUCCESS);
    }
}

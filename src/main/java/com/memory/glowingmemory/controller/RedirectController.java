package com.memory.glowingmemory.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录相关的操作
 *
 * @author zc
 */
@Controller
@RequestMapping("/redirect")
@Slf4j
public class RedirectController {

    @GetMapping("/login")
    public String login() {
        return "login/login.html";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "login/signup.html";
    }

    @GetMapping("/index")
    public String index() {
        return "login/index.html";
    }

    @GetMapping("/home")
    public String home() {
        return "login/home.html";
    }
}

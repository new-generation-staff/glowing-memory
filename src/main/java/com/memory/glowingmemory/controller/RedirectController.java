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
        return "loginHtml/login.html";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "loginHtml/signup.html";
    }

    @GetMapping("/index")
    public String index() {
        return "loginHtml/index.html";
    }

    @GetMapping("/home")
    public String home() {
        return "loginHtml/home.html";
    }
}

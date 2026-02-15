package com.devnotfound.talenthub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @GetMapping("/home")
    public String homeUser() {
        return "user/home";
    }

    @GetMapping("/login-user")
    public String loginUser() {
        return "user/login";
    }
}
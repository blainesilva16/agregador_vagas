package com.devnotfound.talenthub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @GetMapping("/home")
    public String homeCustomer() {
        return "customer/home"; // retorna a view customer/home.html
    }

    @GetMapping("/login-customer")
    public String loginCustomer() {
        return "customer/login"; // retorna a view customer/login.html
    }
}
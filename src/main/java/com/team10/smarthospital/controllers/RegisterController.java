package com.team10.smarthospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {

    @GetMapping("/register")
    public String registerPage() {
        return "register";  // loads register.html
    }

    @GetMapping("/EmailVerify")
    public String EmailPage() {
        return "EmailVerify";  // loads register.html
    }

    @GetMapping("/PasswordVerify")
    public String PasswordPage() {
        return "PasswordVerify";  // loads register.html
    }

}

package com.team10.smarthospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class PrescriptionController {

    @GetMapping("/prescription")
    public String prescriptionPage() {
        return "prescription"; // 返回 prescription.html 模板
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 清除 session
        return "redirect:/login"; // 重導向到登入頁面
    }

    @GetMapping("/login")
    public String loginPage() {
        return "homePage"; // 返回 homePage.html 模板
    }
}
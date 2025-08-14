package com.team10.smarthospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ComingSoonController {

  @GetMapping("/comingSoon")
  public String showComingSoon() {
    return "comingSoon";  // 对应 comingSoon.html
  }
}

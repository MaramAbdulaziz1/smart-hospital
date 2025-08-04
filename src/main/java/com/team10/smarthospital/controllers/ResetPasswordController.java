package com.team10.smarthospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class ResetPasswordController {

  @GetMapping("/resetPassword")
  public String resetPasswordPage() {
    return "layouts/resetPassword";  // appoint layouts file file
  }

  @PostMapping("/resetPassword")
  public String handleResetPassword(
    @RequestParam String email,
    @RequestParam String newPassword,
    @RequestParam String confirmPassword,
    Model model) {

    // Verify whether the password matches
    if (!newPassword.equals(confirmPassword)) {
      model.addAttribute("error", "Passwords do not match. Please try again.");
      return "layouts/resetPassword";
    }

    // Verify password length
    if (newPassword.length() < 6) {
      model.addAttribute("error", "Password must be at least 6 characters long.");
      return "layouts/resetPassword";
    }

    // Verify the email address format
    if (!isValidEmail(email)) {
      model.addAttribute("error", "Please enter a valid email address.");
      return "layouts/resetPassword";
    }

    // The actual password reset logic can be added here
    // For example: checking whether the user exists, updating the password in the database, etc
    // userService.resetPassword(email, newPassword);

    // Add a success message and redirect to the login page
    model.addAttribute("success", "Password has been reset successfully. You can now log in with your new password.");
    return "redirect:/login";
  }

  // Auxiliary methods for verifying email format
  private boolean isValidEmail(String email) {
    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    return email != null && email.matches(emailRegex);
  }
}

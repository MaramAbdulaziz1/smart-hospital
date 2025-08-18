package com.team10.smarthospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

@Controller
public class PatientRegistrationController {

  @GetMapping("/patient/register")  // Modified path
  public String patientRegistrationPage(Model model) {

    model.addAttribute("pageTitle", "Patient Registration");
    return "patientRegistration";  // patientRegistration.html in templates folder
  }

  @PostMapping("/patient/register")  // Modified path
  public String registerPatient(
    // Personal Information
    @RequestParam String firstName,
    @RequestParam String lastName,
    @RequestParam String dateOfBirth,
    @RequestParam(required = false) String height,
    @RequestParam(required = false) String weight,
    @RequestParam(required = false) String bloodType,
    @RequestParam(required = false) String homeAddress,
    @RequestParam String nationalId,
    @RequestParam String email,
    @RequestParam String mobileNumber,
    @RequestParam(required = false) String nearestClinic,
    @RequestParam String password,
    @RequestParam String confirmPassword,

    // Emergency Contact Information
    @RequestParam String emergencyFirstName,
    @RequestParam String emergencyLastName,
    @RequestParam String emergencyRelationship,
    @RequestParam String emergencyPhone,

    // Medical History Information
    @RequestParam(required = false) String currentCondition,
    @RequestParam(required = false) String currentConditionDetails,
    @RequestParam(required = false) String allergies,
    @RequestParam(required = false) String currentMedications,
    @RequestParam(required = false) String pastMedicalConditions,
    @RequestParam(required = false) String pastSurgicalHistory,
    @RequestParam(required = false) String pastMedications,
    @RequestParam(required = false) String familyHistory,
    @RequestParam(required = false) String socialHistory,

    // Profile Picture
    @RequestParam(required = false) MultipartFile profilePicture,

    Model model) {

    // Basic validation
    if (!password.equals(confirmPassword)) {
      model.addAttribute("error", "Passwords do not match!");
      model.addAttribute("pageTitle", "Patient Registration");
      return "patientRegistration";
    }

    // Validate National ID format (13 digits)
    if (nationalId == null || !nationalId.matches("\\d{13}")) {
      model.addAttribute("error", "National ID must be 13 digits!");
      model.addAttribute("pageTitle", "Patient Registration");
      return "patientRegistration";
    }

    // Validate Mobile Number format (9 digits)
    if (mobileNumber == null || !mobileNumber.matches("\\d{9}")) {
      model.addAttribute("error", "Mobile number must be 9 digits!");
      model.addAttribute("pageTitle", "Patient Registration");
      return "patientRegistration";
    }

    try {
      // Add success message
      model.addAttribute("message", "Registration successful! Please check your email for verification.");
      model.addAttribute("pageTitle", "Registration Complete");

      // Temporarily return to registration page with success message, add redirect after patient homepage is completed
      return "redirect:/homepage";

    } catch (Exception e) {
      // Handle any errors during registration
      model.addAttribute("error", "Registration failed. Please try again.");
      model.addAttribute("pageTitle", "Patient Registration");
      return "patientRegistration";
    }
  }

  @GetMapping("/register/choice")
  public String registerChoicePage(Model model) {
    // Page to choose between Patient/Doctor/Nurse registration
    model.addAttribute("pageTitle", "Choose Registration Type");
    return "registerChoice";  // registerChoice.html in templates folder
  }

  @GetMapping("/register/success")
  public String registrationSuccessPage(Model model) {
    model.addAttribute("pageTitle", "Registration Successful");
    return "registrationSuccess";  // registrationSuccess.html in templates folder
  }

  @GetMapping("/verify-email")
  public String verifyEmail(@RequestParam String token, Model model) {
    // Email verification logic
    try {
      // Temporarily return success message, add redirect after patient homepage is completed
      model.addAttribute("message", "Email verified successfully!");
      return "emailVerificationSuccess";  // emailVerificationSuccess.html in templates folder

    } catch (Exception e) {
      model.addAttribute("error", "Email verification failed.");
      return "verificationError";  // verificationError.html in templates folder
    }
  }
}

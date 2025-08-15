package com.team10.smarthospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

@Controller
public class PatientRegistrationController {

  @GetMapping("/patientRegistration")
  public String patientRegistrationPage(Model model) {

    model.addAttribute("pageTitle", "Patient Registration");
    return "patientRegistration";  // templates文件夹下的patientRegistration.html
  }

  @PostMapping("/patientRegistration")
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

    // Check if email already exists
    // TODO: Add service call to check email existence
    // if (patientService.emailExists(email)) {
    //     model.addAttribute("error", "Email already registered!");
    //     model.addAttribute("pageTitle", "Patient Registration");
    //     return "patientRegistration";
    // }

    try {
      // Create Patient object and save to database
      // TODO: Add service logic here

      // Example of what the service call might look like:
            /*
            Patient patient = new Patient();
            patient.setFirstName(firstName);
            patient.setLastName(lastName);
            patient.setDateOfBirth(LocalDate.parse(dateOfBirth));
            patient.setHeight(height != null ? Double.parseDouble(height) : null);
            patient.setWeight(weight != null ? Double.parseDouble(weight) : null);
            patient.setBloodType(bloodType);
            patient.setHomeAddress(homeAddress);
            patient.setNationalId(nationalId);
            patient.setEmail(email);
            patient.setMobileNumber(mobileNumber);
            patient.setNearestClinic(nearestClinic);
            patient.setPassword(passwordEncoder.encode(password));

            // Emergency Contact
            EmergencyContact emergencyContact = new EmergencyContact();
            emergencyContact.setFirstName(emergencyFirstName);
            emergencyContact.setLastName(emergencyLastName);
            emergencyContact.setRelationship(emergencyRelationship);
            emergencyContact.setPhoneNumber(emergencyPhone);
            patient.setEmergencyContact(emergencyContact);

            // Medical History
            MedicalHistory medicalHistory = new MedicalHistory();
            medicalHistory.setCurrentCondition(currentCondition);
            medicalHistory.setCurrentConditionDetails(currentConditionDetails);
            medicalHistory.setAllergies(allergies);
            medicalHistory.setCurrentMedications(currentMedications);
            medicalHistory.setPastMedicalConditions(pastMedicalConditions);
            medicalHistory.setPastSurgicalHistory(pastSurgicalHistory);
            medicalHistory.setPastMedications(pastMedications);
            medicalHistory.setFamilyHistory(familyHistory);
            medicalHistory.setSocialHistory(socialHistory);
            patient.setMedicalHistory(medicalHistory);

            // Handle profile picture upload
            if (profilePicture != null && !profilePicture.isEmpty()) {
                String fileName = fileStorageService.storeFile(profilePicture);
                patient.setProfilePicture(fileName);
            }

            // Save patient
            patientService.savePatient(patient);

            // Send verification email
            emailService.sendVerificationEmail(patient.getEmail(), patient.getId());
            */

      // Add success message
      model.addAttribute("message", "Registration successful! Please check your email for verification.");
      model.addAttribute("pageTitle", "Registration Complete");

      // 暂时返回注册页面显示成功消息，等patient homepage完成后再添加重定向
      return "patientRegistration";

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
    return "registerChoice";  // templates文件夹下的registerChoice.html
  }

  @GetMapping("/register/success")
  public String registrationSuccessPage(Model model) {
    model.addAttribute("pageTitle", "Registration Successful");
    return "registrationSuccess";  // templates文件夹下的registrationSuccess.html
  }

  @GetMapping("/verify-email")
  public String verifyEmail(@RequestParam String token, Model model) {
    // Email verification logic
    try {
      // TODO: Add email verification service
      // boolean verified = emailVerificationService.verifyEmail(token);

      // if (verified) {
      //     model.addAttribute("message", "Email verified successfully! You can now login.");
      //     return "redirect:/login?verified=true";
      // } else {
      //     model.addAttribute("error", "Invalid or expired verification token.");
      //     return "verificationError";
      // }

      // 暂时返回成功消息，等patient homepage完成后再添加重定向
      model.addAttribute("message", "Email verified successfully!");
      return "emailVerificationSuccess";  // templates文件夹下的emailVerificationSuccess.html

    } catch (Exception e) {
      model.addAttribute("error", "Email verification failed.");
      return "verificationError";  // templates文件夹下的verificationError.html
    }
  }
}

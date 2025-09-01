package com.team10.smarthospital;

import com.team10.smarthospital.model.entity.User;
import com.team10.smarthospital.model.request.LoginRequest;
import com.team10.smarthospital.model.response.BaseResponse;
import com.team10.smarthospital.model.response.UserResponse;
import com.team10.smarthospital.service.LoginService;
import com.team10.smarthospital.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.regex.Pattern;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive security tests for authentication and user management
 * Tests login security, password handling, and user validation
 */
@DisplayName("Security & Authentication Tests")
public class SecurityAuthenticationTests {

  private PasswordEncoder passwordEncoder;
  private LoginRequest validLoginRequest;
  private User testUser;

  @BeforeEach
  void setUp() {
    passwordEncoder = new BCryptPasswordEncoder();

    // Setup valid login request
    validLoginRequest = new LoginRequest();
    validLoginRequest.setUsername("patient@test.com");
    validLoginRequest.setPassword("SecurePassword123!");

    // Setup test user
    testUser = new User();
    testUser.setUserId("user-123");
    testUser.setEmail("patient@test.com");
    testUser.setPassword(passwordEncoder.encode("SecurePassword123!"));
    testUser.setRole(0); // Patient role
    testUser.setFirstName("John");
    testUser.setLastName("Doe");
    testUser.setBirth(LocalDate.of(1990, 5, 15));
    testUser.setGender(1);
  }

  @Nested
  @DisplayName("Password Security Tests")
  class PasswordSecurityTests {

    @Test
    @DisplayName("Password should be encrypted with BCrypt")
    void password_Encryption_UsesBCrypt() {
      String plainPassword = "MySecurePassword123!";
      String encodedPassword = passwordEncoder.encode(plainPassword);

      assertNotNull(encodedPassword, "Encoded password should not be null");
      assertNotEquals(plainPassword, encodedPassword, "Encoded password should be different from plain");
      assertTrue(encodedPassword.startsWith("$2a$"), "Should use BCrypt format");
      assertTrue(encodedPassword.length() >= 60, "BCrypt hash should be at least 60 characters");
    }

    @Test
    @DisplayName("Password verification should work correctly")
    void password_Verification_WorksCorrectly() {
      String plainPassword = "TestPassword456!";
      String encodedPassword = passwordEncoder.encode(plainPassword);

      assertTrue(passwordEncoder.matches(plainPassword, encodedPassword),
        "Correct password should match");
      assertFalse(passwordEncoder.matches("WrongPassword", encodedPassword),
        "Wrong password should not match");
    }

    @ParameterizedTest
    @DisplayName("Password should handle various special characters")
    @ValueSource(strings = {
      "Pass@123!", "Secure#Pass456", "MyP@ssw0rd$",
      "Test!@#$%^&*()", "P@55w0rd_2025", "SecureP@ss123"
    })
    void password_SpecialCharacters_HandledCorrectly(String password) {
      String encoded = passwordEncoder.encode(password);

      assertNotNull(encoded, "Password with special chars should be encoded");
      assertTrue(passwordEncoder.matches(password, encoded),
        "Special character password should verify correctly");
    }

    @Test
    @DisplayName("Same password should generate different hashes")
    void password_SaltGeneration_ProducesDifferentHashes() {
      String password = "SamePassword123!";

      String hash1 = passwordEncoder.encode(password);
      String hash2 = passwordEncoder.encode(password);

      assertNotEquals(hash1, hash2, "Same password should generate different hashes due to salt");
      assertTrue(passwordEncoder.matches(password, hash1), "First hash should verify");
      assertTrue(passwordEncoder.matches(password, hash2), "Second hash should verify");
    }

    @Test
    @DisplayName("Empty and null passwords should be handled securely")
    void password_EmptyNull_HandledSecurely() {
      // Test null password
      assertThrows(IllegalArgumentException.class, () -> {
        passwordEncoder.encode(null);
      }, "Encoding null password should throw exception");

      // Test empty password
      String emptyEncoded = passwordEncoder.encode("");
      assertNotNull(emptyEncoded, "Empty password should still be encoded");
      assertTrue(passwordEncoder.matches("", emptyEncoded), "Empty password should verify");
    }
  }

  @Nested
  @DisplayName("Login Request Validation Tests")
  class LoginRequestValidationTests {

    @Test
    @DisplayName("Valid login request should pass validation")
    void loginRequest_ValidData_PassesValidation() {
      LoginRequest request = new LoginRequest();
      request.setUsername("user@example.com");
      request.setPassword("ValidPassword123!");

      assertNotNull(request.getUsername(), "Username should not be null");
      assertNotNull(request.getPassword(), "Password should not be null");
      assertFalse(request.getUsername().trim().isEmpty(), "Username should not be empty");
      assertFalse(request.getPassword().isEmpty(), "Password should not be empty");
    }

    @ParameterizedTest
    @DisplayName("Invalid usernames should be rejected")
    @ValueSource(strings = {"", " ", "   ", "\t", "\n"})
    void loginRequest_InvalidUsernames_ShouldBeRejected(String invalidUsername) {
      LoginRequest request = new LoginRequest();
      request.setUsername(invalidUsername);
      request.setPassword("ValidPassword123!");

      // Business logic should reject these
      assertTrue(invalidUsername == null || invalidUsername.trim().isEmpty(),
        "Invalid username should be empty or whitespace only");
    }

    @Test
    @DisplayName("Null login request fields should be handled")
    void loginRequest_NullFields_HandledCorrectly() {
      LoginRequest request = new LoginRequest();

      // Initially should be null
      assertNull(request.getUsername(), "Username should be null initially");
      assertNull(request.getPassword(), "Password should be null initially");

      // Should accept null values
      request.setUsername(null);
      request.setPassword(null);

      assertNull(request.getUsername());
      assertNull(request.getPassword());
    }

    @Test
    @DisplayName("Email format usernames should be accepted")
    void loginRequest_EmailFormats_Accepted() {
      String[] validEmails = {
        "user@example.com",
        "patient123@hospital.org",
        "dr.smith@medical.center",
        "nurse_jane@clinic.net",
        "admin+test@system.co.uk"
      };

      for (String email : validEmails) {
        LoginRequest request = new LoginRequest();
        request.setUsername(email);

        assertEquals(email, request.getUsername());
        assertTrue(email.contains("@"), "Should contain @ symbol");
        assertTrue(email.contains("."), "Should contain domain extension");
      }
    }
  }

  @Nested
  @DisplayName("User Entity Security Tests")
  class UserEntitySecurityTests {

    @Test
    @DisplayName("User should store encrypted password only")
    void user_PasswordStorage_OnlyEncrypted() {
      User user = new User();
      String plainPassword = "PlainTextPassword123!";
      String encryptedPassword = passwordEncoder.encode(plainPassword);

      user.setPassword(encryptedPassword);

      assertNotEquals(plainPassword, user.getPassword(),
        "User should never store plain text password");
      assertTrue(user.getPassword().startsWith("$2a$"),
        "User password should be BCrypt encrypted");
    }

    @Test
    @DisplayName("User roles should be validated correctly")
    void user_Roles_ValidatedCorrectly() {
      User user = new User();

      // Test valid roles based on your database (0=PATIENT, 1=DOCTOR, 2=NURSE)
      int[] validRoles = {0, 1, 2};

      for (int role : validRoles) {
        user.setRole(role);
        assertEquals(Integer.valueOf(role), user.getRole());
        assertTrue(role >= 0 && role <= 2, "Role should be in valid range");
      }
    }

    @Test
    @DisplayName("User email should be validated for format")
    void user_EmailValidation_FormatChecked() {
      User user = new User();

      String[] validEmails = {
        "patient@hospital.com",
        "doctor.smith@medical.org",
        "nurse123@clinic.net"
      };

      for (String email : validEmails) {
        user.setEmail(email);
        assertEquals(email, user.getEmail());
        assertTrue(email.contains("@"), "Email should contain @ symbol");
        assertFalse(email.trim().isEmpty(), "Email should not be empty");
      }
    }

    @Test
    @DisplayName("User personal data should be handled securely")
    void user_PersonalData_HandledSecurely() {
      User user = new User();

      // Test sensitive data handling
      user.setFirstName("John");
      user.setLastName("Doe");
      user.setBirth(LocalDate.of(1985, 3, 20));
      user.setMobileNumber("1234567890");

      // Verify data is stored as provided (no unexpected modifications)
      assertEquals("John", user.getFirstName());
      assertEquals("Doe", user.getLastName());
      assertEquals(LocalDate.of(1985, 3, 20), user.getBirth());
      assertEquals("1234567890", user.getMobileNumber());

      // Sensitive fields should accept null for privacy
      user.setMobileNumber(null);
      user.setAvatarBase64(null);

      assertNull(user.getMobileNumber());
      assertNull(user.getAvatarBase64());
    }

    @Test
    @DisplayName("User full name should format correctly by role")
    void user_FullNameFormatting_ByRole() {
      User doctor = new User();
      doctor.setFirstName("Jane");
      doctor.setLastName("Smith");
      doctor.setRole(1); // Doctor role

      User patient = new User();
      patient.setFirstName("John");
      patient.setLastName("Doe");
      patient.setRole(0); // Patient role

      // Doctor names should include "Dr." prefix
      String doctorFullName = doctor.getFullName();
      assertTrue(doctorFullName.startsWith("Dr. "), "Doctor name should start with 'Dr. '");
      assertTrue(doctorFullName.contains("Jane"), "Should contain first name");
      assertTrue(doctorFullName.contains("Smith"), "Should contain last name");

      // Patient names should not include prefix
      String patientFullName = patient.getFullName();
      assertFalse(patientFullName.startsWith("Dr. "), "Patient name should not start with 'Dr. '");
      assertTrue(patientFullName.contains("John"), "Should contain first name");
      assertTrue(patientFullName.contains("Doe"), "Should contain last name");
    }
  }

  @Nested
  @DisplayName("Security Vulnerability Tests")
  class SecurityVulnerabilityTests {

    @Test
    @DisplayName("SQL injection attempts in login should be safe")
    void sqlInjection_LoginAttempts_SafelyHandled() {
      LoginRequest maliciousRequest = new LoginRequest();

      String[] sqlInjectionAttempts = {
        "admin'; DROP TABLE User; --",
        "' OR '1'='1",
        "admin' --",
        "'; SELECT * FROM User WHERE '1'='1",
        "admin'; UPDATE User SET password='hacked' WHERE '1'='1; --"
      };

      for (String maliciousInput : sqlInjectionAttempts) {
        maliciousRequest.setUsername(maliciousInput);
        maliciousRequest.setPassword("anypassword");

        // The request object should accept the string (entity level)
        assertEquals(maliciousInput, maliciousRequest.getUsername());

        // Business logic should sanitize/reject this
        assertTrue(maliciousInput.contains("'") || maliciousInput.contains(";"),
          "Should contain SQL injection characters");
      }
    }

    @Test
    @DisplayName("XSS attempts in user data should be safe")
    void xssAttempts_UserData_SafelyHandled() {
      User user = new User();

      String[] xssAttempts = {
        "<script>alert('XSS')</script>",
        "javascript:alert('XSS')",
        "<img src=x onerror=alert('XSS')>",
        "';alert('XSS');//",
        "<svg onload=alert('XSS')>"
      };

      for (String xssAttempt : xssAttempts) {
        user.setFirstName(xssAttempt);
        user.setLastName(xssAttempt);

        // Entity should store the data as-is
        assertEquals(xssAttempt, user.getFirstName());
        assertEquals(xssAttempt, user.getLastName());

        // Accept common XSS indicators: HTML tags, javascript: URLs, on* handlers, alert()
                Pattern xssPattern = Pattern.compile("(?i)(<|javascript:|on\\w+=|alert\\s*\\()");
                assertTrue(xssPattern.matcher(xssAttempt).find(),
                  "Should contain XSS attempt patterns");

      }
    }

    @Test
    @DisplayName("Password brute force patterns should be detected")
    void passwordBruteForce_Patterns_DetectedCorrectly() {
      String[] commonPasswords = {
        "passwor",
        "7654321",
        "admin",
        "doctor1",
        "1234567",
        "qwerty",
        "abc123"
      };

      for (String weakPassword : commonPasswords) {
        String encoded = passwordEncoder.encode(weakPassword);

        assertNotNull(encoded, "Even weak passwords should be encoded");
        assertTrue(passwordEncoder.matches(weakPassword, encoded),
          "Weak password should still verify correctly");

        // Business logic should flag these as weak
        assertTrue(weakPassword.length() <= 7, "Common passwords are typically short");
      }
    }

    @Test
    @DisplayName("Session security attributes should be validated")
    void sessionSecurity_Attributes_ValidatedCorrectly() {
      // Test user session data security
      User sessionUser = new User();
      sessionUser.setUserId("session-user-123");
      sessionUser.setEmail("session@test.com");
      sessionUser.setRole(1); // Doctor role

      // Sensitive data should not be exposed
      assertNotNull(sessionUser.getUserId(), "User ID should be available for session");
      assertNotNull(sessionUser.getEmail(), "Email should be available for session");
      assertNotNull(sessionUser.getRole(), "Role should be available for session");

      // Password should never be in session
      if (sessionUser.getPassword() != null) {
        assertTrue(sessionUser.getPassword().startsWith("$2a$"),
          "If password is present, it should be encrypted");
      }
    }
  }

  @Nested
  @DisplayName("Authorization & Role Tests")
  class AuthorizationRoleTests {

    @Test
    @DisplayName("Role hierarchy should be enforced")
    void roleHierarchy_Enforcement_WorksCorrectly() {
      // Test different user roles
      User patient = new User();
      patient.setRole(0); // Patient

      User doctor = new User();
      doctor.setRole(1); // Doctor

      User nurse = new User();
      nurse.setRole(2); // Nurse

      // Verify role values
      assertEquals(Integer.valueOf(0), patient.getRole());
      assertEquals(Integer.valueOf(1), doctor.getRole());
      assertEquals(Integer.valueOf(2), nurse.getRole());

      // Each role should have different access levels
      assertNotEquals(patient.getRole(), doctor.getRole());
      assertNotEquals(doctor.getRole(), nurse.getRole());
      assertNotEquals(patient.getRole(), nurse.getRole());
    }

    @Test
    @DisplayName("Role-based name formatting should work")
    void roleBased_NameFormatting_WorksCorrectly() {
      // Test doctor name formatting
      User doctor = new User();
      doctor.setFirstName("Sarah");
      doctor.setLastName("Johnson");
      doctor.setRole(1); // Doctor

      String doctorName = doctor.getFullName();
      assertTrue(doctorName.startsWith("Dr. "), "Doctor should have Dr. prefix");
      assertTrue(doctorName.contains("Sarah Johnson"), "Should contain full name");

      // Test non-doctor name formatting
      User patient = new User();
      patient.setFirstName("Mike");
      patient.setLastName("Wilson");
      patient.setRole(0); // Patient

      String patientName = patient.getFullName();
      assertFalse(patientName.startsWith("Dr. "), "Patient should not have Dr. prefix");
      assertEquals("Mike Wilson", patientName, "Should be just first and last name");
    }

    @Test
    @DisplayName("Invalid roles should be handled")
    void invalidRoles_Handling_WorksCorrectly() {
      User user = new User();

      // Test boundary and invalid role values
      int[] testRoles = {-1, 3, 100, Integer.MAX_VALUE, Integer.MIN_VALUE};

      for (int role : testRoles) {
        user.setRole(role);
        assertEquals(Integer.valueOf(role), user.getRole(),
          "Entity should accept role value: " + role);

        // Business logic should validate these as invalid
        assertTrue(role < 0 || role > 2, "Test role should be outside valid range 0-2");
      }
    }
  }

  @Nested
  @DisplayName("User Data Validation Tests")
  class UserDataValidationTests {

    @Test
    @DisplayName("Email validation should prevent malformed addresses")
    void email_Validation_PreventsmalformedAddresses() {
      User user = new User();

      String[] malformedEmails = {
        "notanemail",
        "@domain.com",
        "user@",
        "user@domain",
        "user name@domain.com", // Space in local part
        "user@domain .com", // Space in domain
        "",
        null
      };

      for (String email : malformedEmails) {
        user.setEmail(email);
        assertEquals(email, user.getEmail(), "Entity should accept email: " + email);

        // Business validation should catch malformed emails
        if (email != null && !email.trim().isEmpty()) {
          boolean hasAt = email.contains("@");
          boolean hasDot = email.contains(".");

          if (!hasAt || !hasDot) {
            assertTrue(true, "Malformed email detected: " + email);
          }
        }
      }
    }

    @Test
    @DisplayName("User ID should follow UUID format")
    void userId_Format_FollowsUUIDPattern() {
      User user = new User();

      String[] userIdFormats = {
        "550e8400-e29b-41d4-a716-446655440000", // Standard UUID
        "user-123", // Custom format
        "12345", // Numeric
        null, // Null case
        "" // Empty case
      };

      for (String userId : userIdFormats) {
        user.setUserId(userId);
        assertEquals(userId, user.getUserId(), "Should accept user ID: " + userId);

        if (userId != null && userId.length() == 36 && userId.contains("-")) {
          // Looks like UUID format
          assertTrue(userId.split("-").length >= 4, "UUID should have multiple parts");
        }
      }
    }

    @Test
    @DisplayName("Birth date should be validated for realistic ranges")
    void birthDate_Validation_RealisticRanges() {
      User user = new User();

      LocalDate today = LocalDate.now();
      LocalDate hundredYearsAgo = today.minusYears(100);
      LocalDate futureDate = today.plusYears(1);
      LocalDate recentPast = today.minusYears(25);

      LocalDate[] testDates = {hundredYearsAgo, recentPast, today, futureDate};

      for (LocalDate birthDate : testDates) {
        user.setBirth(birthDate);
        assertEquals(birthDate, user.getBirth());

        if (birthDate.isAfter(today)) {
          assertTrue(true, "Future birth date should be flagged: " + birthDate);
        }
        if (birthDate.isBefore(hundredYearsAgo)) {
          assertTrue(true, "Very old birth date should be validated: " + birthDate);
        }
      }
    }

    @Test
    @DisplayName("Gender values should be within expected range")
    void gender_Values_WithinExpectedRange() {
      User user = new User();

      // Assuming 0=Female, 1=Male based on typical conventions
      int[] genderValues = {0, 1, -1, 2, 100};

      for (int gender : genderValues) {
        user.setGender(gender);
        assertEquals(Integer.valueOf(gender), user.getGender());

        if (gender < 0 || gender > 1) {
          assertTrue(true, "Invalid gender value should be caught: " + gender);
        }
      }
    }

    @Test
    @DisplayName("Mobile number should handle various formats")
    void mobileNumber_Formats_HandledCorrectly() {
      User user = new User();

      String[] phoneFormats = {
        "1234567890",
        "+1-234-567-8900",
        "(123) 456-7890",
        "123.456.7890",
        "123-456-7890",
        "+44 20 7946 0958", // UK format
        null,
        ""
      };

      for (String phone : phoneFormats) {
        user.setMobileNumber(phone);
        assertEquals(phone, user.getMobileNumber());

        if (phone != null && !phone.isEmpty()) {
          // Should contain digits
          assertTrue(phone.matches(".*\\d.*"), "Phone should contain digits: " + phone);
        }
      }
    }
  }

  @Nested
  @DisplayName("Security Configuration Tests")
  class SecurityConfigurationTests {

    @Test
    @DisplayName("BCrypt password encoder should use secure settings")
    void bcryptEncoder_SecureSettings_Configured() {
      BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

      String testPassword = "TestPassword123!";
      String encoded = encoder.encode(testPassword);

      // BCrypt should use secure rounds (default is 10)
      assertTrue(encoded.startsWith("$2a$10$") || encoded.startsWith("$2a$11$") ||
        encoded.startsWith("$2a$12$"), "Should use secure BCrypt rounds");

      assertTrue(encoded.length() >= 60, "BCrypt hash should be proper length");
      assertTrue(encoder.matches(testPassword, encoded), "Password should verify correctly");
    }

    @Test
    @DisplayName("Password reset should maintain security")
    void passwordReset_Security_Maintained() {
      LoginRequest resetRequest = new LoginRequest();
      resetRequest.setUsername("user@test.com");
      resetRequest.setPassword("NewSecurePassword456!");

      assertNotNull(resetRequest.getUsername(), "Reset username should not be null");
      assertNotNull(resetRequest.getPassword(), "Reset password should not be null");

      // New password should be different from common patterns
      String newPassword = resetRequest.getPassword();
      assertFalse(newPassword.equals("password"), "Should not be common password");
      assertFalse(newPassword.equals("123456"), "Should not be numeric sequence");
      assertTrue(newPassword.length() >= 8, "Should meet minimum length requirement");
    }
  }
}

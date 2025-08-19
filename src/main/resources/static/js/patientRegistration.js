
document.addEventListener('DOMContentLoaded', function() {
  // Profile picture upload handlers
  document.getElementById('profilePictureInput').addEventListener('change', handleProfilePictureUpload);
  document.getElementById('cameraInput').addEventListener('change', handleProfilePictureUpload);

  // Form validation
  setupFormValidation();

  // Setup email validation
  setupEmailValidation();
});

function handleProfilePictureUpload(event) {
  const file = event.target.files[0];
  if (file) {
    // Validate file size (5MB limit)
    if (file.size > 5 * 1024 * 1024) {
      alert('Profile picture must be less than 5MB');
      return;
    }

    // Validate file type
    const validTypes = ['image/jpeg', 'image/png', 'image/gif', 'image/webp'];
    if (!validTypes.includes(file.type)) {
      alert('Please select a valid image file (JPEG, PNG, GIF)');
      return;
    }

    const reader = new FileReader();
    reader.onload = function(e) {
      const preview = document.getElementById('profilePreview');
      const placeholder = document.getElementById('profilePlaceholder');

      preview.src = e.target.result;
      preview.style.display = 'block';
      placeholder.style.display = 'none';

      clearError('profile');
    };
    reader.readAsDataURL(file);
  }
}

/**
 * Setup email validation
 */
function setupEmailValidation() {
  const emailInput = document.getElementById('email');

  emailInput.addEventListener('input', function() {
    const email = this.value.trim();

    // Clear previous errors
    clearError('email');

    if (email && isValidEmailFormat(email)) {
      // Show green border for valid email format
      document.getElementById('email').style.borderColor = '#28A745';
    } else if (email) {
      // Show red border for invalid email format
      document.getElementById('email').style.borderColor = '#FF6B6B';
    } else {
      // Reset border color when empty
      document.getElementById('email').style.borderColor = '';
    }
  });
}

function setupFormValidation() {
  const form = document.getElementById('patientRegistrationForm');

  // Real-time validation for password confirmation
  const password = document.getElementById('password');
  const confirmPassword = document.getElementById('confirmPassword');

  confirmPassword.addEventListener('input', function() {
    if (password.value !== confirmPassword.value) {
      showError('confirmPassword', 'Passwords do not match');
    } else {
      clearError('confirmPassword');
    }
  });

  // National ID validation
  const nationalId = document.getElementById('nationalId');
  nationalId.addEventListener('input', function() {
    const value = this.value.replace(/\D/g, '');
    this.value = value.length > 13 ? value.substr(0, 13) : value;
    if (value.length !== 13 && value.length > 0) {
      showError('nationalId', 'National ID must be 13 digits');
    } else {
      clearError('nationalId');
    }
  });

  // Mobile number validation
  const mobileNumber = document.getElementById('mobileNumber');
  mobileNumber.addEventListener('input', function() {
    const value = this.value.replace(/\D/g, '');
    this.value = value.length > 9 ? value.substr(0, 9) : value;
    if (value.length !== 9 && value.length > 0) {
      showError('mobileNumber', 'Mobile number must be 9 digits');
    } else {
      clearError('mobileNumber');
    }
  });

  // Emergency phone validation
  const emergencyPhone = document.getElementById('emergencyPhone');
  emergencyPhone.addEventListener('input', function() {
    const value = this.value.replace(/\D/g, '');
    this.value = value.length > 9 ? value.substr(0, 9) : value;
    if (value.length !== 9 && value.length > 0) {
      showError('emergencyPhone', 'Phone number must be 9 digits');
    } else {
      clearError('emergencyPhone');
    }
  });

  // Modify form submission
  form.addEventListener('submit', function(e) {
    e.preventDefault();

    if (validateForm()) {
      handleSuccessfulRegistration();
    }
  });
}

/**
 * Enhanced form validation
 */
function validateForm() {
  let isValid = true;

  // Get all required form fields
  const requiredFields = [
    'firstName', 'lastName', 'dateOfBirth', 'gender', 'bloodType', 'height', 'weight',
    'homeAddress', 'nationalId', 'email', 'mobileNumber', 'nearestClinic',
    'password', 'confirmPassword', 'emergencyFirstName', 'emergencyLastName',
    'emergencyRelationship', 'emergencyPhone'
  ];

  // Check each required field
  requiredFields.forEach(fieldName => {
    const field = document.getElementById(fieldName);
    if (field && !field.value.trim()) {
      showError(fieldName, 'This field is required');
      isValid = false;
    } else if (field && field.value.trim()) {
      clearError(fieldName);
    }
  });

  // Special validation for password match
  const password = document.getElementById('password').value;
  const confirmPassword = document.getElementById('confirmPassword').value;
  if (password && confirmPassword && password !== confirmPassword) {
    showError('confirmPassword', 'Passwords do not match');
    isValid = false;
  }

  // Password strength validation
  if (password && password.length < 8) {
    showError('password', 'Password must be at least 8 characters long');
    isValid = false;
  }

  // Email validation
  const email = document.getElementById('email').value;
  if (email && !isValidEmailFormat(email)) {
    showError('email', 'Please enter a valid email address');
    isValid = false;
  }

  // Height and weight validation
  const height = parseFloat(document.getElementById('height').value);
  const weight = parseFloat(document.getElementById('weight').value);

  if (height && (height < 50 || height > 250)) {
    showError('height', 'Please enter a valid height (50-250 cm)');
    isValid = false;
  }

  if (weight && (weight < 10 || weight > 300)) {
    showError('weight', 'Please enter a valid weight (10-300 kg)');
    isValid = false;
  }

  return isValid;
}

/**
 * New registration success handling function
 */
function handleSuccessfulRegistration() {
  // Disable submit button to prevent double submission
  const submitBtn = document.querySelector('.btn-register');
  const originalText = submitBtn.textContent;
  submitBtn.disabled = true;
  submitBtn.textContent = 'Registering...';

  // Simulated processing time
  setTimeout(() => {
    // Show success message
    alert('Registration successful! You will be redirected to the homepage.');

    // Redirect to HomePage - Based on the root path mapping of HomeController
    window.location.href = '/';

  }, 1500); //  1.5-second delay in displaying processing status
}

/**
 * Helper function to validate email format
 */
function isValidEmailFormat(email) {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
}

function showError(fieldName, message) {
  // Handle general registration errors with simple alert
  if (fieldName === 'registration') {
    alert(message);
    return;
  }

  // Handle profile picture errors with simple alert
  if (fieldName === 'profile') {
    alert(message);
    return;
  }

  const errorElement = document.getElementById(fieldName + '-error');
  if (errorElement) {
    errorElement.textContent = message;
    errorElement.style.display = 'block';
  }

  const field = document.getElementById(fieldName);
  if (field) {
    field.classList.add('error');
  }
}

function clearError(fieldName) {
  const errorElement = document.getElementById(fieldName + '-error');
  if (errorElement) {
    errorElement.style.display = 'none';
  }

  const field = document.getElementById(fieldName);
  if (field) {
    field.classList.remove('error');
    field.style.borderColor = ''; // Reset border color
  }
}

// Add simple CSS for button states only
const style = document.createElement('style');
style.textContent = `
  .btn-register:disabled {
    background: #DDD !important;
    cursor: not-allowed;
  }
`;
document.head.appendChild(style);

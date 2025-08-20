document.addEventListener('DOMContentLoaded', function() {
  document.getElementById('profilePictureInput').addEventListener('change', handleProfilePictureUpload);
  document.getElementById('cameraInput').addEventListener('change', handleProfilePictureUpload);

  const form = document.getElementById('registrationForm');
  form.addEventListener('submit', function(e) {
    e.preventDefault();
    handleRegistrationSubmit();
  });

  setupRealTimeValidation();
});

function setupRealTimeValidation() {
  const inputs = document.querySelectorAll('input, select');
  inputs.forEach(input => {
    input.addEventListener('blur', function() {
      validateField(this);
    });

    input.addEventListener('input', function() {
      const errorElement = document.getElementById(`${this.id}Error`);
      if (errorElement) {
        errorElement.textContent = '';
        this.classList.remove('error');
      }
    });
  });

  const password = document.getElementById('password');
  const confirmPassword = document.getElementById('confirmPassword');

  confirmPassword.addEventListener('input', function() {
    if (password.value !== confirmPassword.value) {
      showError('confirmPassword', 'Passwords do not match');
    } else {
      clearError('confirmPassword');
    }
  });
}

function showError(fieldId, message) {
  const errorElement = document.getElementById(`${fieldId}Error`);
  const field = document.getElementById(fieldId);

  if (errorElement && field) {
    errorElement.textContent = message;
    field.classList.add('error');
  }
}

function clearError(fieldId) {
  const errorElement = document.getElementById(`${fieldId}Error`);
  const field = document.getElementById(fieldId);

  if (errorElement && field) {
    errorElement.textContent = '';
    field.classList.remove('error');
  }
}

function validateField(field) {
  const value = field.value.trim();
  const errorElement = document.getElementById(`${field.id}Error`);

  if (!errorElement) return true;

  errorElement.textContent = '';
  field.classList.remove('error');

  if (field.hasAttribute('required') && !value) {
    showError(field.id, 'This field is required');
    return false;
  }

  switch(field.id) {
    case 'email':
      if (!isValidEmail(value)) {
        showError(field.id, 'Please enter a valid email address');
        return false;
      }
      break;

    case 'password':
      if (value.length < 8) {
        showError(field.id, 'Password must be at least 8 characters');
        return false;
      }
      break;

    case 'confirmPassword':
      const password = document.getElementById('password').value;
      if (value !== password) {
        showError(field.id, 'Passwords do not match');
        return false;
      }
      break;

    case 'mobileNumber':
    case 'workNumber':
      if (value && !isValidPhoneNumber(value)) {
        showError(field.id, 'Please enter a valid phone number');
        return false;
      }
      break;

    case 'birth':
      if (value && !isValidbirth(value)) {
        showError(field.id, 'Please enter a valid date of birth');
        return false;
      }
      break;
  }

  return true;
}

function isValidbirth(dateString) {
  const birthDate = new Date(dateString);
  const today = new Date();
  const minDate = new Date();
  minDate.setFullYear(today.getFullYear() - 100); // 100年前

  return birthDate <= today && birthDate >= minDate;
}

function validateForm() {
  let isValid = true;
  const inputs = document.querySelectorAll('input[required], select[required]');

  inputs.forEach(input => {
    if (!validateField(input)) {
      isValid = false;
    }
  });

  const password = document.getElementById('password');
  const confirmPassword = document.getElementById('confirmPassword');

  if (password && confirmPassword && password.value !== confirmPassword.value) {
    showError('confirmPassword', 'Passwords do not match');
    isValid = false;
  }

  return isValid;
}

function isValidEmail(email) {
  const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return re.test(email);
}

function isValidPhoneNumber(phone) {
  const re = /^[+]?[(]?[0-9]{1,4}[)]?[-\s./0-9]*$/;
  return re.test(phone);
}

function handleProfilePictureUpload(event) {
  const file = event.target.files[0];
  if (file) {
    if (!file.type.startsWith('image/')) {
      alert('Please select an image file');
      return;
    }

    if (file.size > 5 * 1024 * 1024) {
      alert('Image size should be less than 5MB');
      return;
    }

    const reader = new FileReader();
    reader.onload = function(e) {
      const preview = document.getElementById('profilePreview');
      const placeholder = document.getElementById('profilePlaceholder');

      preview.src = e.target.result;
      preview.style.display = 'block';
      placeholder.style.display = 'none';
    };
    reader.readAsDataURL(file);
  }
}

function collectFormData() {
  const formData = {
    firstName: document.getElementById('firstName').value.trim(),
    lastName: document.getElementById('lastName').value.trim(),
    birth: document.getElementById('birth').value,
    gender: parseInt(document.getElementById('gender').value),
    email: document.getElementById('email').value.trim(),
    password: document.getElementById('password').value,
    role: parseInt(document.getElementById('position').value),
    mobileNumber: document.getElementById('mobileNumber').value.trim(),
    department: parseInt(document.getElementById('department').value),
    employeeId: document.getElementById('employeeId').value.trim(),
    location: document.getElementById('location').value.trim(),
    workNumber: document.getElementById('workNumber').value.trim() || null
  };

  const profilePreview = document.getElementById('profilePreview');
  if (profilePreview.style.display !== 'none') {
    formData.avatarBase64 = profilePreview.src;
  }

  return formData;
}

async function handleRegistrationSubmit() {
  if (!validateForm()) {
    alert('Please correct the errors in the form');
    return;
  }

  const submitBtn = document.getElementById('submitBtn');
  const originalText = submitBtn.textContent;
  submitBtn.disabled = true;
  submitBtn.textContent = 'REGISTERING...';

  try {
    const formData = collectFormData();

    const response = await fetch('/user/register/employee', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formData)
    });

    const data = await response.json();

    if (!response.ok) {
      throw new Error(data.message || 'Registration failed');
    }

    if (data.code === "0") {
      // Registration successful
      handleSuccessfulRegistration();
    } else {
      // Registration failed with server error
      throw new Error(data.message || 'Registration failed');
    }
  } catch (error) {
    // Handle errors
    console.error('Error:', error);
    alert('Registration failed: ' + error.message);
  } finally {
    // Re-enable the submit button
    submitBtn.disabled = false;
    submitBtn.textContent = originalText;
  }
}

function handleSuccessfulRegistration() {
  alert('Registration successful! You can now login with your credentials.');
  window.location.href = '/';
}

function cancelRegistration() {
  if (confirm('Are you sure you want to cancel registration? All entered data will be lost.')) {
    window.location.href = '/';
  }
}

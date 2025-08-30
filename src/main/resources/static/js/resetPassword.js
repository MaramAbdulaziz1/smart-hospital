/**
 * Enhanced Reset Password Page JavaScript with Backend Connection
 */

document.addEventListener('DOMContentLoaded', function() {
  const form = document.querySelector('.reset-form');
  const newPasswordInput = document.getElementById('newPassword');
  const confirmPasswordInput = document.getElementById('confirmPassword');
  const emailInput = document.getElementById('email');

  // Setup English validation messages
  emailInput.addEventListener('invalid', function(e) {
    if (this.validity.valueMissing) {
      this.setCustomValidity('Please enter your email address');
    } else if (this.validity.typeMismatch) {
      this.setCustomValidity('Please enter a valid email address');
    }
  });

  newPasswordInput.addEventListener('invalid', function(e) {
    if (this.validity.valueMissing) {
      this.setCustomValidity('Please enter a new password');
    } else if (this.validity.tooShort) {
      this.setCustomValidity('Password must be at least 8 characters long');
    } else if (this.validity.tooLong) {
      this.setCustomValidity('Password must not exceed 16 characters');
    }
  });

  confirmPasswordInput.addEventListener('invalid', function(e) {
    if (this.validity.valueMissing) {
      this.setCustomValidity('Please confirm your password');
    } else if (this.validity.tooShort) {
      this.setCustomValidity('Password must be at least 8 characters long');
    } else if (this.validity.tooLong) {
      this.setCustomValidity('Password must not exceed 16 characters');
    }
  });

  // Clear custom validation messages
  [emailInput, newPasswordInput, confirmPasswordInput].forEach(input => {
    input.addEventListener('input', function() {
      this.setCustomValidity('');
    });
  });

  // Real-time password match validation
  confirmPasswordInput.addEventListener('input', function() {
    const newPassword = newPasswordInput.value;
    const confirmPassword = this.value;

    if (confirmPassword.length > 0) {
      if (newPassword !== confirmPassword) {
        this.style.borderColor = '#e74c3c';
        this.style.background = '#ffeaa7';
      } else {
        this.style.borderColor = '#27ae60';
        this.style.background = '#d5f4e6';
      }
    } else {
      this.style.borderColor = '#ddd';
      this.style.background = '#f9f9f9';
    }
  });

  //  Enhanced email validation with backend check
  if (emailInput) {
    emailInput.addEventListener('blur', async function() {
      const email = this.value.trim();
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

      if (email && emailRegex.test(email)) {
        try {
          // 🔗 Call backend API to check if email exists
          const url = new URL('/user/checkEmail', window.location.origin);
          url.searchParams.append('email', email);

          const response = await fetch(url, {
            method: 'GET',
            headers: {
              'Content-Type': 'application/json'
            }
          });

          const result = await response.json();

          if (result.code === '0') {
            this.style.borderColor = '#27ae60';
            this.style.background = '#d5f4e6';
            clearEmailError();
          } else {
            this.style.borderColor = '#e74c3c';
            this.style.background = '#ffeaa7';
            showEmailError('Email address not found in our system');
          }
        } catch (error) {
          console.error('Error checking email:', error);
          // Network error - neutral style
          this.style.borderColor = '#ddd';
          this.style.background = '#f9f9f9';
          showEmailError('Unable to verify email. Please try again.');
        }
      } else if (email && !emailRegex.test(email)) {
        // Invalid email format
        this.style.borderColor = '#e74c3c';
        this.style.background = '#ffeaa7';
        showEmailError('Please enter a valid email address');
      } else {
        // Empty email - reset to default
        this.style.borderColor = '#ddd';
        this.style.background = '#f9f9f9';
        clearEmailError();
      }
    });
  }

  //  Enhanced form submission with backend API call
  if (form) {
    form.addEventListener('submit', async function(e) {
      e.preventDefault(); // Prevent default form submission

      const email = emailInput.value.trim();
      const newPassword = newPasswordInput.value;
      const confirmPassword = confirmPasswordInput.value;

      // Client-side validation
      if (!email) {
        showError('Please enter your email address.');
        emailInput.focus();
        return false;
      }

      if (newPassword !== confirmPassword) {
        showError('Passwords do not match. Please try again.');
        confirmPasswordInput.focus();
        return false;
      }

      if (newPassword.length < 8 || newPassword.length > 16) {
        showError('Password must be between 8 and 16 characters long.');
        newPasswordInput.focus();
        return false;
      }

      // Show loading state
      const submitBtn = form.querySelector('button[type="submit"]');
      const originalText = submitBtn.textContent;
      submitBtn.textContent = 'RESETTING...';
      submitBtn.disabled = true;

      try {
        // 🔗 Call backend API to reset password
        const response = await fetch('/user/resetPassword', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            username: email,
            password: newPassword
          })
        });

        const result = await response.json();

        if (result.code === '0') {
          // Success - show success message and redirect
          showSuccess('Password reset successfully! Redirecting to login page...');

          setTimeout(() => {
            alert('Password reset successfully');
            window.location.href = '/';
          }, 1000);

        } else {
          // Error from backend
          showError(result.message || 'Password reset failed. Please try again.');
        }

      } catch (error) {
        console.error('Error resetting password:', error);
        showError('Network error. Please check your connection and try again.');
      } finally {
        // Restore button state
        submitBtn.textContent = originalText;
        submitBtn.disabled = false;
      }
    });
  }

  // Show general error message
  function showError(message) {
    // Remove existing messages
    clearMessages();

    // Create new error message
    const errorDiv = document.createElement('div');
    errorDiv.className = 'error-message';
    errorDiv.textContent = message;
    errorDiv.style.cssText = `
      background: #ffebee;
      color: #c62828;
      padding: 15px;
      border-radius: 5px;
      margin-bottom: 15px;
      border-left: 4px solid #e53935;
      font-weight: 500;
      animation: slideIn 0.3s ease-out;
    `;

    // Insert before button group
    const buttonGroup = document.querySelector('.button-group');
    buttonGroup.parentNode.insertBefore(errorDiv, buttonGroup);

    // Auto-remove after 5 seconds
    setTimeout(() => {
      if (errorDiv.parentNode) {
        errorDiv.remove();
      }
    }, 5000);
  }

  //  Show success message
  function showSuccess(message) {
    // Remove existing messages
    clearMessages();

    // Create new success message
    const successDiv = document.createElement('div');
    successDiv.className = 'success-message';
    successDiv.textContent = message;
    successDiv.style.cssText = `
      background: #e8f5e8;
      color: #2e7d32;
      padding: 15px;
      border-radius: 5px;
      margin-bottom: 15px;
      border-left: 4px solid #4caf50;
      font-weight: 500;
      animation: slideIn 0.3s ease-out;
    `;

    // Insert before button group
    const buttonGroup = document.querySelector('.button-group');
    buttonGroup.parentNode.insertBefore(successDiv, buttonGroup);
  }

  // Show email-specific error
  function showEmailError(message) {
    clearEmailError();

    const errorSpan = document.createElement('span');
    errorSpan.className = 'email-error';
    errorSpan.textContent = message;
    errorSpan.style.cssText = `
      color: #e74c3c;
      font-size: 12px;
      margin-top: 5px;
      display: block;
      animation: fadeIn 0.3s ease-out;
    `;

    emailInput.parentNode.appendChild(errorSpan);
  }

  // Clear email error
  function clearEmailError() {
    const existingError = document.querySelector('.email-error');
    if (existingError) {
      existingError.remove();
    }
  }

  // Clear all messages
  function clearMessages() {
    const existingError = document.querySelector('.error-message');
    const existingSuccess = document.querySelector('.success-message');
    if (existingError) existingError.remove();
    if (existingSuccess) existingSuccess.remove();
  }
});

// Go back function
function goBack() {
  if (window.history.length > 1) {
    window.history.back();
  } else {
    // Fallback to login page
    window.location.href = '/login';
  }
}

// Expose function to global scope for HTML onclick attribute
window.goBack = goBack;

//  Add CSS animations
const style = document.createElement('style');
style.textContent = `
  @keyframes slideIn {
    from {
      opacity: 0;
      transform: translateY(-10px);
    }
    to {
      opacity: 1;
      transform: translateY(0);
    }
  }

  @keyframes fadeIn {
    from {
      opacity: 0;
    }
    to {
      opacity: 1;
    }
  }
`;
document.head.appendChild(style);

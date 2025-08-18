/**
 * Enhanced Doctor Account Page JavaScript with Backend Connection
 *
 */

// Store original data for restoring when canceling edits
const originalData = {
  personal: {},
  hospital: {}
};

/**
 * Initialize after page loading is complete
 */
document.addEventListener('DOMContentLoaded', function() {
  initializePage();
});

/**
 * Initialize the page
 */
async function initializePage() {
  // 🆕 Load doctor profile data from backend first
  await loadDoctorProfile();

  // Then save the loaded data as "original" for editing functionality
  saveOriginalData('personal');
  saveOriginalData('hospital');
  bindEventListeners();
}

/**
 *  Load doctor profile data from backend and replace static HTML data
 */
async function loadDoctorProfile() {
  try {
    showLoading(true);

    // Call backend API to get doctor profile
    const response = await fetch('/api/doctor/profile', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    });

    const result = await response.json();

    if (result.code === '0' && result.data) {
      //  Replace static HTML data with real backend data
      replaceStaticDataWithRealData(result.data);
    } else {
      console.warn('Failed to load profile data, using static HTML data as fallback');
      // Keep using static HTML data if API fails
    }
  } catch (error) {
    console.error('Error loading doctor profile:', error);
    console.warn('Network error, using static HTML data as fallback');
    // Keep using static HTML data if network fails
  } finally {
    showLoading(false);
  }
}

/**
 * Replace static HTML data with real backend data
 */
function replaceStaticDataWithRealData(data) {
  // Update profile header section (replace "Ginny Weasley" with real name)
  if (data.firstName && data.lastName) {
    const profileName = document.querySelector('.profile-name1');
    if (profileName) {
      profileName.textContent = `${data.firstName} ${data.lastName}`;
    }
  }

  if (data.workLocation) {
    const profileLocation = document.querySelector('.profile-location1');
    if (profileLocation) {
      profileLocation.textContent = data.workLocation;
    }
  }

  // Replace personal information form data
  const personalForm = document.getElementById('personalForm');
  if (personalForm) {
    const firstNameInput = personalForm.querySelector('[name="firstName"]');
    const lastNameInput = personalForm.querySelector('[name="lastName"]');
    const emailInput = personalForm.querySelector('[name="email"]');
    const phoneInput = personalForm.querySelector('[name="phoneNumber"]');
    const genderSelect = personalForm.querySelector('[name="gender"]');

    // Replace static values with real data
    if (data.firstName && firstNameInput) firstNameInput.value = data.firstName;
    if (data.lastName && lastNameInput) lastNameInput.value = data.lastName;
    if (data.email && emailInput) emailInput.value = data.email;
    if (data.phoneNumber && phoneInput) phoneInput.value = data.phoneNumber;
    if (data.gender && genderSelect) genderSelect.value = data.gender;

    // Update birth date (find the readonly input after "Date of Birth" label)
    if (data.birthDate) {
      const dobInput = Array.from(personalForm.querySelectorAll('input[readonly]'))
        .find(input => {
          const label = input.parentElement.querySelector('.info-label');
          return label && label.textContent === 'Date of Birth';
        });
      if (dobInput) {
        dobInput.value = formatDate(data.birthDate);
      }
    }
  }

  // Replace hospital information form data
  const hospitalForm = document.getElementById('hospitalForm');
  if (hospitalForm) {
    const inputs = hospitalForm.querySelectorAll('input[readonly]');

    // Replace department (2nd input - index 1)
    if (data.department && inputs[1]) {
      inputs[1].value = data.department;
    }

    // Replace employee ID (3rd input - index 2)
    if (data.employeeId && inputs[2]) {
      inputs[2].value = data.employeeId;
    }

    // Replace work location (4th input - index 3)
    if (data.workLocation && inputs[3]) {
      inputs[3].value = data.workLocation;
    }

    // Replace date joined (5th input - index 4)
    if (data.dateJoined && inputs[4]) {
      inputs[4].value = formatDate(data.dateJoined);
    }
  }

  // Update profile avatar if available
  if (data.profileImageUrl) {
    updateProfileAvatar(data.profileImageUrl);
  }
}

/**
 *  Format date for display (DD/MM/YYYY)
 */
function formatDate(dateString) {
  if (!dateString) return '';
  const date = new Date(dateString);
  const day = String(date.getDate()).padStart(2, '0');
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const year = date.getFullYear();
  return `${day}/${month}/${year}`;
}

/**
 * Bind event listeners
 */
function bindEventListeners() {
  // Personal information form submit event
  document.getElementById('personalForm').addEventListener('submit', handlePersonalFormSubmit);

  // Hospital information form submit event
  document.getElementById('hospitalForm').addEventListener('submit', handleHospitalFormSubmit);

  // Edit avatar button event
  document.querySelector('.edit-photo-btn').addEventListener('click', handleEditPhoto);
}

/**
 * Save original data
 * @param {string} section - Form section (personal or hospital)
 */
function saveOriginalData(section) {
  const form = document.getElementById(section + 'Form');
  const inputs = form.querySelectorAll('input, select');
  originalData[section] = {};

  inputs.forEach(input => {
    if (input.name) {
      originalData[section][input.name] = input.value;
    }
  });
}

/**
 * Toggle edit mode
 * @param {string} section - Form section (personal or hospital)
 */
function toggleEdit(section) {
  const form = document.getElementById(section + 'Form');
  const inputs = form.querySelectorAll('input, select');
  const actions = document.getElementById(section + 'Actions');
  const editBtn = document.querySelector(`[onclick="toggleEdit('${section}')"]`);

  if (inputs[0].readOnly || inputs[0].disabled) {
    // Enter edit mode
    enableEditing(inputs, actions, editBtn, section);
  } else {
    // Cancel edit
    cancelEdit(section);
  }
}

/**
 * Enable editing mode
 * @param {NodeList} inputs - List of input elements
 * @param {Element} actions - Action button container
 * @param {Element} editBtn - Edit button
 * @param {string} section - Form section
 */
function enableEditing(inputs, actions, editBtn, section) {
  inputs.forEach(input => {
    // Date of Birth and some hospital fields are not editable
    const label = input.parentElement.querySelector('.info-label')?.textContent;
    const nonEditableFields = ['Date of Birth', 'Position', 'Employee ID', 'Date Joined'];

    if (!nonEditableFields.includes(label)) {
      input.readOnly = false;
      input.disabled = false;
      input.classList.add('editable');
    }
  });

  actions.style.display = 'flex';
  editBtn.textContent = 'Cancel';
  editBtn.classList.add('editing');
  editBtn.setAttribute('onclick', `cancelEdit('${section}')`);
}

/**
 * Cancel edit
 * @param {string} section - Form section (personal or hospital)
 */
function cancelEdit(section) {
  const form = document.getElementById(section + 'Form');
  const inputs = form.querySelectorAll('input, select');
  const actions = document.getElementById(section + 'Actions');
  const editBtn = document.querySelector(`[onclick="cancelEdit('${section}')"]`);

  // Restore to readonly state and revert to original data
  inputs.forEach(input => {
    input.readOnly = true;
    input.disabled = true;
    input.classList.remove('editable');

    if (input.name && originalData[section][input.name]) {
      input.value = originalData[section][input.name];
    }
  });

  // Hide action buttons and reset edit button
  actions.style.display = 'none';
  editBtn.textContent = 'Edit';
  editBtn.classList.remove('editing');
  editBtn.setAttribute('onclick', `toggleEdit('${section}')`);
}

/**
 * Handle personal information form submission with API call
 * @param {Event} e - Form submit event
 */
async function handlePersonalFormSubmit(e) {
  e.preventDefault();

  if (!validatePersonalForm()) {
    return;
  }

  try {
    showLoading(true, 'Saving personal information...');

    // Collect form data
    const form = document.getElementById('personalForm');
    const formData = {
      firstName: form.querySelector('[name="firstName"]').value.trim(),
      lastName: form.querySelector('[name="lastName"]').value.trim(),
      email: form.querySelector('[name="email"]').value.trim(),
      phoneNumber: form.querySelector('[name="phoneNumber"]').value.trim(),
      gender: form.querySelector('[name="gender"]').value
    };

    //  Call backend API to update personal information
    const response = await fetch('/api/doctor/profile/personal', {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(formData)
    });

    const result = await response.json();

    if (result.code === '0') {
      showSuccess('Personal information saved successfully!');
      cancelEdit('personal');
      saveOriginalData('personal');

      // Update profile header with new name
      document.querySelector('.profile-name1').textContent = `${formData.firstName} ${formData.lastName}`;
    } else {
      showError('Failed to save personal information: ' + (result.message || 'Unknown error'));
    }
  } catch (error) {
    console.error('Error saving personal information:', error);
    showError('Network error while saving personal information.');
  } finally {
    showLoading(false);
  }
}

/**
 *  Handle hospital information form submission with API call
 * @param {Event} e - Form submit event
 */
async function handleHospitalFormSubmit(e) {
  e.preventDefault();

  if (!validateHospitalForm()) {
    return;
  }

  try {
    showLoading(true, 'Saving hospital information...');

    // Collect editable hospital data
    const form = document.getElementById('hospitalForm');
    const inputs = form.querySelectorAll('input');
    const formData = {
      department: inputs[1].value.trim(), // Department field
      workLocation: inputs[3].value.trim() // Work Location field
    };

    // 🔗 Call backend API to update hospital information
    const response = await fetch('/api/doctor/profile/hospital', {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(formData)
    });

    const result = await response.json();

    if (result.code === '0') {
      showSuccess('Hospital information saved successfully!');
      cancelEdit('hospital');
      saveOriginalData('hospital');

      // Update profile location
      document.querySelector('.profile-location1').textContent = formData.workLocation;
    } else {
      showError('Failed to save hospital information: ' + (result.message || 'Unknown error'));
    }
  } catch (error) {
    console.error('Error saving hospital information:', error);
    showError('Network error while saving hospital information.');
  } finally {
    showLoading(false);
  }
}

/**
 * Validate personal information form
 * @returns {boolean} - Whether validation passes
 */
function validatePersonalForm() {
  const form = document.getElementById('personalForm');
  const firstName = form.querySelector('[name="firstName"]').value.trim();
  const lastName = form.querySelector('[name="lastName"]').value.trim();
  const email = form.querySelector('[name="email"]').value.trim();
  const phoneNumber = form.querySelector('[name="phoneNumber"]').value.trim();

  if (!firstName || !lastName) {
    showError('First name and last name are required.');
    return false;
  }

  if (!isValidEmail(email)) {
    showError('Please enter a valid email address.');
    return false;
  }

  if (!phoneNumber) {
    showError('Phone number is required.');
    return false;
  }

  return true;
}

/**
 * Validate hospital information form
 * @returns {boolean} - Whether validation passes
 */
function validateHospitalForm() {
  const form = document.getElementById('hospitalForm');
  const inputs = form.querySelectorAll('input');
  const department = inputs[1].value.trim();
  const workLocation = inputs[3].value.trim();

  if (!department) {
    showError('Department is required.');
    return false;
  }

  if (!workLocation) {
    showError('Work location is required.');
    return false;
  }

  return true;
}

/**
 * Validate email format
 * @param {string} email - Email address
 * @returns {boolean} - Whether it's a valid email
 */
function isValidEmail(email) {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
}

/**
 *  Handle edit photo functionality with file upload
 */
async function handleEditPhoto() {
  const fileInput = createFileInput();

  fileInput.addEventListener('change', async function(e) {
    const file = e.target.files[0];
    if (file) {
      // Validate file type
      if (!isValidImageFile(file)) {
        showError('Please select a valid image file (JPEG, PNG, GIF).');
        return;
      }

      // Validate file size (limit to 5MB)
      if (file.size > 5 * 1024 * 1024) {
        showError('File size must be less than 5MB.');
        return;
      }

      await uploadProfileImage(file);
    }
  });

  // Trigger file selection
  document.body.appendChild(fileInput);
  fileInput.click();
  document.body.removeChild(fileInput);
}

/**
 *  Upload profile image to backend
 */
async function uploadProfileImage(file) {
  try {
    showLoading(true, 'Uploading profile image...');

    // Create FormData for file upload
    const formData = new FormData();
    formData.append('profileImage', file);

    // 🔗 Call backend API to upload image
    const response = await fetch('/api/doctor/profile/avatar', {
      method: 'POST',
      body: formData
    });

    const result = await response.json();

    if (result.code === '0' && result.data.imageUrl) {
      updateProfileAvatar(result.data.imageUrl);
      showSuccess('Profile image updated successfully!');
    } else {
      showError('Failed to upload image: ' + (result.message || 'Unknown error'));
    }
  } catch (error) {
    console.error('Error uploading profile image:', error);
    showError('Network error while uploading image.');
  } finally {
    showLoading(false);
  }
}

/**
 * Create file input element
 * @returns {HTMLInputElement} - File input element
 */
function createFileInput() {
  const fileInput = document.createElement('input');
  fileInput.type = 'file';
  fileInput.accept = 'image/*';
  fileInput.style.display = 'none';
  return fileInput;
}

/**
 * Validate image file type
 * @param {File} file - File object
 * @returns {boolean} - Whether it's a valid image file
 */
function isValidImageFile(file) {
  const validTypes = ['image/jpeg', 'image/png', 'image/gif', 'image/webp'];
  return validTypes.includes(file.type);
}

/**
 * Update avatar display
 * @param {string} imageUrl - Image URL
 */
function updateProfileAvatar(imageUrl) {
  const profileAvatar = document.querySelector('.profile-avatar1');
  if (profileAvatar) {
    profileAvatar.innerHTML = `<img src="${imageUrl}" alt="Profile Picture" style="width: 100%; height: 100%; object-fit: cover; border-radius: 50%;">`;
  }
}

/**
 *  Show loading state
 */
function showLoading(show, message = 'Loading...') {
  let loader = document.getElementById('loading-overlay');

  if (show) {
    if (!loader) {
      loader = document.createElement('div');
      loader.id = 'loading-overlay';
      loader.style.cssText = `
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: rgba(0,0,0,0.5);
        display: flex;
        justify-content: center;
        align-items: center;
        z-index: 9999;
        color: white;
        font-size: 1.2rem;
      `;
      document.body.appendChild(loader);
    }
    loader.textContent = message;
    loader.style.display = 'flex';
  } else {
    if (loader) {
      loader.style.display = 'none';
    }
  }
}

/**
 *  Show success message
 */
function showSuccess(message) {
  showNotification(message, 'success');
}

/**
 *  Show error message
 */
function showError(message) {
  showNotification(message, 'error');
}

/**
 *  Show notification
 */
function showNotification(message, type) {
  // Remove existing notifications
  const existingNotification = document.querySelector('.notification');
  if (existingNotification) {
    existingNotification.remove();
  }

  // Create notification
  const notification = document.createElement('div');
  notification.className = `notification ${type}`;
  notification.textContent = message;

  const bgColor = type === 'success' ? '#e8f5e8' : '#ffebee';
  const textColor = type === 'success' ? '#2e7d32' : '#c62828';
  const borderColor = type === 'success' ? '#4caf50' : '#e53935';

  notification.style.cssText = `
    position: fixed;
    top: 20px;
    right: 20px;
    background: ${bgColor};
    color: ${textColor};
    padding: 15px 20px;
    border-radius: 8px;
    border-left: 4px solid ${borderColor};
    box-shadow: 0 4px 12px rgba(0,0,0,0.15);
    z-index: 10000;
    max-width: 400px;
    font-weight: 500;
    animation: slideInRight 0.3s ease-out;
  `;

  document.body.appendChild(notification);

  // Auto-remove after 5 seconds
  setTimeout(() => {
    if (notification.parentNode) {
      notification.style.animation = 'slideOutRight 0.3s ease-out';
      setTimeout(() => notification.remove(), 300);
    }
  }, 5000);
}

// Add CSS animations
const style = document.createElement('style');
style.textContent = `
  @keyframes slideInRight {
    from {
      opacity: 0;
      transform: translateX(100%);
    }
    to {
      opacity: 1;
      transform: translateX(0);
    }
  }

  @keyframes slideOutRight {
    from {
      opacity: 1;
      transform: translateX(0);
    }
    to {
      opacity: 0;
      transform: translateX(100%);
    }
  }
`;
document.head.appendChild(style);

// Expose functions to global scope so onclick in HTML can call them
window.toggleEdit = toggleEdit;
window.cancelEdit = cancelEdit;

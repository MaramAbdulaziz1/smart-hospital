
document.addEventListener('DOMContentLoaded', function() {
  document.getElementById('profilePictureInput').addEventListener('change', handleProfilePictureUpload);
  document.getElementById('cameraInput').addEventListener('change', handleProfilePictureUpload);

  // Only add form submission redirection functionality
  const form = document.querySelector('form');
  form.addEventListener('submit', function(e) {
    e.preventDefault();

    // Jump directly to the homepage without performing any verification.
    handleRegistrationSubmit();
  });
});

function handleProfilePictureUpload(event) {
  const file = event.target.files[0];
  if (file) {
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

/**
 * Process registration submission - Simple redirect to homepage
 */
function handleRegistrationSubmit() {
  // Display processing status
  const submitBtn = document.querySelector('.action-btn:not(.cancel-btn)');
  const originalText = submitBtn.textContent;
  submitBtn.disabled = true;
  submitBtn.textContent = 'REGISTERING...';

  // Redirect after a short delay
  setTimeout(() => {
    alert('Registration successful! You will be redirected to the homepage.');
    window.location.href = '/';
  }, 1000);
}

/**
 * Cancel registration function (Maintain existing functions)
 */
function cancelRegistration() {
  window.location.href = '/';
}

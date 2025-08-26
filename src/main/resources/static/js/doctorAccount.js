function showSuccess(message) {
  const successElement = document.getElementById('successMessage');
  successElement.textContent = message;
  successElement.style.display = 'block';

  document.getElementById('errorMessage').style.display = 'none';

  setTimeout(() => {
    successElement.style.display = 'none';
  }, 5000);
}

function showError(message) {
  const errorElement = document.getElementById('errorMessage');
  errorElement.textContent = message;
  errorElement.style.display = 'block';

  document.getElementById('successMessage').style.display = 'none';

  setTimeout(() => {
    errorElement.style.display = 'none';
  }, 5000);
}

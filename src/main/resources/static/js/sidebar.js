document.addEventListener('DOMContentLoaded', function () {
  logout();
});
function logout() {
  // Logout functionality
  const logoutBtns = document.querySelectorAll('[data-action="logout"]');
  logoutBtns.forEach(btn => {
      btn.addEventListener('click', () => {
          if (confirm('Are you sure you want to log out?')) {
              fetch('/user/logout', {
                  method: 'POST',
                  headers: {
                      'Content-Type': 'application/json'
                  }
              })
              .then(response => response.json())
              .then(data => {
                  if (data.code == '0')
                  window.location.href = '/';
              })
              .catch(error => {
                  console.error('Error:', error);
                  alert('An error occurred during logout.');
              });
          }
      });
  });
}

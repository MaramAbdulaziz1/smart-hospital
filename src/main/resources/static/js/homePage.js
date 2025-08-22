function openLoginPopup() {
  const overlay = document.createElement('div');

  // Create overlay background
  overlay.style.position = 'fixed';
  overlay.style.top = '0';
  overlay.style.left = '0';
  overlay.style.width = '100%';
  overlay.style.height = '100%';
  overlay.style.background = 'rgba(0,0,0,0.5)';
  overlay.style.display = 'flex';
  overlay.style.justifyContent = 'center';
  overlay.style.alignItems = 'center';
  overlay.style.zIndex = '1000';

  // Close popup when clicking outside the modal
  overlay.addEventListener('click', (event) => {
    if (event.target === overlay) {
      closeLoginPopup();
    }
  });

  // Add inner login popup HTML
  overlay.innerHTML = `
    <div style="
      background: #BCE6F5;
      padding: 30px;
      border-radius: 12px;
      box-shadow: 0px 4px 10px rgba(0,0,0,0.2);
      text-align: center;
      width: 350px;
      height: 360px;
      position: relative;
    ">
      <span style="
        position: absolute;
        top: 10px;
        right: 15px;
        font-size: 24px;
        cursor: pointer;
      " onclick="closeLoginPopup()">&times;</span>

      <h2>Login</h2>

      <!-- Username -->
      <input id="username" type="text" placeholder="Username" style="
          display: block;
          width: 80%;
          margin: 50px auto;
          padding: 8px;
          border-radius: 6px;
          border: 1px solid #ccc;
        "
      >

      <!-- Password -->
      <input id="password" type="password" placeholder="Password" style="
          display: block;
          width: 80%;
          margin: -40px auto 10px;
          padding: 8px;
          border-radius: 6px;
          border: 1px solid #ccc;
        "
      >

      <!-- Login button -->
      <button id="login-button" style="
        width: 130px;
        height: 40px;
        background-color: rgba(7, 7, 56, 0.8);
        border: 1px solid #070738;
        border-radius: 12px;
        font-family: 'Neuton', serif;
        font-weight: 700;
        font-size: 20px;
        color: #FFFFFF;
        text-align: center;
        line-height: 40px;
        cursor: pointer;
        margin-top: 20px;
      ">
        Login
      </button>

      <!-- Forgot Password link -->
      <div style="
        margin-top: 10px;
        font-family: 'Neuton', serif;
        font-size: 16px;
        font-weight: 300;
        text-align: center;
        color: #000;
      ">
        <a href="/resetPassword" class="forgot-link">Forgot Password?</a>
      </div>
    </div>
  `;

  // Append to the body
  document.body.appendChild(overlay);

  // Add event listener for login button
  document.getElementById('login-button').addEventListener('click', function () {
      const username = document.getElementById('username').value;
      const password = document.getElementById('password').value;

      fetch('/user/login', {
          method: 'POST',
          headers: {
              'Content-Type': 'application/json'
          },
          body: JSON.stringify({ username, password }),
          credentials: 'include'
      })
          .then(response => response.json())
          .then(data => {
              if (data.code == '0') {
                  const role = data.data.role;
                  let redirectUrl = '/dashboard';

                  if (role === 0) {
                      redirectUrl = '/patient/patientDashboard';
                  } else if (role === 1) {
                      redirectUrl = '/dashboard';
                  } else if (role === 2) {
                      redirectUrl = '/dashboard-patient';
                  }
                  window.location.href = redirectUrl;
              } else {
                  alert('Login failed: ' + data.message);
              }
          })
          .catch(error => {
              console.error('Error:', error);
              alert('An error occurred during login.');
          });
  });
}

function closeLoginPopup() {
  const popup = document.querySelector('[style*="position: fixed"]');
  if (popup) popup.remove();
}

  (function () {
  const registerBtn = document.getElementById('registerBtn');
  const registerMenu = document.getElementById('registerMenu');

  if (!registerBtn || !registerMenu) return;

  registerBtn.addEventListener('click', function (e) {
  e.stopPropagation();
  const open = registerMenu.classList.toggle('show');
  registerBtn.setAttribute('aria-expanded', open ? 'true' : 'false');
  registerMenu.setAttribute('aria-hidden', open ? 'false' : 'true');
});

  // Close when clicking outside
  document.addEventListener('click', function (e) {
  if (registerMenu.classList.contains('show') &&
  !registerMenu.contains(e.target) &&
  e.target !== registerBtn) {
  registerMenu.classList.remove('show');
  registerBtn.setAttribute('aria-expanded', 'false');
  registerMenu.setAttribute('aria-hidden', 'true');
}
});

  // Close on Escape
  document.addEventListener('keydown', function (e) {
  if (e.key === 'Escape') {
  registerMenu.classList.remove('show');
  registerBtn.setAttribute('aria-expanded', 'false');
  registerMenu.setAttribute('aria-hidden', 'true');
}
});
})();


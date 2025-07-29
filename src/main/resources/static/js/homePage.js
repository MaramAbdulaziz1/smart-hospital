/* Function to display the login popup */
function openLoginPopup() {
  const overlay = document.createElement('div');
  overlay.innerHTML = `
    <div style="position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); display: flex; justify-content: center; align-items: center; z-index: 1000;">
        <div style="background: #BCE6F5; padding: 30px; border-radius: 12px; box-shadow: 0px 4px 10px rgba(0,0,0,0.2); text-align: center; width: 350px; position: relative;">
            <span style="position: absolute; top: 10px; right: 15px; font-size: 24px; cursor: pointer;" onclick="closeLoginPopup()">&times;</span>
            <h2>Login</h2>
            <input type="text" placeholder="Username" style="display: block; width: 80%; margin: 10px auto; padding: 8px; border-radius: 6px; border: 1px solid #ccc;">
            <input type="password" placeholder="Password" style="display: block; width: 80%; margin: 10px auto; padding: 8px; border-radius: 6px; border: 1px solid #ccc;">
            <button style="width: 130px; height: 40px; background-color: rgba(7, 7, 56, 0.8); border: 1px solid #070738; border-radius: 12px; font-family: 'Neuton', serif; font-weight: 700; font-size: 20px; color: #FFFFFF; text-align: center; line-height: 40px; cursor: pointer;">Login</button>
        </div>
    </div>`;
  document.body.appendChild(overlay.firstElementChild);
}

/* Function to close the login popup */
function closeLoginPopup() {
  const popup = document.querySelector('[style*="position: fixed"]');
  if (popup) popup.remove();
}

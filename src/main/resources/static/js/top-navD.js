// Function to fetch and update profile data
function fetchProfileData() {
  fetch('/profile/me/doctor')
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .then(data => {
      if (data.code === "0") {
        // Update profile information
        const profileData = data.data;

        // Update avatar
        const avatarElement = document.getElementById('profile-avatar');
        const avatarElement1 = document.getElementById('doctorProfileIcon');
        if (profileData.avatarBase64 && profileData.avatarBase64.startsWith('data:image')) {
          avatarElement.src = profileData.avatarBase64;
          avatarElement1.src = profileData.avatarBase64;
        }

        // Update name
        const nameElement = document.getElementById('profile-name');
        nameElement.textContent = `Dr. ${profileData.firstName} ${profileData.lastName}`;

        // Update email/specialty
        const emailElement = document.getElementById('profile-email');
        emailElement.textContent = profileData.email;
      } else {
        console.error('Error fetching profile data:', data.message);
      }
    })
    .catch(error => {
      console.error('Error fetching profile data:', error);
    });
}

// Fetch profile data when DOM is loaded
document.addEventListener('DOMContentLoaded', fetchProfileData);

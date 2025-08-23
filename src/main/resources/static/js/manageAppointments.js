document.addEventListener('DOMContentLoaded', function () {
    fetchPatientProfile();
    setupEventListeners();
});

function setupEventListeners() {
    // Notifications popup
    const notificationIcon = document.getElementById('notificationIcon');
    const notificationPopup = document.getElementById('notificationPopup');
    const closeNotifications = document.getElementById('closeNotifications');

    if (notificationIcon && notificationPopup) {
        notificationIcon.addEventListener('click', (e) => {
            e.stopPropagation();
            notificationPopup.classList.toggle('show');
            const profilePopup = document.getElementById('profilePopup');
            if (profilePopup) profilePopup.classList.remove('show');
        });
        closeNotifications.addEventListener('click', () => notificationPopup.classList.remove('show'));
    }

    // Profile popup
    const patientProfileIcon = document.getElementById('profileAvatarSmall');
    const profilePopup = document.getElementById('profilePopup');
    const closeProfile = document.getElementById('closeProfile');

    if (patientProfileIcon && profilePopup) {
        patientProfileIcon.addEventListener('click', (e) => {
            e.stopPropagation();
            profilePopup.classList.toggle('show');
            if (notificationPopup) notificationPopup.classList.remove('show');
        });
        closeProfile.addEventListener('click', () => profilePopup.classList.remove('show'));
    }

    // Close popups when clicking outside
    document.addEventListener('click', (e) => {
        if (notificationPopup && !notificationPopup.contains(e.target) && e.target !== notificationIcon) {
            notificationPopup.classList.remove('show');
        }
        if (profilePopup && !profilePopup.contains(e.target) && e.target !== patientProfileIcon) {
            profilePopup.classList.remove('show');
        }
    });

    // Mark all notifications as read
    const markAllReadBtn = document.querySelector('.mark-all-read');
    if (markAllReadBtn) {
        markAllReadBtn.addEventListener('click', () => {
            document.querySelectorAll('.notification-item.unread').forEach(item => {
                item.classList.remove('unread');
                const dot = item.querySelector('.notification-dot');
                if (dot) dot.remove();
            });
        });
    }

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

async function fetchPatientProfile() {
    try {
        const response = await fetch('/profile/me/patient', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const result = await response.json();

        if (result.code === '0') {
            updateProfileData(result.data);
        } else {
            console.error('Failed to fetch patient profile:', result.message);
            showNotification('Failed to load profile data', 'error');
        }
    } catch (error) {
        console.error('Error fetching patient profile:', error);
        showNotification('Error loading profile data', 'error');
    }
}

function updateProfileData(patientData) {
    if (patientData.avatarBase64) {
        document.getElementById('profileAvatarSmall').src = patientData.avatarBase64;
        document.getElementById('profileAvatarLarge').src = patientData.avatarBase64;
    }

    const fullName = `${patientData.firstName || ''} ${patientData.lastName || ''}`.trim();
    if (fullName) {
        document.getElementById('profileName').textContent = fullName;
    }

    if (patientData.patientCode) {
        document.getElementById('profilePatientId').textContent = `Patient ID: ${patientData.patientCode}`;
    }
}

// Cancel appointment function
function cancelAppointment(appointmentId, buttonElement) {
    if (confirm('Are you sure you want to cancel this appointment?')) {
        // Send AJAX request to cancel appointment
        fetch('/patient/appointments/cancel/' + appointmentId, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-Requested-With': 'XMLHttpRequest'
            }
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                // Update UI on success
                const row = buttonElement.closest('tr');
                row.style.opacity = '0.5';
                buttonElement.textContent = 'Cancelled';
                buttonElement.disabled = true;
                buttonElement.classList.add('disabled');

                // Show success message
                showSuccessMessage('Appointment cancelled successfully!');

                // Optionally remove row after animation
                setTimeout(() => {
                    row.style.transition = 'all 0.5s ease';
                    row.style.transform = 'translateX(-100%)';
                    setTimeout(() => row.remove(), 500);
                }, 2000);
            } else {
                throw new Error(data.message || 'Failed to cancel appointment');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            showSuccessMessage('Error cancelling appointment. Please try again.', 'error');
        });
    }
}

// Show success message
function showSuccessMessage(message, type = 'success') {
    const messageElement = document.getElementById('successMessage');
    const messageText = document.getElementById('messageText');

    messageText.textContent = message;
    messageElement.className = 'success-message ' + type;
    messageElement.style.display = 'flex';

    // Auto hide after 5 seconds
    setTimeout(() => {
        closeMessage();
    }, 5000);
}

// Close message
function closeMessage() {
    const messageElement = document.getElementById('successMessage');
    messageElement.style.display = 'none';
}

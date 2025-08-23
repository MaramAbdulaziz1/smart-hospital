let departments = [];
let doctors = [];
let nurses = [];
let availableTimes = [];
let vitalAvailableTimes = [];

document.addEventListener('DOMContentLoaded', function () {
    const tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    const minDate = tomorrow.toISOString().split('T')[0];
    document.getElementById('appointmentDate').setAttribute('min', minDate);
    document.getElementById('vitalDate').setAttribute('min', minDate);

    loadDepartments();

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

function loadDepartments() {
    fetch('/data/department', {
        method: 'GET'
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === "0") {
            departments = data.data;
            const departmentSelect = document.getElementById('department');
            const vitalDepartmentSelect = document.getElementById('vitalDepartment');

            departmentSelect.innerHTML = '<option value="">- Select Department -</option>';
            vitalDepartmentSelect.innerHTML = '<option value="">- Select Department -</option>';

            departments.forEach(dept => {
                departmentSelect.innerHTML += `<option value="${dept.departmentCode}">${dept.departmentName}</option>`;
                vitalDepartmentSelect.innerHTML += `<option value="${dept.departmentCode}">${dept.departmentName}</option>`;
            });
        } else {
            throw new Error('Failed to load departments');
        }
    })
    .catch(error => {
        console.error('Error loading departments:', error);
        showNotification('Failed to load departments', 'error');
    });
}

function loadDoctors() {
    const departmentCode = document.getElementById('department').value;
    const doctorSelect = document.getElementById('doctor');

    if (departmentCode) {
        fetch(`/doctor/department?department=${departmentCode}`, {
            method: 'GET'
        })
        .then(response => response.json())
        .then(data => {
            if (data.code === "0") {
                doctors = data.data;
                doctorSelect.innerHTML = '<option value="">- Select Doctor -</option>';
                doctors.forEach(doctor => {
                    doctorSelect.innerHTML += `<option value="${doctor.providerId}">${doctor.fullName}</option>`;
                });
            } else {
                throw new Error('Failed to load doctors');
            }
        })
        .catch(error => {
            console.error('Error loading doctors:', error);
            doctorSelect.innerHTML = '<option value="">Failed to load doctors</option>';
            showNotification('Failed to load doctors', 'error');
        });
    } else {
        doctorSelect.innerHTML = '<option value="">- Please select a department first -</option>';
    }
}

function loadNurses() {
    const departmentCode = document.getElementById('vitalDepartment').value;
    const nurseSelect = document.getElementById('nurse');

    if (departmentCode) {
        fetch(`/nurse/department?department=${departmentCode}`, {
            method: 'GET'
        })
        .then(response => response.json())
        .then(data => {
            if (data.code === "0") {
                nurses = data.data;
                nurseSelect.innerHTML = '<option value="">- Select Nurse -</option>';
                nurses.forEach(nurse => {
                    nurseSelect.innerHTML += `<option value="${nurse.providerId}">${nurse.fullName}</option>`;
                });
            } else {
                throw new Error('Failed to load nurses');
            }
        })
        .catch(error => {
            console.error('Error loading nurses:', error);
            nurseSelect.innerHTML = '<option value="">Failed to load nurses</option>';
            showNotification('Failed to load nurses', 'error');
        });
    } else {
        nurseSelect.innerHTML = '<option value="">- Please select a department first -</option>';
    }
}

function loadAvailableTimes() {
    const doctorId = document.getElementById('doctor').value;
    const date = document.getElementById('appointmentDate').value;
    const timeSelect = document.getElementById('appointmentTime');

    if (doctorId && date) {
        fetch(`/doctor/appointment?doctorId=${doctorId}&date=${date}`, {
            method: 'GET'
        })
        .then(response => response.json())
        .then(data => {
            if (data.code === "0") {
                availableTimes = data.data;
                timeSelect.innerHTML = '<option value="">- Select Time -</option>';
                availableTimes.forEach(time => {
                    timeSelect.innerHTML += `<option value="${time.timeCode}">${time.startTime}</option>`;
                });
            } else {
                throw new Error('Failed to load available times');
            }
        })
        .catch(error => {
            console.error('Error loading available times:', error);
            timeSelect.innerHTML = '<option value="">Failed to load available times</option>';
            showNotification('Failed to load available times', 'error');
        });
    }
}

function loadVitalAvailableTimes() {
    const nurseId = document.getElementById('nurse').value;
    const date = document.getElementById('vitalDate').value;
    const timeSelect = document.getElementById('vitalTime');

    if (nurseId && date) {
        fetch(`/nurse/appointment?nurseId=${nurseId}&date=${date}`, {
            method: 'GET'
        })
        .then(response => response.json())
        .then(data => {
            if (data.code === "0") {
                vitalAvailableTimes = data.data;
                timeSelect.innerHTML = '<option value="">- Select Time -</option>';
                vitalAvailableTimes.forEach(time => {
                    timeSelect.innerHTML += `<option value="${time.timeCode}">${time.startTime}</option>`;
                });
            } else {
                throw new Error('Failed to load available times');
            }
        })
        .catch(error => {
            console.error('Error loading available times:', error);
            timeSelect.innerHTML = '<option value="">Failed to load available times</option>';
            showNotification('Failed to load available times', 'error');
        });
    }
}

function handleDoctorAppointment(event) {
    event.preventDefault();

    const doctorId = document.getElementById('doctor').value;
    const date = document.getElementById('appointmentDate').value;
    const timeCode = document.getElementById('appointmentTime').value;
    const submitBtn = event.target.querySelector('button[type="submit"]');

    submitBtn.classList.add('loading');
    submitBtn.disabled = true;

    const requestBody = {
        date: date,
        time: parseInt(timeCode),
        type: 1,
        providerId: doctorId
    };

    fetch('/appointment/book', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === "0") {
            showNotification('Appointment Booked Successfully!', 'success');
            resetForm('appointmentForm');
        } else {
            throw new Error(data.message || 'Booking failed');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        showNotification('Booking failed: ' + error.message, 'error');
    })
    .finally(() => {
        submitBtn.classList.remove('loading');
        submitBtn.disabled = false;
    });
}

function handleVitalAppointment(event) {
    event.preventDefault();

    const nurseId = document.getElementById('nurse').value;
    const date = document.getElementById('vitalDate').value;
    const timeCode = document.getElementById('vitalTime').value;
    const submitBtn = event.target.querySelector('button[type="submit"]');

    submitBtn.classList.add('loading');
    submitBtn.disabled = true;

    const requestBody = {
        date: date,
        time: parseInt(timeCode),
        type: 2,
        providerId: nurseId
    };

    fetch('/appointment/book', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === "0") {
            showNotification('Vital Appointment Booked Successfully!', 'success');
            resetForm('vitalAppointmentForm');
        } else {
            throw new Error(data.message || 'Booking failed');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        showNotification('Booking failed: ' + error.message, 'error');
    })
    .finally(() => {
        submitBtn.classList.remove('loading');
        submitBtn.disabled = false;
    });
}

function showNotification(message, type = 'success') {
    const notification = document.getElementById('notification');
    const messageElement = document.getElementById('notificationMessage');

    messageElement.textContent = message;
    notification.className = `notification ${type} show`;

    setTimeout(() => {
        hideNotification();
    }, 3000);
}

function hideNotification() {
    const notification = document.getElementById('notification');
    notification.classList.add('hiding');

    setTimeout(() => {
        notification.classList.remove('show', 'hiding');
    }, 300);
}

function resetForm(formId) {
    const form = document.getElementById(formId);
    form.reset();

    const doctorSelect = document.getElementById('doctor');
    const nurseSelect = document.getElementById('nurse');
    const timeSelect = formId === 'appointmentForm' ?
        document.getElementById('appointmentTime') :
        document.getElementById('vitalTime');

    if (doctorSelect) {
        doctorSelect.innerHTML = '<option value="">- Please select a department first -</option>';
    }
    if (nurseSelect) {
        nurseSelect.innerHTML = '<option value="">- Please select a department first -</option>';
    }
    if (timeSelect) {
        timeSelect.innerHTML = '<option value="">- Select Time -</option>';
    }
}

function loadAvailableDates() {
    // Implementation if needed
}

function loadVitalAvailableDates() {
    // Implementation if needed
}

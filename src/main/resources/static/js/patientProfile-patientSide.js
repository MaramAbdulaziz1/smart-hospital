document.addEventListener('DOMContentLoaded', function () {
  async function fetchPatientData() {
    try {
      const response = await fetch('/profile/me/patient', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        }
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      const result = await response.json();

      if (result.code === "0") {
        populatePatientData(result.data);
      } else {
        console.error('API returned error:', result.message);
        alert('Failed to load patient data: ' + result.message);
      }
    } catch (error) {
      console.error('Error fetching patient data:', error);
      alert('Failed to load patient data. Please try again later.');
    }
  }

  function populatePatientData(data) {
    if (data.avatarBase64) {
      document.getElementById('avatar').src = data.avatarBase64;
      document.getElementById('headerAvatar').src = data.avatarBase64;
      document.getElementById('popupAvatar').src = data.avatarBase64;
    }

    const fullName = `${data.firstName} ${data.lastName}`;
    document.getElementById('fullName').textContent = fullName;
    document.getElementById('popupName').textContent = fullName;
    document.getElementById('email').textContent = data.email || 'N/A';
    document.getElementById('mobileNumber').textContent = data.mobileNumber || 'N/A';

    document.getElementById('ecName').textContent = `${data.ecFirstName} ${data.ecLastName}`;
    document.getElementById('ecRelationship').textContent = data.ecRelationshipName;
    document.getElementById('ecMobileNumber').textContent = data.ecMobileNumber || 'N/A';
    document.getElementById('gender').textContent = data.genderName;

    if (data.birth) {
      const birthDate = new Date(data.birth);
      const age = calculateAge(birthDate);
      document.getElementById('age').textContent = age;
      document.getElementById('birth').textContent = formatDate(birthDate);
    } else {
      document.getElementById('age').textContent = 'N/A';
      document.getElementById('birth').textContent = 'N/A';
    }
    document.getElementById('weight').textContent = data.weight ? `${data.weight} kg` : 'N/A';
    document.getElementById('height').textContent = data.height ? `${data.height} cm` : 'N/A';
    document.getElementById('bloodType').textContent = data.bloodTypeName;
    document.getElementById('nationalId').textContent = data.nationalId || 'N/A';
    document.getElementById('address').innerHTML = data.address ? data.address.replace(/\n/g, '<br/>') : 'N/A';
    document.getElementById('nearestClinic').textContent = data.nearestClinic || 'N/A';

    document.getElementById('popupPatientCode').textContent = `Patient ID: ${data.patientCode}`;

    document.getElementById('chiefComplaint').value = data.chiefComplaint || 'N/A';
    document.getElementById('pmhx').value = data.pastMedicalConditions || 'N/A';
    document.getElementById('currentMeds').value = data.currentMedications || 'N/A';
    document.getElementById('pshx').value = data.pastSurgicalHistory || 'N/A';
    document.getElementById('allergy').value = data.allergies || 'N/A';
    document.getElementById('fhx').value = data.familyHistory || 'N/A';
    document.getElementById('socialHx').value = data.socialHistory || 'N/A';
    document.getElementById('pastMeds').value = data.pastMedications || 'N/A';
  }

  function calculateAge(birthDate) {
    const today = new Date();
    let age = today.getFullYear() - birthDate.getFullYear();
    const monthDiff = today.getMonth() - birthDate.getMonth();

    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
      age--;
    }

    return age;
  }

  function formatDate(date) {
    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const year = date.getFullYear();

    return `${day}/${month}/${year}`;
  }

  fetchPatientData();

  /* Notifications */
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

  /* Profile popup */
  const patientProfileIcon = document.getElementById('patientProfileIcon');
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

  /* Close popups when clicking outside */
  document.addEventListener('click', (e) => {
    if (notificationPopup && !notificationPopup.contains(e.target) && e.target !== notificationIcon) {
      notificationPopup.classList.remove('show');
    }
    if (profilePopup && !profilePopup.contains(e.target) && e.target !== patientProfileIcon) {
      profilePopup.classList.remove('show');
    }
  });

  /* Mark all notifications as read */
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

  /* Active tab */
  const tabs = document.querySelectorAll('.tab[data-page]');
  const currentPage = window.location.pathname;
  tabs.forEach(tab => {
    const link = tab.querySelector('a');
    if (link && link.getAttribute('href') === currentPage) tab.classList.add('active-tab');
  });

  /* -------- Global view ↔ edit toggle for the whole page -------- */
  const editBtn = document.querySelector('.page-actions .btn-edit');
  const saveBtn = document.querySelector('.page-actions .btn-save');

  // Readonly inputs (lower card)
  const formFields = document.querySelectorAll('.hc-input');

  // Editable text nodes (top card & elsewhere)
  const textNodes = document.querySelectorAll('.editable');

  function setMode(editing) {
    // inputs
    formFields.forEach(el => {
      if (el.matches('input, textarea')) el.readOnly = !editing;
    });
    // contenteditable text
    textNodes.forEach(el => el.contentEditable = editing ? 'true' : 'false');

    editBtn.disabled = editing;
    saveBtn.disabled = !editing;
  }

  // initial: view only
  setMode(false);

  editBtn.addEventListener('click', () => setMode(true));

  saveBtn.addEventListener('click', async () => {
    const updateData = {
      chiefComplaint: document.getElementById('chiefComplaint').value,
      allergies: document.getElementById('allergy').value,
      pastMedicalConditions: document.getElementById('pmhx').value,
      currentMedications: document.getElementById('currentMeds').value,
      pastSurgicalHistory: document.getElementById('pshx').value,
      pastMedications: document.getElementById('pastMeds').value,
      familyHistory: document.getElementById('fhx').value,
      socialHistory: document.getElementById('socialHx').value
    };

    try {
      const response = await fetch('/profile/patient/update', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(updateData)
      });

      const result = await response.json();

      if (result.code === "0") {
        window.location.href = '/patientProfile-patientSide';
      } else {
        alert('Failed to update information: ' + (result.message || 'Unknown error'));
      }
    } catch (error) {
      console.error('Error updating profile:', error);
      alert('Failed to update information. Please try again.');
    }
  });
});

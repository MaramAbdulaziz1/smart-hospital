document.addEventListener('DOMContentLoaded', function() {
  loadUpcomingAppointments();
  loadAppointmentHistory();
});

function loadUpcomingAppointments() {
  fetch('/appointment/patient/upcoming', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json'
    }
  })
  .then(response => response.json())
  .then(data => {
    if (data.code === "0") {
      renderUpcomingAppointments(data.data);
    } else {
      console.error('Failed to load upcoming appointments:', data.message);
    }
  })
  .catch(error => {
    console.error('Error loading upcoming appointments:', error);
  });
}

function loadAppointmentHistory() {
  fetch('/appointment/patient/me', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json'
    }
  })
  .then(response => response.json())
  .then(data => {
    if (data.code === "0") {
      renderAppointmentHistory(data.data);
    } else {
      console.error('Failed to load appointment history:', data.message);
    }
  })
  .catch(error => {
    console.error('Error loading appointment history:', error);
  });
}

function renderUpcomingAppointments(appointments) {
  const tbody = document.getElementById('upcomingAppointmentsBody');
  const emptyState = document.getElementById('upcomingEmptyState');

  tbody.innerHTML = '';

  if (appointments && appointments.length > 0) {
    emptyState.style.display = 'none';

    appointments.forEach(appointment => {
      const row = document.createElement('tr');

      const dateParts = appointment.date.split('-');
      const formattedDate = `${dateParts[1]} ${dateParts[2]} ${dateParts[0]}`;

      row.innerHTML = `
        <td>${formattedDate}</td>
        <td>${appointment.startTime || 'N/A'}</td>
        <td>${appointment.providerName}</td>
        <td>
          <button class="cancel-btn" onclick="cancelAppointment('${appointment.appointmentId}', this)">
            Cancel
          </button>
        </td>
      `;

      tbody.appendChild(row);
    });
  } else {
    emptyState.style.display = 'block';
  }
}

function renderAppointmentHistory(appointments) {
  const tbody = document.getElementById('appointmentHistoryBody');
  const emptyState = document.getElementById('historyEmptyState');

  tbody.innerHTML = '';

  if (appointments && appointments.length > 0) {
    emptyState.style.display = 'none';

    appointments.forEach(appointment => {
      const row = document.createElement('tr');

      const dateParts = appointment.date.split('-');
      const formattedDate = `${dateParts[1]} ${dateParts[2]} ${dateParts[0]}`;

      const statusClass = appointment.status ? appointment.status.toLowerCase() : 'upcoming';

      row.innerHTML = `
        <td>${formattedDate}</td>
        <td>${appointment.startTime || 'N/A'}</td>
        <td>${appointment.providerName}</td>
        <td>
          <span class="status ${statusClass}">${appointment.status || 'Upcoming'}</span>
        </td>
      `;

      tbody.appendChild(row);
    });
  } else {
    emptyState.style.display = 'block';
  }
}

function cancelAppointment(appointmentId, buttonElement) {
  if (confirm('Are you sure you want to cancel this appointment?')) {
    fetch('/appointment/cancel?appointmentId=' + appointmentId, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'X-Requested-With': 'XMLHttpRequest'
      }
    })
    .then(response => response.json())
    .then(data => {
      if (data.code === "0") {
        const row = buttonElement.closest('tr');
        row.style.opacity = '0.5';
        buttonElement.textContent = 'Cancelled';
        buttonElement.disabled = true;
        buttonElement.classList.add('disabled');

        showSuccessMessage('Appointment cancelled successfully!');

        setTimeout(() => {
          loadUpcomingAppointments();
          loadAppointmentHistory();
        }, 1000);
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

function showSuccessMessage(message, type = 'success') {
  const messageElement = document.getElementById('successMessage');
  const messageText = document.getElementById('messageText');

  messageText.textContent = message;
  messageElement.className = 'success-message ' + type;
  messageElement.style.display = 'flex';

  setTimeout(() => {
    closeMessage();
  }, 5000);
}

function closeMessage() {
  const messageElement = document.getElementById('successMessage');
  messageElement.style.display = 'none';
}

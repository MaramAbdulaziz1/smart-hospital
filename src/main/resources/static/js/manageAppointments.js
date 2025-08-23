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

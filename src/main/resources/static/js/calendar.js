// Calendar functionality
class Calendar {
    constructor() {
        // Set to August 2025 to match the image
        this.currentDate = new Date(2025, 7, 1); // August 1, 2025
        this.selectedDate = new Date(2025, 7, 12); // August 12, 2025 (selected date)
        this.reminders = [
            {
                id: 1,
                name: "Harry Potter",
                date: new Date(2025, 7, 12), // August 12, 2025
                status: "completed"
            }
        ];
        
        this.init();
    }

    init() {
        this.renderCalendar();
        this.updateMonthYear();
        this.renderReminders();
        this.addEventListeners();
    }

    // Render the calendar grid
    renderCalendar() {
        const calendarDays = document.getElementById('calendarDays');
        if (!calendarDays) return;

        const year = this.currentDate.getFullYear();
        const month = this.currentDate.getMonth();
        
        // Get first day of month and last day of month
        const firstDay = new Date(year, month, 1);
        const lastDay = new Date(year, month + 1, 0);
        const startDate = new Date(firstDay);
        startDate.setDate(startDate.getDate() - firstDay.getDay());
        
        let html = '';
        
        // Generate 42 days (6 weeks * 7 days)
        for (let i = 0; i < 42; i++) {
            const currentDate = new Date(startDate);
            currentDate.setDate(startDate.getDate() + i);
            
            const isCurrentMonth = currentDate.getMonth() === month;
            const isToday = this.isToday(currentDate);
            const isSelected = this.isSelected(currentDate);
            const hasReminder = this.hasReminder(currentDate);
            
            let className = 'calendar-day';
            if (!isCurrentMonth) className += ' other-month';
            if (isToday) className += ' today';
            if (isSelected) className += ' selected';
            if (hasReminder) className += ' has-reminder';
            
            const dateString = `${currentDate.getFullYear()}-${String(currentDate.getMonth() + 1).padStart(2, '0')}-${String(currentDate.getDate()).padStart(2, '0')}`;
            html += `
                <div class="${className}" 
                     data-date="${dateString}"
                     onclick="calendar.selectDate('${dateString}')">
                    ${currentDate.getDate()}
                </div>
            `;
        }
        
        calendarDays.innerHTML = html;
    }

    // Update month and year display
    updateMonthYear() {
        const monthYear = document.getElementById('monthYear');
        if (!monthYear) return;

        const options = { year: 'numeric', month: 'long' };
        monthYear.textContent = this.currentDate.toLocaleDateString('en-US', options);
    }

    // Render reminders panel
    renderReminders() {
        const reminderList = document.getElementById('reminderList');
        if (!reminderList) return;

        const currentMonth = this.currentDate.getMonth();
        const currentYear = this.currentDate.getFullYear();
        
        const monthReminders = this.reminders.filter(reminder => {
            return reminder.date.getMonth() === currentMonth && 
                   reminder.date.getFullYear() === currentYear;
        });

        if (monthReminders.length === 0) {
            reminderList.innerHTML = '<div class="no-reminders">No reminders for this month</div>';
            return;
        }

        let html = '';
        monthReminders.forEach(reminder => {
            const dateStr = reminder.date.toLocaleDateString('en-US', { 
                day: 'numeric', 
                month: 'short' 
            });
            
            html += `
                <div class="reminder-item">
                    <div class="reminder-status ${reminder.status}">
                        ${reminder.status === 'completed' ? 
                            '<svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/></svg>' :
                            '<svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor"><circle cx="12" cy="12" r="10"/></svg>'
                        }
                    </div>
                    <div class="reminder-content">
                        <div class="reminder-name">${reminder.name}</div>
                        <div class="reminder-date">${dateStr}</div>
                    </div>
                </div>
            `;
        });
        
        reminderList.innerHTML = html;
    }

    // Add event listeners
    addEventListeners() {
        // Add keyboard navigation
        document.addEventListener('keydown', (e) => {
            switch(e.key) {
                case 'ArrowLeft':
                    this.previousMonth();
                    break;
                case 'ArrowRight':
                    this.nextMonth();
                    break;
                case 'Home':
                    this.goToToday();
                    break;
            }
        });
    }

    // Navigation methods
    previousMonth() {
        this.currentDate.setMonth(this.currentDate.getMonth() - 1);
        this.renderCalendar();
        this.updateMonthYear();
        this.renderReminders();
    }

    nextMonth() {
        this.currentDate.setMonth(this.currentDate.getMonth() + 1);
        this.renderCalendar();
        this.updateMonthYear();
        this.renderReminders();
    }

    goToToday() {
        this.currentDate = new Date();
        this.selectedDate = new Date();
        this.renderCalendar();
        this.updateMonthYear();
        this.renderReminders();
    }

    // Date selection
    selectDate(dateString) {
        const [year, month, day] = dateString.split('-').map(Number);
        this.selectedDate = new Date(year, month - 1, day);
        this.renderCalendar();
        
        // Can add additional functionality here~~
        console.log('Selected date:', dateString);
    }

    // Utility methods
    isToday(date) {
        const today = new Date();
        return date.getDate() === today.getDate() &&
               date.getMonth() === today.getMonth() &&
               date.getFullYear() === today.getFullYear();
    }

    isSelected(date) {
        return date.getDate() === this.selectedDate.getDate() &&
               date.getMonth() === this.selectedDate.getMonth() &&
               date.getFullYear() === this.selectedDate.getFullYear();
    }

    hasReminder(date) {
        return this.reminders.some(reminder => {
            return reminder.date.getDate() === date.getDate() &&
                   reminder.date.getMonth() === date.getMonth() &&
                   reminder.date.getFullYear() === date.getFullYear();
        });
    }

    // Add reminder method
    addReminder(name, date, status = 'pending') {
        const reminder = {
            id: Date.now(),
            name: name,
            date: new Date(date),
            status: status
        };
        this.reminders.push(reminder);
        this.renderCalendar();
        this.renderReminders();
    }

    // Remove reminder method
    removeReminder(id) {
        this.reminders = this.reminders.filter(reminder => reminder.id !== id);
        this.renderCalendar();
        this.renderReminders();
    }
}

// Global calendar instance
let calendar;

// Initialize calendar when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    calendar = new Calendar();
});

// Global functions for HTML onclick events
function previousMonth() {
    if (calendar) calendar.previousMonth();
}

function nextMonth() {
    if (calendar) calendar.nextMonth();
}

function goToToday() {
    if (calendar) calendar.goToToday();
}

// Shared Layout JavaScript
// Handles notification popup and doctor profile popup functionality

document.addEventListener('DOMContentLoaded', function() {
    console.log('SharedLayout.js loaded');

    // Notification functionality
    const notificationIcon = document.getElementById('notificationIcon');
    const notificationPopup = document.getElementById('notificationPopup');
    const closeNotifications = document.getElementById('closeNotifications');
    const markAllReadBtn = document.querySelector('.mark-all-read');
    const viewAllBtn = document.querySelector('.view-all');

    // Profile functionality
    const doctorProfileIcon = document.getElementById('doctorProfileIcon');
    const profilePopup = document.getElementById('profilePopup');
    const closeProfile = document.getElementById('closeProfile');
    const profileMenuItems = document.querySelectorAll('.profile-menu-item');

    console.log('Profile elements found:', {
        doctorProfileIcon: !!doctorProfileIcon,
        profilePopup: !!profilePopup,
        closeProfile: !!closeProfile,
        profileMenuItems: profileMenuItems.length
    });

    // Notification toggle
    if (notificationIcon && notificationPopup) {
        notificationIcon.addEventListener('click', function(e) {
            e.preventDefault();
            e.stopPropagation();
            console.log('Notification icon clicked');
            toggleNotificationPopup();
        });
    }

    // Close notification popup
    if (closeNotifications) {
        closeNotifications.addEventListener('click', function(e) {
            e.stopPropagation();
            hideNotificationPopup();
        });
    }

    // Profile toggle
    if (doctorProfileIcon && profilePopup) {
        console.log('Adding click listener to doctor profile icon');
        doctorProfileIcon.addEventListener('click', function(e) {
            console.log('Doctor profile icon clicked');
            e.preventDefault();
            e.stopPropagation();
            toggleProfilePopup();
        });
    } else {
        console.error('Doctor profile icon or popup not found:', {
            doctorProfileIcon: !!doctorProfileIcon,
            profilePopup: !!profilePopup
        });
    }

    // Close profile popup
    if (closeProfile) {
        closeProfile.addEventListener('click', function(e) {
            e.stopPropagation();
            hideProfilePopup();
        });
    }

    // Mark all notifications as read
    if (markAllReadBtn) {
        markAllReadBtn.addEventListener('click', function() {
            markAllNotificationsAsRead();
        });
    }

    // View all notifications
    if (viewAllBtn) {
        viewAllBtn.addEventListener('click', function() {
            viewAllNotifications();
        });
    }

    // Profile menu item actions
    profileMenuItems.forEach(item => {
        item.addEventListener('click', function(e) {
            const action = this.getAttribute('data-action');
            handleProfileAction(action);
        });
    });

    // Logout button actions (for all logout buttons except profile menu items)
    const logoutButtons = document.querySelectorAll('[data-action="logout"]:not(.profile-menu-item)');
    logoutButtons.forEach(btn => {
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

    // Close popups when clicking outside
    document.addEventListener('click', function(e) {
        if (!notificationPopup?.contains(e.target) && !notificationIcon?.contains(e.target)) {
            hideNotificationPopup();
        }
        if (!profilePopup?.contains(e.target) && !doctorProfileIcon?.contains(e.target)) {
            hideProfilePopup();
        }
    });

    // Close popups on escape key
    document.addEventListener('keydown', function(e) {
        if (e.key === 'Escape') {
            hideNotificationPopup();
            hideProfilePopup();
        }
    });

    // Functions
    function toggleNotificationPopup() {
        if (notificationPopup.classList.contains('show')) {
            hideNotificationPopup();
        } else {
            showNotificationPopup();
        }
    }

    function showNotificationPopup() {
        hideProfilePopup(); // Close other popup first
        console.log('Showing notification popup');
        console.log('Notification popup element:', notificationPopup);
        console.log('Current classes:', notificationPopup.className);
        notificationPopup.classList.add('show');
        console.log('After adding show class:', notificationPopup.className);
        notificationIcon.style.transform = 'scale(1.1)';
    }

    function hideNotificationPopup() {
        notificationPopup.classList.remove('show');
        notificationIcon.style.transform = 'scale(1)';
    }

    function toggleProfilePopup() {
        console.log('toggleProfilePopup called');
        if (profilePopup.classList.contains('show')) {
            hideProfilePopup();
        } else {
            showProfilePopup();
        }
    }

    function showProfilePopup() {
        console.log('showProfilePopup called');
        hideNotificationPopup(); // Close other popup first
        console.log('Profile popup element:', profilePopup);
        console.log('Current classes:', profilePopup.className);
        profilePopup.classList.add('show');
        console.log('After adding show class:', profilePopup.className);
        doctorProfileIcon.style.transform = 'scale(1.05)';
        console.log('Profile popup should now be visible');
    }

    function hideProfilePopup() {
        console.log('hideProfilePopup called');
        profilePopup.classList.remove('show');
        doctorProfileIcon.style.transform = 'scale(1)';
    }

    function markAllNotificationsAsRead() {
        const unreadItems = document.querySelectorAll('.notification-item.unread');
        unreadItems.forEach(item => {
            item.classList.remove('unread');
            const dot = item.querySelector('.notification-dot');
            if (dot) {
                dot.style.display = 'none';
            }
        });

        // Update notification icon (remove badge if exists)
        updateNotificationBadge(0);

        // Show success message
        showToast('All notifications marked as read');
    }

    function viewAllNotifications() {
        // Navigate to notifications page or show full list
        console.log('Navigate to notifications page');
        showToast('Opening notifications page...');
        hideNotificationPopup();
    }

    function handleProfileAction(action) {
        switch (action) {
            case 'profile':
                // Navigate to the doctor's account settings page
                window.location.href = '/doctorAccount';
                break;
            case 'settings':
                showToast('Opening application settings...');
                // Navigate to settings page
                break;
            case 'help':
                showToast('Opening help & support...');
                // Navigate to help page
                break;
            case 'logout':
                if (confirm('Are you sure you want to log out?')) {
                    showToast('Logging out...');
                    // Perform logout action
                    setTimeout(() => {
                        window.location.href = '/login';
                    }, 1000);
                }
                break;
            default:
                console.log('Unknown action:', action);
        }
        hideProfilePopup();
    }

    function updateNotificationBadge(count) {
        // Remove existing badge if count is 0
        let badge = notificationIcon.querySelector('.notification-badge');
        if (count === 0 && badge) {
            badge.remove();
        } else if (count > 0) {
            if (!badge) {
                badge = document.createElement('div');
                badge.className = 'notification-badge';
                notificationIcon.appendChild(badge);
            }
            badge.textContent = count > 99 ? '99+' : count;
        }
    }

    function showToast(message) {
        // Create toast notification
        const toast = document.createElement('div');
        toast.className = 'toast-notification';
        toast.textContent = message;
        toast.style.cssText = `
            position: fixed;
            top: 20px;
            right: 20px;
            background: #333;
            color: white;
            padding: 12px 20px;
            border-radius: 6px;
            z-index: 10000;
            font-size: 14px;
            opacity: 0;
            transform: translateX(100%);
            transition: all 0.3s ease;
        `;

        document.body.appendChild(toast);

        // Animate in
        setTimeout(() => {
            toast.style.opacity = '1';
            toast.style.transform = 'translateX(0)';
        }, 10);

        // Remove after 3 seconds
        setTimeout(() => {
            toast.style.opacity = '0';
            toast.style.transform = 'translateX(100%)';
            setTimeout(() => {
                if (toast.parentNode) {
                    toast.parentNode.removeChild(toast);
                }
            }, 300);
        }, 3000);
    }

    // Initialize notification count (example)
    updateNotificationBadge(2);

    // Add notification badge styles
    const style = document.createElement('style');
    style.textContent = `
        .notification-badge {
            position: absolute;
            top: -5px;
            right: -5px;
            background: #FF3B30;
            color: white;
            border-radius: 50%;
            width: 18px;
            height: 18px;
            font-size: 10px;
            font-weight: bold;
            display: flex;
            align-items: center;
            justify-content: center;
            border: 2px solid white;
        }

        .notification-container {
            position: relative;
        }
    `;
    document.head.appendChild(style);
});

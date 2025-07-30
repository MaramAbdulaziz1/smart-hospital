/* Shared Layout JavaScript */

// Function to set active tab
function setActiveTab(tabName) {
  // Remove active class from all tabs
  document.querySelectorAll('.tab').forEach(tab => {
    tab.classList.remove('active-tab');
  });

  // Add active class to the specified tab
  const targetTab = document.querySelector(`[data-page="${tabName}"]`);
  if (targetTab) {
    targetTab.classList.add('active-tab');
  }
}

// Function to update page title
function updatePageTitle(title) {
  const pageTitle = document.querySelector('.page-title');
  if (pageTitle) {
    pageTitle.textContent = title;
  }
}

// Navigation handlers
function handleTabClick(event) {
  const tab = event.currentTarget;
  const page = tab.getAttribute('data-page');

  // Set active tab
  setActiveTab(page);

  // Handle navigation based on page
  switch(page) {
    case 'home':
      updatePageTitle('Home Dashboard');
      navigateToPage('/home');
      break;
    case 'calendar':
      updatePageTitle('Calendar');
      navigateToPage('/calendar');
      break;
    case 'patient-profile':
      updatePageTitle('Patient Profile');
      navigateToPage('/patient-profile');
      break;
    case 'vitals':
      updatePageTitle('Patient Vitals');
      navigateToPage('/vitals');
      break;
    case 'prescription':
      updatePageTitle('Prescription Management');
      navigateToPage('/prescription');
      break;
    case 'logout':
      handleLogout();
      break;
    default:
      console.log('/homePage', page);
  }
}

// Function to navigate to different pages
function navigateToPage(url) {
  // Add loading state or transition effects here if needed
  window.location.href = url;
}

// Function to handle logout
function handleLogout() {
  if (confirm('Are you sure you want to log out?')) {
    window.location.href = '/login';
  }
}

// Initialize the layout
document.addEventListener('DOMContentLoaded', function() {
  // Add click listeners to all tabs
  document.querySelectorAll('.tab').forEach(tab => {
    tab.addEventListener('click', handleTabClick);
  });

  // Set initial active tab based on current page
  const currentPath = window.location.pathname;
  if (currentPath.includes('vitals')) {
    setActiveTab('vitals');
    updatePageTitle('Patient Vitals');
  } else if (currentPath.includes('calendar')) {
    setActiveTab('calendar');
    updatePageTitle('Calendar');
  } else if (currentPath.includes('patient-profile')) {
    setActiveTab('patient-profile');
    updatePageTitle('Patient Profile');
  } else if (currentPath.includes('prescription')) {
    setActiveTab('prescription');
    updatePageTitle('Prescription Management');
  } else {
    setActiveTab('home');
    updatePageTitle('Home Dashboard');
  }
});

// Export functions for use in other pages
window.SharedLayout = {
  setActiveTab,
  updatePageTitle,
  navigateToPage
};

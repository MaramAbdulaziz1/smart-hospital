// Prescription JS

class PrescriptionManager {
    constructor() {
        this.medicationCards = []; // Store all saved medication cards
        this.currentCardId = 1; // Current card number
        this.currentCardData = {}; // Current card data being edited
        this.isEditing = false; // Whether in edit mode
        this.editingCardIndex = -1; // Index of card being edited
        
        this.init();
    }

    init() {
        this.bindEvents();
        this.updateCardHeader();
        this.updateDropdown();
        this.loadExternalData(); // Load initial data
        this.setupDataSync(); // Setup real-time data synchronization
    }

    // Load external data from Clinical Notes and Report pages
    loadExternalData() {
        // Load from localStorage (persistent across pages)
        const diagnosis = localStorage.getItem('clinicalNotes_diagnosis');
        const drugAllergy = localStorage.getItem('report_medicalHistory');
        
        // Fill in the diagnosis textarea (from Clinical Notes)
        if (diagnosis) {
            const diagnosisTextarea = document.querySelector('.fixed-section textarea');
            if (diagnosisTextarea) {
                diagnosisTextarea.value = diagnosis;
                console.log('Diagnosis loaded from Clinical Notes:', diagnosis);
            }
        }
        
        // Fill in the drug allergy textarea (from Report page)
        if (drugAllergy) {
            const drugAllergyTextarea = document.querySelectorAll('.fixed-section textarea')[1];
            if (drugAllergyTextarea) {
                drugAllergyTextarea.value = drugAllergy;
                console.log('Drug Allergy loaded from Report page:', drugAllergy);
            }
        }
        
        // Show success message if data was loaded
        if (diagnosis || drugAllergy) {
            this.showMessage('Patient data synchronized successfully', 'success');
        }
    }

    // Setup real-time data synchronization
    setupDataSync() {
        // Listen for localStorage changes from other pages
        window.addEventListener('storage', (e) => {
            if (e.key === 'clinicalNotes_diagnosis') {
                // Update diagnosis textarea when Clinical Notes changes
                const diagnosisTextarea = document.querySelector('.fixed-section textarea');
                if (diagnosisTextarea && e.newValue) {
                    diagnosisTextarea.value = e.newValue;
                    this.showMessage('Diagnosis updated from Clinical Notes', 'info');
                    console.log('Diagnosis synchronized:', e.newValue);
                }
            }
            
            if (e.key === 'report_medicalHistory') {
                // Update drug allergy textarea when Report page changes
                const drugAllergyTextarea = document.querySelectorAll('.fixed-section textarea')[1];
                if (drugAllergyTextarea && e.newValue) {
                    drugAllergyTextarea.value = e.newValue;
                    this.showMessage('Drug Allergy updated from Report page', 'info');
                    console.log('Drug Allergy synchronized:', e.newValue);
                }
            }
        });
        
        // Also save changes made directly in prescription page
        this.setupLocalSync();
    }

    // Setup local synchronization (save prescription changes to localStorage)
    setupLocalSync() {
        // Monitor diagnosis textarea changes
        const diagnosisTextarea = document.querySelector('.fixed-section textarea');
        if (diagnosisTextarea) {
            diagnosisTextarea.addEventListener('input', (e) => {
                localStorage.setItem('prescription_diagnosis', e.target.value);
            });
        }
        
        // Monitor drug allergy textarea changes
        const drugAllergyTextarea = document.querySelectorAll('.fixed-section textarea')[1];
        if (drugAllergyTextarea) {
            drugAllergyTextarea.addEventListener('input', (e) => {
                localStorage.setItem('prescription_drugAllergy', e.target.value);
            });
        }
    }

    // Bind all event listeners
    bindEvents() {
        // Add Card button
        const addCardBtn = document.querySelector('.button-bar .action-btn:first-child');
        if (addCardBtn) {
            addCardBtn.addEventListener('click', () => this.handleAddCard());
        }

        // Delete Card button
        const deleteCardBtn = document.querySelector('.button-bar .action-btn:last-child');
        if (deleteCardBtn) {
            deleteCardBtn.addEventListener('click', () => this.handleDeleteCard());
        }

        // Save button - find by text content since the class might be the same
        const saveBtn = this.findButtonByText('Save') || this.findButtonByText('Submitting...');
        if (saveBtn) {
            saveBtn.addEventListener('click', () => this.handleSaveCurrentInput());
        }

        // Submit button - find by text content
        const submitBtn = this.findButtonByText('Submit');
        if (submitBtn) {
            submitBtn.addEventListener('click', () => this.handleSubmitPrescription());
        }

        // Dropdown selection
        const dropdown = document.querySelector('.dropdown');
        if (dropdown) {
            dropdown.addEventListener('change', (e) => this.handleCardSelection(e));
        }

        // Monitor form input changes
        this.bindFormInputs();
    }

    // Helper function to find button by text content
    findButtonByText(text) {
        const buttons = document.querySelectorAll('.floating-buttons button');
        for (let button of buttons) {
            if (button.textContent.trim() === text) {
                return button;
            }
        }
        return null;
    }

    // Bind form input events
    bindFormInputs() {
        const inputs = document.querySelectorAll('.medication-form input, .medication-form textarea');
        inputs.forEach(input => {
            input.addEventListener('input', (e) => this.handleInputChange(e));
        });
    }

    // Handle input changes
    handleInputChange(event) {
        const input = event.target;
        const label = input.previousElementSibling;
        const fieldName = this.getFieldName(label.textContent);
        
        this.currentCardData[fieldName] = input.value;
    }

    // Get field name based on label text
    getFieldName(labelText) {
        const fieldMap = {
            'Medication Name': 'medicationName',
            'Dosage': 'dosage',
            'Frequency': 'frequency',
            'Note': 'note',
            'Route': 'route',
            'Duration': 'duration'
        };
        return fieldMap[labelText] || labelText.toLowerCase().replace(/\s+/g, '');
    }

    // Handle Add Card
    handleAddCard() {
        if (!this.validateCurrentCard()) {
            this.showMessage('Please fill in required medication information', 'error');
            return;
        }

        // If in editing mode, update existing card
        if (this.isEditing) {
            this.updateExistingCard();
        } else {
            // Add new card
            this.addNewCard();
        }

        this.resetForm();
        this.updateDropdown();
        this.showMessage('Medication card added successfully', 'success');
    }

    // Add new medication card
    addNewCard() {
        const newCard = {
            id: this.currentCardId,
            ...this.getCurrentFormData(),
            createdAt: new Date().toISOString()
        };

        this.medicationCards.push(newCard);
        this.currentCardId++;
    }

    // Update existing medication card
    updateExistingCard() {
        if (this.editingCardIndex >= 0) {
            this.medicationCards[this.editingCardIndex] = {
                ...this.medicationCards[this.editingCardIndex],
                ...this.getCurrentFormData(),
                updatedAt: new Date().toISOString()
            };
        }
        this.isEditing = false;
        this.editingCardIndex = -1;
    }

    // Handle Delete Card
    handleDeleteCard() {
        if (this.isEditing && this.editingCardIndex >= 0) {
            // Delete the card being edited
            const cardToDelete = this.medicationCards[this.editingCardIndex];
            if (confirm(`Are you sure you want to delete Medication Card #${cardToDelete.id}?`)) {
                this.medicationCards.splice(this.editingCardIndex, 1);
                this.resetForm();
                this.updateDropdown();
                this.showMessage('Medication card deleted successfully', 'success');
                this.isEditing = false;
                this.editingCardIndex = -1;
            }
        } else {
            // If no card is selected, clear current form
            if (confirm('Are you sure you want to clear the current form?')) {
                this.resetForm();
                this.showMessage('Form cleared', 'info');
            }
        }
    }

    // Handle dropdown selection
    handleCardSelection(event) {
        const selectedValue = event.target.value;
        
        if (selectedValue === 'Select Medication Card') {
            this.resetForm();
            return;
        }

        const cardId = parseInt(selectedValue);
        const cardIndex = this.medicationCards.findIndex(card => card.id === cardId);
        
        if (cardIndex >= 0) {
            this.loadCardForEditing(cardIndex);
        }
    }

    // Load card for editing
    loadCardForEditing(cardIndex) {
        this.isEditing = true;
        this.editingCardIndex = cardIndex;
        const card = this.medicationCards[cardIndex];
        
        // Update form title
        this.updateCardHeader(`Medication Card #${card.id} (Editing)`);
        
        // Populate form data
        this.populateForm(card);
        
        // Update current card data
        this.currentCardData = { ...card };
    }

    // Populate form with data
    populateForm(cardData) {
        const fieldMap = {
            'medicationName': 'input[placeholder="Add medicine name..."]',
            'dosage': 'input[placeholder="e.g., 500mg"]',
            'frequency': 'input[placeholder="e.g., Twice a day"]',
            'note': 'textarea[placeholder="e.g., Take before meals"]',
            'route': 'input[placeholder="e.g., Oral or IV"]',
            'duration': 'input[placeholder="e.g., For 7 days"]'
        };

        Object.keys(fieldMap).forEach(key => {
            const element = document.querySelector(fieldMap[key]);
            if (element && cardData[key]) {
                element.value = cardData[key];
            }
        });
    }

    // Get current form data
    getCurrentFormData() {
        return {
            medicationName: document.querySelector('input[placeholder="Add medicine name..."]')?.value || '',
            dosage: document.querySelector('input[placeholder="e.g., 500mg"]')?.value || '',
            frequency: document.querySelector('input[placeholder="e.g., Twice a day"]')?.value || '',
            note: document.querySelector('textarea[placeholder="e.g., Take before meals"]')?.value || '',
            route: document.querySelector('input[placeholder="e.g., Oral or IV"]')?.value || '',
            duration: document.querySelector('input[placeholder="e.g., For 7 days"]')?.value || ''
        };
    }

    // Validate current card data
    validateCurrentCard() {
        const data = this.getCurrentFormData();
        return data.medicationName.trim() !== '' && data.dosage.trim() !== '';
    }

    // Reset form
    resetForm() {
        const inputs = document.querySelectorAll('.medication-form input, .medication-form textarea');
        inputs.forEach(input => {
            input.value = '';
        });

        this.currentCardData = {};
        this.isEditing = false;
        this.editingCardIndex = -1;
        this.updateCardHeader();
        
        // Reset dropdown
        const dropdown = document.querySelector('.dropdown');
        if (dropdown) {
            dropdown.value = 'Select Medication Card';
        }
    }

    // Update card header
    updateCardHeader(title = null) {
        const header = document.querySelector('.medication-card-header');
        if (header) {
            header.textContent = title || `Medication Card #${this.currentCardId}`;
        }
    }

    // Update dropdown options
    updateDropdown() {
        const dropdown = document.querySelector('.dropdown');
        if (!dropdown) return;

        // Clear existing options (keep first default option)
        dropdown.innerHTML = '<option>Select Medication Card</option>';

        // Add saved cards
        this.medicationCards.forEach(card => {
            const option = document.createElement('option');
            option.value = card.id;
            option.textContent = `Medication Card #${card.id} - ${card.medicationName}`;
            dropdown.appendChild(option);
        });
    }

    // Handle Save Current Input (saves current form data temporarily)
    handleSaveCurrentInput() {
        // Save current input data to temporary storage
        this.currentCardData = this.getCurrentFormData();
        
        // Show save confirmation
        this.showMessage('Current input saved temporarily', 'info');
        
        // Visual feedback - briefly highlight the save button
        const saveBtn = this.findButtonByText('Save');
        if (saveBtn) {
            const originalStyle = saveBtn.style.backgroundColor;
            saveBtn.style.backgroundColor = '#e8f7fb';
            setTimeout(() => {
                saveBtn.style.backgroundColor = originalStyle;
            }, 1000);
        }
        
        console.log('Current input saved:', this.currentCardData);
    }

    // Handle Submit Prescription (submits entire prescription)
    handleSubmitPrescription() {
        // Check if there are any saved medication cards
        if (this.medicationCards.length === 0) {
            this.showMessage('Please add at least one medication card before submitting', 'warning');
            return;
        }

        // Gather all prescription data
        const prescriptionData = {
            diagnosis: document.querySelector('.fixed-section textarea')?.value || '',
            drugAllergy: document.querySelectorAll('.fixed-section textarea')[1]?.value || '',
            medicationCards: this.medicationCards,
            submittedAt: new Date().toISOString()
        };

        // Here you can implement the API call to submit to backend/report system
        console.log('Submitting prescription to report system:', prescriptionData);
        
        // Simulate submission process
        this.showSubmissionProgress();
        
        // For now, show success message
        // In the future, replace this with actual backend submission
        setTimeout(() => {
            this.showMessage('Prescription submitted successfully to report system', 'success');
            
            // Optionally redirect to report page or clear data
            // window.location.href = '/reports';
        }, 1500);
    }

    // Show submission progress (visual feedback)
    showSubmissionProgress() {
        const submitBtn = this.findButtonByText('Submit');
        if (submitBtn) {
            const originalText = submitBtn.textContent;
            submitBtn.textContent = 'Submitting...';
            submitBtn.disabled = true;
            submitBtn.style.opacity = '0.7';
            
            setTimeout(() => {
                submitBtn.textContent = originalText;
                submitBtn.disabled = false;
                submitBtn.style.opacity = '1';
            }, 1500);
        }
    }

    // Show message
    showMessage(message, type = 'info') {
        // Create message element
        const messageDiv = document.createElement('div');
        messageDiv.className = `message message-${type}`;
        messageDiv.textContent = message;
        messageDiv.style.cssText = `
            position: fixed;
            top: 20px;
            right: 20px;
            padding: 12px 20px;
            border-radius: 6px;
            color: white;
            font-weight: 500;
            z-index: 1000;
            opacity: 0;
            transition: opacity 0.3s;
        `;

        // Set color based on type
        const colors = {
            success: '#10B981',
            error: '#EF4444',
            warning: '#F59E0B',
            info: '#3B82F6'
        };
        messageDiv.style.backgroundColor = colors[type] || colors.info;

        document.body.appendChild(messageDiv);

        // Show animation
        requestAnimationFrame(() => {
            messageDiv.style.opacity = '1';
        });

        // Auto remove after 3 seconds
        setTimeout(() => {
            messageDiv.style.opacity = '0';
            setTimeout(() => {
                if (messageDiv.parentNode) {
                    messageDiv.parentNode.removeChild(messageDiv);
                }
            }, 300);
        }, 3000);
    }

    // Get all medication cards (for external use)
    getAllMedicationCards() {
        return [...this.medicationCards];
    }

    // Clear all data (for external use)
    clearAllData() {
        if (confirm('Are you sure you want to clear all data? This action cannot be undone.')) {
            this.medicationCards = [];
            this.currentCardId = 1;
            this.resetForm();
            this.updateDropdown();
            this.showMessage('All data cleared successfully', 'success');
        }
    }
}

// When page is loaded, initialize the system
document.addEventListener('DOMContentLoaded', function() {
    // Initialize prescription manager
    window.prescriptionManager = new PrescriptionManager();
    
    console.log('Prescription system initialized');
});

// Export for use in other modules
if (typeof module !== 'undefined' && module.exports) {
    module.exports = PrescriptionManager;
}
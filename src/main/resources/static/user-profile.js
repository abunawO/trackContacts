// Function to reset the form and send a submit request
function resetForm() {
    var form = document.querySelector('.search-form');
    var inputFirstName = form.querySelector('input[name="firstName"]');
    var inputLastName = form.querySelector('input[name="lastName"]');
    
    inputFirstName.value = ''; // Clear the First Name field
    inputLastName.value = '';  // Clear the Last Name field
}

function openContactForm() {
    var modal = document.getElementById("contact-form-modal");
    modal.style.display = "block";
}


function closeContactForm() {
    var modal = document.getElementById("contact-form-modal");
    modal.style.display = "none";
}

// Function to open the edit contact form
function editContact(contactId) {
    var modal = document.getElementById("edit-contact-modal");
    modal.style.display = "block";
}

// Function to close the edit contact form
function closeEditContactForm() {
    var modal = document.getElementById("edit-contact-modal");
    modal.style.display = "none";
}

/* Function to open the edit contact modal and populate it */
function populateEditForm(contactId) {
    var modal = document.getElementById("edit-contact-modal");
    modal.style.display = "block";

    // Set the contact ID in the hidden input field
    document.getElementById("edit-contact-id").value = contactId;

    // Fetch contact details using AJAX and populate the form fields
    $.ajax({
        url: '/contact/getContactDetails/' + contactId, // Include the contactId in the URL
        method: 'GET',
        success: function (data) {
            document.getElementById("edit-firstName").value = data.firstName;
            document.getElementById("edit-lastName").value = data.lastName;
            document.getElementById("edit-email").value = data.email;
            document.getElementById("edit-phone").value = data.phoneNumber;
        },
        error: function (error) {
            console.log("Error fetching contact details: " + error);
        }
    });
}

function deleteContact(contactId, userId) {
    if (confirm("Are you sure you want to delete this contact?")) {
        // User confirmed the deletion
        $.ajax({
            url: '/contact/delete', // Endpoint to delete the contact
            method: 'POST',
            data: {
                userId: userId,
                contactId: contactId
            },
            success: function () {
                // Contact deleted successfully, reload the page or update the UI as needed
                location.reload(); // Reloading the page for simplicity
            },
            error: function (error) {
                console.log("Error deleting contact: " + error);
            }
        });
    }
}

function closeMessage(element) {
    // Find the parent element (the message container) and hide it
    element.parentElement.style.display = "none";
}

// Function to focus on the first box if all are empty, or the last filled box
function handleFocusOnClick() {
    let otpFields = document.querySelectorAll('.otp-box');
    let firstEmptyBox = null;

    // Loop through all OTP input fields
    otpFields.forEach(function(input, index) {
        if (input.value === '' && firstEmptyBox === null) {
            firstEmptyBox = input; // Find the first empty box
        }
    });

    // If all boxes are empty, focus on the first box, otherwise focus on the last filled box
    if (firstEmptyBox) {
        firstEmptyBox.focus();
    } else {
        otpFields[otpFields.length - 1].focus(); // Focus on the last box if all are filled
    }
}

// Add event listeners to all OTP fields
document.querySelectorAll('.otp-box').forEach(function(input) {
    input.addEventListener('click', handleFocusOnClick);
});

// Function to move to the next or previous field based on input or backspace
function moveToNext(current, nextFieldId, prevFieldId) {
    // Move to the next field if the current field is filled
    if (current.value.length === 1 && nextFieldId) {
        document.getElementById(nextFieldId).focus();
    }

    // Move to the previous field on Backspace if the field is empty
    current.addEventListener('keydown', function(event) {
        if (event.key === 'Backspace' && current.value === '' && prevFieldId) {
            document.getElementById(prevFieldId).focus();
        }
    });
}

document.getElementById('resend-otp').addEventListener('click', function (event) {
    event.preventDefault(); // Prevent the default anchor action
    const errorMessage = document.getElementById('otp-error');
    errorMessage.style.display = 'none'; // Hide error message

    // Disable the OTP verify button
    $('#otp-verify-btn').prop('disabled', true);

    // Disable the resend link visually
    $(this).addClass('disabled').html('<i class="fa fa-spinner fa-spin"></i> Sending...'); // Add spinner

    // Get email from session storage
    const sessionEmail = sessionStorage.getItem('reset-password-email');

    // Make AJAX request to resend OTP
    $.ajax({
        url: '/monitoring/rest/otp/request', // Replace with your API endpoint
        type: 'POST',
        contentType: 'application/json', // Set the content type to JSON
        data: JSON.stringify({ // Convert data to JSON string
            email: sessionEmail
        }),
        success: function (response) {
            Notification.notifySuccess("Success", "Successfully sent OTP to " + sessionEmail);
        },
        error: function (xhr, status, error) {
            // Check if the response contains a JSON object
            if (xhr.responseJSON && xhr.responseJSON.description) {
                Notification.notifyError('Error', xhr.responseJSON.description);
            } else {
                // Fallback if there's no responseJSON
                Notification.notifyError('Error', "Server Error");
            }
        },
        complete: function () {
            // Re-enable the buttons and remove the spinner
            $('#otp-verify-btn').prop('disabled', false);
            $('#resend-otp').removeClass('disabled').html('<small>Resend OTP</small>'); // Reset the link text
        }
    });
});

document.getElementById('otp-verify-btn').addEventListener('click', function () {
    event.preventDefault(); // Prevent the default anchor action

    $('#otp-verify-btn').prop('disabled', true).prepend('<i class="fa fa-spinner fa-spin"></i> ');

    const otpInputs = document.querySelectorAll('.otp-input-group input');
    const errorMessage = document.getElementById('otp-error');
    let allFilled = true;

    // Check if all OTP fields are filled
    otpInputs.forEach(input => {
        if (!input.value) {
            allFilled = false;
        }
    });

    // Display or hide error message
    if (!allFilled) {
        errorMessage.style.display = 'block'; // Show error message
        $('#otp-verify-btn').prop('disabled', false).find('i.fa-spinner').remove();
    } else {
        errorMessage.style.display = 'none'; // Hide error message
        // Here you can proceed with further actions, like submitting the OTP
        // For example, you might want to gather the OTP values and send them to your server.
        const otpValues = Array.from(otpInputs).map(input => input.value).join('');
    }
    setTimeout(() => {
        $('#otp-verify-btn').prop('disabled', false).find('i.fa-spinner').remove();
    }, 3000); // Re-enable after 30 seconds
});


// Initialize the Login module
$(document).ready(function () {
    document.getElementById('email-display').textContent = sessionStorage.getItem('reset-password-email'); // Display email
    document.getElementById('otp1').focus();
});

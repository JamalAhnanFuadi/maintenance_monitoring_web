
document.getElementById('change-password-btn').addEventListener('click', function (event) {
    event.preventDefault(); // Prevent the default anchor action
    const errorMessage = document.getElementById('change-password-error');
    errorMessage.style.display = 'none'; // Hide error message

    // Disable the resend link visually
    $('#change-password-btn').prop('disabled', true).prepend('<i class="fa fa-spinner fa-spin"></i> ');

    const newPassword = document.getElementById('new-password').value;
    const confirmPassword = document.getElementById('confirm-password').value;
    if (!newPassword || !confirmPassword) {
        errorMessage.style.display = 'block';
        errorMessage.textContent = "Please fill in all fields.";
        $('#change-password-btn').prop('disabled', false).find('i.fa-spinner').remove();
        return;
    }
    if (newPassword !== confirmPassword) {
        errorMessage.style.display = 'block';
        errorMessage.textContent = "Passwords do not match. Please try again.";
        $('#change-password-btn').prop('disabled', false).find('i.fa-spinner').remove();
        return;
    } else {
        // Get email from session storage
        const sessionEmail = sessionStorage.getItem('reset-password-email');

        // Make AJAX request to resend OTP
        $.ajax({
            url: '/monitoring/rest/passwords/change', // Replace with your API endpoint
            type: 'POST',
            contentType: 'application/json', // Set the content type to JSON
            data: JSON.stringify({ // Convert data to JSON string
                email: sessionEmail,
                password: newPassword
            }),
            success: function (response) {
                Notification.notifySuccess("Success", "Successfully change user password");
                window.location.href = 'login';
            },
            error: function (xhr, status, error) {
                // Check if the response contains a JSON object
                if (xhr.responseJSON && xhr.responseJSON.description) {
                    Notification.notifyError('Error', xhr.responseJSON.description);
                } else {
                    // Fallback if there's no responseJSON
                    Notification.notifyError('Error', "Server Error");
                }
                $('#change-password-btn').prop('disabled', false).find('i.fa-spinner').remove();
            },
            complete: function () {
                // Re-enable the buttons and remove the spinner
                $('#change-password-btn').prop('disabled', false).find('i.fa-spinner').remove();
            }
        });
    }
});

// Initialize the Login module
$(document).ready(function () {

});

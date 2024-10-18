var Login = function () {

    // Function for switching form views (login, reminder, and register forms)
    var switchView = function (viewHide, viewShow, viewHash) {
        viewHide.slideUp(250);
        viewShow.slideDown(250, function () {
            $('input').placeholder();
        });

        if (viewHash) {
            window.location = '#' + viewHash;
        } else {
            window.location = '#';
        }
    };

    // Function to handle the login API call
    var callLoginAPI = function (email, password) {
        $.ajax({
            url: '/monitoring/rest/authentications', // Replace with your API endpoint
            type: 'POST',
            contentType: 'application/json', // Set the content type to JSON
            data: JSON.stringify({ // Convert data to JSON string
                username: email,
                password: password
            }),
            success: function (response) {
                window.location.href = 'index';
                sessionStorage.setItem("authenticated", true); // Save to sessionStorage
            },
            error: function (xhr, status, error) {
                // Handle error
                Notification.notifyError('Unauthorized', "Invalid email and/or password");
                $('#login-btn').prop('disabled', false).find('i.fa-spinner').remove();
            }
        });
    };

    // Function to handle the login API call
    var callValidateEmailAPI = function (email) {
        $.ajax({
            url: '/monitoring/rest/users/validate/email', // Replace with your API endpoint
            type: 'POST',
            contentType: 'application/json', // Set the content type to JSON
            data: JSON.stringify({ // Convert data to JSON string
                email: email
            }),
            success: function (response) {
                console.log(response.description)
                Notification.notifySuccess('Success', "Successfully delete user " + response);
                $('#reset-password-btn').prop('disabled', false).find('i.fa-spinner').remove();
            },
            error: function (xhr, status, error) {
                // Check if the response contains a JSON object
                if (xhr.responseJSON && xhr.responseJSON.description) {
                    Notification.notifyError('Error', xhr.responseJSON.description);
                } else {
                    // Fallback if there's no responseJSON
                    Notification.notifyError('Error', "Server Error");
                }
                $('#reset-password-btn').prop('disabled', false).find('i.fa-spinner').remove();
            }
        });
    };


    return {
        init: function () {
            /* Switch Login, Reminder and Register form views */
            var formLogin = $('#form-login'),
                formReminder = $('#form-reminder');

            $('#link-reminder-login').click(function () {
                switchView(formLogin, formReminder, 'reminder');
            });

            $('#link-reminder').click(function () {
                switchView(formReminder, formLogin, '');
            });

            // If the link includes the hashtag 'reminder', show the reminder form instead of login
            if (window.location.hash === '#reminder') {
                formLogin.hide();
                formReminder.show();
            }

            // Initialize Validation and handle form submission
            $('#form-login').validate({
                errorClass: 'help-block animation-slideDown',
                errorElement: 'div',
                errorPlacement: function (error, e) {
                    e.parents('.form-group > div').append(error);
                },
                highlight: function (e) {
                    $(e).closest('.form-group').removeClass('has-success has-error').addClass('has-error');
                    $(e).closest('.help-block').remove();
                },
                success: function (e) {
                    e.closest('.form-group').removeClass('has-success has-error');
                    e.closest('.help-block').remove();
                },
                rules: {
                    'login-email': {
                        required: true,
                        email: true
                    },
                    'login-password': {
                        required: true,
                        minlength: 5
                    }
                },
                messages: {
                    'login-email': 'Please enter your account\'s email',
                    'login-password': {
                        required: 'Please provide your password',
                        minlength: 'Your password must be at least 5 characters long'
                    }
                },
                submitHandler: function (form) {
                    // This is called when the form is valid
                    var email = $('#login-email').val();
                    var password = $('#login-password').val();
                    $('#login-btn').prop('disabled', true).prepend('<i class="fa fa-spinner fa-spin"></i> ');

                    callLoginAPI(email, password); // Call the login API
                }
            });

            /* Reminder form - Initialize Validation */
            $('#form-reminder').validate({
                errorClass: 'help-block animation-slideDown',
                errorElement: 'div',
                errorPlacement: function (error, e) {
                    e.parents('.form-group > div').append(error);
                },
                highlight: function (e) {
                    $(e).closest('.form-group').removeClass('has-success has-error').addClass('has-error');
                    $(e).closest('.help-block').remove();
                },
                success: function (e) {
                    e.closest('.form-group').removeClass('has-success has-error');
                    e.closest('.help-block').remove();
                },
                rules: {
                    'reminder-email': {
                        required: true,
                        email: true
                    }
                },
                messages: {
                    'reminder-email': 'Please enter your account\'s email'
                },
                submitHandler: function (form) {
                    // This is called when the form is valid
                    var email = $('#reminder-email').val();
                    $('#reset-password-btn').prop('disabled', true).prepend('<i class="fa fa-spinner fa-spin"></i> ');

                    callValidateEmailAPI(email); // Call the login API
                }
            });
        }
    };
}();

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


// Initialize the Login module
$(document).ready(function () {
    Login.init();
    const sessionEmail = sessionStorage.getItem('reset-password-email');
    document.getElementById('email-display').textContent = sessionEmail; // Display email
    document.getElementById('otp1').focus();
});

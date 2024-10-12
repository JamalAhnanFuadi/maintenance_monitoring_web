var Login = function() {

    // Function for switching form views (login, reminder, and register forms)
    var switchView = function(viewHide, viewShow, viewHash) {
        viewHide.slideUp(250);
        viewShow.slideDown(250, function() {
            $('input').placeholder();
        });

        if (viewHash) {
            window.location = '#' + viewHash;
        } else {
            window.location = '#';
        }
    };

    // Function to handle the login API call
    var callLoginAPI = function(email, password) {
        $.ajax({
            url: '/monitoring/rest/authentications', // Replace with your API endpoint
            type: 'POST',
            contentType: 'application/json', // Set the content type to JSON
            data: JSON.stringify({ // Convert data to JSON string
                username: email,
                password: password
            }),
            success: function(response) {
                window.location.href = 'index';
                sessionStorage.setItem("authenticated", true); // Save to sessionStorage
            },
            error: function(xhr, status, error) {
                // Handle error
                Notification.notifyError('Unauthorized', "Invalid email and/or password");
                $('#login-btn').prop('disabled', false).find('i.fa-spinner').remove();
            }
        });
    };


    return {
        init: function() {
            /* Switch Login, Reminder and Register form views */
            var formLogin = $('#form-login'),
                formReminder = $('#form-reminder');

            $('#link-reminder-login').click(function() {
                switchView(formLogin, formReminder, 'reminder');
            });

            $('#link-reminder').click(function() {
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
                errorPlacement: function(error, e) {
                    e.parents('.form-group > div').append(error);
                },
                highlight: function(e) {
                    $(e).closest('.form-group').removeClass('has-success has-error').addClass('has-error');
                    $(e).closest('.help-block').remove();
                },
                success: function(e) {
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
                submitHandler: function(form) {
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
                errorPlacement: function(error, e) {
                    e.parents('.form-group > div').append(error);
                },
                highlight: function(e) {
                    $(e).closest('.form-group').removeClass('has-success has-error').addClass('has-error');
                    $(e).closest('.help-block').remove();
                },
                success: function(e) {
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
                }
            });
        }
    };
}();

// Initialize the Login module
$(document).ready(function() {
    Login.init();
});

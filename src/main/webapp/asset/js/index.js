$(document).ready(function () {
    let firstname = 'Guest'; // Default value
    let authenticated = sessionStorage.getItem("authenticated");

    session(); // Call session function on document ready
    retrieveUser(); // Call retrieveUser function on document ready

    function session() {
        $.ajax({
            url: '/monitoring/rest/authentications/session',
            method: 'GET',
            success: function (response) {
                if (response.status === 200) {
                    authenticated = true; // User is authenticated
                }
            },
            error: function (xhr) {
                Notification.notifyError('Unauthorized', "Login session timed out");
                location.href = 'login'; // Redirect to login page
                authenticated = false;
            }
        });
    }

    function retrieveUser() {
        $.ajax({
            url: '/monitoring/rest/authentications/profile',
            method: 'GET',
            success: function (response) {
                if (response && response.firstname) {
                    firstname = response.firstname; // Update fullname
                    sessionStorage.setItem("firstname", firstname); // Save to sessionStorage

                    // Update the welcome message on the page
                    $('#firstname').text(firstname);
                } else {
                    Notification.notifyError('Profile retrieval failed, status', response.status);
                }
            },
            error: function (xhr) {
                Notification.notifyError('Oops', xhr);
            }
        });
    }
});

var TablesDatatables = (function () {
    var initDatatable = function () {
        // Check if DataTable is already initialized and destroy it
        if ($.fn.DataTable.isDataTable('#user-management')) {
            $('#user-management').DataTable().destroy();
        }

        // Initialize Bootstrap Datatables Integration
        App.datatables();

        // Initialize Datatables with AJAX source
        $('#user-management').DataTable({
            autoWidth: false, // Disable auto width calculation
            ajax: {
                url: '/monitoring/rest/users',
                method: 'GET',
                dataSrc: function (json) {
                    return json.users ? json.users : [];
                }
            },
            columns: [
                {
                    data: null, // No specific data field, as this column will have action buttons
                    className: 'text-center', // Center the buttons
                    orderable: false, // Disable ordering for this column
                    render: function (data) {
                        return ''; // Handle null/undefined
                    }
                },
                {
                    data: 'fullname',
                    render: function (data) {
                        return data ? data : '-'; // Handle null/undefined
                    }
                },
                {
                    data: 'email',
                    render: function (data) {
                        return data ? data : '-'; // Handle null/undefined
                    }
                },
                {
                    data: 'department',
                    className: 'text-center',
                    render: function (data) {
                        return data ? data : '-'; // Handle null/undefined
                    }
                },
                {
                    data: 'accessGroupName',
                    className: 'text-center',
                    render: function (data) {
                        return data ? data : '-'; // Handle null/undefined
                    }
                },
                {
                    data: 'status',
                    className: 'text-center',
                    render: function (data, type, row) {
                        return `
                            <label class="switch switch-primary"  data-toggle="tooltip"  data-placement="right"  title="${data ? 'Active' : 'Inactive'}"> 
                                <input type="checkbox" ${data ? 'checked' : ''} class="status-toggle" data-uid="${row.uid}"> <span></span> 
                            </label>`;
                    }
                },
                {
                    data: null, // No specific data field, as this column will have action buttons
                    className: 'text-center', // Center the buttons
                    orderable: false, // Disable ordering for this column
                    render: function (data, type, row) {
                        const isDeletable = row.status === false; // or row.status === 0 depending on your data structure
                        return `
                            <div class="btn-group">
                                <button class="btn btn-sm btn-primary edit-btn" data-id="${row.uid}">Edit</button>
                                <button class="btn btn-sm btn-danger delete-btn" data-id="${row.uid}" data-fullname="${row.fullname}" ${isDeletable ? '' : 'disabled'} title="${isDeletable ? '' : 'Not deletable when status is active'}">Delete</button>
                            </div>`;
                    }
                }
            ],
            columnDefs: [
                { width: '3%', targets: 0 }, // Set width for the first column
                { width: '15%', targets: 1 }, // Set width for the first column
                { width: '15%', targets: 2 }, // Set width for the second column
                { width: '15%', targets: 3 }, // Set width for the third column
                { width: '15%', targets: 4 }, // Set width for the fourth column
                { width: '10%', targets: 5 }, // Set width for the fourth column
                { width: '15%', targets: 6 }  // Set width for the fifth (actions) column
            ],
            pageLength: 10,
            lengthMenu: [[10, 20, 30, -1], [10, 20, 30, 'All']],
            responsive: true // Ensure responsiveness
        });

        // Add placeholder attribute to the search input
        $('.dataTables_filter input').attr('placeholder', 'Search');
    };

    return {
        init: function () {
            initDatatable();
        }
    };
})();

$(document).on('click', '.edit-btn', function () {
    var userId = $(this).data('id');
    // Fetch user data via AJAX
    $.ajax({
        url: '/monitoring/rest/users/' + userId, // Your backend API endpoint for getting user details
        method: 'GET',
        data: { id: userId },
        success: function (response) {
            if (response.status === 200) {
                // Extract user data from the response
                var user = {
                    uid: response.user.uid,
                    firstname: response.user.firstname,
                    lastname: response.user.lastname,
                    email: response.user.email,
                    mobileNumber: response.user.mobileNumber,
                    accessGroupUid: response.user.accessGroupUid ? response.user.accessGroupUid : '',
                    department: response.user.department ? response.user.department : '',
                    dob: response.user.dob
                };

                // Call the openUpdateUserModal with the extracted user data
                openUpdateUserModal(user);
            } else {
                console.log('Error: ' + response.description);
            }
        },
        error: function () {
            console.log('Error fetching user data');
        }
    });
});

function openUpdateUserModal(user) {
    // Reset the form fields
    $('#user-form')[0].reset();
    // Reset validation (remove error classes and messages)
    $('#user-form').find('.form-group').removeClass('has-error has-success');
    $('#user-form').find('.help-block').remove();

    // Populate the form fields with user data
    $('#val_firstname').val(user.firstname);
    $('#val_lastname').val(user.lastname);
    $('#val_mobileNumber').val(user.mobileNumber);
    $('#val_usergroup').val(user.accessGroupUid).trigger("chosen:updated");
    $('#val_department').val(user.department).trigger("chosen:updated");

    // Convert user.dob to yyyy-mm-dd format
    const dob = new Date(user.dob);
    const formattedDob = dob.toISOString().split('T')[0];
    $('#val_dob').val(formattedDob);

    // Set the email and ensure it is editable
    $('#val_email').val(user.email).prop('readonly', true).prop('disabled', true);

    $('#submit-button').data('id', user.uid); // Replace with the actual property for user ID

    // Change modal title and button labels
    $('.modal-title').text('Update User');
    $('#submit-button').text('Save Changes');
    $('#reset-button').text('Close');

    // Open the modal
    $('#user-modal').modal('show');

}


// Function to populate the dropdown from API
function populateDepartments() {
    $.ajax({
        url: '/monitoring/rest/departments', // API endpoint
        method: 'GET',
        dataType: 'json', // Expect JSON response
        success: function (response) {
            // Iterate through the API response and append options to the dropdown
            response.forEach(function (department) {
                $('#val_department').append(
                    $('<option>', {
                        value: department.displayName,
                        text: department.displayName
                    })
                );
            });

            // If using Chosen or Select2, you may need to trigger an update after adding options
            $('#val_department').trigger("chosen:updated"); // For Chosen dropdown
            // For Select2, you can use: $('#val_department').select2();
        },
        error: function (xhr, status, error) {
            console.error("Error fetching departments:", status, error);
        }
    });
}

function populateAccessGroup() {
    $.ajax({
        url: '/monitoring/rest/accessgroup', // API endpoint
        method: 'GET',
        dataType: 'json', // Expect JSON response
        success: function (response) {
            // Iterate through the API response and append options to the dropdown
            response.forEach(function (department) {
                $('#val_usergroup').append(
                    $('<option>', {
                        value: department.uid,
                        text: department.displayName
                    })
                );
            });

            // If using Chosen or Select2, you may need to trigger an update after adding options
            $('#val_usergroup').trigger("chosen:updated"); // For Chosen dropdown
            // For Select2, you can use: $('#val_department').select2();
        },
        error: function (xhr, status, error) {
            console.error("Error fetching user group:", status, error);
        }
    });
}

// Function to initialize validation
function validateForm() {
    // Check if the validation is already set up
    if (!$.fn.validate.hasOwnProperty('validator')) {
        $('#user-form').validate({ // Initialize validation
            ignore: [], // Ensure it validates the hidden fields used by Chosen or Select2
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
                val_firstname: {
                    required: true
                },
                val_lastname: {
                    required: true
                },
                val_email: {
                    required: true,
                    email: true
                },
                val_mobileNumber: {
                    required: true,
                    minlength: 11 // Adjust based on your mobile number format
                },
                val_usergroup: {
                    required: true
                },
                val_department: {
                    required: true
                },
                val_dob: {
                    required: true,
                    date: true // Validate date format
                }
            },
            messages: {
                val_firstname: {
                    required: 'Please enter your first name',
                    minlength: 'Your first name must be at least 2 characters long'
                },
                val_lastname: {
                    required: 'Please enter your last name',
                    minlength: 'Your last name must be at least 2 characters long'
                },
                val_email: 'Please enter a valid email address',
                val_mobileNumber: {
                    required: 'Please enter your mobile number',
                    minlength: 'Your mobile number must be at least 11 characters long' // Adjust accordingly
                },
                val_usergroup: {
                    required: 'Please select a group'
                },
                val_department: {
                    required: 'Please select a department'
                },
                val_dob: {
                    required: 'Please enter your date of birth',
                    date: 'Please enter a valid date'
                }
            }
        });
    }

    // Return validity of the form
    return $('#user-form').valid(); // Returns true if valid, false otherwise
}

$(document).on('click', '#reset-button', function () {
    // Check if the button label is "Close"
    if ($(this).text().trim() === 'Close') {
        // Optionally, you can add any additional logic here before closing the modal
        // For example, you can clear form fields or show a confirmation dialog

        // Close the modal
        $('#user-modal').modal('hide');
    }
});

$(document).on('click', '#reset-button', function () {
    // Check if the button label is "Close"
    if ($(this).text().trim() === 'Close') {
        // Optionally, you can add any additional logic here before closing the modal
        // For example, you can clear form fields or show a confirmation dialog

        // Close the modal
        $('#user-modal').modal('hide');
    }
});

$(document).on('click', '.delete-btn', function () {
    var userId = $(this).data('id');
    var fullName = $(this).data('fullname');

    // Reset the modal before using it
    $('#delete-user-modal .modal-body').empty(); // Clear the modal body
    $('#delete-user-modal .modal-footer').empty(); // Clear the modal footer

    // Set the modal body content
    $('#delete-user-modal .modal-body').html(`Are you sure you want to delete the user: <strong>${fullName}</strong>?`);

    // Store the userId in the modal for later use
    $('#delete-user-modal').data('userId', userId);

    // Set the modal title for adding
    $('.modal-title').text('Delete User'); // Change title to indicate deletion

    // Create the Confirm Delete button
    var confirmDeleteButton = $('<button>', {
        id: 'confirm-delete-button',
        class: 'btn btn-sm btn-danger',
        text: 'Confirm Delete',
        click: function () {

            $(this).prop('disabled', true).prepend('<i class="fa fa-spinner fa-spin"></i> ');
            $('#cancel-button').prop('disabled', true).prepend('<i class="fa fa-spinner fa-spin"></i> ');

            // Handle the delete action here
            $.ajax({
                url: `/monitoring/rest/users/${userId}`,
                type: 'DELETE',
                data: { id: userId }, // Send the userId to the server
                success: function (response) {
                    Notification.notifySuccess('Success', "Successfully delete user " + fullName);
                    setTimeout(function () {
                        $('#delete-user-modal').modal('hide'); // Close the modal
                        $('#user-management').DataTable().destroy();
                        TablesDatatables.init();
                    }, 3000); // 3000 milliseconds = 3 seconds;
                },
                error: function (xhr, status, error) {
                    // Handle error response
                    Notification.notifyError('Error', "Failed delete user " + fullName);
                }
            });
        }
    });

    // Create the Cancel button
    var cancelButton = $('<button>', {
        id: 'cancel-button',
        class: 'btn btn-sm btn-default',
        text: 'Cancel',
        click: function () {
            $('#delete-user-modal').modal('hide'); // Just close the modal
        }
    });

    // Append both buttons to the modal footer
    $('#delete-user-modal .modal-footer').append(confirmDeleteButton, cancelButton);

    // Show the modal
    $('#delete-user-modal').modal('show');
});

$(document).on('click', '#submit-button', function () {
    event.preventDefault(); // Prevent default form submission

    var userId = $(this).data('id');

    // Perform validation
    const isValid = validateForm(); // Call your validation function
    if (isValid) {

        // Disable the submit and reset buttons, add spinner icon
        $(this).prop('disabled', true).prepend('<i class="fa fa-spinner fa-spin"></i> ');
        $('#reset-button').prop('disabled', true).prepend('<i class="fa fa-spinner fa-spin"></i> ');

        // Gather user data from input fields
        const userData = {
            uid : userId, // Get userId for update if it exists
            firstname: $('#val_firstname').val(), // Get first name
            lastname: $('#val_lastname').val(),   // Get last name
            email: $('#val_email').val(),         // Get email
            mobileNumber: $('#val_mobileNumber').val(), // Get mobile number
            accessGroupUid: $('#val_usergroup').val(), // Get department
            department: $('#val_department').val(), // Get department
            dob: $('#val_dob').val() // Get date of birth
        };
        if ($(this).text().trim() === 'Submit') {
            $.ajax({
                url: '/monitoring/rest/users', // Your endpoint
                method: 'POST',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(userData),
                success: function (response, status, xhr) {
                    if (xhr.status === 200) {
                        Notification.notifySuccess('Success', "Successfully add new user");
                        // Delay the closing of the modal and refreshing the user list
                        setTimeout(function () {
                            $('#user-modal').modal('hide'); // Close the modal
                            $('#user-management').DataTable().destroy();
                            TablesDatatables.init();
                        }, 3000); // 3000 milliseconds = 3 seconds
                    } else {
                        Notification.notifyError('Error', "Unexpected response: " + xhr.status);
                    }
                },
                error: function (xhr, status, error) {
                    // Handle error response based on the status code
                    switch (xhr.status) {
                        case 400: // Bad Request
                            // Attempt to extract the description from the response
                            let errorMessage = "Invalid input. Please check your data.";
                            if (xhr.responseJSON && xhr.responseJSON.description) {
                                errorMessage = xhr.responseJSON.description; // Get the description from the response
                            }
                            Notification.notifyError('Error', errorMessage);
                            break;
                        case 401: // Unauthorized
                            Notification.notifyError('Error', "You are not authorized to perform this action.");
                            break;
                        case 404: // Not Found
                            Notification.notifyError('Error', "User not found.");
                            break;
                        case 500: // Internal Server Error
                            Notification.notifyError('Error', "An unexpected error occurred. Please try again later.");
                            break;
                        default: // Other status codes
                            Notification.notifyError('Error', "Failed adding new user. Status: " + xhr.status);
                            break;
                    }
                }

            });
        }

        if ($(this).text().trim() === 'Save Changes') {
            $.ajax({
                url: '/monitoring/rest/users', // Your endpoint
                method: 'PUT',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(userData),
                success: function (response, status, xhr) {
                    if (xhr.status === 200) {
                        Notification.notifySuccess('Success', "Successfully update user");
                        // Delay the closing of the modal and refreshing the user list
                        setTimeout(function () {

                            $('#user-modal').modal('hide'); // Close the modal
                            $('#user-management').DataTable().destroy();
                            TablesDatatables.init();
                        }, 3000); // 3000 milliseconds = 3 seconds
                    } else {
                        Notification.notifyError('Error', "Unexpected response: " + xhr.status);
                    }
                },
                error: function (xhr, status, error) {
                    // Handle error response based on the status code
                    switch (xhr.status) {
                        case 400: // Bad Request
                            // Attempt to extract the description from the response
                            let errorMessage = "Invalid input. Please check your data.";
                            if (xhr.responseJSON && xhr.responseJSON.description) {
                                errorMessage = xhr.responseJSON.description; // Get the description from the response
                            }
                            Notification.notifyError('Error', errorMessage);
                            break;
                        case 401: // Unauthorized
                            Notification.notifyError('Error', "You are not authorized to perform this action.");
                            break;
                        case 404: // Not Found
                            Notification.notifyError('Error', "User not found.");
                            break;
                        case 500: // Internal Server Error
                            Notification.notifyError('Error', "An unexpected error occurred. Please try again later.");
                            break;
                        default: // Other status codes
                            Notification.notifyError('Error', "Failed adding new user. Status: " + xhr.status);
                            break;
                    }
                }

            });
        }

        setTimeout(function () {
            $('#submit-button').prop('disabled', false).find('i.fa-spinner').remove();
            $('#reset-button').prop('disabled', false).find('i.fa-spinner').remove();

        }, 3000); // 3000 milliseconds = 3 seconds
    }
});

$(document).on('click', '#add-user-button', function () {
    // Reset the form fields
    $('#user-form')[0].reset();
    $('#val_usergroup').val('').trigger('chosen:updated'); // Reset the Chosen dropdown
    $('#val_department').val('').trigger('chosen:updated'); // Reset the Chosen dropdown

    // Reset the validation (remove error classes and messages)
    $('#user-form').find('.form-group').removeClass('has-error has-success');
    $('#user-form').find('.help-block').remove();

    // Set the email and ensure it is editable
    $('#val_email').val('').prop('readonly', false).prop('disabled', false);

    // Set the modal title for adding
    $('.modal-title').text('Add User');
    $('#submit-button').text('Submit'); // Change button label to "Save Changes"
    $('#reset-button').text('Reset'); // Change button label to "Save Changes"

    // Optionally hide the user ID input if you're using it for editing
    $('#user-id').val(''); // Clear the user ID if you have one
});

$(document).on('click', '#reset-button', function () {
    // Reset input fields
    $('#val_firstname').val('');
    $('#val_lastname').val('');
    $('#val_email').val('');
    $('#val_mobileNumber').val('');
    $('#val_dob').val('');

    // Reset the dropdown
    $('#val_usergroup').val('').trigger('chosen:updated'); // Reset the Chosen dropdown
    $('#val_department').val('').trigger('chosen:updated'); // Reset the Chosen dropdown
});

$(document).on('change', '.status-toggle', function() {
    const isChecked = $(this).is(':checked'); // Get the checkbox state (checked or not)
    const uid = $(this).data('uid'); // Get the user ID from the data attribute

    // Prepare the data to be sent to the server
    const data = {
        uid: uid,
        status: isChecked // Set status to true if checked, false otherwise
    };

    // Make the AJAX call
    $.ajax({
        url: '/monitoring/rest/users/status', // Replace with your actual endpoint
        method: 'POST', // or 'PUT' depending on your API
        contentType: 'application/json', // Set content type to JSON
        data: JSON.stringify(data), // Convert data object to JSON string
        success: function (response, status, xhr) {
            if (xhr.status === 200) {
                Notification.notifySuccess('Success', "Successfully update user");
                setTimeout(function () {
                    TablesDatatables.init();
                }, 2000); // 3000 milliseconds = 3 seconds
            } else {
                Notification.notifyError('Error', "Unexpected response: " + xhr.status);
            }
        },
        error: function (xhr, status, error) {
            // Handle error response based on the status code
            switch (xhr.status) {
                case 400: // Bad Request
                    // Attempt to extract the description from the response
                    let errorMessage = "Invalid input. Please check your data.";
                    if (xhr.responseJSON && xhr.responseJSON.description) {
                        errorMessage = xhr.responseJSON.description; // Get the description from the response
                    }
                    Notification.notifyError('Error', errorMessage);
                    break;
                case 401: // Unauthorized
                    Notification.notifyError('Error', "You are not authorized to perform this action.");
                    break;
                case 404: // Not Found
                    Notification.notifyError('Error', "User not found.");
                    break;
                case 500: // Internal Server Error
                    Notification.notifyError('Error', "An unexpected error occurred. Please try again later.");
                    break;
                default: // Other status codes
                    Notification.notifyError('Error', "Failed adding new user. Status: " + xhr.status);
                    break;
            }
        }
    });
});


$(document).ready(function () {
    $(".select-chosen").chosen(); // Initialize Chosen

    TablesDatatables.init(); // Initialize the datatable when the document is ready
    populateDepartments();
    populateAccessGroup();
});

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
                    data: null, // No specific data field, as this column will have action buttons
                    className: 'text-center', // Center the buttons
                    orderable: false, // Disable ordering for this column
                    render: function (data, type, row) {
                        return `
                            <div class="btn-group">
                                <button class="btn btn-sm btn-primary edit-btn" data-id="${row.uid}">Edit</button>
                                <button class="btn btn-sm btn-danger delete-btn" data-id="${row.uid}" data-fullname="${row.fullname}">Delete</button>
                            </div>`;
                    }
                }
            ],
            columnDefs: [
                { width: '5%', targets: 0 }, // Set width for the first column
                { width: '25%', targets: 1 }, // Set width for the first column
                { width: '20%', targets: 2 }, // Set width for the second column
                { width: '15%', targets: 3 }, // Set width for the third column
                { width: '15%', targets: 4 }, // Set width for the fourth column
                { width: '10%', targets: 5 }  // Set width for the fifth (actions) column
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

$(document).ready(function () {

});

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

function openAddUserModal() {
    // Clear the form fields
    $('#val_firstname').val('');
    $('#val_lastname').val('');
    $('#val_email').val('');
    $('#val_mobileNumber').val('');
    $('#val_department').val('').trigger("chosen:updated"); // Reset Chosen dropdown
    $('#val_dob').val('');

    // Change modal title and button labels for adding a user
    $('.modal-title').text('Add User'); // Change modal title to "Add User"
    $('#btn-close-modal').text('Reset'); // Ensure button label is "Close"
    $('#btn-save-user').text('Submit'); // Change button label to "Add User"

    // Open the modal
    $('#user-modal').modal('show');
}

function openUpdateUserModal(user) {
    // Reset the form fields
    $('#user-form')[0].reset();
    // Reset the validation (remove error classes and messages)
    $('#user-form').find('.form-group').removeClass('has-error has-success');
    $('#user-form').find('.help-block').remove();

    // Populate the form fields with the user data
    $('#val_firstname').val(user.firstname);
    $('#val_lastname').val(user.lastname);
    $('#val_email').val(user.email);
    $('#val_mobileNumber').val(user.mobileNumber);
    $('#val_department').val(user.department).trigger("chosen:updated"); // Update Chosen dropdown

    // Convert user.dob to yyyy-mm-dd format for the date input
    const dob = new Date(user.dob);
    const formattedDob = dob.toISOString().split('T')[0]; // Get yyyy-mm-dd format
    $('#val_dob').val(formattedDob); // Set the value for date input

    $('.modal-title').text('Update User'); // Change modal title to "Update User"
    $('#submit-button').text('Save Changes'); // Change button label to "Save Changes"
    $('#reset-button').text('Close'); // Change button label to "Save Changes"

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
        class: 'btn btn-danger',
        text: 'Confirm Delete',
        click: function () {

            // Handle the delete action here
            $.ajax({
                url: `/monitoring/rest/users/${userId}`,
                type: 'DELETE',
                data: { id: userId }, // Send the userId to the server
                success: function(response) {
                    // Handle success response
                    $('#delete-user-modal').modal('hide'); // Close the modal after the action
                    Notification.notifySuccess('Success', "Success delete user " + fullName);
                    $('#user-management').DataTable().destroy();
                    TablesDatatables.init();
                },
                error: function(xhr, status, error) {
                    // Handle error response
                    Notification.notifyError('Error', "Failed delete user " + fullName);
                }
            });
        }
    });

    // Create the Cancel button
    var cancelButton = $('<button>', {
        id: 'cancel-button',
        class: 'btn btn-default',
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

    // Perform validation
    const isValid = validateForm(); // Call your validation function

    if ($(this).text().trim() === 'Submit') {
        console.log('Trigger add user')
    }

    if ($(this).text().trim() === 'Save Changes') {
        console.log('Trigger update user')
    }
});

$(document).on('click', '#add-user-button', function () {
    // Reset the form fields
    $('#user-form')[0].reset();

    // Reset the validation (remove error classes and messages)
    $('#user-form').find('.form-group').removeClass('has-error has-success');
    $('#user-form').find('.help-block').remove();

    // Set the modal title for adding
    $('.modal-title').text('Add User');
    $('#submit-button').text('Submit'); // Change button label to "Save Changes"
    $('#reset-button').text('Reset'); // Change button label to "Save Changes"

    // Optionally hide the user ID input if you're using it for editing
    $('#user-id').val(''); // Clear the user ID if you have one
});
$(document).ready(function () {
    $(".select-chosen").chosen(); // Initialize Chosen

    TablesDatatables.init(); // Initialize the datatable when the document is ready
    populateDepartments();
});

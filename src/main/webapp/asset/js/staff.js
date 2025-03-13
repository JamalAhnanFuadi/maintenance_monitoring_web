$(document).ready(function () {

    $('#staff-link').addClass('active');
    $('#staff-link').trigger('click');

    $(".select-chosen").chosen(); // Initialize Chosen

    StaffDatatables.init(); // Initialize the datatable when the document is ready
    populateDepartments();
});

var StaffDatatables = (function () {
    var initDatatable = function () {
        // Check if DataTable is already initialized and destroy it
        if ($.fn.DataTable.isDataTable('#staff-datatable')) {
            $('#staff-datatable').DataTable().destroy();
        }

        // Initialize Bootstrap Datatables Integration
        App.datatables();

        // Initialize Datatables with AJAX source
        $('#staff-datatable').DataTable({
            autoWidth: false, // Disable auto width calculation
            ajax: {
                url: '/monitoring/rest/staff',
                method: 'GET',
                dataSrc: function (response) {
                    return response ? response : [];
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
                    data: null,
                    render: function (data) {
                        return data ? `${data.firstname} ${data.lastname}` :  '-'; // Handle null/undefined
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
                    data: 'status',
                    className: 'text-center',
                    render: function (data, type, row) {
                        const statusClass = data ? 'label label-success' : 'label label-warning'; // Menentukan class berdasarkan status
                        const statusText = data ? 'Active' : 'Inactive'; // Menentukan teks status
                        return `<span class="${statusClass}">${statusText}</span>`; // Mengembalikan elemen dengan class dan teks status
                    }
                },
                {
                    data: null, // No specific data field, as this column will have action buttons
                    className: 'text-center', // Center the buttons
                    orderable: false, // Disable ordering for this column
                    render: function (data, type, row) {
                        const locked = row.status === false; // or row.status === 0 depending on your data structure
                        return `
                            <div class="btn-group">
                            <div class="text-center">
                                <a class="btn btn-sm btn-info view-button" data-id="${row.uid}">
                                    <i class="fa fa-eye"></i> View
                                </a>
                                <a class="btn btn-sm btn-danger delete-btn" data-id="${row.uid}" 
                                    data-displayname="${row.firstname} ${row.lastname}" 
                                    ${locked ? '' : 'disabled title="Cannot delete user when status is active"'}">
                                    <i class="fa fa-times-circle"></i> Delete
                                </a>
                            </div>
                            </div>`;
                    }
                }
            ],
            columnDefs: [
                { width: '3%', targets: 0 }, // Set width for the first column
                { width: '15%', targets: 1 }, // Set width for the first column
                { width: '15%', targets: 2 }, // Set width for the second column
                { width: '15%', targets: 3 }, // Set width for the third column
                { width: '10%', targets: 4 }, // Set width for the fourth column
                { width: '10%', targets: 5 }, // Set width for the fourth column
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

$(document).on('click', '.add-staff-button', function () {
    // Reset the form fields
    $('#add-form')[0].reset();
    $('#val_department').val('').trigger('chosen:updated');

    // Reset the validation (remove error classes and messages)
    $('#add-form').find('.form-group').removeClass('has-error has-success');
    $('#add-form').find('.help-block').remove();
});

function populateDepartments() {
    $.ajax({
        url: '/monitoring/rest/departments',
        method: 'GET',
        dataType: 'json',
        success: function (response) {
            response.forEach(function (department) {
                $('#val_department').append(
                    $('<option>', {
                        value: department.uid,
                        text: department.displayName
                    })
                );
            });
            $('#val_department').trigger("chosen:updated");
        },
        error: function (xhr, status, error) {
            console.error("Unable to fetching departments");
        }
    });
}

function addValidation() {
    if (!$.fn.validate.hasOwnProperty('validator')) {
        $('#add-form').validate({
            ignore: [],
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
                val_email: {
                    required: true,
                    email: true
                },
                val_mobileNumber: {
                    required: true,
                    minlength: 11
                },
                val_department: {
                    required: true
                }
            },
            messages: {
                val_firstname: {
                    required: 'Please enter your first name',
                    minlength: 'Your first name must be at least 2 characters long'
                },
                val_email: 'Please enter a valid email address',
                val_mobileNumber: {
                    required: 'Please enter your mobile number',
                    minlength: 'Your mobile number must be at least 11 characters long'
                },
                val_department: {
                    required: 'Please select a department'
                }
            }
        });
    }
    return $('#add-form').valid();
}

$(document).on("click", "#submit-button", async function (event) {
    event.preventDefault(); // Prevent default form submission
    const isValid = addValidation(); // Assume this is a synchronous validation function
    if (isValid) {
        $("#submit-button").addClass("disabled").html('<i class="fa fa-spinner fa-spin"></i> Submitting...');

        const staffRequest = {
            firstname: $('#val_firstname').val(),
            lastname: $('#val_lastname').val(),
            email: $('#val_email').val(),
            mobileNumber: $('#val_mobileNumber').val(),
            department: $('#val_department').val(),
            dob: $('#val_dob').val()
        };

        var email = $('#val_email').val();
        const isExist = await validateEmail(email); // Await the Promise

        if(!isExist) {
            $.ajax({
                url: '/monitoring/rest/staff',
                method: 'POST',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(staffRequest),
                success: function (response, status, xhr) {
                    Notification.notifySuccess('Success', "Successfully added new user");
                    setTimeout(function () {
                        $('#add-modal').modal('hide');
                        $('#staff-datatable').DataTable().destroy();
                        StaffDatatables.init();
                    }, 2000);
                },
                error: function (xhr, status, error) {
                    Notification.notifyError('Error', "Unable to add new staff");
                    $("#submit-button").removeClass("disabled").html('Submit');
                }
            });
        } else {
            $("#email-error").text("Staff with this email already exists").show();
            $("#submit-button").removeClass("disabled").html('Submit');
        }
    }
});


function validateEmail(email) {
    return new Promise((resolve, reject) => { // Return a Promise
        const validateRequest = {
            email: email // Use the passed email parameter
        };
        $.ajax({
            url: '/monitoring/rest/staff/validate/email',
            method: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(validateRequest),
            success: function (response) {
                resolve(true); // resolve the Promise as valid
            },
            error: function (xhr, status, error) {
                resolve(false); // resolve the Promise as not valid
            }
        });
    });
}


$(document).on("click", ".view-button", function () {
    var staffId = $(this).data("id");

    $.ajax({
        url: "/monitoring/rest/staff/" + staffId,
        method: "GET",
        data: { id: staffId},
        success: function (response, status, xhr) {

            $("#val_vuid").val(response.uid);

            $("#val_vfirstname").val(response.firstname);
            $("#val_vlastname").val(response.lastname);
            $("#val_vemail").val(response.email);
            $("#val_vmobileNumber").val(response.mobileNumber);
            $("#val_vdepartment").val(response.department);
            $("#val_vdob").val(response.dob);

            $("#val_vstatus").val(response.status);
            if (response.status === true) {
                $('#val_vstatus').prop('checked', true).change();
            } else {
                $('#val_vstatus').prop('checked', false).change();
            }

            $("#val_vuid").prop("readonly", true);
            $("#val_vdepartment_name").prop("readonly", true);
            $("#val_vdescription").prop("readonly", true);

            $("#cancel-update-button").removeClass("disabled").html('Cancel');
            $("#confirm-update-button").removeClass("disabled").html('Save changes');

            $("#update-button").show();
            $("#update-modal-footer").hide();

            $("#view-modal").modal("show");
        },
        error: function () {
            Notification.notifyError(
                "Error",
                "Unable to fetch staff information"
            );
        },
    });
});




$(document).on("click", ".delete-btn", function () {
    var id = $(this).data("id");
    var displayName = $(this).data("displayname");

    console.log(id);
    console.log(displayName);

    $("#cancel-delete-button").removeClass("disabled").html('Cancel');

    $("#confirm-delete-button").attr("data-id", id);
    $("#confirm-delete-button").removeClass("disabled").html('Confirm Delete');

    $("#delete-modal .modal-body").empty();
    $("#delete-modal .modal-body").html(
        `Are you sure you want to delete : <strong>${displayName}</strong>?`
    );

    $("#delete-modal").data("id", id);
    $(".modal-title").text("Delete Staff");
    $("#delete-modal").modal("show");
});

$(document).on("click", ".cancel-delete-button", function () {

    $("#cancel-delete-button").addClass("disabled").html('<i class="fa fa-spinner fa-spin"></i> Canceling...');
    $("#confirm-delete-button").addClass("disabled").html('<i class="fa fa-spinner fa-spin"></i> Confirm Delete');
    $("#delete-modal").modal("hide");
});

$(document).on("click", ".confirm-delete-button", function () {
    var id = $(this).data("id");

    $("#cancel-delete-button").addClass("disabled").html('<i class="fa fa-spinner fa-spin"></i> Cancel');
    $("#confirm-delete-button").addClass("disabled").html('<i class="fa fa-spinner fa-spin"></i> Confirming...');

    $.ajax({
        url: `/monitoring/rest/staff/${id}`,
        type: "DELETE",
        data: { id: id }, // Send the id to the server
        success: function (response) {
            Notification.notifySuccess(
                "Success",
                "Delete successfully"
            );
            setTimeout(function () {
                $("#delete-modal").modal("hide"); // Close the modal
                $("#staff-datatable").DataTable().destroy();
                StaffDatatables.init();
            }, 2000);
        },
        error: function (xhr, status, error) {
            // Handle error response
            Notification.notifyError(
                "Error",
                "Unable to delete staff"
            );
            $("#cancel-delete-button").removeClass("disabled").html('Cancel');
            $("#confirm-delete-button").removeClass("disabled").html('Confirm Delete');
        },
    });
});






$(document).on('click', '.edit-btn', function () {
    var userId = $(this).data('id');
    // Fetch user data via AJAX
    $.ajax({
        url: '/monitoring/rest/staff/' + userId, // Your backend API endpoint for getting user details
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
        url: '/monitoring/rest/staff/status', // Replace with your actual endpoint
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


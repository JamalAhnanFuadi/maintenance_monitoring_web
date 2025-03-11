$(document).ready(function () {

    $('#department-link').addClass('active');
    $('#department-link').trigger('click');

    DepartmentDatatables.init(); // Initialize the datatable when the document is ready

});

var DepartmentDatatables = (function () {
    var initDatatable = function () {
        // Check if DataTable is already initialized and destroy it
        if ($.fn.DataTable.isDataTable('#table_department')) {
            $('#table_department').DataTable().destroy();
        }

        // Initialize Bootstrap Datatables Integration
        App.datatables();

        // Initialize Datatables with AJAX source
        $('#table_department').DataTable({
            autoWidth: false, // Disable auto width calculation
            ajax: {
                url: '/monitoring/rest/departments',
                method: 'GET',
                dataSrc: function (json) {
                    return json ? json : [];
                }
            },
            columns: [
                {
                    data: 'displayName',
                    render: function (data) {
                        return data ? data : '-';
                    }
                },
                {
                    data: 'description',
                    render: function (data) {
                        return data ? data : '-';
                    }
                },
                {
                    data: 'createDt',
                    className: 'text-center',
                    render: function (data) {
                        return data ? data : '-';
                    }
                },
                {
                    data: 'modifyDt',
                    className: 'text-center',
                    render: function (data) {
                        return data ? data : '-';
                    }
                },
                {
                    data: null,
                    className: 'text-center',
                    orderable: false,
                    render: function (data, type, row) {
                        const locked = row.locked === false; // Check if the row is not locked
                        return `
                            <div class="btn-group">
                                <button class="btn btn-sm btn-primary edit-btn" data-id="${row.uid}">Edit</button>
                                <button class="btn btn-sm btn-danger delete-btn" data-id="${row.uid}" data-displayname="${row.displayName}" ${locked ? '' : 'disabled'} title="${locked ? '' : 'Department is in use'}">Delete</button>
                            </div>`;
                    }
                }
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

$(document).on('click', '#add-department-button', function () {
    // Reset the form fields
    $('#add-form')[0].reset();

    // Reset the validation (remove error classes and messages)
    $('#add-form').find('.form-group').removeClass('has-error has-success');
    $('#add-form').find('.help-block').remove();

    // Set the modal title for adding
    $('.modal-title').text('Add Department');
    $('#submit-button').text('Submit'); // Change button label to "Save Changes"
    $('#reset-button').text('Reset'); // Change button label to "Save Changes"

    $('#department-id').val('');
});

function validateForm() {
    // Check if the validation is already set up
    if (!$.fn.validate.hasOwnProperty('validator')) {
        $('#add-form').validate({ // Initialize validation
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
                val_department: {
                    required: true,
                    minlength: 2
                }
            },
            messages: {
                val_department: {
                    required: 'Please enter the department name',
                    minlength: 'The department name must be at least 2 characters long'
                }
            }
        });
    }

    // Return validity of the form
    return $('#add-form').valid(); // Returns true if valid, false otherwise
}

$(document).on('click', '#reset-button', function () {
    // Reset input fields
    $('#val_department').val('');
    $('#val_description').val('');
});

$(document).on('click', '#submit-button', function () {
    event.preventDefault(); // Prevent default form submission

    var departmentId = $(this).data('id');

    // Perform validation
    const isValid = validateForm(); // Call your validation function
    if (isValid) {

        // Disable the submit and reset buttons, add spinner icon
        $(this).prop('disabled', true).prepend('<i class="fa fa-spinner fa-spin"></i> ');
        $('#reset-button').prop('disabled', true).prepend('<i class="fa fa-spinner fa-spin"></i> ');

        const departmentData = {
            uid: departmentId,
            displayName: $('#val_department').val(),
            description: $('#val_description').val()
        };
        if ($(this).text().trim() === 'Submit') {
            $.ajax({
                url: '/monitoring/rest/departments',
                method: 'POST',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(departmentData),
                success: function (response, status, xhr) {
                    if (xhr.status === 200) {
                        Notification.notifySuccess('Success', "Successfully add new department");
                    } else {
                        Notification.notifyError('Error', "Failed add new department: " + xhr.status);
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
                            Notification.notifyError('Error', "Department not found.");
                            break;
                        case 500: // Internal Server Error
                            Notification.notifyError('Error', "An unexpected error occurred. Please try again later.");
                            break;
                        default: // Other status codes
                            Notification.notifyError('Error', "Failed adding new department. Status: " + xhr.status);
                            break;
                    }
                }
            });
            setTimeout(function () {
                $('#add-modal').modal('hide'); // Close the modal
                $('#table_department').DataTable().destroy();
                DepartmentDatatables.init();
            }, 2000);
        }

        if ($(this).text().trim() === 'Save Changes') {
            $.ajax({
                url: '/monitoring/rest/departments', // Your endpoint
                method: 'PUT',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(departmentData),
                success: function (response, status, xhr) {
                    if (xhr.status === 200) {
                        Notification.notifySuccess('Success', "Successfully update department");
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
                            Notification.notifyError('Error', "Department not found.");
                            break;
                        case 500: // Internal Server Error
                            Notification.notifyError('Error', "An unexpected error occurred. Please try again later.");
                            break;
                        default: // Other status codes
                            Notification.notifyError('Error', "Failed update department. Status: " + xhr.status);
                            break;
                    }
                }

            });
            setTimeout(function () {
                $('#add-modal').modal('hide'); // Close the modal
                $('#table_department').DataTable().destroy();
                DepartmentDatatables.init();
            }, 2000);
        }
        setTimeout(function () {
            $('#submit-button').prop('disabled', false).find('i.fa-spinner').remove();
            $('#reset-button').prop('disabled', false).find('i.fa-spinner').remove();

        }, 3000); // 3000 milliseconds = 3 seconds
    }
});

$(document).on('click', '.edit-btn', function () {
    var departmentId = $(this).data('id');
    $.ajax({
        url: '/monitoring/rest/departments/' + departmentId,
        method: 'GET',
        data: {id: departmentId},
        success: function (response, status, xhr) {
            if (xhr.status === 200) {
                var data = {
                    uid: response.uid,
                    displayName: response.displayName,
                    description: response.description
                };

                console.log(data);
                openUpdateModal(data);
            } else {
                console.log('Error: ' + response.description);
            }
        },
        error: function () {
            console.log('Error fetching department data');
        }
    });
});

function openUpdateModal(data) {
    // Reset the form fields
    $('#add-form')[0].reset();
    // Reset validation (remove error classes and messages)
    $('#add-form').find('.form-group').removeClass('has-error has-success');
    $('#add-form').find('.help-block').remove();

    $('#val_department').val(data.displayName);
    $('#val_description').val(data.description);

    $('#submit-button').data('id', data.uid);

    // Change modal title and button labels
    $('.modal-title').text('Update Department');
    $('#submit-button').text('Save Changes');
    $('#reset-button').text('Close');

    // Open the modal
    $('#add-modal').modal('show');
}

$(document).on('click', '#reset-button', function () {
    // Check if the button label is "Close"
    if ($(this).text().trim() === 'Close') {
        // Close the modal
        $('#add-modal').modal('hide');
    }
});


$(document).on('click', '.delete-btn', function () {
    var id = $(this).data('id');
    var displayName = $(this).data('displayname');

    // Reset the modal before using it
    $('#delete-modal .modal-body').empty(); // Clear the modal body
    $('#delete-modal .modal-footer').empty(); // Clear the modal footer

    // Set the modal body content
    $('#delete-modal .modal-body').html(`Are you sure you want to delete : <strong>${displayName}</strong>?`);

    // Store the id in the modal for later use
    $('#delete-modal').data('id', id);

    // Set the modal title for adding
    $('.modal-title').text('Delete Department'); // Change title to indicate deletion

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
                url: `/monitoring/rest/departments/${id}`,
                type: 'DELETE',
                data: {id: id}, // Send the id to the server
                success: function (response) {
                    Notification.notifySuccess('Success', "Successfully delete department " + displayName);
                },
                error: function (xhr, status, error) {
                    // Handle error response
                    Notification.notifyError('Error', "Failed delete department " + displayName);
                }
            });
            setTimeout(function () {
                $('#delete-modal').modal('hide'); // Close the modal
                $('#table_department').DataTable().destroy();
                DepartmentDatatables.init();
            }, 2000);
        }
    });

    // Create the Cancel button
    var cancelButton = $('<button>', {
        id: 'cancel-button',
        class: 'btn btn-sm btn-default',
        text: 'Cancel',
        click: function () {
            $('#delete-modal').modal('hide'); // Just close the modal
        }
    });

    // Append both buttons to the modal footer
    $('#delete-modal .modal-footer').append(confirmDeleteButton, cancelButton);

    // Show the modal
    $('#delete-modal').modal('show');
});

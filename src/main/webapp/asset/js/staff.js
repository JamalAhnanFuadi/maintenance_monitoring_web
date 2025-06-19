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
                        return data ? `${data.firstname} ${data.lastname}` : '-'; // Handle null/undefined
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
                        const locked = row.status === false; // atau tergantung struktur data `row.status === 0`
                        return `
                            <div class="btn-group">
                            <div class="text-center">
                                <a class="btn btn-sm btn-info view-button" data-id="${row.uid}">
                                    <i class="fa fa-eye"></i> View
                                </a>
                                <a class="btn btn-sm btn-danger delete-btn" data-id="${row.uid}" 
                                    data-displayname="${row.firstname} ${row.lastname}" 
                                    ${locked ? '' : 'disabled title="Cannot delete user when status is active"'} >
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
    $('#add-form')[0].reset();

    $('#val_department').val('').trigger('chosen:updated');

    $('#add-form').find('.form-group').removeClass('has-error has-success');
    $('#add-form').find('.help-block').remove();
    $("#submit-button").removeClass("disabled").html('Submit');
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

                $('#val_vdepartment').append(
                    $('<option>', {
                        value: department.uid,
                        text: department.displayName
                    })
                );
            });

            $('#val_department').trigger("chosen:updated");
            $('#val_vdepartment').trigger("chosen:updated");
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
            departmentUid: $('#val_department').val(),
            dob: $('#val_dob').val()
        };

        confirm

        var email = $('#val_email').val();
        const isExist = await validateEmail(email); // Await the Promise

        if (!isExist) {
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
        data: { id: staffId },
        success: function (response) {

            $("#val_vuid").prop("readonly", true);
            $("#val_vstatus").prop('disabled', true);
            $("#val_vfirstname").prop("readonly", true);
            $("#val_vlastname").prop("readonly", true);
            $("#val_vemail").prop("readonly", true);
            $("#val_vmobileNumber").prop("readonly", true);
            $("#val_vdepartment").prop("disabled", true);
            $("#val_vdob").prop("readonly", true);

            $('#val_vdepartment').val('').trigger('chosen:updated');

            $("#val_vuid").val(response.uid);
            $("#val_vfirstname").val(response.firstname);
            $("#val_vlastname").val(response.lastname);
            $("#val_vemail").val(response.email);
            $("#val_vmobileNumber").val(response.mobileNumber);

            $("#val_vstatus").val(response.status);

            var dobRaw = response.dob;
            if (dobRaw && dobRaw.trim() !== "") {
                var formattedDate = formatDate(dobRaw);
                $("#val_vdob").val(formattedDate);
            } else {
                $("#val_vdob").val('');
            }

            $('#val_vdepartment').val(response.departmentUid);
            $('#val_vdepartment').trigger("chosen:updated");

            $('#val_vstatus').prop('checked', response.status).change();

            $("#cancel-update-button").removeClass("disabled").html('Cancel');
            $("#confirm-update-button").removeClass("disabled").html('Save changes');
            $("#update-button").show();
            $("#update-modal-footer").hide();
            $("#view-modal").modal("show");
        },
        error: function () {
            Notification.notifyError("Error", "Unable to fetch staff information");
        },
    });
});

function formatDate(dateString) {
    var datePart = dateString.split(" ")[0];
    return datePart;
}

$(document).on("click", "#update-button", function () {
    $("#update-button").hide();

    $("#val_vuid").prop("readonly", true);
    $("#val_vstatus").prop('disabled', false);
    $("#val_vfirstname").prop("readonly", false);
    $("#val_vlastname").prop("readonly", false);
    $("#val_vemail").prop("readonly", true);
    $("#val_vmobileNumber").prop("readonly", false);
    $("#val_vdepartment").prop("disabled", false);
    $('#val_vdepartment').trigger("chosen:updated");
    $("#val_vdob").prop("readonly", false);
    $("#update-modal-footer").show();

    sessionStorage.setItem("originalData", JSON.stringify({
        uid: $("#val_vuid").val(),
        firstname: $("#val_vfirstname").val(),
        lastname: $("#val_vlastname").val(),
        email: $("#val_vemail").val(),
        mobileNumber: $("#val_vmobileNumber").val(),
        departmentUid: $("#val_vdepartment").val(),
        status: $("#val_vstatus").is(':checked'),
        dob: $("#val_vdob").val()
    }));

});

$(document).on("click", "#cancel-update-button", function () {

    $("#val_vdepartment").prop("disabled", true);

    $('#val_vdepartment').val('').trigger('chosen:updated');

    // Retrieve original values from sessionStorage
    var originalData = JSON.parse(sessionStorage.getItem("originalData"));
    if (originalData) {
        $("#val_vuid").val(originalData.uid);
        $("#val_vfirstname").val(originalData.firstname);
        $("#val_vlastname").val(originalData.lastname);
        $("#val_vemail").val(originalData.email);
        $("#val_vmobileNumber").val(originalData.mobileNumber);

        $('#val_vdepartment').val(originalData.departmentUid);
        $('#val_vdepartment').trigger("chosen:updated");

        $('#val_vstatus').prop('checked', originalData.status).trigger('change');
        $("#val_vdob").val(originalData.dob);
    }


    $("#update-button").show();
    $("#val_vuid").prop("readonly", true);
    $("#val_vstatus").prop('disabled', true);
    $("#val_vfirstname").prop("readonly", true);
    $("#val_vlastname").prop("readonly", true);
    $("#val_vemail").prop("readonly", true);
    $("#val_vmobileNumber").prop("readonly", true);
    $("#val_vdob").prop("readonly", true);

    $("#update-modal-footer").hide();
});


$(document).on("click", ".delete-btn", function () {

    if ($(this).is('[disabled]')) {
        Notification.notifyInfo(
            "Info",
            "Unable to delete staff when status is locked"
        );
    } else {
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
    }
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


$(document).on("click", ".confirm-update-button", function () {
    const isValid = updateValidation(); // Call your validation function
    if (isValid) {
        $("#cancel-update-button").addClass("disabled").html('<i class="fa fa-spinner fa-spin"></i> Cancel');
        $("#confirm-update-button").addClass("disabled").html('<i class="fa fa-spinner fa-spin"></i> Saving...');

        const deparmentRequest = {
            uid: $("#val_vuid").val(),
            firstname: $("#val_vfirstname").val(),
            lastname: $("#val_vlastname").val(),
            email: $("#val_vemail").val(),
            mobileNumber: $("#val_vmobileNumber").val(),
            departmentUid: $("#val_vdepartment").val(),
            status: $("#val_vstatus").is(':checked'),
            dob: $("#val_vdob").val()
        };
        $.ajax({
            url: "/monitoring/rest/staff", // Your endpoint
            method: "PUT",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(deparmentRequest),
            success: function (response) {
                Notification.notifySuccess(
                    "Success",
                    "Successfully update staff"
                );
                setTimeout(function () {
                    $("#view-modal").modal("hide"); // Close the modal
                    $("#staff-datatable").DataTable().destroy();
                    StaffDatatables.init();
                }, 2000);
            },
            error: function (xhr, status, error) {
                // Handle error response
                Notification.notifyError(
                    "Error",
                    "Unable to update staff"
                );
                $("#cancel-update-button").removeClass("disabled").html('Cancel');
                $("#confirm-update-button").removeClass("disabled").html('Save changes');
            },
        });
    }
});

function updateValidation() {
    if (!$.fn.validate.hasOwnProperty('validator')) {
        $('#update-form').validate({
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
                val_vuid: {
                    required: true
                },
                val_vfirstname: {
                    required: true
                },
                val_vemail: {
                    required: true,
                    email: true
                },
                val_vmobileNumber: {
                    required: true,
                    minlength: 11
                },
                val_vdepartment: {
                    required: true
                }
            },
            messages: {
                val_vuid: 'Staff ID is required',
                val_vfirstname: {
                    required: 'Please enter your first name',
                    minlength: 'Your first name must be at least 2 characters long'
                },
                val_vemail: 'Please enter a valid email address',
                val_vmobileNumber: {
                    required: 'Please enter your mobile number',
                    minlength: 'Your mobile number must be at least 11 characters long'
                },
                val_vdepartment: {
                    required: 'Please select a department'
                }
            }
        });
    }
    return $('#update-form').valid();
}
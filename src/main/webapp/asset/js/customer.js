$(document).ready(function () {
    $("#customer-link").addClass("active");
    $("#customer-link").trigger("click");

    CustomerDatatables.init(); // Initialize the datatable when the document is ready
});

var CustomerDatatables = (function () {
    var initDatatable = function () {
        // Check if DataTable is already initialized and destroy it
        if ($.fn.DataTable.isDataTable("#table_customer")) {
            $("#table_customer").DataTable().destroy();
        }

        // Initialize Bootstrap Datatables Integration
        App.datatables();

        // Initialize Datatables with AJAX source
        $("#table_customer").DataTable({
            autoWidth: false, // Disable auto width calculation
            ajax: {
                url: "/monitoring/rest/customers",
                method: "GET",
                dataSrc: function (json) {
                    return json ? json : [];
                },
            },
            columns: [
                {
                    data: "displayName",
                    render: function (data) {
                        return data ? `<strong>${data}</strong>` : "-";
                    },
                },
                {
                    data: "email",
                    render: function (data) {
                        return data ? data : "-";
                    },
                },
                {
                    data: "phone",
                    render: function (data) {
                        return data ? data : "-";
                    },
                },
                {
                    data: "status",
                    className: "text-center",
                    render: function (data, type, row) {
                        const statusClass = data ? 'label label-success' : 'label label-warning'; // Menentukan class berdasarkan status
                        const statusText = data ? 'Active' : 'Inactive'; // Menentukan teks status
                        return `<span class="${statusClass}">${statusText}</span>`; // Mengembalikan elemen dengan class dan teks status
                    }
                },
                {
                    data: null,
                    className: "text-center",
                    orderable: false,
                    render: function (data, type, row) {
                        const locked = row.locked === false;
                        return `
                            <div class="btn-group">
                            <div class="text-center">
                                <a class="btn btn-sm btn-info view-button" data-id="${row.uid
                        }">
                                    <i class="fa fa-eye"></i> View
                                </a>
                                <a class="btn btn-sm btn-danger delete-btn" data-id="${row.uid
                        }" 
                                    data-displayname="${row.displayName}" 
                                    ${locked
                            ? ""
                            : 'disabled title="Cannot delete when customerin use"'
                        }">
                                    <i class="fa fa-times-circle"></i> Delete
                                </a>
                            </div>
                            </div>`;
                    },
                },
            ],
            pageLength: 10,
            lengthMenu: [
                [10, 20, 30, -1],
                [10, 20, 30, "All"],
            ],
            responsive: true, // Ensure responsiveness
        });

        // Add placeholder attribute to the search input
        $(".dataTables_filter input").attr("placeholder", "Search");
    };

    return {
        init: function () {
            initDatatable();
        },
    };
})();

$(document).on("click", "#export-customer-button", function () {
    console.log("Export button clicked");

    $.ajax({
        url: "/monitoring/rest/customers/export",
        method: "GET",
        contentType: "application/json", // Set content type for the request
        dataType: "text", // Expecting text response (CSV)
        success: function (response, status, xhr) {
            if (xhr.status === 200) {
                // Assuming response is a CSV formatted string
                const csvData = response.split("\n").map((row) => row.split(","));
                exportToCSV("customer.csv", csvData);
            } else {
                console.log("Error: " + response.description);
            }
        },
        error: function () {
            console.log("Error fetching customerdata");
        },
    });
});

function exportToCSV(filename, csvData) {
    const csvContent =
        "data:text/csv;charset=utf-8," + csvData.map((e) => e.join(",")).join("\n");

    const encodedUri = encodeURI(csvContent);
    const link = document.createElement("a");
    link.setAttribute("href", encodedUri);
    link.setAttribute("download", filename);
    document.body.appendChild(link); // Required for Firefox

    link.click(); // This will download the file
}

$(document).on("click", "#add-customer-button", function () {
    // Reset the form fields
    $("#add-form")[0].reset();
    // Reset the validation (remove error classes and messages)
    $("#add-form").find(".form-group").removeClass("has-error has-success");
    $("#add-form").find(".help-block").remove();
    $("#submit-button").removeClass("disabled").html('Submit');
});

function addValidation() {
    // Check if the validation is already set up
    if (!$.fn.validate.hasOwnProperty("validator")) {
        $("#add-form").validate({
            // Initialize validation
            ignore: [], // Ensure it validates the hidden fields used by Chosen or Select2
            errorClass: "help-block animation-slideDown",
            errorElement: "div",
            errorPlacement: function (error, e) {
                e.parents(".form-group > div").append(error);
            },
            highlight: function (e) {
                $(e)
                    .closest(".form-group")
                    .removeClass("has-success has-error")
                    .addClass("has-error");
                $(e).closest(".help-block").remove();
            },
            success: function (e) {
                e.closest(".form-group").removeClass("has-success has-error");
                e.closest(".help-block").remove();
            },
            rules: {
                val_displayname: {
                    required: true,
                    minlength: 2,
                },
                val_email: {
                    email: true
                },
                val_phone: {
                    digits: true,
                    minlength: 6,
                    maxlength: 15
                },
                val_website: {
                    url: true
                }
            },
            messages: {
                val_email: {
                    email: "Please enter a valid email address (e.g example@mail.com)"
                },
                val_website: {
                    url: "Please enter a valid URL (e.g http://www.example.com)"
                },
                val_phone: {
                    digits:    "Please enter only digits",
                    minlength: "Phone number must be at least 10 digits",
                    maxlength: "Phone number cannot exceed 15 digits"
                },
                val_displayname: {
                    required:  "Customer name is required",
                    minlength: "Customer name must be at least 2 characters"
                },

            }

        });
    }

    // Return validity of the form
    return $("#add-form").valid(); // Returns true if valid, false otherwise
}

$(document).on("click", "#submit-button", function () {
    event.preventDefault(); // Prevent default form submission

    const isValid = addValidation(); // Call your validation function
    if (isValid) {
        $("#submit-button").addClass("disabled").html('<i class="fa fa-spinner fa-spin"></i> Submiting...');

        const customerRequest = {
            displayName: $("#val_displayname").val(),
            email: $("#val_email").val(),
            phone: $("#val_phone").val(),
            website: $("#val_website").val(),
            address: $("#val_address").val(),
        };

        $.ajax({
            url: "/monitoring/rest/customers",
            method: "POST",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(customerRequest),
            success: function (response) {
                Notification.notifySuccess(
                    "Success",
                    "Successfully added customer"
                );
                setTimeout(function () {
                    $("#add-modal").modal("hide"); // Close the modal
                    $("#table_customer").DataTable().destroy();
                    CustomerDatatables.init();
                }, 2000);
            },
            error: function (xhr, status, error) {
                // Handle error response
                Notification.notifyError(
                    "Error",
                    "Unable to add customer"
                );
                $("#submit-button").removeClass("disabled").html('Submit');
            },
        });
    }
});

$(document).on("click", ".view-button", function () {
    var customerId = $(this).data("id");

    $.ajax({
        url: "/monitoring/rest/customers/" + customerId,
        method: "GET",
        data: { id: customerId },
        success: function (response, status, xhr) {

            $("#val_vcustomer_id").prop("readonly", true);
            $("#val_vdisplayname").prop("readonly", true);
            $("#val_vemail").prop("readonly", true);
            $("#val_vphone").prop("readonly", true);
            $("#val_vwebsite").prop("readonly", true);
            $("#val_vaddress").prop("readonly", true);
            $("#val_vstatus").prop("disabled", true);

            $("#val_vcustomer_id").val(response.uid);
            $("#val_vdisplayname").val(response.displayName);
            $("#val_vemail").val(response.email);
            $("#val_vphone").val(response.phone);
            $("#val_vwebsite").val(response.website);
            $("#val_vaddress").val(response.address);

            $("#val_vstatus").val(response.status);
            $('#val_vstatus').prop('checked', response.status).change();

            $("#cancel-update-button").removeClass("disabled").html('Cancel');
            $("#confirm-update-button").removeClass("disabled").html('Save changes');

            $("#update-button").show();
            $("#update-modal-footer").hide();

            $("#view-modal").modal("show");
        },
        error: function () {
            Notification.notifyError(
                "Error",
                "Unable to fetch customerinformation"
            );
        },
    });
});

$(document).on("click", "#update-button", function () {
    $("#update-button").hide();

    $("#val_vcustomer_id").prop("readonly", true);
    $("#val_vdisplayname").prop("readonly", false);
    $("#val_vemail").prop("readonly", false);
    $("#val_vphone").prop("readonly", false);
    $("#val_vwebsite").prop("readonly", false);
    $("#val_vaddress").prop("readonly", false);
    $("#val_vstatus").prop('disabled', false);

    $("#update-modal-footer").show();

    sessionStorage.setItem("originalData", JSON.stringify({
        uid: $("#val_vcustomer_id").val(),
        displayName: $("#val_vdisplayname").val(),
        email: $("#val_vemail").val(),
        phone: $("#val_vphone").val(),
        website: $("#val_vwebsite").val(),
        address: $("#val_vaddress").val(),
        status: $("#val_vstatus").is(':checked'),
    }));

});

$(document).on("click", "#cancel-update-button", function () {


    // Retrieve original values from sessionStorage
    var originalData = JSON.parse(sessionStorage.getItem("originalData"));
    if (originalData) {
        $("#val_vcustomer_id").val(originalData.uid);
        $("#val_vdisplayname").val(originalData.displayName);
        $("#val_vemail").val(originalData.email);
        $("#val_vphone").val(originalData.phone);
        $("#val_vwebsite").val(originalData.website);
        $("#val_vaddress").val(originalData.address);
        $('#val_vstatus').prop('checked', originalData.status).trigger('change');
    }

    $("#update-button").show();

    $("#val_vcustomer_id").prop("readonly", true);
    $("#val_vdisplayname").prop("readonly", true);
    $("#val_vemail").prop("readonly", true);
    $("#val_vphone").prop("readonly", true);
    $("#val_vwebsite").prop("readonly", true);
    $("#val_vaddress").prop("readonly", true);
    $("#val_vstatus").prop("disabled", true);

    $("#update-modal-footer").hide();

});

$(document).on("click", ".confirm-update-button", function () {
    const isValid = updateValidation(); // Call your validation function
    if (isValid) {
        $("#cancel-update-button").addClass("disabled").html('<i class="fa fa-spinner fa-spin"></i> Cancel');
        $("#confirm-update-button").addClass("disabled").html('<i class="fa fa-spinner fa-spin"></i> Saving...');

        const deparmentRequest = {
            uid: $("#val_vcustomer_id").val(),
            displayName: $("#val_vdisplayname").val(),
            email: $("#val_vemail").val(),
            phone: $("#val_vphone").val(),
            website: $("#val_vwebsite").val(),
            address: $("#val_vaddress").val(),
            status: $("#val_vstatus").is(':checked'),
        };
        $.ajax({
            url: "/monitoring/rest/customers", // Your endpoint
            method: "PUT",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(deparmentRequest),
            success: function (response) {
                Notification.notifySuccess(
                    "Success",
                    "Successfully update customer"
                );
                setTimeout(function () {
                    $("#view-modal").modal("hide"); // Close the modal
                    $("#table_customer").DataTable().destroy();
                    CustomerDatatables.init();
                }, 2000);
            },
            error: function (xhr, status, error) {
                // Handle error response
                Notification.notifyError(
                    "Error",
                    "Unable to update customer"
                );
                $("#cancel-update-button").removeClass("disabled").html('Cancel');
                $("#confirm-update-button").removeClass("disabled").html('Save changes');
            },
        });
    }
});

function updateValidation() {
    // Check if the validation is already set up
    if (!$.fn.validate.hasOwnProperty("validator")) {
        $("#update-form").validate({
            // Initialize validation
            ignore: [], // Ensure it validates the hidden fields used by Chosen or Select2
            errorClass: "help-block animation-slideDown",
            errorElement: "div",
            errorPlacement: function (error, e) {
                e.parents(".form-group > div").append(error);
            },
            highlight: function (e) {
                $(e)
                    .closest(".form-group")
                    .removeClass("has-success has-error")
                    .addClass("has-error");
                $(e).closest(".help-block").remove();
            },
            success: function (e) {
                e.closest(".form-group").removeClass("has-success has-error");
                e.closest(".help-block").remove();
            },
            rules: {
                val_vcustomer_id: {
                    required: true,
                },
                val_vdisplayname: {
                    required: true,
                    minlength: 2,
                },
                val_vemail: {
                    email: true
                },
                val_vphone: {
                    digits: true,
                    minlength: 6,
                    maxlength: 15
                },
                val_vwebsite: {
                    url: true
                }
            },
            messages: {
                val_vcustomer_id: {
                    email: "Customer ID is required"
                },
                val_vemail: {
                    email: "Please enter a valid email address (e.g example@mail.com)"
                },
                val_vwebsite: {
                    url: "Please enter a valid URL (e.g http://www.example.com)"
                },
                val_vphone: {
                    digits:    "Please enter only digits",
                    minlength: "Phone number must be at least 10 digits",
                    maxlength: "Phone number cannot exceed 15 digits"
                },
                val_vdisplayname: {
                    required:  "Customer name is required",
                    minlength: "Customer name must be at least 2 characters"
                },

            }
        });
    }

    // Return validity of the form
    return $("#update-form").valid(); // Returns true if valid, false otherwise
}


$(document).on("click", ".delete-btn", function () {
    if ($(this).is('[disabled]')) {
        Notification.notifyWarning(
            "Warning",
            "Unable to delete customerwhen in used"
        );
    } else {
        var id = $(this).data("id");
        var displayName = $(this).data("displayname");

        $("#cancel-delete-button").removeClass("disabled").html('Cancel');

        $("#confirm-delete-button").attr("data-id", id);
        $("#confirm-delete-button").removeClass("disabled").html('Confirm Delete');

        $("#delete-modal .modal-body").empty();
        $("#delete-modal .modal-body").html(
            `Are you sure you want to delete : <strong>${displayName}</strong>?`
        );

        $("#confirm-delete-button").data("id", id);
        $(".modal-title").text("Delete customer");
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
        url: `/monitoring/rest/customers/${id}`,
        type: "DELETE",
        data: { id: id }, // Send the id to the server
        success: function (response) {
            Notification.notifySuccess(
                "Success",
                "Delete successfully"
            );
            setTimeout(function () {
                $("#delete-modal").modal("hide"); // Close the modal
                $("#table_customer").DataTable().destroy();
                CustomerDatatables.init();
            }, 2000);
        },
        error: function (xhr, status, error) {
            // Handle error response
            Notification.notifyError(
                "Error",
                "Unable to delete customer"
            );
            $("#cancel-delete-button").removeClass("disabled").html('Cancel');
            $("#confirm-delete-button").removeClass("disabled").html('Confirm Delete');
        },
    });
});
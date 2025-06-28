$(document).ready(function () {
    $("#project-link").addClass("active");
    $("#project-link").trigger("click");

    ProjectDatatables.init(); // Initialize the datatable when the document is ready
    populateStaffList();
    populateCustomers();
});

var ProjectDatatables = (function () {
    var initDatatable = function () {
        // Check if DataTable is already initialized and destroy it
        if ($.fn.DataTable.isDataTable("#project-table")) {
            $("#project-table").DataTable().destroy();
        }

        // Initialize Bootstrap Datatables Integration
        App.datatables();

        // Initialize Datatables with AJAX source
        $("#project-table").DataTable({
            autoWidth: false, // Disable auto width calculation
            ajax: {
                url: "/monitoring/rest/projects",
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
                    data: "customerName",
                    className: "text-center",
                    render: function (data) {
                        return data ? data : "-";
                    },
                },
                {
                    data: "salesOrderNumber",
                    className: "text-center",
                    render: function (data) {
                        return data ? data : "-";
                    },
                },
                {
                    data: "jobCode",
                    className: "text-center",
                    render: function (data) {
                        return data ? data : "-";
                    },
                },
                {
                    data: "staffPic",
                    render: function (data, type, row) {
                        if (!data || data.length === 0) return "-";
                        const pic = data.map(
                            (t) =>
                                `• <span class="">${t.staffName} (${t.staffEmail})</span>`
                        );
                        return pic.join("<br>");
                    },
                },
                {
                    data: "projectTags",
                    className: "text-center",
                    render: function (data, type, row) {
                        if (!data || data.length === 0) return "-";
                        const tags = data.map(
                            (t) => `<span class="label label-info">${t.tag}</span>`
                        );
                        return tags.join(" "); // multiple tags as labeled badges
                    },
                },
                {
                    data: null,
                    className: "text-center",
                    orderable: false,
                    render: function (data, type, row) {
                        const locked = row.markForDeletion === true;
                        const deleteLabel = "Marked for Deletion";
                        return `
        <div class="btn-group">
            <div class="text-center">
                <a class="btn btn-sm btn-info view-button" data-id="${row.uid}">
                    <i class="fa fa-eye"></i> Details
                </a>
                <a class="btn btn-sm btn-danger delete-btn"
                   data-id="${row.uid}"
                   data-displayname="${row.displayName}"
                   ${
                            locked
                                ? `disabled title="Permanently deleted by ${row.markForDeletionDt},\n Contact your system administrator for restore options"`
                                : ""
                        }>
                    <i class="fa fa-times-circle"></i> ${deleteLabel}
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

$(document).on("click", ".view-button", function () {
    var projectId = $(this).data("id");
    window.location.href = "project-overview?id=" + projectId;
});

$(document).on("click", "#add-project-button", function () {
    // Reset the form fields
    cleanFields();
    $("#add-form")[0].reset();
    // Reset the validation (remove error classes and messages)
    $("#add-form").find(".form-group").removeClass("has-error has-success");
    $("#add-form").find(".help-block").remove();
    $("#submit-button").removeClass("disabled").html("Submit");
});

function cleanFields() {
    $("#val_project_name").val("");
    $("#val_customer_name").val("");
    $("#val_so_number").val("");
    $("#val_job_code").val("");
    $("#val_staff_pic").val("").trigger("chosen:updated");
}

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
                val_project_name: {
                    required: true,
                    minlength: 2,
                },
                val_customer_name: {
                    required: true,
                },
                val_staff_pic: {
                    required: true,
                },
            },
            messages: {
                val_project_name: {
                    required: "Please enter the product name",
                    minlength: "The product name must be at least 2 characters long",
                },
                val_customer_name: {
                    required: "Customer is required",
                },
                val_staff_pic: {
                    required: "Staff PIC is required",
                },
            },
        });
    }

    // Return validity of the form
    return $("#add-form").valid(); // Returns true if valid, false otherwise
}

$(document).on("click", "#submit-button", function () {
    event.preventDefault(); // Prevent default form submission
    const isValid = addValidation(); // Call your validation function
    if (isValid) {
        $("#submit-button")
            .addClass("disabled")
            .html('<i class="fa fa-spinner fa-spin"></i> Submiting...');

        const request = {
            projectName: $("#val_project_name").val(),
            jobCode: $("#val_job_code").val(),
            soNumber: $("#val_so_number").val(),
            description: $("#val_description").val(),
            customerUid: $("#val_customer_name").val(),
            staffPic: $("#val_staff_pic").val(),
        };

        $.ajax({
            url: "/monitoring/rest/projects",
            method: "POST",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(request),
            success: function (response) {
                Notification.notifySuccess("Success", "Successfully added project");
                setTimeout(function () {
                    $("#add-modal").modal("hide"); // Close the modal
                    $("#project-table").DataTable().destroy();
                    ProjectDatatables.init();
                }, 2000);
            },
            error: function (xhr, status, error) {
                // Handle error response
                Notification.notifyError("Error", "Unable to add project");
                $("#submit-button").removeClass("disabled").html("Submit");
            },
        });
    }
});

function populateStaffList() {
    $.ajax({
        url: "/monitoring/rest/staff",
        method: "GET",
        dataType: "json",
        success: function (response) {
            response.forEach(function (staff) {
                $("#val_staff_pic").append(
                    $("<option>", {
                        value: staff.uid,
                        text: staff.firstname + " " + staff.lastname,
                    })
                );
            });

            $("#val_staff_pic").trigger("chosen:updated");
        },
        error: function (xhr, status, error) {
            console.error("Unable to fetching staff list");
        },
    });
}

function populateCustomers() {
    $.ajax({
        url: "/monitoring/rest/customers",
        method: "GET",
        dataType: "json",
        success: function (response) {
            response.forEach(function (customer) {
                $("#val_customer_name").append(
                    $("<option>", {
                        value: customer.uid,
                        text: customer.displayName,
                    })
                );
            });

            $("#val_customer_name").trigger("chosen:updated");
        },
        error: function (xhr, status, error) {
            console.error("Unable to fetching customer list");
        },
    });
}

$(document).on("click", ".delete-btn", function () {
    if ($(this).is("[disabled]")) {
        const tooltip = $(this).attr("title");
        Notification.notifyWarning("Warning", tooltip);
        return;
    }

    const id = $(this).data("id");
    const displayName = $(this).data("displayname");

    $("#cancel-delete-button").removeClass("disabled").html("Cancel");

    $("#confirm-delete-button").attr("data-id", id);
    $("#confirm-delete-button").removeClass("disabled").html("Mark for deletion");

    $("#delete-modal .modal-body").html(`
    <div>
      <p><strong>⚠️ Soft Deletion Notice</strong></p>
      <p>You are about to mark <strong>${displayName}</strong> for deletion.</p>
      <p>This item will be retained for <strong>30 days</strong> before it is permanently deleted.</p>
      <p>During this period, you can still recover or restore the data.</p>
      <p>Do you want to continue with the deletion process?</p>
    </div>
  `);

    $(".modal-title").text("Delete product");
    $("#delete-modal").modal("show");
});

$(document).on("click", ".cancel-delete-button", function () {
    $("#cancel-delete-button")
        .addClass("disabled")
        .html('<i class="fa fa-spinner fa-spin"></i> Canceling...');
    $("#confirm-delete-button")
        .addClass("disabled")
        .html('<i class="fa fa-spinner fa-spin"></i> Confirm Delete');
    $("#delete-modal").modal("hide");
});

$(document).on("click", ".confirm-delete-button", function () {
    var id = $(this).data("id");

    $("#cancel-delete-button")
        .addClass("disabled")
        .html('<i class="fa fa-spinner fa-spin"></i> Cancel');
    $("#confirm-delete-button")
        .addClass("disabled")
        .html('<i class="fa fa-spinner fa-spin"></i> Confirming...');

    $.ajax({
        url: `/monitoring/rest/projects/${id}`,
        type: "DELETE",
        data: { id: id }, // Send the id to the server
        success: function (response) {
            Notification.notifySuccess("Success", "Delete successfully");
            setTimeout(function () {
                $("#delete-modal").modal("hide"); // Close the modal
                $("#project-table").DataTable().destroy();
                ProjectDatatables.init();
            }, 2000);
        },
        error: function (xhr, status, error) {
            // Handle error response
            Notification.notifyError("Error", "Unable to delete project");
            $("#cancel-delete-button").removeClass("disabled").html("Cancel");
            $("#confirm-delete-button")
                .removeClass("disabled")
                .html("Confirm Delete");
        },
    });
});

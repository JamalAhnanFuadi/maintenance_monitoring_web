$(document).ready(function () {
    $('#product-parent-link').addClass('active');
    $('#product-parent-link').trigger('click');
    $('#product-link').addClass('active');

    ProductDatatables.init(); // Initialize the datatable when the document is ready

    $(".select-chosen").chosen(); // Initialize Chosen
    populateProductBrands();
    populateProductCategories();
});

var ProductDatatables = (function () {
    var initDatatable = function () {
        // Check if DataTable is already initialized and destroy it
        if ($.fn.DataTable.isDataTable("#product-table")) {
            $("#product-table").DataTable().destroy();
        }

        // Initialize Bootstrap Datatables Integration
        App.datatables();

        // Initialize Datatables with AJAX source
        $("#product-table").DataTable({
            autoWidth: false, // Disable auto width calculation
            ajax: {
                url: "/monitoring/rest/products",
                method: "GET",
                dataSrc: function (json) {
                    return json ? json : [];
                },
            },
            columns: [
                {
                    data: "displayName",
                    render: function (data) {
                        return data ? data : "-";
                    },
                },
                {
                    data: "description",
                    render: function (data) {
                        return data ? data : "-";
                    },
                },
                {
                    data: "brandName",
                    render: function (data) {
                        return data ? data : "-";
                    },
                },
                {
                    data: "categoryName",
                    render: function (data) {
                        return data ? data : "-";
                    },
                },
                {
                    data: "createDt",
                    className: "text-center",
                    render: function (data) {
                        return data ? data : "-";
                    },
                },
                {
                    data: "modifyDt",
                    className: "text-center",
                    render: function (data) {
                        return data ? data : "-";
                    },
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
                            : 'disabled title="Cannot delete when product in use"'
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

$(document).on("click", "#export-product-button", function () {
    console.log("Export button clicked");

    $.ajax({
        url: "/monitoring/rest/products/export",
        method: "GET",
        contentType: "application/json", // Set content type for the request
        dataType: "text", // Expecting text response (CSV)
        success: function (response, status, xhr) {
            if (xhr.status === 200) {
                // Assuming response is a CSV formatted string
                const csvData = response.split("\n").map((row) => row.split(","));
                exportToCSV("products.csv", csvData);
            } else {
                console.log("Error: " + response.description);
            }
        },
        error: function () {
            console.log("Error fetching product data");
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

function populateProductBrands() {
    $.ajax({
        url: '/monitoring/rest/productBrands',
        method: 'GET',
        dataType: 'json',
        success: function (response) {
            response.forEach(function (brand) {
                $('#val_brand').append(
                    $('<option>', {
                        value: brand.uid,
                        text: brand.displayName
                    })
                );

                $('#val_vbrand').append(
                    $('<option>', {
                        value: brand.uid,
                        text: brand.displayName
                    })
                );
            });

            $('#val_brand').trigger("chosen:updated");
            $('#val_vbrand').trigger("chosen:updated");
        },
        error: function (xhr, status, error) {
            console.error("Unable to fetching product brands");
        }
    });
}

function populateProductCategories() {
    $.ajax({
        url: '/monitoring/rest/productCategories',
        method: 'GET',
        dataType: 'json',
        success: function (response) {
            response.forEach(function (category) {
                $('#val_category').append(
                    $('<option>', {
                        value: category.uid,
                        text: category.displayName
                    })
                );

                $('#val_vcategory').append(
                    $('<option>', {
                        value: category.uid,
                        text: category.displayName
                    })
                );
            });

            $('#val_category').trigger("chosen:updated");
            $('#val_vcategory').trigger("chosen:updated");
        },
        error: function (xhr, status, error) {
            console.error("Unable to fetching product categories");
        }
    });
}

$(document).on("click", "#add-product-button", function () {
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
                val_product_name: {
                    required: true,
                    minlength: 2,
                },
                val_brand: {
                    required: true
                },
                val_category: {
                    required: true
                },
            },
            messages: {
                val_product_name: {
                    required: "Please enter the product name",
                    minlength: "The product name must be at least 2 characters long",
                },
                val_brand: {
                    required: "Brand is required",
                },
                val_category: {
                    required: "Category is required",
                }
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
        $("#submit-button").addClass("disabled").html('<i class="fa fa-spinner fa-spin"></i> Submiting...');

        const request = {
            displayName: $("#val_product_name").val(),
            description: $("#val_description").val(),
            brandUid: $("#val_brand").val(),
            categoryUid: $("#val_category").val()
        };

        $.ajax({
            url: "/monitoring/rest/products",
            method: "POST",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(request),
            success: function (response) {
                Notification.notifySuccess(
                    "Success",
                    "Successfully added product"
                );
                setTimeout(function () {
                    $("#add-modal").modal("hide"); // Close the modal
                    $("#product-table").DataTable().destroy();
                    ProductDatatables.init();
                }, 2000);
            },
            error: function (xhr, status, error) {
                // Handle error response
                Notification.notifyError(
                    "Error",
                    "Unable to add product"
                );
                $("#submit-button").removeClass("disabled").html('Submit');
            },
        });
    }
});

$(document).on("click", ".view-button", function () {
    var productId = $(this).data("id");

    $.ajax({
        url: "/monitoring/rest/products/" + productId,
        method: "GET",
        data: { id: productId },
        success: function (response, status, xhr) {

            $("#val_vproduct_id").prop("readonly", true);
            $("#val_vproduct_name").prop("readonly", true);
            $("#val_vdescription").prop("readonly", true);
            $("#val_vbrand").prop("disabled", true);
            $("#val_vcategory").prop("disabled", true);
            $("#val_vstatus").prop("disabled", true);

            $('#val_vbrand').val('').trigger('chosen:updated');
            $('#val_vcategory').val('').trigger('chosen:updated');


            $("#val_vproduct_id").val(response.uid);
            $("#val_vproduct_name").val(response.displayName);
            $("#val_vdescription").val(response.description);
            $("#val_vbrand").val(response.brandUid);
            $("#val_vcategory").val(response.categoryUid);
            $("#val_vstatus").val(response.active);

            $('#val_vbrand').val(response.brandUid);
            $('#val_vbrand').trigger("chosen:updated");

            $('#val_vcategory').val(response.categoryUid);
            $('#val_vcategory').trigger("chosen:updated");

            $('#val_vstatus').prop('checked', response.active).change();

            $("#cancel-update-button").removeClass("disabled").html('Cancel');
            $("#confirm-update-button").removeClass("disabled").html('Save changes');

            $("#update-button").show();
            $("#update-modal-footer").hide();

            $("#view-modal").modal("show");
        },
        error: function () {
            Notification.notifyError(
                "Error",
                "Unable to fetch product information"
            );
        },
    });
});

$(document).on("click", "#update-button", function () {
    $("#update-button").hide();

    $("#val_vstatus").prop('disabled', false);
    $("#val_vproduct_id").prop("readonly", true);
    $("#val_vproduct_name").prop("readonly", false);
    $("#val_vdescription").prop("readonly", false);

    $("#val_vbrand").prop("disabled", false);
    $('#val_vbrand').trigger("chosen:updated");

    $("#val_vcategory").prop("disabled", false);
    $('#val_vcategory').trigger("chosen:updated");

    $("#update-modal-footer").show();

    sessionStorage.setItem("originalProductData", JSON.stringify({
        uid: $("#val_vproduct_id").val(),
        displayName: $("#val_vproduct_name").val(),
        description: $("#val_vdescription").val(),
        brandUid: $("#val_vbrand").val(),
        categoryUid: $("#val_vcategory").val(),
        status: $("#val_vstatus").val(),
    }));

});

$(document).on("click", "#cancel-update-button", function () {

    $("#val_vbrand").prop("disabled", true);
    $('#val_vbrand').trigger("chosen:updated");

    $("#val_vcategory").prop("disabled", true);
    $('#val_vcategory').trigger("chosen:updated");

    // Retrieve original values from sessionStorage
    var originalData = JSON.parse(sessionStorage.getItem("originalProductData"));
    if (originalData) {
        $("#val_vproduct_id").val(originalData.uid);
        $("#val_vproduct_name").val(originalData.displayName);
        $("#val_vdescription").val(originalData.description);

        $("#val_vbrand").val(originalData.brandUid);
        $('#val_vbrand').trigger("chosen:updated");

        $("#val_vcategory").val(originalData.categoryUid);
        $('#val_vcategory').trigger("chosen:updated");

        $("#val_vstatus").val(originalData.active);
        $('#val_vstatus').prop('checked', originalData.status).change();
    }


    $("#update-button").show();
    $("#val_vstatus").prop('disabled', true);
    $("#val_vproduct_id").prop("readonly", true);
    $("#val_vproduct_name").prop("readonly", true);
    $("#val_vdescription").prop("readonly", true);
    $("#update-modal-footer").hide();
});

$(document).on("click", ".confirm-update-button", function () {
    const isValid = updateValidation(); // Call your validation function
    if (isValid) {
        $("#cancel-update-button").addClass("disabled").html('<i class="fa fa-spinner fa-spin"></i> Cancel');
        $("#confirm-update-button").addClass("disabled").html('<i class="fa fa-spinner fa-spin"></i> Saving...');

        const deparmentRequest = {
            uid: $("#val_vproduct_id").val(),
            displayName: $("#val_vproduct_name").val(),
            description: $("#val_vdescription").val(),
            brandUid: $("#val_vbrand").val(),
            categoryUid: $("#val_vcategory").val(),
            active: $("#val_vstatus").val()
        };
        $.ajax({
            url: "/monitoring/rest/products", // Your endpoint
            method: "PUT",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(deparmentRequest),
            success: function (response) {
                Notification.notifySuccess(
                    "Success",
                    "Successfully update product"
                );
                setTimeout(function () {
                    $("#view-modal").modal("hide"); // Close the modal
                    $("#product-table").DataTable().destroy();
                    ProductDatatables.init();
                }, 2000);
            },
            error: function (xhr, status, error) {
                // Handle error response
                Notification.notifyError(
                    "Error",
                    "Unable to update product"
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
                val_vproduct_name: {
                    required: true,
                    minlength: 2,
                },
                val_vcategory: {
                    required: true
                },
                val_vbrand: {
                    required: true
                },
                val_vproduct_id: {
                    required: true
                }
            },
            messages: {
                val_vproduct_name: {
                    required: "Please enter the product name",
                    minlength: "The product name must be at least 2 characters long",
                },
                val_vproduct_id: {
                    required: "Product ID is required",
                },
                val_vbrand: {
                    required: "Brand is required",
                },
                val_vcategory: {
                    required: "Category is required",
                }
            },
        });
    }

    // Return validity of the form
    return $("#update-form").valid(); // Returns true if valid, false otherwise
}


$(document).on("click", ".delete-btn", function () {
    if ($(this).is('[disabled]')) {
        Notification.notifyWarning(
            "Warning",
            "Unable to delete product when in used"
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
        $(".modal-title").text("Delete product");
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
        url: `/monitoring/rest/products/${id}`,
        type: "DELETE",
        data: { id: id }, // Send the id to the server
        success: function (response) {
            Notification.notifySuccess(
                "Success",
                "Delete successfully"
            );
            setTimeout(function () {
                $("#delete-modal").modal("hide"); // Close the modal
                $("#product-table").DataTable().destroy();
                ProductDatatables.init();
            }, 2000);
        },
        error: function (xhr, status, error) {
            // Handle error response
            Notification.notifyError(
                "Error",
                "Unable to delete product"
            );
            $("#cancel-delete-button").removeClass("disabled").html('Cancel');
            $("#confirm-delete-button").removeClass("disabled").html('Confirm Delete');
        },
    });
});
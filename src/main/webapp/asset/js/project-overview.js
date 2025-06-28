let cachedStaffList = [];
let cachedCustomerList = [];
let markForDeletion = false; // global flag

$(document).ready(function () {
    $("#project-link").addClass("active").trigger("click");

    const urlParams = new URLSearchParams(window.location.search);
    const projectId = urlParams.get("id");
    fetchProject(projectId);
    fetchProjectHistory(projectId);
});

function fetchProject(projectId) {
    $.ajax({
        url: "/monitoring/rest/projects/" + projectId,
        method: "GET",
        success: function (response) {
            document.title = response.displayName;
            $("#projectTitle, #bc-name").text(response.displayName);
            $("#customerTitle").text(response.customerName);
            $("#update-project-button").attr("data-id", projectId);

            $("#projectName").text(response.displayName);
            $("#customerName").text(response.customerName);
            $("#salesOrderNumber").text(response.salesOrderNumber);
            $("#jobCode").text(response.jobCode);
            $("#description").text(response.description);
            $("#createDt").text(response.createDt);
            $("#modified").text(response.modifyDt);

            markForDeletion = response.markForDeletion;
            const locked = markForDeletion === true;
            if (locked) {
                $("#update-project-button").addClass("disabled");
                $("#add_service-button").addClass("disabled");
                $("#delete-btn").addClass("disabled");
                $("#update-customer-pic-button").addClass("disabled");

                let warningLabel = `<span class="label label-danger">**** This project will permanently deleted on ${response.markForDeletionDt}</span>`;
                $("#warningTitle").html(warningLabel);
            } else {
                $("#warningTitleId").hide();
            }
            let titleHtml = markForDeletion
                ? `<span class="label label-danger">${response.displayName} (Marked for deletion)</span>`
                : response.displayName;

            $("#projectTitle").html(titleHtml);

            renderStaffPICList(response.staffPic);
            renderCustomerPICList(response.customerPic);
            maintenanceServiceDatatable.init(response.uid);
        },
        error: function () {
            $("#projectTitle, #customerTitle, #bc-name").text("Unknown");
            Notification.notifyError("Error", "Unable to fetch project information");
        },
    });
}

function fetchProjectHistory(projectId) {
    $.ajax({
        url: "/monitoring/rest/projects/history/" + projectId,
        method: "GET",
        success: function (response) {
            renderTimeline(response);
        },
        error: function () {
            Notification.notifyError("Error", "Unable to fetch project history");
        },
    });
}

function renderTimeline(timelineData) {
    const $container = $(".timeline-list").empty();

    function isToday(dateString) {
        const today = new Date().toLocaleDateString("en-GB", {
            day: "2-digit",
            month: "long",
            year: "numeric",
        });
        return dateString === today;
    }

    const groups = {};
    timelineData.forEach((item) => {
        if (!groups[item.date]) groups[item.date] = [];
        groups[item.date].push(item);
    });

    Object.entries(groups).forEach(([date, items]) => {
        items.forEach((item, index) => {
            const isFirst = index === 0;
            const isLast = index === items.length - 1;
            const showTimelineTime = isFirst || isLast;
            const label = isToday(item.date) ? "<strong>Today</strong>" : item.date;
            const timelineTime = showTimelineTime
                ? `<div class="timeline-time">${label}</div>`
                : "";

            const li = `
        <li>
          <div class="timeline-icon"><i class="${item.icon}"></i></div>
          ${timelineTime}
          <div class="timeline-content">
            <p class="push-bit">${item.time}</p>
            <p class="push-bit"><strong>${item.messageTitle}</strong></p>
            <p class="push-bit">${item.messageDetail}</p>
            <p class="push-bit">Triggered by <strong>${item.actor}</strong></p>
          </div>
        </li>
      `;
            $container.append(li);
        });
    });
}

function cleanFields() {
    $("#val_project_uid").val("").prop("readonly", false);
    $("#val_project_name").val("");
    $("#val_so_number").val("");
    $("#val_job_code").val("");
    $("#val_description").val("\n\n\n\n"); // reset value
    $("#val_description").prop("rows", 4); // set to 4 rows
    $("#val_description").css("height", "auto"); // reset height if needed

    $("#val_customer_name").val("").trigger("chosen:updated");
    $("#val_staff_pic").val([]).trigger("chosen:updated");
}

function populateStaffList(callback) {
    const $select = $("#val_staff_pic").empty();

    if (cachedStaffList.length > 0) {
        renderStaffOptions($select);
        callback?.();
        return;
    }

    $.ajax({
        url: "/monitoring/rest/staff",
        method: "GET",
        dataType: "json",
        success: function (response) {
            cachedStaffList = response;
            renderStaffOptions($select);
            callback?.();
        },
        error: function () {
            console.error("Unable to fetch staff list");
            callback?.();
        },
    });
}

function renderStaffOptions($select) {
    cachedStaffList.forEach((staff) => {
        $select.append(
            $("<option>", {
                value: staff.uid,
                text: `${staff.firstname} ${staff.lastname}`,
            })
        );
    });
    $select.trigger("chosen:updated");
}

function populateCustomers(callback) {
    const $select = $("#val_customer_name").empty();

    if (cachedCustomerList.length > 0) {
        renderCustomerOptions($select);
        callback?.();
        return;
    }

    $.ajax({
        url: "/monitoring/rest/customers",
        method: "GET",
        dataType: "json",
        success: function (response) {
            cachedCustomerList = response;
            renderCustomerOptions($select);
            callback?.();
        },
        error: function () {
            console.error("Unable to fetch customer list");
            callback?.();
        },
    });
}

function renderCustomerOptions($select) {
    cachedCustomerList.forEach((customer) => {
        $select.append(
            $("<option>", {
                value: customer.uid,
                text: customer.displayName,
            })
        );
    });
    $select.trigger("chosen:updated");
}

$(document).on("click", "#update-project-button", function () {
    $("#update-basic-information-modal").modal("show");
    cleanFields();

    const projectId = $(this).data("id");

    populateStaffList(() => {
        populateCustomers(() => {
            $.ajax({
                url: "/monitoring/rest/projects/" + projectId,
                method: "GET",
                success: function (response) {
                    $("#val_project_uid").prop("readonly", true).val(response.uid);
                    $("#val_project_name").val(response.displayName);
                    $("#val_so_number").val(response.salesOrderNumber);
                    $("#val_job_code").val(response.jobCode);
                    $("#val_description").val(response.description);
                    $("#val_customer_name")
                        .val(response.customerUid)
                        .trigger("chosen:updated");

                    const staffUidList = response.staffPic.map((pic) => pic.staffUid);
                    $("#val_staff_pic").val(staffUidList).trigger("chosen:updated");
                },
                error: function () {
                    Notification.notifyError(
                        "Error",
                        "Unable to fetch project information"
                    );
                },
            });
        });
    });
});

function renderStaffPICList(
    staffPicList,
    containerSelector = "#staff-pic-container"
) {
    const container = $(containerSelector).empty();

    staffPicList.forEach((pic, index) => {
        const html = `
      <div class="col-sm-6 col-lg-6">
        <div class="widget">
          <div class="widget-simple">
            <h4 class="widget-content text-right">
              <strong>${pic.staffName}</strong><br>
              <small>${pic.staffEmail}</small>
            </h4>
          </div>
        </div>
      </div>
    `;
        container.append(html);
    });

    $('[data-toggle="tooltip"]').tooltip();
}

function renderCustomerPICList(
    customerPicList,
    containerSelector = "#customer-pic-container"
) {
    const container = $(containerSelector).empty();

    customerPicList.forEach((pic, index) => {
        const html = `
      <div class="col-sm-6 col-lg-6">
        <div class="widget">
          <div class="widget-simple">
            <h4 class="widget-content text-right">
              <strong>${pic.name}</strong><br>
              <small>${pic.email}</small><br>
              <small>${pic.phone}</small>
            </h4>
          </div>
        </div>
      </div>
    `;
        container.append(html);
    });

    $('[data-toggle="tooltip"]').tooltip();
}

const maintenanceServiceDatatable = (function () {
    function initDatatable(projectId) {
        if (!projectId) {
            Notification.notifyError(
                "Error",
                "Unable to fetch project service information"
            );
            return;
        }

        if ($.fn.DataTable.isDataTable("#project-service-table")) {
            $("#project-service-table").DataTable().destroy();
        }

        App.datatables();

        $("#project-service-table").DataTable({
            autoWidth: false,
            ajax: {
                url: "/monitoring/rest/projects/service/" + projectId,
                method: "GET",
                dataSrc: (json) => json || [],
            },
            columns: [
                {
                    data: "contractNumber",
                    render: (data) => (data ? `<strong>${data}</strong>` : "-"),
                },
                {
                    data: "serviceQty",
                    className: "text-center",
                },
                {
                    data: "projectTags",
                    className: "text-center",
                    render: (data) => {
                        if (!data || data.length === 0) return "-";
                        return data
                            .map((t) => `<span class="label label-info">${t.tag}</span>`)
                            .join(" ");
                    },
                },
                {
                    data: null,
                    className: "text-center",
                    orderable: false,
                    render: (data, type, row) => {
                        const lockedByService = parseInt(row.serviceQty) > 0;
                        const disabled = lockedByService || markForDeletion;

                        let tooltip = "";
                        if (lockedByService && !markForDeletion) {
                            tooltip = 'title="Cannot delete when serviceQty greater than 0"';
                        } else if (markForDeletion) {
                            tooltip = 'title="Already marked for deletion"';
                        }

                        return `
                                <div class="btn-group">
                                  <div class="text-center">
                                    <a class="btn btn-sm btn-info view-button" data-id="${
                            row.uid
                        }">
                                      <i class="fa fa-eye"></i> View
                                    </a>
                                    <a class="btn btn-sm btn-danger delete-btn"
                                       data-id="${row.uid}"
                                       data-displayname="${row.displayName}"
                                       ${disabled ? `disabled ${tooltip}` : ""}>
                                      <i class="fa fa-times-circle"></i> Delete
                                    </a>
                                  </div>
                                </div>
                              `;
                    },
                },
            ],
            pageLength: 10,
            lengthMenu: [
                [10, 20, 30, -1],
                [10, 20, 30, "All"],
            ],
            responsive: true,
        });

        $(".dataTables_filter input").attr("placeholder", "Search");
    }

    return {
        init: initDatatable,
    };
})();

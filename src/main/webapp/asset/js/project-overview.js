$(document).ready(function () {
    $("#project-link").addClass("active");
    $("#project-link").trigger("click");

    const urlParams = new URLSearchParams(window.location.search);
    const projectId = urlParams.get('id');
    fetchProject(projectId);
});


function fetchProject(projectId) {
    $.ajax({
        url: "/monitoring/rest/projects/" + projectId,
        method: "GET",
        data: { id: projectId },
        success: function (response, status, xhr) {

            projectName = response.displayName;
            customerName = response.customerName;
            salesOrderNumber = response.salesOrderNumber;
            staffName = response.staffName;
            jobCode = response.jobCode;
            modifyDt = response.modifyDt;
            createDt = response.createDt;

            document.title = projectName;
            // Title block
            $('#projectTitle').text(projectName);
            $('#customerTitle').text(customerName);
            $('#bc-name').text(projectName);

            // Basic Information block
            $('#projectName').text(projectName);
            $('#customerName').text(customerName);
            $('#salesOrderNumber').text(salesOrderNumber);
            $('#jobCode').text(jobCode);
            $('#projectPic').text(staffName);
            $('#createDt').text(createDt);
            $('#modified').text(modifyDt);

            renderStaffPICList(response.staffPic);
            renderCustomerPICList(response.customerPic);
        },
        error: function () {
            $('#projectTitle').text('Unknown');
            $('#customerTitle').text('Unknown');
            $('#bc-name').text('Unknown');

            Notification.notifyError(
                "Error",
                "Unable to fetch project information"
            );
        },
    });
}

$(document).on("click", "#update-button", function () {
    $("#update-modal").modal("show");
});

function renderStaffPICList(staffPicList, containerSelector = '#staff-pic-container') {
    const container = $(containerSelector);
    container.empty();

    staffPicList.forEach((pic, index) => {
        const avatarIndex = (index % 13) + 1; // Cycle through avatar1.jpg to avatar13.jpg
        const html = `
            <div class="col-sm-6 col-lg-4">
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

    $('[data-toggle="tooltip"]').tooltip(); // Reinitialize tooltips
}


function renderCustomerPICList(customerPicList, containerSelector = '#customer-pic-container') {
    const container = $(containerSelector);
    container.empty();

    customerPicList.forEach((pic, index) => {
        const avatarIndex = (index % 13) + 1; // Cycle through avatar1.jpg to avatar13.jpg
        const html = `
            <div class="col-sm-6 col-lg-4">
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

    $('[data-toggle="tooltip"]').tooltip(); // Reinitialize tooltips
}

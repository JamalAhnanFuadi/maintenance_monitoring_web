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
            console.log(response);
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

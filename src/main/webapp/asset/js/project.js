$(document).ready(function () {
    $("#project-link").addClass("active");
    $("#project-link").trigger("click");

    ProjectDatatables.init(); // Initialize the datatable when the document is ready
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
                    data: "staffName",
                    className: "text-center",
                    render: function (data) {
                        return data ? data : "-";
                    },
                },
                {
                    data: "projectTags",
                    className: "text-center",
                    render: function (data, type, row) {
                        if (!data || data.length === 0) return '-';
                        const tags = data.map(t => `<span class="label label-info">${t.tag}</span>`);
                        return tags.join(" "); // multiple tags as labeled badges
                    }
                }
                ,
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

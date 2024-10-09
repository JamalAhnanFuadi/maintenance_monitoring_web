var TablesDatatables = (function() {
    var initDatatable = function() {
        // Check if DataTable is already initialized and destroy it
        if ($.fn.DataTable.isDataTable('#user-management')) {
            $('#user-management').DataTable().destroy();
        }

        // Initialize Bootstrap Datatables Integration
        App.datatables();

        // Initialize Datatables with AJAX source
        $('#user-management').DataTable({
            autoWidth: false, // Disable auto width calculation
            ajax: {
                url: '/monitoring/rest/users',
                method: 'GET',
                dataSrc: function(json) {
                    return json.users ? json.users : [];
                }
            },
            columns: [
                {
                    data: null, // No specific data field, as this column will have action buttons
                    className: 'text-center', // Center the buttons
                    orderable: false, // Disable ordering for this column
                    render: function(data) {
                        return ''; // Handle null/undefined
                    }
                },
                {
                    data: 'fullname',
                    render: function(data) {
                        return data ? data : '-'; // Handle null/undefined
                    }
                },
                {
                    data: 'email',
                    render: function(data) {
                        return data ? data : '-'; // Handle null/undefined
                    }
                },
                {
                    data: 'department',
                    className: 'text-center',
                    render: function(data) {
                        return data ? data : '-'; // Handle null/undefined
                    }
                },
                {
                    data: 'accessGroupName',
                    className: 'text-center',
                    render: function(data) {
                        return data ? data : '-'; // Handle null/undefined
                    }
                },
                {
                    data: null, // No specific data field, as this column will have action buttons
                    className: 'text-center', // Center the buttons
                    orderable: false, // Disable ordering for this column
                    render: function(data, type, row) {
                        return `
                            <div class="btn-group">
                                <button class="btn btn-sm btn-primary edit-btn" data-id="${row.uid}">Edit</button>
                                <button class="btn btn-sm btn-danger delete-btn" data-id="${row.uid}">Delete</button>
                            </div>`;
                    }
                }
            ],
            columnDefs: [
                { width: '5%', targets: 0 }, // Set width for the first column
                { width: '25%', targets: 1 }, // Set width for the first column
                { width: '20%', targets: 2 }, // Set width for the second column
                { width: '15%', targets: 3 }, // Set width for the third column
                { width: '15%', targets: 4 }, // Set width for the fourth column
                { width: '10%', targets: 5 }  // Set width for the fifth (actions) column
            ],
            pageLength: 10,
            lengthMenu: [[10, 20, 30, -1], [10, 20, 30, 'All']],
            responsive: true // Ensure responsiveness
        });

        // Add placeholder attribute to the search input
        $('.dataTables_filter input').attr('placeholder', 'Search');
    };

    return {
        init: function() {
            initDatatable();
        }
    };
})();

$(document).on('click', '.edit-btn', function() {
    var userId = $(this).data('id');
    console.log('Edit user with ID:', userId);
    // Add your edit logic here
});

$(document).on('click', '.delete-btn', function() {
    var userId = $(this).data('id');
    console.log('Delete user with ID:', userId);
    // Add your delete logic here
});


$(document).ready(function() {
    TablesDatatables.init(); // Initialize the datatable when the document is ready
});

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/pages/css_import.jsp" %>
    <title>Dashboard</title>
</head>
<body>
<div id="page-wrapper">
    <!-- Preloader -->
    <!-- Preloader functionality (initialized in js/app.js) - pageLoading() -->
    <div class="preloader themed-background">
        <h1 class="push-top-bottom text-light text-center"><strong>Pro</strong>UI</h1>
        <div class="inner">
            <h3 class="text-light visible-lt-ie10"><strong>Loading..</strong></h3>
            <div class="preloader-spinner hidden-lt-ie10"></div>
        </div>
    </div>
    <!-- END Preloader -->

    <!-- Page Container -->
    <div id="page-container"
         class="header-fixed-top sidebar-partial sidebar-visible-lg sidebar-no-animations footer-fixed">

        <!-- Main Sidebar -->
        <%@include file="/WEB-INF/pages/left_nav.jsp" %>
        <!-- END Main Sidebar -->

        <!-- Main Container -->
        <div id="main-container">

            <!-- Header -->
            <%@include file="/WEB-INF/pages/top_nav.jsp" %>
            <!-- END Header -->

            <!-- Page content -->
            <div id="page-content">
                <!-- Fixed Top Header + Footer Header -->
                <div class="content-header">
                    <div class="header-section">
                        <h1>
                            <i class="gi gi-show_big_thumbnails"></i>Welcome, {username}
                        </h1>
                    </div>
                </div>
                <!-- END Fixed Top Header + Footer Header -->

                <!-- Datatables Content -->
                <div class="block full">
                    <div class="block-title">
                        <h2><strong>Project List</strong></h2>
                        <button type="button" class="btn btn-primary pull-right">Add New</button>
                    </div>
                    <div class="search-container">
                        <input type="text" id="SearchInput" placeholder="Search...">
                    </div>
                    <div class="table-responsive">
                        <table id="example-datatable" class="table table-vcenter table-condensed table-bordered">
                            <thead>
                            <tr>
                                <th class="text-center">No</th>
                                <th class="text-center">Project</th>
                                <th class="text-center">Job Code</th>
                                <th class="text-center">Q</th>
                                <th class="text-center">BOQ Received</th>
                                <th class="text-center">Target BAST/UAT</th>
                                <th class="text-center">Deadline Date</th>
                                <th class="text-center">Real BAST/UAT</th>
                                <th class="text-center">Status</th>
                                <th class="text-center">Notes</th>

                                <th class="text-center">Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td class="text-center">1</td>
                                <td>Brimob Phase 2 (Vidcon)</td>
                                <td>OP20-0036</td>
                                <td>2021-Q2</td>
                                <td>Jan 22, 2021</td>
                                <td>Week 4 Aug 2021</td>
                                <td>Mar 1, 2021</td>
                                <td>Sep 6, 2021</td>
                                <td>Heavy</td>
                                <td>--</td>



                                <td class="text-center">
                                    <div class="btn-group">
                                        <a href="javascript:void(0)" data-toggle="tooltip" title="Edit" class="btn btn-xs btn-default"><i class="fa fa-pencil"></i></a>
                                        <a href="javascript:void(0)" data-toggle="tooltip" title="Delete" class="btn btn-xs btn-danger"><i class="fa fa-times"></i></a>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-center">2</td>
                                <td>Carolus (H3C Phase 3)</td>
                                <td>OP21-0038</td>
                                <td>2021-Q2</td>
                                <td>Mar 22, 2021</td>
                                <td>Week 4 Juni 2021</td>
                                <td>Apr 21, 2021</td>
                                <td>June 14. 2021</td>
                                <td>Heavy</td>
                                <td>--</td>


                                <td class="text-center">
                                    <div class="btn-group">
                                        <a href="javascript:void(0)" data-toggle="tooltip" title="Edit" class="btn btn-xs btn-default"><i class="fa fa-pencil"></i></a>
                                        <a href="javascript:void(0)" data-toggle="tooltip" title="Delete" class="btn btn-xs btn-danger"><i class="fa fa-times"></i></a>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-center">3</td>
                                <td>Pedima (Meraki MX64)</td>
                                <td>OP21-0035</td>
                                <td>2021-Q2</td>
                                <td>Apr 8, 2021</td>
                                <td>Week 2 Mei 2021</td>
                                <td>May 8, 2021</td>
                                <td>May 4, 2021 (SVR)</td>
                                <td>Heavy</td>
                                <td>--</td>


                                <td class="text-center">
                                    <div class="btn-group">
                                        <a href="javascript:void(0)" data-toggle="tooltip" title="Edit" class="btn btn-xs btn-default"><i class="fa fa-pencil"></i></a>
                                        <a href="javascript:void(0)" data-toggle="tooltip" title="Delete" class="btn btn-xs btn-danger"><i class="fa fa-times"></i></a>
                                    </div>
                                </td>
                            </tr>
                            </tbody>

                    </div>
                    <!-- END Dummy Content -->
                </div>
                <!-- END Page Content -->
            </div>
            <!-- END Main Container -->
        </div>
        <!-- END Page Container -->
    </div>
    <!-- END Page Wrapper -->

    <!-- Scroll to top link, initialized in js/app.js - scrollToTop() -->
    <a href="#" id="to-top"><i class="fa fa-angle-double-up"></i></a>

    <%@include file="/WEB-INF/pages/js_import.jsp" %>
    <script>
        const searchInput = document.getElementById('SearchInput');
        const dataTable = document.getElementById('example-datatable');

        searchInput.addEventListener('keyup', function() {
            const filter = searchInput.value.toLowerCase();
            const rows = dataTable.getElementsByTagName('tr');

            for (let i = 1; i < rows.length; i++) {
                const cells = rows[i].getElementsByTagName('td');
                let found = false;

                for (let j = 0; j < cells.length; j++) {
                    if (cells[j].textContent.toLowerCase().includes(filter)) {
                        found = true;
                        break;
                    }
                }

                rows[i].style.display = found ? '' : 'none';
            }
        });
    </script>
</body>
</html>
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
                        <h2><strong>Preventive Maintenance</strong></h2>
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
                                <th class="text-center">Ticket ID</th>
                                <th class="text-center">PIC</th>
                                <th class="text-center">Date</th>
                                <th class="text-center">Customer</th>
                                <th class="text-center">Problem Description</th>
                                <th class="text-center">Type Of Action</th>
                                <th class="text-center">Status</th>
                                <th class="text-center">Solved Date</th>
                                <th class="text-center">Close Ticket Date</th>

                                <th class="text-center">Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td class="text-center">1</td>
                                <td>PM-21001</td>
                                <td>jamal </td>
                                <td>Feb 2, 2021</td>
                                <td>Oshop/</td>
                                <td>Report PM 7, February 2021</td>
                                <td>Report</td>
                                <td>Solved</td>
                                <td>Feb 2, 2021</td>
                                <td>Apr 5, 2021</td>

                                <td class="text-center">
                                    <div class="btn-group">
                                        <a href="javascript:void(0)" data-toggle="tooltip" title="Edit" class="btn btn-xs btn-default"><i class="fa fa-pencil"></i></a>
                                        <a href="javascript:void(0)" data-toggle="tooltip" title="Delete" class="btn btn-xs btn-danger"><i class="fa fa-times"></i></a>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-center">2</td>
                                <td>PM-21002</td>
                                <td>ahnan </td>
                                <td>Mar 29, 2021</td>
                                <td>PT ENERGY TRANSPORTER INDONESIA</td>
                                <td>Report PM 3, March 2021</td>
                                <td>Report</td>
                                <td>Solved</td>
                                <td>Mar 29, 2021</td>
                                <td>Apr 5, 2021</td>



                                <td class="text-center">
                                    <div class="btn-group">
                                        <a href="javascript:void(0)" data-toggle="tooltip" title="Edit" class="btn btn-xs btn-default"><i class="fa fa-pencil"></i></a>
                                        <a href="javascript:void(0)" data-toggle="tooltip" title="Delete" class="btn btn-xs btn-danger"><i class="fa fa-times"></i></a>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-center">3</td>
                                <td>PM-21003</td>
                                <td>fuadi </td>
                                <td>Mar 18, 2021</td>
                                <td>PT NIAGA NUSA ABADI</td>
                                <td>Report PM 3, March 2021</td>
                                <td>Report</td>
                                <td>Solved</td>
                                <td>Mar 18, 2021</td>
                                <td>Apr 5, 2021</td>



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
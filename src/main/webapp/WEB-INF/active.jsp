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
                        <h2><strong>Master</strong>User</h2>
                        <button type="button" class="btn btn-primary pull-right">Add New</button>
                    </div>
                    <div class="table-responsive">
                        <table id="example-datatable" class="table table-vcenter table-condensed table-bordered">
                            <thead>
                            <tr>
                                <th class="text-center">ID</th>
                                <th class="text-center"><i class="gi gi-user"></i></th>
                                <th class="text-center">Last Update</th>
                                <th class="text-center">MTC Contract</th>
                                <th class="text-center">SO Number</th>
                                <th class="text-center">Service Level</th>
                                <th class="text-center">Customer Name</th>
                                <th class="text-center">Product Number</th>
                                <th class="text-center">Description</th>
                                <th class="text-center">Function</th>
                                <th class="text-center">Serial Number</th>
                                <th class="text-center">Broken Serial Number</th>
                                <th class="text-center">MTC Begin Date</th>
                                <th class="text-center">MTC End Date</th>
                                <th class="text-center">Job Code</th>
                                <th class="text-center">PO From Cust</th>
                                <th class="text-center">PO to Disti</th>
                                <th class="text-center">Agin & MTC</th>
                                <th class="text-center">End User Mtc</th>
                                <th class="text-center">Project Name</th>
                                <th class="text-center">Sales Name</th>
                                <th class="text-center">Contract Principle Number</th>
                                <th class="text-center">Vendor Name</th>
                                <th class="text-center">End User Contract Principle</th>
                                <th class="text-center">Contract Principle Begin Date</th>
                                <th class="text-center">Contract Principle End Date</th>
                                <th class="text-center">Aging Contract</th>
                                <th class="text-center">Email</th>


                                <th class="text-center">Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td class="text-center">1</td>
                                <td class="text-center"><img src="img/placeholders/avatars/avatar15.jpg" alt="avatar" class="img-circle"></td>
                                <td>21-feb-23</td>
                                <td>DCW32-0002</td>
                                <td>0715-145/ALT.02-PO-TSI/2022</td>
                                <td>Device Waranty</td>
                                <td>PT ANOMALI LINTAS ENERGI</td>
                                <td>MR-44-HW</td>
                                <td>Catalyst 9500 48-port x 1/10/25g + 4-port 40/100g, Advantage</td>
                                <td>qwertyuy</td>
                                <td>FD027021GY</td>
                                <td>---</td>
                                <td>25-nov-23</td>
                                <td>5-nov-24</td>
                                <td>OP23-0093</td>
                                <td>smartnet</td>
                                <td>smartnet</td>
                                <td>102</td>
                                <td>kemenkes</td>
                                <td>Cisco Meraki Maintenance for Kemenkes</td>
                                <td>sales 1</td>
                                <td>---</td>
                                <td>---</td>
                                <td>kemenkes</td>
                                <td>28-oct-22</td>
                                <td>16-mar-25</td>
                                <td>233</td>
                                <td>contoh@gmail.com</td>



                                <td class="text-center">
                                    <div class="btn-group">
                                        <a href="javascript:void(0)" data-toggle="tooltip" title="Edit" class="btn btn-xs btn-default"><i class="fa fa-pencil"></i></a>
                                        <a href="javascript:void(0)" data-toggle="tooltip" title="Delete" class="btn btn-xs btn-danger"><i class="fa fa-times"></i></a>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                            <tr>
                                <td class="text-center">1</td>
                                <td class="text-center"><img src="img/placeholders/avatars/avatar15.jpg" alt="avatar" class="img-circle"></td>
                                <td>21-feb-23</td>
                                <td>DCW32-0002</td>
                                <td>0715-145/ALT.02-PO-TSI/2022</td>
                                <td>Device Waranty</td>
                                <td>PT ANOMALI LINTAS ENERGI</td>
                                <td>MR-44-HW</td>
                                <td>Catalyst 9500 48-port x 1/10/25g + 4-port 40/100g, Advantage</td>
                                <td>qwertyuy</td>
                                <td>FD027021GY</td>
                                <td>---</td>
                                <td>25-nov-23</td>
                                <td>5-nov-24</td>
                                <td>OP23-0093</td>
                                <td>smartnet</td>
                                <td>smartnet</td>
                                <td>102</td>
                                <td>kemenkes</td>
                                <td>Cisco Meraki Maintenance for Kemenkes</td>
                                <td>sales 1</td>
                                <td>---</td>
                                <td>---</td>
                                <td>kemenkes</td>
                                <td>28-oct-22</td>
                                <td>16-mar-25</td>
                                <td>233</td>
                                <td>contoh@gmail.com</td>




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
</body>
</html>
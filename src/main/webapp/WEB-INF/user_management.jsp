<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">

    <title>User Management</title>

    <meta name="description" content="">
    <meta name="author" content="The Special One">
    <meta name="robots" content="noindex, nofollow">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=0">

    <%@include file="/WEB-INF/pages/css_import.jsp" %>
</head>

<body>
<!-- Page Wrapper -->
<div id="page-wrapper">
    <!-- Preloader -->
    <div class="preloader themed-background">
        <h1 class="push-top-bottom text-light text-center"><strong>Monitoring and Maintenance</strong></h1>
        <div class="inner">
            <h3 class="text-light visible-lt-ie10"><strong>Loading..</strong></h3>
            <div class="preloader-spinner hidden-lt-ie10"></div>
        </div>
    </div>
    <!-- END Preloader -->

    <!-- Page Container -->
    <div id="page-container" class="header-fixed-top sidebar-partial sidebar-visible-lg sidebar-no-animations">
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
                <!-- Fixed Top Header Header -->
                <div class="content-header">
                    <div class="header-section">
                        <h1>
                            <i class="gi gi-show_big_thumbnails"></i> User Management
                        </h1>
                    </div>
                </div>
                <ul class="breadcrumb breadcrumb-top">
                    <li><a href="index">Home</a></li>
                    <li><a href="#">User</a></li>
                    <li><a href="#">User Management</a></li>
                </ul>
                <!-- END Fixed Top Header Header -->

                <!-- Content -->
                <div class="block full block-alt-noborder">
                    <div class="text-right">
                        <a id="add-user-button" href="#user-modal" class="btn btn-sm btn-primary"
                           data-toggle="modal"><i class="fa fa-plus"></i> Add User</a>
                    </div>
                    <br />
                    <!-- Datatables Content -->
                    <div class="table-responsive">
                        <table id="user-management"
                               class="table table-vcenter table-striped table-bordered">
                            <thead>
                            <tr>
                                <th class="text-center"></th>
                                <th class="text-center"><i class="gi gi-user"></i></th>
                                <th class="text-center">Email</th>
                                <th class="text-center">Department</th>
                                <th class="text-center">User Group</th>
                                <th class="text-center">Status</th>
                                <th class="text-center">Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                    <!-- END Datatables Content -->
                </div>
                <!-- END Content -->
            </div>
            <!-- END Page Content -->

            <!-- Footer -->
            <footer class="clearfix">
                <div class="pull-right">
                    Crafted with <i class="fa fa-heart text-danger"></i> by <a href="#"
                                                                               target="_blank">The Special One</a>
                </div>
            </footer>
            <!-- END Footer -->
        </div>
        <!-- END Main Container -->
    </div>
    <!-- END Page Container -->
</div>
<!-- END Page Wrapper -->

<!-- Scroll to top link, initialized in js/app.js - scrollToTop() -->
<div class="pull-right">
    <a href="#" id="to-top"><i class="fa fa-angle-double-up"></i></a>
</div>


<!-- Add/Update User modal -->
<div id="user-modal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h3 class="modal-title">Add user</h3>
            </div>
            <div class="modal-body">
                <form id="user-form" class="form-horizontal form-bordered">
                    <fieldset>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_firstname">First Name <span
                                    class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <input type="text" id="val_firstname" name="val_firstname" class="form-control"
                                       placeholder="First Name..">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_lastname">Last Name <span
                                    class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <input type="text" id="val_lastname" name="val_lastname" class="form-control"
                                       placeholder="Last Name..">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_email">Email <span
                                    class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <input type="email" id="val_email" name="val_email" class="form-control"
                                       placeholder="test@example.com">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_mobileNumber">Mobile Number <span
                                    class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <input type="text" id="val_mobileNumber" name="val_mobileNumber"
                                       class="form-control" placeholder="081234567805">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_usergroup">
                                User Group <span class="text-danger">*</span>
                            </label>
                            <div class="col-md-6">
                                <select id="val_usergroup" name="val_usergroup"
                                        class="form-control select-chosen" data-placeholder="Select user group"
                                        style="width: 250px;">
                                    <option value=""></option>
                                    <!-- User group list fetch from API -->
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_department">
                                Department <span class="text-danger">*</span>
                            </label>
                            <div class="col-md-6">
                                <select id="val_department" name="val_department"
                                        class="form-control select-chosen" data-placeholder="Select department"
                                        style="width: 250px;">
                                    <option value=""></option>
                                    <!-- Department list fetch from API -->
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_dob">Date of Birth <span
                                    class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <input type="date" id="val_dob" name="val_dob" class="form-control">
                            </div>
                        </div>
                    </fieldset>
                    <div class=" modal-footer form-group form-actions">
                        <div class="col-md-8 col-md-offset-4">
                            <button id="submit-button" type="submit" class="btn btn-sm btn-primary"><i
                                    class="fa fa-arrow-right"></i>
                                Submit
                            </button>
                            <button id="reset-button" type="reset" class="btn btn-sm btn-warning"><i
                                    class="fa fa-repeat"></i> Reset
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- END Add/Update User modal -->

<!-- Delete User modal -->
<div id="delete-user-modal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h3 class="modal-title">Modal Title</h3>
            </div>
            <div class="modal-body">
            </div>
            <div class="modal-footer">
            </div>
        </div>
    </div>
</div>
<!-- END Delete User modal -->


<%@include file="/WEB-INF/pages/js_import.jsp" %>
<!-- Load and execute javascript code used only in this page -->
<script src="${pageContext.request.contextPath}/asset/js/user-management.js"></script>
<script>$(function () {
    TablesDatatables.init();
});</script>
</body>

</html>
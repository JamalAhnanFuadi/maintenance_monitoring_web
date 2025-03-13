<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">

    <title>Staff</title>

    <meta name="description" content="">
    <meta name="author" content="The Special One">
    <meta name="robots" content="noindex, nofollow">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,staff-scalable=0">

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
                            <i class="gi gi-show_big_thumbnails"></i> Staff
                        </h1>
                    </div>
                </div>
                <ul class="breadcrumb breadcrumb-top">
                    <li><a href="index">Home</a></li>
                    <li><a href="#">Staff</a></li>
                </ul>
                <!-- END Fixed Top Header Header -->

                <!-- Content -->
                <div class="block full block-alt-noborder">
                    <div class="table-options clearfix">

                        <div class="btn-group btn-group-sm pull-left" data-toggle="buttons">
                            <a id="export-staff-button" class="btn btn-sm btn-default" >
                                <i class="fa fa-print"></i> Export</a>
                        </div>
                        <div class="btn-group btn-group-sm pull-right" data-toggle="buttons">
                            <a id="add-staff-button" href="#add-modal" class="btn btn-sm btn-info add-staff-button"
                               data-toggle="modal"><i class="fa fa-plus"></i> Add Staff</a>
                        </div>
                    </div>

                    <!-- Datatables Content -->
                    <div class="table-responsive">
                        <table id="staff-datatable"
                               class="table table-vcenter table-striped table-bordered">
                            <thead>
                            <tr>
                                <th class="text-center"></th>
                                <th class="text-center"><i class="gi gi-user"></i></th>
                                <th class="text-center">Email</th>
                                <th class="text-center">Department</th>
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



<!-- Add staff modal -->
<div id="add-modal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h3 class="modal-title">Add staff</h3>
            </div>
            <div class="modal-body">
                <form id="add-form" class="form-horizontal form-bordered">
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
                            <label class="col-md-4 control-label" for="val_lastname">Last Name</label>
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
                                <div id="email-error" class="text-danger" style="display: none;"></div>
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
                            <label class="col-md-4 control-label" for="val_department">
                                Department <span class="text-danger">*</span>
                            </label>
                            <div class="col-md-6">
                                <select id="val_department" name="val_department"
                                        class="form-control select-chosen" data-placeholder="Select department"
                                        style="width: 250px;">
                                    <option value=""></option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_dob">Date of Birth</label>
                            <div class="col-md-6">
                                <input type="date" id="val_dob" name="val_dob" class="form-control">
                            </div>
                        </div>
                    </fieldset>
                    <div class=" modal-footer form-group form-actions">
                        <div class="col-md-8 col-md-offset-4">
                            <div class="btn-group btn-group-sm pull-right" data-toggle="buttons">
                                <a id="submit-button" class="btn btn-sm btn-info submit-button"></i>Submit</a>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- END Add staff modal -->

<!-- View/Update staff modal -->
<div id="view-modal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h3 class="modal-title" style="display: inline-block; margin: 0; margin-right: 10px;">View staff</h3>
                <a id="update-button" class="btn btn-sm btn-info update-button" style="display: inline-block; vertical-align: middle; margin-top: -2px;">Edit</a>
            </div>
            <div class="modal-body">
                <form id="view-form" class="form-horizontal form-bordered">
                    <fieldset>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_vuid">Staff ID <span
                                    class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <input type="text" id="val_vuid" name="val_vuid" class="form-control"
                                       placeholder="Staff ID..">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_vstatus">Status <span
                                    class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <label class="switch switch-primary" for="val_vstatus">
                                    <input type="checkbox" id="val_vstatus" name="val_vstatus">
                                    <span data-toggle="tooltip" title="" data-original-title="Enable / Disable staff"></span>
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_vfirstname">First Name <span
                                    class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <input type="text" id="val_vfirstname" name="val_vfirstname" class="form-control"
                                       placeholder="First Name..">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_vlastname">Last Name</label>
                            <div class="col-md-6">
                                <input type="text" id="val_vlastname" name="val_vlastname" class="form-control"
                                       placeholder="Last Name..">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_vemail">Email <span
                                    class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <input type="email" id="val_vemail" name="val_vemail" class="form-control"
                                       placeholder="test@example.com">
                                <div id="email-error" class="text-danger" style="display: none;"></div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_vmobileNumber">Mobile Number <span
                                    class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <input type="text" id="val_vmobileNumber" name="val_vmobileNumber"
                                       class="form-control" placeholder="081234567805">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_vdepartment">
                                Department <span class="text-danger">*</span>
                            </label>
                            <div class="col-md-6">
                                <select id="val_vdepartment" name="val_vdepartment"
                                        class="form-control select-chosen" data-placeholder="Select department"
                                        style="width: 250px;">
                                    <option value=""></option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_vdob">Date of Birth</label>
                            <div class="col-md-6">
                                <input type="date" id="val_vdob" name="val_vdob" class="form-control">
                            </div>
                        </div>
                    </fieldset>
                    <div class=" modal-footer form-group form-actions">
                        <div class="col-md-8 col-md-offset-4">
                            <div class="btn-group btn-group-sm pull-right" data-toggle="buttons">
                                <a id="submit-button" class="btn btn-sm btn-info submit-button"></i>Submit</a>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- END View/Update staff modal -->

<!-- Delete modal -->
<div id="delete-modal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h3 class="modal-title">Modal Title</h3>
            </div>
            <div class="modal-body">
            </div>
            <div class="modal-footer">
                <div class="btn-group btn-group-sm pull-left" data-toggle="buttons">
                    <a id="cancel-delete-button" class="btn btn-sm btn-default cancel-delete-button">Cancel</a>
                </div>
                <div class="btn-group btn-group-sm pull-right" data-toggle="buttons">
                    <a id="confirm-delete-button" class="btn btn-sm btn-danger confirm-delete-button"></i>Confirm Delete</a>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- END Delete modal -->


<%@include file="/WEB-INF/pages/js_import.jsp" %>
<!-- Load and execute javascript code used only in this page -->
<script src="${pageContext.request.contextPath}/asset/js/staff.js"></script>
</body>

</html>
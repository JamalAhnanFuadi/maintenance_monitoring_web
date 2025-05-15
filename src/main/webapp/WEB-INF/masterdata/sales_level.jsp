<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">

    <title>Sales Levels</title>

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
                            <i class="gi gi-show_big_thumbnails"></i> Sales Levels
                        </h1>
                    </div>
                </div>
                <ul class="breadcrumb breadcrumb-top">
                    <li><a href="index">Home</a></li>
                    <li><a href="#">Sales Levels</a></li>
                </ul>
                <!-- END Fixed Top Header Header -->

                <!-- Content -->
                <div class="block full block-alt-noborder">

                    <div class="table-options clearfix">

                        <div class="btn-group btn-group-sm pull-left" data-toggle="buttons">
                            <a id="export-saleslevel-button" class="btn btn-sm btn-default">
                                <i class="fa fa-print"></i> Export</a>
                        </div>
                        <div class="btn-group btn-group-sm pull-right" data-toggle="buttons">
                            <a id="add-saleslevel-button" href="#add-modal" class="btn btn-sm btn-info"
                               data-toggle="modal"><i class="fa fa-plus"></i> Add Sales Level</a>
                        </div>
                    </div>
                    <!-- Datatables Content -->
                    <div class="table-responsive">
                        <table id="sales-level-table"
                               class="table table-vcenter table-striped table-bordered">
                            <thead>
                            <tr>
                                <th class="text-center">Sales Level</th>
                                <th class="text-center">Description</th>
                                <th class="text-center">Created</th>
                                <th class="text-center">Modified</th>
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


<!-- Add modal -->
<div id="add-modal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h3 class="modal-title">Add Sales Levels</h3>
            </div>
            <div class="modal-body">
                <form id="add-form" class="form-horizontal form-bordered">
                    <fieldset>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_saleslevel_name">Sales Levels Name <span
                                    class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <input type="text" id="val_saleslevel_name" name="val_saleslevel_name"
                                       class="form-control" placeholder="Sales Level Name..">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_description">Description </label>
                            <div class="col-md-6">
                                        <textarea id="val_description" name="val_description" class="form-control"
                                                  placeholder="Description.." rows="4"></textarea>
                            </div>
                        </div>
                    </fieldset>

                    <div class="modal-footer">
                        <div class="btn-group btn-group-sm pull-right" data-toggle="buttons">
                            <a id="submit-button" class="btn btn-sm btn-info submit-button"></i>Submit</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- END Add modal -->

<!-- View/Update modal -->
<div id="view-modal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h3 class="modal-title" style="display: inline-block; margin: 0; margin-right: 10px;">View Sales Levels</h3>
                <a id="update-button" class="btn btn-sm btn-info update-button" style="display: inline-block; vertical-align: middle; margin-top: -2px;">Edit</a>
            </div>
            <div class="modal-body">
                <form id="update-form" class="form-horizontal form-bordered">
                    <fieldset>
                        <div id="saleslevel-id" class="form-group">
                            <label class="col-md-4 control-label" for="val_vsalesname_id">Sales Levels ID <span
                                    class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <input type="text" id="val_vsalesname_id" name="val_vsalesname_id"
                                       class="form-control" placeholder="Sales Level ID.." readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_vsaleslevel_name">Sales Levels Name <span
                                    class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <input type="text" id="val_vsaleslevel_name" name="val_vsaleslevel_name"
                                       class="form-control" placeholder="Sales Level Name..">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_vdescription">Description </label>
                            <div class="col-md-6">
                                        <textarea id="val_vdescription" name="val_vdescription" class="form-control"
                                                  placeholder="Description.." rows="4"></textarea>
                            </div>
                        </div>
                    </fieldset>

                    <div id="update-modal-footer" class="modal-footer">
                        <div class="modal-footer">
                            <div class="btn-group btn-group-sm pull-left" data-toggle="buttons">
                                <a id="cancel-update-button" class="btn btn-sm btn-default cancel-update-button">Cancel</a>
                            </div>
                            <div class="btn-group btn-group-sm pull-right" data-toggle="buttons">
                                <a id="confirm-update-button" class="btn btn-sm btn-danger confirm-update-button"></i>Save changes</a>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- END View/Update modal -->

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
<script src="${pageContext.request.contextPath}/asset/js/sales-level.js"></script>
</body>

</html>
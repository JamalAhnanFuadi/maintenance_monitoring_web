<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">

    <title>Projects</title>

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
                            <i class="gi gi-show_big_thumbnails"></i> <strong><span
                                id="projectTitle"></span></strong>
                        </h1>
                        <h2><span id="customerTitle"></span></h2>
                    </div>
                </div>
                <ul class="breadcrumb breadcrumb-top">
                    <li><a href="index">Home</a></li>
                    <li><a href="projects">Projects</a></li>
                    <li><a href="#" id="bc-name"></a></li>
                </ul>
                <!-- END Fixed Top Header Header -->

                <!-- Content -->
                <div class="row">
                    <div class="col-md-6">
                        <!-- Basic Project Information Block -->
                        <div class="block">
                            <!-- Basic Project Information Title -->
                            <div class="block-title">
                                <h2><strong>Basic project information</strong></h2>
                                <a id="update-project-button" class="btn btn-sm btn-info update-button"
                                   style="display: inline-block; vertical-align: middle; margin-top: -2px;">
                                    Update</a>
                            </div>
                            <!-- END Project Information Title -->

                            <!-- Basic Project Information Content -->
                            <form class="form-horizontal form-bordered">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Project Name :</label>
                                    <div class="col-xs-9">
                                        <p class="form-control-static" id="projectName"> - </p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Customer Name :</label>
                                    <div class="col-xs-9">
                                        <p class="form-control-static" id="customerName"> - </p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Sales Order Number :</label>
                                    <div class="col-xs-9">
                                        <p class="form-control-static" id="salesOrderNumber"> - </p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Job Code :</label>
                                    <div class="col-xs-9">
                                        <p class="form-control-static" id="jobCode"> - </p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Create Date :</label>
                                    <div class="col-xs-9">
                                        <p class="form-control-static" id="createDt"> - </p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Last Modified :</label>
                                    <div class="col-xs-9">
                                        <p class="form-control-static" id="modified"> - </p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Last Maintenance :</label>
                                    <div class="col-xs-9">
                                        <p class="form-control-static" id="maintenanced"> - </p>
                                    </div>
                                </div>
                            </form>
                            <!-- END Project Information Content -->
                        </div>
                        <!-- END Basic Project Information Block -->
                    </div>

                    <!-- Maintenance Sercice Block -->
                    <div class="col-md-6">
                        <div class="block">
                            <!-- Maintenance Sercice Title -->
                            <div class="block-title">
                                <h2><strong>Maintenance Service's</strong></h2>
                                <a id="update-button" class="btn btn-sm btn-info update-button"
                                   style="display: inline-block; vertical-align: middle; margin-top: -2px;">
                                    Add Service</a>
                            </div>
                            <!-- END Maintenance Sercice Title -->
                            <!-- Maintenance Sercice Content -->
                            <div class="table-responsive">
                                <table id="project-service-table"
                                       class="table table-vcenter table-striped table-bordered">
                                    <thead>
                                    <tr>
                                        <th class="text-center">Contract No</th>
                                        <th class="text-center">Service Qty</th>
                                        <th class="text-center">Action</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                            <!-- END Maintenance Sercice Content -->
                        </div>
                    </div>
                    <!-- END Maintenance Sercice Block -->
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <!-- Staff PIC Block -->
                        <div class="block">
                            <!-- Staff PIC Title -->
                            <div class="block-title">
                                <h2><strong>Staff PIC</strong></h2>
                                <a id="update-project-button" class="btn btn-sm btn-info update-button"
                                   style="display: inline-block; vertical-align: middle; margin-top: -2px;">
                                    Update</a>
                            </div>
                            <!-- END Staff PIC Title -->

                            <!-- Staff PIC Content -->
                            <div class="row style-alt" id="staff-pic-container"></div>
                            <!-- END Staff PIC Content -->
                        </div>
                        <!-- END Staff PIC Block -->
                    </div>

                    <!-- Customer PIC Block -->
                    <div class="col-md-6">
                        <!-- Customer PIC Block -->
                        <div class="block">
                            <!-- Customer PIC Title -->
                            <div class="block-title">
                                <h2><strong>Customer PIC</strong></h2>
                                <a id="update-button" class="btn btn-sm btn-info update-button"
                                   style="display: inline-block; vertical-align: middle; margin-top: -2px;">
                                    Update</a>
                            </div>
                            <!-- END Customer PIC Title -->

                            <!-- Customer PIC Content -->
                            <div class="row style-alt" id="customer-pic-container"></div>
                            <!-- END Customer PIC Content -->
                        </div>
                        <!-- END Customer PIC Block -->
                    </div>

                </div>
                <!-- END Content -->
            </div>
            <!-- END Page Content -->

            <!-- Footer -->
            <footer class="clearfix">
                <div class="pull-right">
                    Crafted with <i class="fa fa-heart text-danger"></i> by <a href="#"
                                                                               target="_blank">The
                    Special One</a>
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

<!-- View/Update modal -->
<div id="update-project-modal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h3 class="modal-title" style="display: inline-block; margin: 0; margin-right: 10px;">Update
                    Project Basic Information</h3>
            </div>
            <div class="modal-body">
                <form id="update-project-form" class="form-horizontal form-bordered">
                    <fieldset>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_status">Status <span
                                    class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <label class="switch switch-primary" for="val_status">
                                    <input type="checkbox" id="val_status" name="val_status">
                                    <span data-toggle="tooltip" title=""
                                          data-original-title="Enable / Disable project"></span>
                                </label>
                            </div>
                        </div>
                        <div id="product-id" class="form-group">
                            <label class="col-md-4 control-label" for="val_project_id">Project ID <span
                                    class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <input type="text" id="val_project_id" name="val_project_id"
                                       class="form-control" placeholder="Project ID.." readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_project_name">Project Name
                                <span class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <input type="text" id="val_project_name" name="val_project_name"
                                       class="form-control" placeholder="Project Name..">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_customer_name">
                                Customer Name <span class="text-danger">*</span>
                            </label>
                            <div class="col-md-6">
                                <select id="val_customer_name" name="val_customer_name"
                                        class="form-control select-chosen" data-placeholder="Select Customer.."
                                        style="width: 250px;">
                                    <option value=""></option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_sales_order_number">Sales Order
                                Number
                                <span class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <input type="text" id="val_sales_order_number" name="val_sales_order_number"
                                       class="form-control" placeholder="Sales Order Number..">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_sales_order_number">Sales Order
                                Number
                                <span class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <input type="text" id="val_sales_order_number" name="val_sales_order_number"
                                       class="form-control" placeholder="Sales Order Number..">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_sales_order_number">Sales Order
                                Number
                                <span class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <input type="text" id="val_sales_order_number" name="val_sales_order_number"
                                       class="form-control" placeholder="Sales Order Number..">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_sales_order_number">Sales Order
                                Number
                                <span class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <input type="text" id="val_sales_order_number" name="val_sales_order_number"
                                       class="form-control" placeholder="Sales Order Number..">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_sales_order_number">Job Code
                                <span class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <input type="text" id="val_sales_order_number" name="val_sales_order_number"
                                       class="form-control" placeholder="Sales Order Number..">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_customer_name">
                                Company PIC <span class="text-danger">*</span>
                            </label>
                            <div class="col-md-6">
                                <select id="val_customer_name" name="val_customer_name"
                                        class="form-control select-chosen" data-placeholder="Select PIC Staff.."
                                        style="width: 250px;">
                                    <option value=""></option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label" for="val_vdescription">Description
                            </label>
                            <div class="col-md-6">
                                        <textarea id="val_vdescription" name="val_vdescription" class="form-control"
                                                  placeholder="Description.." rows="4"></textarea>
                            </div>
                        </div>
                    </fieldset>

                    <div id="update-modal-footer" class="modal-footer">
                        <div class="modal-footer">
                            <div class="btn-group btn-group-sm pull-left" data-toggle="buttons">
                                <a id="cancel-update-button"
                                   class="btn btn-sm btn-default cancel-update-button">Cancel</a>
                            </div>
                            <div class="btn-group btn-group-sm pull-right" data-toggle="buttons">
                                <a id="confirm-update-button"
                                   class="btn btn-sm btn-danger confirm-update-button"></i>Save changes</a>
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
                    <a id="confirm-delete-button"
                       class="btn btn-sm btn-danger confirm-delete-button"></i>Confirm Delete</a>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- END Delete modal -->


<%@include file="/WEB-INF/pages/js_import.jsp" %>
<!-- Load and execute javascript code used only in this page -->
<script src="${pageContext.request.contextPath}/asset/js/project-overview.js"></script>
</body>

</html>
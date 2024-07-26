<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <%@include file="pages/css_import.jsp"%>
    <title>UMX</title>
</head>

<body class="nav-md">
    <!-- Loading Screen -->
    <div class="loader"></div>
    <div class="container body">
        <div class="main_container">
            <!-- side nav -->
            <%@include file="pages/side_menu.jsp"%>
            <!-- /side nav -->
            <!-- top navigation -->
            <%@include file="pages/top_nav.jsp"%>
            <!-- /top navigation -->
            <!-- page content -->
            <main class="right_col" role="main" id="vue">
                <div class="x_panel">
                    <article class="x_content">
                        <section class="row">
                            <div class="table-responsive">
                                <table id="businessRoleDT" class="table table-hover" style="width: 100%;">
                                    <thead>
                                        <th class="bizrole">Business Role</th>
                                        <th class="hrrole">HR Role</th>
                                        <th class="appname">Applications Name</th>
                                        <th class="details">Details</th>
                                    </thead>
                                </table>
                            </div>
                        </section>
                    </article>
                </div>
                <!-- importModal -->
                <div class="modal fade" id="importModal" tabindex="-1" role="dialog" data-keyboard="false"
                    data-backdrop="static" @keyup.esc="cancelImport()">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-body">
                                <div class="container-fluid">
                                    <div class="x_title">
                                        <button type="button" class="close" aria-label="Close"
                                            onclick="main.cancelImport()">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                        <h3 class="modal-title">Import Business roles</h3>
                                    </div>
                                    <div class="x_content">
                                        <div class="row">
                                            <p>
                                                <span class="badge badge-danger col-xs-12">
                                                    Note : Business roles to be imported will replace the existing list.
                                                </span>
                                            </p>
                                            <div class="col-xs-12">
                                                <h5>You must include the following:</h5>
                                                <ul>
                                                    <li>
                                                        <p>
                                                            <h5>Column names:</h5>
                                                            <span>
                                                                "<em>Name</em>",
                                                                "<em>HR Role</em>",
                                                                "<em>Application</em>",
                                                                "<em>Role</em>"
                                                            </span>
                                                        </p>
                                                    </li>
                                                </ul>
                                                <p>
                                                    <br />
                                                    <h5>For example:</h5>
                                                    <div class="well">
                                                        <p>
                                                            Name,HR Role,Application,Role<br/>
                                                            Biz Role 1,HR Role 1;HR Role 2,AMLO,Administrator<br/>
                                                            Biz Role 1,HR Role 1;HR Role 2,KServe,SUPER ADMIN<br/>
                                                        </p>
                                                    </div>
                                                    <em>
                                                        Tip : To retain the existing list, export the current list, append to the list and import.
                                                    </em>
                                                    <textarea v-model="data" class="form-control"
                                                        style="resize: vertical;" @keyup.ctrl.enter="doImport()">
                                                    </textarea>
                                                    <div class="ln_solid"></div>
                                                    <button class="btn btn-sm btn-primary iconed-button" type="button"
                                                        onclick="main.doImport()">
                                                        <i class="fa fa-fw fa-upload"></i>
                                                        <span>Import</span>
                                                    </button>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /importModal -->
                <!-- detailsModal -->
                <div class="modal fade" id="detailsModal" tabindex="-1" role="dialog">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-body">
                                <div class="container-fluid">
                                    <div class="x_title">
                                        <button type="button" class="close" aria-label="Close" data-dismiss="modal">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                        <h3 class="modal-title">Business Role Details</h3>
                                    </div>
                                    <div class="x_content">
                                        <div class="row mb-1">
                                            <dt class="col-md-3">
                                                Business Role
                                            </dt>
                                            <dd class="col-md-9">
                                                : {{ ((selectedBizRole || {}).name) }}
                                            </dd>
                                        </div>
                                        <div class="row">
                                            <dt class="col-md-3">
                                                HR Role
                                            </dt>
                                            <dd class="col-md-9">
                                                : {{ (((selectedBizRole || {}).hrRole) || "").replace(";",", ") }}
                                            </dd>
                                        </div>
                                        <div class="row">
                                            <div class="table-responsive pt-2">
                                                <table id="businessRoleDetailsDT" class="table table-hover" style="width: 100%;">
                                                    <thead>
                                                        <th class="appname">Application Name</th>
                                                        <th class="roleName">Role Name</th>
                                                    </thead>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            <!-- /detailsModal -->
            </main>
            <!-- /page content -->
        </div>
        <!-- footer content -->
        <%@include file="pages/footer.jsp"%>
        <!-- /footer content -->
    </div>
    <!-- javascript imports -->
    <%@include file="pages/js_import.jsp"%>
    <script src="/umx/js/businessRoleManagement.js"></script>
</body>

</html>
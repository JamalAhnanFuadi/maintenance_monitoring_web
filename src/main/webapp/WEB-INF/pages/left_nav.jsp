<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- Main Sidebar -->
<div id="sidebar" class="themed-color-dark-lake themed-background-dark-lake">
    <!-- Wrapper for scrolling functionality -->
    <div id="sidebar-scroll">
        <!-- Sidebar Content -->
        <div class="sidebar-content">
            <!-- Brand -->
            <a href="index" class="sidebar-brand">
                <i class="gi gi-home"></i><span class="sidebar-nav-mini-hide"><strong>Monitoring</strong></span>
            </a>
            <!-- END Brand -->
            <!-- Sidebar Navigation -->
            <ul class="sidebar-nav">
                <li class="sidebar-header">
                    <span class="sidebar-header-options clearfix"><a href="javascript:void(0)" data-toggle="tooltip" title="Master data"><i class="gi gi-server"></i></a></span>
                    <span class="sidebar-header-title">Reports</span>
                </li>
                <li>
                    <a href="#"><i class="gi gi-server_flag sidebar-nav-icon"></i><span
                            class="sidebar-nav-mini-hide">Service Reports</span></a>
                </li>
                <li>
                    <a href="#"><i class="gi gi-server_new sidebar-nav-icon"></i><span
                            class="sidebar-nav-mini-hide">Maintenance Reports</span></a>
                </li>
                <li class="sidebar-header">
                    <span class="sidebar-header-options clearfix"><a href="javascript:void(0)" data-toggle="tooltip" title="Master data"><i class="gi gi-cargo"></i></a></span>
                    <span class="sidebar-header-title">Master Data</span>
                </li>
                <li>
                    <a href="#" id="user-management-link" class="sidebar-nav-menu"><i
                            class="fa fa-angle-left sidebar-nav-indicator sidebar-nav-mini-hide"></i><i
                            class="gi gi-user sidebar-nav-icon"></i><span
                            class="sidebar-nav-mini-hide">Staff Management</span></a>
                    <ul>
                        <li>
                            <a id="user-management-sub-link" href="user-management">Staff</a>
                        </li>
                        <li>
                            <a id="user-access-matrix-sub-link" href="#">Staff Access</a>
                        </li>
                    </ul>
                </li>

                <li>
                    <a href="#" id="customer-management-link" class="sidebar-nav-menu"><i
                            class="fa fa-angle-left sidebar-nav-indicator sidebar-nav-mini-hide"></i><i
                            class="gi gi-group sidebar-nav-icon"></i><span
                            class="sidebar-nav-mini-hide">Stakeholders</span></a>
                    <ul>
                        <li>
                            <a id="end-user-management-sub-link" href="end-user-management">End Users</a>
                        </li>
                        <li>
                            <a id="principal-user-management-sub-link" href="principal-user-management">Principal Users</a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="stakeholders" id="stakeholder-link"><i class="gi gi-group sidebar-nav-icon"></i><span
                            class="sidebar-nav-mini-hide">Stakeholders</span></a>
                </li>
                <li class="sidebar-header">
                    <span class="sidebar-header-options clearfix"><a href="javascript:void(0)" data-toggle="tooltip" title="Quick menu"><i class="gi gi-package"></i></a></span>
                    <span class="sidebar-header-title">Menu</span>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/rest/authentications/logout"><i class="gi gi-log_out sidebar-nav-icon"></i><span
                            class="sidebar-nav-mini-hide">logout</span></a>
                </li>
            </ul>
            <!-- END Sidebar Navigation -->
        </div>
        <!-- END Sidebar Content -->
    </div>
    <!-- END Wrapper for scrolling functionality -->
</div>
<!-- END Main Sidebar -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- Main Sidebar -->
<div id="sidebar" class="themed-background-default themed-color-default">
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
                    <span class="sidebar-header-options clearfix"><a href="javascript:void(0)" data-toggle="tooltip" title="Master data"><i class="gi gi-home"></i></a></span>
                    <span class="sidebar-header-title">Dasboards</span>
                </li>
                <li>
                    <a href="index"><i class="gi gi-stats sidebar-nav-icon"></i><span
                            class="sidebar-nav-mini-hide">Dashboard 1</span></a>
                </li>
                <li>
                    <a href="#"><i class="gi gi-stats sidebar-nav-icon"></i><span
                            class="sidebar-nav-mini-hide">Dashboard 2</span></a>
                </li>





                <li class="sidebar-header">
                    <span class="sidebar-header-options clearfix"><a href="javascript:void(0)" data-toggle="tooltip" title="Master data"><i class="gi gi-cargo"></i></a></span>
                    <span class="sidebar-header-title">Master Data</span>
                </li>
                <li>
                    <a href="#" class="sidebar-nav-menu"><i
                            class="fa fa-angle-left sidebar-nav-indicator sidebar-nav-mini-hide"></i><i
                            class="gi gi-user sidebar-nav-icon"></i><span
                            class="sidebar-nav-mini-hide">User</span></a>
                    <ul>
                        <li>
                            <a href="users">Users</a>
                        </li>
                        <li>
                            <a href="master_user_group">Master User Group</a>
                        </li>
                        <li>
                            <a href="user_group">User Group</a>
                        </li>

                    </ul>
                </li>
                <li>
                    <a href="organization"><i class="gi gi-group sidebar-nav-icon"></i><span
                            class="sidebar-nav-mini-hide">Organization</span></a>
                </li>

                <li>
                    <a href="active"><i class="gi gi-stats sidebar-nav-icon"></i><span
                            class="sidebar-nav-mini-hide">Active</span></a>
                </li>
                <li>
                    <a href="list_mtc"><i class="gi gi-stats sidebar-nav-icon"></i><span
                            class="sidebar-nav-mini-hide">List Maintenance</span></a>
                </li>
                <li>
                    <a href="history"><i class="gi gi-stats sidebar-nav-icon"></i><span
                            class="sidebar-nav-mini-hide">History Of Maintenance</span></a>
                </li>
                <li>
                    <a href="corrective_maintenance"><i class="gi gi-stats sidebar-nav-icon"></i><span
                            class="sidebar-nav-mini-hide">Corrective Maintenance Ticket</span></a>
                </li>
                <li>
                    <a href="#" class="sidebar-nav-menu"><i
                            class="fa fa-angle-left sidebar-nav-indicator sidebar-nav-mini-hide"></i><i
                            class="gi gi-settings sidebar-nav-icon"></i><span
                            class="sidebar-nav-mini-hide">Settings</span></a>
                    <ul>
                        <li>
                            <a href="#">Notification settings</a>
                        </li>
                        <li>
                            <a href="#">Escalation settings</a>
                        </li>
                    </ul>
                </li>
                <li class="sidebar-header">
                    <span class="sidebar-header-options clearfix"><a href="javascript:void(0)" data-toggle="tooltip" title="Quick menu"><i class="gi gi-package"></i></a></span>
                    <span class="sidebar-header-title">Menu</span>
                </li>
                <li>
                    <a href="/monitoring/rest/authentication/logout"><i class="gi gi-log_out sidebar-nav-icon"></i><span
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

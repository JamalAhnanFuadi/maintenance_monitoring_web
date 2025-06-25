<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

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
                        <span class="sidebar-header-options clearfix"><a href="javascript:void(0)" data-toggle="tooltip"
                                                                         title="Master data"><i class="gi gi-server"></i></a></span>
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
                        <span class="sidebar-header-options clearfix"><a href="javascript:void(0)" data-toggle="tooltip"
                                                                         title="Master data"><i class="gi gi-cargo"></i></a></span>
                    <span class="sidebar-header-title">Master Data</span>
                </li>
                <li>
                    <a href="staff" id="staff-link"><i class="gi gi-user sidebar-nav-icon"></i><span
                            class="sidebar-nav-mini-hide">Staff</span></a>
                </li>
                <li>
                    <a href="customer" id="customer-link"><i class="gi gi-old_man sidebar-nav-icon"></i><span
                            class="sidebar-nav-mini-hide">Customers</span></a>
                </li>
                <li>
                    <a href="departments" id="department-link"><i class="gi gi-building sidebar-nav-icon"></i><span
                            class="sidebar-nav-mini-hide">Departments</span></a>
                </li>
                <li>
                    <a href="service-levels" id="service-level-link"><i
                            class="gi gi-vcard sidebar-nav-icon"></i><span class="sidebar-nav-mini-hide">Service
                                Levels</span></a>
                </li>
                <li>
                    <a href="#" id="product-parent-link" class="sidebar-nav-menu"><i
                            class="fa fa-angle-left sidebar-nav-indicator sidebar-nav-mini-hide"></i><i
                            class="gi gi-sort sidebar-nav-icon"></i><span
                            class="sidebar-nav-mini-hide">Products</span></a>
                    <ul>
                        <li>
                            <a id="product-link" href="products">Products</a>
                        </li>
                        <li>
                            <a id="product-brand-link" href="product-brands">Product Brands</a>
                        </li>
                        <li>
                            <a id="product-category-link" href="product-categories">Product Categories</a>
                        </li>
                    </ul>
                </li>
                <li class="sidebar-header">
                        <span class="sidebar-header-options clearfix"><a href="javascript:void(0)" data-toggle="tooltip"
                                                                         title="Quick menu"><i class="gi gi-package"></i></a></span>
                    <span class="sidebar-header-title">Menu</span>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/rest/authentications/logout"><i
                            class="gi gi-log_out sidebar-nav-icon"></i><span
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
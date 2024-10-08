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
        <div id="landingPage">
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
                                <i class="gi gi-show_big_thumbnails"></i>Welcome,  <b>{{fullname}}</b>
                            </h1>
                        </div>
                    </div>
                    <!-- END Fixed Top Header + Footer Header -->

                    <!-- Dummy Content -->
                    <div class="block full block-alt-noborder">
                        <h3 class="sub-header text-center"><strong>Dummy Content</strong> for layout demostration</h3>
                        <div class="row">
                            <div class="col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2">

                            </div>
                        </div>
                    </div>
                    <!-- END Dummy Content -->
                </div>
                <!-- END Page Content -->
            </div>
            <!-- END Main Container -->
        </div>
    </div>
    <!-- END Page Container -->
</div>
<!-- END Page Wrapper -->

<!-- Scroll to top link, initialized in js/app.js - scrollToTop() -->
<a href="#" id="to-top"><i class="fa fa-angle-double-up"></i></a>

<%@include file="/WEB-INF/pages/js_import.jsp" %>
<script src="${pageContext.request.contextPath}/lib/moment/js/moment.min.js"></script>
<script src="${pageContext.request.contextPath}/lib/bootstrap-notify/js/bootstrap-notify.min.js"></script>
<script src="${pageContext.request.contextPath}/js/customVue.js"></script>
<script type="module" src="${pageContext.request.contextPath}/js/index.js"></script>
</body>
</html>
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
            <h2><strong>List User Log</strong></h2>
            <button type="button" class="btn btn-primary pull-right">Add New</button>
          </div>
          <div class="table-responsive">
            <table id="example-datatable" class="table table-vcenter table-condensed table-bordered">
              <thead>
              <tr>
                <th class="text-center">Log ID</th>
                <th class="text-center"><i class="gi gi-user"></i></th>
                <th class="text-center">User ID</th>
                <th class="text-center">Action</th>
                <th class="text-center">Times</th>
                <th class="text-center">Detail</th>


              </tr>
              </thead>
              <tbody>
              <tr>
                <td class="text-center">1</td>
                <td class="text-center"><img src="img/placeholders/avatars/avatar15.jpg" alt="avatar" class="img-circle"></td>
                <td><a>1234</a></td>
                <td><span class="label label-info">Login</span></td>
                <td>2024-09-16 08:00:00</td>
                <td>succesful login</td>



              </tr>
              <tr>
                <td class="text-center">1</td>
                <td class="text-center"><img src="img/placeholders/avatars/avatar15.jpg" alt="avatar" class="img-circle"></td>
                <td><a>4321</a></td>
                <td><span class="label label-warning">logout</span></td>
                <td>2024-09-16 08:00:00</td>
                <td>succesful logout</td>



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
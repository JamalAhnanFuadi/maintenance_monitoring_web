<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">

    <title>TSI - Change password</title>

    <meta name="description" content="ProUI is a Responsive Bootstrap Admin Template created by pixelcave and published on Themeforest.">
    <meta name="author" content="pixelcave">
    <meta name="robots" content="noindex, nofollow">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,staff-scalable=0">

    <!-- Icons -->
    <!-- The following icons can be replaced with your own, they are used by desktop and mobile browsers -->
    <link rel="apple-touch-icon" sizes="180x180" href="asset/img/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="asset/img/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="asset/img/favicon-16x16.png">
    <link rel="manifest" href="asset/img/site.webmanifest">
    <!-- END Icons -->

    <!-- Stylesheets -->
    <!-- Bootstrap is included in its original form, unaltered -->
    <link rel="stylesheet" href="asset/css/bootstrap.min.css">

    <!-- Related styles of various icon packs and plugins -->
    <link rel="stylesheet" href="asset/css/plugins.css">

    <!-- The main stylesheet of this template. All Bootstrap overwrites are defined in here -->
    <link rel="stylesheet" href="asset/css/main.css">

    <!-- Include a specific file here from css/themes/ folder to alter the default theme of the template -->

    <!-- The themes stylesheet of this template (for using specific theme color in individual elements - must included last) -->
    <link rel="stylesheet" href="asset/css/themes.css">
    <!-- END Stylesheets -->

    <!-- Modernizr (browser feature detection library) -->
    <script src="asset/js/vendor/modernizr.min.js"></script>
</head>
<body>
<!-- Login Full Background -->
<!-- For best results use an image with a resolution of 1280x1280 pixels (prefer a blurred image for smaller file size) -->
<img src="asset/img/placeholders/backgrounds/login_full_bg.jpg" alt="Login Full Background" class="full-bg animation-pulseSlow">
<!-- END Login Full Background -->

<!-- Login Container -->
<div id="login-container" class="animation-fadeIn">
    <!-- Login Title -->
    <div class="login-title text-center" style="display: flex; align-items: left; justify-content: left;">
        <img src="asset/img/logo.png" alt="Company Logo" style="width: 150px; height: auto; object-fit: contain; margin-right: 20px;">
        <h1 style="text-align: left;"><small><strong>Change your password</strong></small></h1>
    </div>
    <!-- END Login Title -->

    <!-- Login Block -->
    <div class="block push-bit">

        <!-- Login Form -->
        <form id="form-login" class="form-horizontal form-bordered form-control-borderless">
            <div class="form-group">
                <div class="col-xs-12">
                    <div class="input-group">
                        <p id="change-password-error" style="color: red; display: none;"></p>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="col-xs-12">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="gi gi-asterisk"></i></span>
                        <input type="password" id="new-password" name="new-password" class="form-control input-lg" placeholder="New password">
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="col-xs-12">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="gi gi-asterisk"></i></span>
                        <input type="password" id="confirm-password" name="confirm-password" class="form-control input-lg" placeholder="Confirm password">
                    </div>
                </div>
            </div>
            <div class="form-group form-actions">
                <div class="col-xs-4">
                </div>
                <div class="col-xs-8 text-right">
                    <button id="change-password-btn" type="submit" class="btn btn-sm btn-primary"></i>Change password</button>
                </div>
            </div>
        </form>
        <!-- END Login Form -->

    </div>
    <!-- END Login Block -->
</div>
<!-- END Login Container -->

<!-- jQuery, Bootstrap.js, jQuery plugins and Custom JS code -->
<script src="asset/js/vendor/jquery.min.js"></script>
<script src="asset/js/vendor/bootstrap.min.js"></script>
<script src="asset/js/plugins.js"></script>
<script src="asset/js/app.js"></script>
<script src="asset/lib/bootstrap-notify/js/bootstrap-notify.min.js"></script>
<script src="asset/js/main.js"></script>

<!-- Load and execute javascript code used only in this page -->
<script src="asset/js/change-password.js"></script>
</body>
</html>
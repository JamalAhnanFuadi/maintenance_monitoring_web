<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">

    <title>TSI - OTP verification</title>

    <meta name="description" content="ProUI is a Responsive Bootstrap Admin Template created by pixelcave and published on Themeforest.">
    <meta name="author" content="pixelcave">
    <meta name="robots" content="noindex, nofollow">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=0">

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
        <h1 style="text-align: left;"><small><strong>OTP verification</strong></small></h1>
    </div>
    <!-- END Login Title -->

    <!-- Login Block -->
    <div class="block push-bit">

        <!-- Login Form -->
        <form id="form-login" class="form-horizontal form-bordered form-control-borderless">
            <div class="form-group">
                <div class="col-xs-12">
                    <div class="input-group">
                        <p> An OTP code has been sent to your email address: <strong  id="email-display"></strong>.</p>
                        <p>Please check your inbox and use the code to complete the verification process. If you don't receive the email within a few minutes, check your spam folder or request a new code.</p>

                        <p id="otp-error" style="color: red; display: none;">Please fill in all OTP fields.</p> <!-- Error message -->

                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="col-xs-12">
                    <div class="input-group otp-input-group">
                        <input type="tel" id="otp1" maxlength="1" class="form-control input-lg otp-box" oninput="moveToNext(this, 'otp2', null)">
                        <input type="tel" id="otp2" maxlength="1" class="form-control input-lg otp-box" oninput="moveToNext(this, 'otp3', 'otp1')">
                        <input type="tel" id="otp3" maxlength="1" class="form-control input-lg otp-box" oninput="moveToNext(this, 'otp4', 'otp2')">
                        <input type="tel" id="otp4" maxlength="1" class="form-control input-lg otp-box" oninput="moveToNext(this, 'otp5', 'otp3')">
                        <input type="tel" id="otp5" maxlength="1" class="form-control input-lg otp-box" oninput="moveToNext(this, 'otp6', 'otp4')">
                        <input type="tel" id="otp6" maxlength="1" class="form-control input-lg otp-box" oninput="moveToNext(this, null, 'otp5')">
                    </div>
                </div>
            </div>
            <div class="form-group form-actions">
                <div class="col-xs-4">
                </div>
                <div class="col-xs-8 text-right">
                    <button id="otp-verify-btn" type="submit" class="btn btn-sm btn-primary"></i>Verify</button>
                </div>
            </div>
            <div class="form-group">
                <div class="col-xs-12 text-center">
                    <a href="" id="resend-otp"><small>Resend OTP</small></a>
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
<script src="asset/js/reset-password-otp.js"></script>
</body>
</html>
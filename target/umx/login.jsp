<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <%@include file="WEB-INF/pages/css_import.jsp"%>
    <link href="css/umx.css" rel="stylesheet">
    <link href="css/login.css" rel="stylesheet">

    <title>UMX</title>
</head>

<body class="nav-md">

    <!-- Loading Screen -->
    <div class="loader"></div>

    <div id="vue">
        <div class="login_wrapper" v-if="authenticated == false">
            <div class="form login_form" @keyup.enter="login()">
                <section id="wrapper" class="login-register">
                    <div id="login-header" class="text-center">
                        <div class="row">
                            <div class="pull-left col-xs-4">
                                <img src="images/logo.png" alt="logo">
                            </div>
                            <div id="app-name" class="col-xs-4">
                                <h1>UMX</h1>
                            </div>
                            <div id="user-logo" class="pull-right col-xs-4">
                                <img src="images/kbtg-logo.png" alt="logo">
                            </div>
                        </div>
                    </div>
                    <div>
                        <div class="form-group">
                            <input ref="username" v-model="username" name="username" class="form-control"
                                placeholder="Username" required v-focus />
                        </div>
                        <div class="form-group">
                            <input ref="password" type="password" v-model="password" name="password"
                                class="form-control" placeholder="Password" required />
                        </div>
                        <div>
                            <button class="btn btn-lg btn-block btn-login" type="submit" @click="login()">Login</button>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                    <div class="separator">
                        <div class="clearfix"></div>
                        <div class="pull-right">v2.5</div>
                    </div>
                </section>
            </div>
        </div>
    </div>

    <!-- javascript imports -->
    <%@include file="WEB-INF/pages/js_import.jsp"%>
    <script src="lib/moment/js/moment.min.js"></script>
    <script src="lib/bootstrap-notify/js/bootstrap-notify.min.js"></script>
    <script src="js/customVue.js"></script>
    <script src="js/login.js"></script>

    <!-- Automatically provides/replaces `Promise` if missing or broken. -->
    <script src="https://cdn.jsdelivr.net/npm/es6-promise@4/dist/es6-promise.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/es6-promise@4/dist/es6-promise.auto.js"></script>

</body>

</html>
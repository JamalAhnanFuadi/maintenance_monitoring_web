<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <%@include file="/WEB-INF/pages/css_import.jsp" %>
    <title>Login</title>
</head>

<body>
<!-- Login Alternative Row -->
<div id="vue" class="container">
    <div class="row">
        <div class="text-center">
            <!-- Login Container -->
            <div id="login-container">
                <!-- Login Title -->
                <div class="login-title text-center">
                    <h1><strong>Monitoring and Maintenance</strong></h1>
                    <h1>Dashboard</h1>
                </div>
                <!-- END Login Title -->

                <!-- Login Block -->
                <div class="block push-bit">
                    <!-- Login Form -->
                    <div id="form-login" class="form-horizontal" >
                        <div class="form-group">
                            <div class="col-xs-12">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="gi gi-user"></i></span>
                                    <input ref="username" v-model="username" name="username" class="form-control"
                                           placeholder="Username" required v-focus />
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-xs-12">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="gi gi-asterisk"></i></span>
                                    <input ref="password" type="password" v-model="password" name="password"
                                           class="form-control" placeholder="Password" required />
                                </div>
                            </div>
                        </div>
                        <div class="form-group form-actions">
                            <div class="col-xs-8 text-right">
                                <button type="submit" class="btn btn-sm btn-primary" @click="login()"></i> Login to
                                    Dashboard</button>
                            </div>
                        </div>
                    </div>
                    <!-- END Login Form -->
                </div>
                <!-- END Login Block -->
            </div>
            <!-- END Login Container -->
        </div>
    </div>
</div>
<!-- END Login Alternative Row -->

<!-- javascript imports -->
<%@include file="/WEB-INF/pages/js_import.jsp"%>
<script src="${pageContext.request.contextPath}/lib/moment/js/moment.min.js"></script>
<script src="${pageContext.request.contextPath}/lib/bootstrap-notify/js/bootstrap-notify.min.js"></script>
<script src="${pageContext.request.contextPath}/js/customVue.js"></script>
<script type="module" src="${pageContext.request.contextPath}/js/login.js"></script>

<!-- Automatically provides/replaces `Promise` if missing or broken. -->
<script src="https://cdn.jsdelivr.net/npm/es6-promise@4/dist/es6-promise.js"></script>
<script src="https://cdn.jsdelivr.net/npm/es6-promise@4/dist/es6-promise.auto.js"></script>
</body>

</html>
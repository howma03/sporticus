<!DOCTYPE html>

<html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<link rel="stylesheet" type="text/css" media="all" href="<spring:url value="/resources/styles/login2.css" />"/>

<jsp:include page="../header.jsp"/>

<spring:url htmlEscape="true" var="urlRegistration" value="/registration"/>
<spring:url htmlEscape="true" var="urlPasswordReset" value="/password/reset"/>

<body>

<nav class="navbar navbar-default navbar-inverse" role="navigation">
    <div class="container-fluid">

        <jsp:include page="../branding-navbar-header.jsp"/>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

            <ul class="nav navbar-nav navbar-right">
                <li><a href="${urlRegistration}"><b>Join Us</b></a>
                </li>
                <li><p class="navbar-text">Already have an account?</p></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><b>Login</b> <span class="caret"></span></a>
                    <ul id="login-dp" class="dropdown-menu">
                        <li>
                            <div class="row">
                                <div class="col-md-12">
                                    <div hidden="true">
                                        Login via
                                        <div class="social-buttons">
                                            <a href="#" class="btn btn-fb"><i class="fa fa-facebook"></i>
                                                Facebook</a>
                                            <a href="#" class="btn btn-tw"><i class="fa fa-twitter"></i>
                                                Twitter</a>
                                        </div>
                                        or
                                    </div>
                                    <form:form id="myLoginForm" method="POST" autocomplete="off" action="${urlLogin}"
                                               always-use-default-target="true">
                                        <div class="form-group">
                                            <label class="sr-only" for="username">Email address</label>
                                            <input type="text" style="height: 36px" class="form-control"
                                                   id="username" name="username" value="" required="true"
                                                   autocomplete="false"
                                                   title="Please enter you email address"
                                                   placeholder="example@gmail.com">
                                            <span class="help-block"></span>
                                        </div>
                                        <div class="form-group">
                                            <label class="sr-only" for="password">Password</label>
                                            <input type="password" style="height: 36px" class="form-control"
                                                   id="password" name="password" value="" required="true"
                                                   autocomplete="false"
                                                   title="Please enter your password">
                                            <span class="help-block"></span>
                                        </div>
                                        <div>
                                            <div class="help-block text-right">
                                                <a href="${urlPasswordReset}">Forget your password ?</a>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <button type="submit" class="btn btn-primary btn-block">Login</button>
                                        </div>
                                        <div class="checkbox">
                                            <label>
                                                <input type="checkbox"> keep me logged-in
                                            </label>
                                        </div>
                                    </form:form>
                                </div>
                                <div class="bottom text-center">
                                    New here ? <a href="${urlRegistration}"><b>Join Us</b></a>
                                </div>
                            </div>
                        </li>
                    </ul>
                </li>
            </ul>
            <script>
                $("#login-dp").show();
            </script>
        </div>
    </div>
</nav>
</body>
</html>



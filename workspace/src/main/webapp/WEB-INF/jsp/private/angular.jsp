<html lang="en">

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:include page="../header.jsp"/>

<link rel="stylesheet"
      href="<spring:url value="/resources/styles/login.css" />"
      type="text/css" media="all"/>

<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.1/angular.min.js"></script>

<spring:url htmlEscape="true" var="urlLogout" value="/logout"/>
<spring:url htmlEscape="true" var="urlResetPassword" value="/password/reset"/>

<body>
<div class="wrap">
    <div class="stacked">

        <h2>My-Tasks</h2>

        <form>

            <div class="well">
                ${message}
            </div>

            <br/>

            <%--<div ng-app="">--%>
            <%--<label>Name:</label>--%>
            <%--<input type="text" ng-model="yourName" placeholder="Enter a name here">--%>
            <%--<hr>--%>
            <%--<h1>Hello {{yourName}}!</h1>--%>
            <%--</div>--%>

            <br/>

            <div class="well">

                <h3>This is my first Angular Application!</h3>

                <br/>

                <div ng-app="myApp" ng-controller="myCtrl">

                    <label class="control-label">First Name: </label>
                    <input type="text" ng-model="firstName" class="form-control" style="height: 36px"><br>
                    <label class="control-label">Last Name: </label>
                    <input type="text" ng-model="lastName" class="form-control" style="height: 36px"><br>
                    <hr/>
                    <b>Full Name: {{firstName + " " + lastName}}</b>

                    <script>
                        var app = angular.module('myApp', []);
                        app.controller('myCtrl', function ($scope) {
                            $scope.firstName = "Mark";
                            $scope.lastName = "Howell";
                        });
                    </script>
                </div>

            </div>

            <hr/>

            <br>

            <div class="btn-group">
                <button class="btn btn-success" type="button"
                        onClick="window.location.href='${urlLogout}'">Logout
                </button>
                <button class="btn btn-danger" type="button"
                        onClick="window.location.href='${urlResetPassword}'">Reset Password
                </button>
            </div>

        </form>

    </div>
</div>
</body>
</html>


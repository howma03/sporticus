<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<spring:url htmlEscape="true" var="urlServiceBase" value="/api"/>

<html lang="en">

<jsp:include page="../header.jsp"/>

<body>

<jsp:include page="navigation.jsp"/>
<jsp:include page="carousel.jsp"/>

<spring:url htmlEscape="true" var="urlGet" value="/api/get"/>

<div class="container">

    <div class="row">
        <div class="col-lg-12">
            <div class="wrap">
                <div class="stacked">

                    <h2>Sporticus</h2>

                    <form>

                        <h3>Request a Report</h3>

                        <p>You should be able to define the things you are interested and then request a report</p>

                        <hr/>

                        <div class="btn-group">
                            <button class="btn btn-primary" type="button"
                                    onClick="window.location.href='${urlLogin}'">Request Report</button>
                            <button class="btn btn-primary" type="button"
                                    onClick="window.location.href='${urlGet}'">Get Data</button>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>

    <hr>

    <jsp:include page="../footer.jsp"/>

</div>

</body>

    <script>




    </script>

</html>

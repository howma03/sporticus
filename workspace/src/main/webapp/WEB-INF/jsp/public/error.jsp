<html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:include page="../header.jsp" />

<link rel="stylesheet"
      href="<spring:url value="/resources/styles/login.css" />"
      type="text/css" media="all" />

<body>
<div class="wrap">
    <div class="stacked">

        <H2>Error</H2>

        <form>

           <div class="alert alert-error">

            <label>You have attempted to access material that is not available for your role.</label>

            <hr/>

            <label>This event has been recorded and may be investigated.</label>

            <hr/>

            <label>Please return to the previous screen</label>

        </form>

        </div>

    </div>
</div>
</body>

</html>



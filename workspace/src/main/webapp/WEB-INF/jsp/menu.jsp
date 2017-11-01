<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:url htmlEscape="true" var="urlLogin" value="/login"/>
<spring:url htmlEscape="true" var="urlLogout" value="/logMeOut"/>
<spring:url htmlEscape="true" var="urlRegistration" value="/registration"/>
<spring:url htmlEscape="true" var="urlPasswordReset" value="/password/reset"/>
<spring:url htmlEscape="true" var="urlAdmin" value="/admin"/>
<spring:url htmlEscape="true" var="urlWelcome" value="/welcome"/>
<spring:url htmlEscape="true" var="urlChat" value="/chat"/>

<form action="${urlLogout}" method="get">
    ${message}

    <div class="btn-group">
        <button type="button" class="btn" type="button btn-primary"
                onClick="window.location.href='${urlAdmin}'">Admin
        </button>
        <button type="button" class="btn" type="button btn-primary"
                onClick="window.location.href='${urlWelcome}'">Welcome
        </button>
        <button type="button" class="btn" type="button btn-default"
                onClick="window.location.href='${urlChat}'">Chat
        </button>
        <button type="submit" class="btn btn-danger">Logout</button>
    </div>

    <hr/>

</form>
<html lang="en">

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script src="webjars/jquery/3.0.0-alpha1/jquery.js"></script>
<script src="webjars/sockjs-client/1.0.0/sockjs.js"></script>
<script src="webjars/stomp-websocket/2.3.3/stomp.js"></script>
<script src="/resources/scripts/chat.js"></script>

<jsp:include page="../header.jsp"/>

<link rel="stylesheet"
      href="<spring:url value="/resources/styles/login.css" />"
      type="text/css" media="all"/>

<body>
<div class="wrap">
    <div class="stacked">

        <h2>My-Tasks (Chat)</h2>

        <jsp:include page="../menu.jsp"/>

        <div class="well" id="chat">

            <form onsubmit="sendMessage(); return false;">

                <label>
                    Message:
                    <input type="text" id="messageInput" class="form-control" style="height: 36px"/>

                </label>

                <div class="btn-group">
                    <button class="btn btn-default" type="submit">Send</button>
                    <button class="btn btn-primary" type="button" onClick="clearMessages(); return false;">Clear
                    </button>
                </div>

            </form>

            <div class="well" id="messages"/>

        </div>

    </div>

</div>
</body>
</html>


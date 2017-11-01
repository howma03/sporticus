<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">

    <meta http-equiv="expires" content="now">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="shortcut icon" type="image/png" href="<spring:url value="/resources/images/favicon.ico" />"/>

    <!-- Scripts -->

    <script src="https://code.jquery.com/jquery-2.2.4.js"
            integrity="sha256-iT6Q9iMJYuQiMWNd9lDyBUStIq/8PuOW33aOqmvFpqI="
            crossorigin="anonymous"></script>

    <!-- Custom CSS -->

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- <script type='text/javascript' src="<spring:url value="/resources/scripts/jquery.min.js" />"></script> -->
    <!-- <script type='text/javascript' src="<spring:url value="/resources/scripts/jquery.validate.min2.js" />"></script> -->
    <script type='text/javascript' src="<spring:url value="/resources/scripts/bootstrap.min.js" />"></script>
    <script type='text/javascript' src="<spring:url value="/resources/scripts/bootstrap-table-1.11.1.js" />"></script>
    <script type='text/javascript' src="<spring:url value="/resources/scripts/Sporticus.js" />"></script>

    <!-- Styles -->

    <link type='text/css' media="all" rel='stylesheet'
          href='https://fonts.googleapis.com/css?family=Open+Sans:400,700,600'/>
    <link type="text/css" media="all" rel="stylesheet" href="<spring:url value="/resources/styles/bootstrap.css" />"/>
    <link type="text/css" media="all" rel="stylesheet"
          href="<spring:url value="/resources/styles/bootstrap-table-1.11.1.css" />"/>
    <link type="text/css" media="all" rel="stylesheet" href="<spring:url value="/resources/styles/main.css" />"/>
    <link type="text/css" media="all" rel="stylesheet" href="<spring:url value="/resources/styles/half-slider.css" />">

    <!-- DataTables -->

    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.css">
    <script type="text/javascript" charset="utf8"
            src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.js"></script>

    <spring:url var="cookiePolicyUrl" value="/cookiePolicy"/>

    <title>Sporticus</title>

    <spring:url htmlEscape="true" var="urlServiceBase" value="/api"/>
    <spring:url htmlEscape="true" var="urlLanding" value="/landing"/>

    <script type="text/javascript" language="javascript" class="init">
        $(document).ready(function () {
	        Sporticus.init("${urlServiceBase}", "hello", "${urlLanding}");
        });
    </script>

</head>


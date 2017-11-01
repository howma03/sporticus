<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Half Page Image Background Carousel Header -->
<header id="myCarousel" class="carousel slide">
    <!-- Indicators -->
    <ol class="carousel-indicators">
        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
        <li data-target="#myCarousel" data-slide-to="1"></li>
        <li data-target="#myCarousel" data-slide-to="2"></li>
        <li data-target="#myCarousel" data-slide-to="3"></li>
        <li data-target="#myCarousel" data-slide-to="4"></li>
    </ol>

    <!-- Wrapper for Slides -->
    <div class="carousel-inner">
        <div class="item active">
            <!-- Set the first background image using inline CSS below. -->
            <div class="fill" style="background-image:url('<spring:url value="/resources/images/carousel1.jpg" />');"></div>
            <div class="carousel-caption">
                <h2>Gather concepts, facilitate discussions, and create diagrams and mockups for review</h2>
            </div>
        </div>
        <div class="item">
            <!-- Set the second background image using inline CSS below. -->
            <div class="fill" style="background-image:url('<spring:url value="/resources/images/carousel2.jpg" />');"></div>
            <div class="carousel-caption">
                <h2>Send requirements directly to development and test tools.</h2>
            </div>
        </div>
        <div class="item">
            <!-- Set the third background image using inline CSS below. -->
            <div class="fill" style="background-image:url('<spring:url value="/resources/images/carousel3.jpg" />');"></div>
            <div class="carousel-caption">
                <h2>View consolidated requirement status across all delivery assets.</h2>
            </div>
        </div>
        <div class="item">
            <!-- Set the third background image using inline CSS below. -->
            <div class="fill" style="background-image:url('<spring:url value="/resources/images/carousel4.jpg" />');"></div>
            <div class="carousel-caption">
                <h2>Track requirement version changes to maintain control and auditability.</h2>
            </div>
        </div>
        <div class="item">
            <!-- Set the third background image using inline CSS below. -->
            <div class="fill" style="background-image:url('<spring:url value="/resources/images/carousel5.jpg" />');"></div>
            <div class="carousel-caption">
                <h2>Track requirement version changes to maintain control and auditability.</h2>
            </div>
        </div>
    </div>

    <!-- Controls -->
    <a class="left carousel-control" href="#myCarousel" data-slide="prev">
        <span class="icon-prev"></span>
    </a>
    <a class="right carousel-control" href="#myCarousel" data-slide="next">
        <span class="icon-next"></span>
    </a>

    <!-- Script to Activate the Carousel -->
    <script type="text/javascript" language="javascript" class="init">
        $('.carousel').carousel({
            interval: 30000 //changes the speed
        });
    </script>


</header>
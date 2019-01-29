<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${pageContext.response.locale}"/>
<fmt:setBundle basename="bundle.errorPage"/>
<html>
    <head>
        <title><fmt:message key="form.title"/></title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="alert alert-danger jumbotron">
            <h5>
                <fmt:message key="errorMessage"/>
            </h5>
            <a href="/course/">
                <fmt:bundle basename = "bundle.common">
                    <fmt:message key="button.allCourses"/>
                </fmt:bundle>
            </a>
        </div>
        <div>
            <div>
                ${userMessage}
            </div>
            <button data-toggle="collapse" data-target="#details"><fmt:message key="details"/></button>
            <div id="details" class="collapse">
                <div>
                    ${message}
                </div>
                <button data-toggle="collapse" data-target="#moreDetails"><fmt:message key="moreDetails"/></button>
                <div id="moreDetails" class="collapse">
                    ${stackTrace}
                </div>
            </div>
        </div>
    </body>
</html>

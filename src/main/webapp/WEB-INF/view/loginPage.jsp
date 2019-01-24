<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<fmt:setLocale value="${pageContext.request.locale}"/>
<fmt:setBundle basename="bundle.login"/>
<%
    String changeLanguageLink = "/authenticator/login";
%>
<html>
    <head>
        <title>Login to facultative</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
        <script>
            $(document).ready(function(){
                $('[data-toggle="allCourses"]').tooltip();
            });
        </script>
    </head>
    <body>
        <div class="header jumbotron">
            <h2><fmt:message key="welcome"/></h2>
        </div>
        <nav class="navbar navbar-expand-sm bg-light">
            <ul class="navbar-nav">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                        language
                    </a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="<%out.print(changeLanguageLink);%>?locale=ru_RU">Русский</a>
                        <a class="dropdown-item" href="<%out.print(changeLanguageLink);%>?locale=en_US">English</a>
                        <a class="dropdown-item" href="<%out.print(changeLanguageLink);%>?locale=es_ES">Español</a>
                    </div>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/course/"
                       data-toggle="allCourses" data-placement="top" title="Back to the main facultative page!">
                        all courses
                    </a>
                </li>
                <li class="nav-item">
                    <sec:authorize access="isAuthenticated()">
                        <a class="nav-link" href="/user/profile">My Profile</a>
                    </sec:authorize>
                </li>
                <sec:authorize access="isAuthenticated()">
                    <form class="form-inline" method="post" action="/logout">
                        <input class="btn btn-warning" type="submit" value="Logout"/>
                        <sec:csrfInput/>
                    </form>
                </sec:authorize>
            </ul>
        </nav>
        <div class="row">
            <div class="col-sm-1"></div>
            <form class="col form" method="post" action="/login">
                <div class="form-group row">
                    <label for="email" class="col-sm-2 col-form-label"><fmt:message key="email"/>:</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control-plaintext" id="email" placeholder="yourEmail@company.com">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="password" class="col-sm-2 col-form-label"><fmt:message key="password"/>:</label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" id="password" placeholder="Password">
                    </div>
                </div>
                <button type="submit" class="btn btn-primary"><fmt:message key="button.loginIn"/></button>
                <sec:csrfInput/>
            </form>
            <div class="col-sm-3"></div>
        </div>
        <div class="row">
            <div class="col-sm-1"></div>
            <div class="col">
                <br>
                <fmt:message key="noRegistration"/> <br>
                <a class="btn btn-info" href="/authenticator/registration">
                    <fmt:message key="button.register"/>
                </a>
            </div>
            <div class="col-sm-3"></div>
        </div>


    </body>
</html>
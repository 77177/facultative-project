<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${pageContext.response.locale}"/>
<fmt:setBundle basename="bundle.common"/>
<%
    String changeLanguageLink = "/authenticator/registration/";
    Object errorMessageObject = request.getAttribute("error");
%>
<html>
    <head>
        <title>
            <fmt:bundle basename = "bundle.register">
                <fmt:message key="title"/>
            </fmt:bundle>
        </title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
        <script>
            $(document).ready(function(){
                $('[data-toggle="allCourses"]').tooltip();
            });
        </script>
        <script>
            $(document).ready(function(){
                $('[data-toggle="login"]').tooltip();
            });
        </script>
        <script>
            $(document).ready(function(){
                $('.toast').toast('show');
            });
        </script>
    </head>
    <body>
        <div class="jumbotron">
            <h2>
                <fmt:bundle basename = "bundle.register">
                    <fmt:message key="welcome"/>
                </fmt:bundle>
            </h2>
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
                        <fmt:message key="button.allCourses"/>
                    </a>
                </li>
                <li class="nav-item">
                    <sec:authorize access="isAuthenticated()">
                        <a class="nav-link" href="/user/profile">
                            <fmt:message key="message.myProfile"/>
                        </a>
                    </sec:authorize>
                </li>
                <li class="nav-item">
                    <sec:authorize access="!isAuthenticated()">
                        <a class="nav-link" href="/authenticator/login">
                            <fmt:message key="button.login"/>
                        </a>
                    </sec:authorize>
                </li>
                <sec:authorize access="isAuthenticated()">
                    <form class="form-inline justify-content-end" method="post" action="/logout">
                        <button type="submit" class="btn btn-warning">
                            <fmt:message key="button.logout"/>
                        </button>
                        <sec:csrfInput/>
                    </form>
                </sec:authorize>
            </ul>
        </nav>
        <div class="row">
            <div class="col-sm-1"></div>
            <%
            if(errorMessageObject != null) {
            %>
            <div class="toast col" data-autohide="false">
                <div class="toast-header">
                    <strong class="mr-auto text-primary">Account was not created!</strong>
                    <button type="button" class="ml-2 mb-1 close" data-dismiss="toast">&times;</button>
                </div>
                <div class="toast-body">
                    <div class="alert-danger">
                        ${error}
                    </div>
                </div>
            </div>
            <% } %>
            <div class="col-sm-1"></div>
        </div>
        <div class="row">
            <div class="col-sm-1"></div>
            <form class="col form" method="post" action="/authenticator/registration">
                <div class="form-group row">
                    <label for="firstName" class="col-sm-2 col-form-label"><fmt:message key="form.firstName"/>:</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control-plaintext" id="firstName" name="firstName"
                               placeholder="first name" required>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="lastName" class="col-sm-2 col-form-label"><fmt:message key="form.lastName"/>:</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control-plaintext" id="lastName" name="lastName"
                               placeholder="last name" required>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="email" class="col-sm-2 col-form-label"><fmt:message key="form.email"/>:</label>
                    <div class="col-sm-10">
                        <input type="email" class="form-control-plaintext" name="email" id="email"
                               placeholder="yourEmail@company.com" required pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="password" class="col-sm-2 col-form-label"><fmt:message key="form.password"/>:</label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" id="password" name="password"
                               placeholder="Password" required>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="position" class="col-sm-2 col-form-label"><fmt:message key="form.position"/>:</label>
                    <select class="form-control" id="position" name="position" required>
                        <fmt:bundle basename = "bundle.register">
                            <option><fmt:message key="option.student"/></option>
                            <option><fmt:message key="option.tutor"/></option>
                        </fmt:bundle>
                    </select>
                </div>
                <input type="hidden" name="registration" value="true"/>
                <sec:csrfInput/>
                <button type="submit" class="btn btn-primary"><fmt:message key="button.submit"/></button>
            </form>
            <div class="col-sm-3"></div>
        </div>
    </body>
</html>
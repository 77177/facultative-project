<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.epam.lab.group1.facultative.security.SecurityContextUser" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${pageContext.response.locale}"/>
<fmt:setBundle basename="bundle.createCourse"/>
<%

    Object errorMessageObject = request.getAttribute("errorMessage");
    String changeLanguageLink = null;
%>
<sec:authorize access="isAuthenticated()">
    <%
        changeLanguageLink = "/course/action/create/";
    %>
</sec:authorize>
<html>
    <head>
        <title><fmt:message key="form.title"/></title>
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
            <h2><fmt:message key="form.title"/></h2>
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

        <sec:authorize access="isAuthenticated()">
            <div class="row">
                <div class="col-sm-1"></div>
                <div class="col">
            <%
            if(errorMessageObject != null) {
            %>
                    <div class="toast col" data-autohide="false">
                        <div class="toast-header">
                            <strong class="mr-auto text-primary">Course creation!</strong>
                            <button type="button" class="ml-2 mb-1 close" data-dismiss="toast">&times;</button>
                        </div>
                        <div class="toast-body">
                            <div class="alert-danger">
                                    ${errorMessage}
                            </div>
                        </div>
                    </div>
            <% } %>
                    <form method="post" action="/course/action/create/">
                        <div class="form-group row">
                            <label for="name" class="col-sm-2 col-form-label"><fmt:message key="courseName"/>:</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control-plaintext" id="name" name="name"
                                       placeholder="course title" minlength="1" required>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="startingDate" class="col-sm-2 col-form-label"><fmt:message key="form.startDate"/>:</label>
                            <div class="col-sm-2">
                                <input type="date" class="form-control-plaintext" id="startingDate" name="startingDate"
                                       required>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="finishDate" class="col-sm-2 col-form-label"><fmt:message key="form.finishDate"/>:</label>
                            <div class="col-sm-2">
                                <input type="date" class="form-control-plaintext" id="finishDate" name="finishingDate"
                                       required>
                            </div>
                        </div>
                        <div class="form-check">
                            <input type="radio" class="form-check-input" id="active" name="active" value="true"
                                   required>
                            <label class="form-check-label" for="active"><fmt:message key="sdfsfd"/></label>
                        </div>
                        <div class="form-check">
                            <input type="radio" class="form-check-input" id="closed" name="active" value="false"
                                   required>
                            <label class="form-check-label" for="closed">
                                <fmt:message key="form.closed"/>
                            </label>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </form>
                </div>
                <div class="col-sm-1"></div>
            </div>

        </sec:authorize>
    </body>
</html>
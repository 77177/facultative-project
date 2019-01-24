<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${pageContext.response.locale}"/>
<fmt:setBundle basename="bundle.course"/>
<%
    List<Course> courseList = (List) request.getAttribute("courseList");
    int pageNumber = (int) request.getAttribute("pageNumber");
    int pageSize = 10;

    String changeLanguageLink = "/course/";
%>
<html>
    <head>
        <title><fmt:message key="title"/></title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="header jumbotron">
            <h1>
                <fmt:message key="allCourses"/>
            </h1>
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
                    <sec:authorize access="!isAuthenticated()">
                        <a class="nav-link" href="/authenticator/login">Login</a>
                    </sec:authorize>
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
        <%
            if (!courseList.isEmpty()) {
            %>
        <div class="row">
            <div class="col-sm-1"></div>
            <table class="table-striped table-hover col">
                <thead>
                    <tr>
                        <th><fmt:message key="title"/></th>
                        <th><fmt:message key="startDate"/></th>
                        <th><fmt:message key="finishDate"/></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (Course course : courseList) {
                    %>
                    <tr>
                        <td><%= course.getName()%></td>
                        <td><%= course.getStartingDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))%></td>
                        <td><%= course.getFinishingDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))%></td>
                        <td><a href="/course/<%=course.getId()%>">info</a></td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>

            </table>
            <div class="col-sm-1"></div>
        </div>
        <br>
        <div class="row">
            <div class="col-sm-1"></div>
            <ul class="pagination">
                <%
                    if (pageNumber > 0) {
                %>
                <li class="page-item">
                    <a class="page-link" href="/course/?page=${pageNumber - 1}">previous 10</a>
                </li>
                <%
                    }
                %>
                <li class="page-item">
                    <a class="page-link" href="#"> ... </a>
                </li>
                <%
                    if (courseList.size() == pageSize) {
                %>
                <li class="page-item">
                    <a class="page-link" href="/course/?page=${pageNumber + 1}">next 10</a>
                </li>
                <%
                    }
                %>
            </ul>
        </div>

            <%
            } else {
            %>
            <p><fmt:message key="noCourses"/></p>
            <%
            }
        %>

    </body>
</html>
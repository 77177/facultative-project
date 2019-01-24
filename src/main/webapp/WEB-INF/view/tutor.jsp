<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="com.epam.lab.group1.facultative.security.SecurityContextUser" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="com.epam.lab.group1.facultative.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${pageContext.response.locale}"/>
<fmt:setBundle basename="bundle.tutor"/>
<%
    SecurityContextUser principal = null;
    Object courseListObject = request.getAttribute("courseList");
    List<Course> courseList = courseListObject != null ? (List) courseListObject : Collections.emptyList();
    Object userObject = request.getAttribute("user");
    User user = userObject != null ? (User) userObject : null;

    int pageNumber = (int) request.getAttribute("pageNumber");
    int pageSize = 10;

    String changeLanguageLink = "/user/profile";
%>
<sec:authorize access="isAuthenticated()">
    <%
        principal = (SecurityContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    %>
</sec:authorize>
<html>
    <head>
        <title><fmt:message key="title"/></title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="jumbotron">
            <h2><fmt:message key="title"/></h2>
            <sec:authorize access="isAuthenticated()">
                <h4>
                    <fmt:message key="greeting"/>, <%=user.getFirstName() + " " + user.getLastName()%>
                </h4>
            </sec:authorize>
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
            <div class="col">
                <a class="btn btn-success" href="/course/action/create/<%=principal.getUserId()%>">
                    <fmt:message key="createNewCourse"/>
                </a>
                <h5><fmt:message key="myCourses"/>:</h5>
                <table class="table-striped table-hover col">
                    <caption><fmt:message key="myCourses"/></caption>
                    <thead>
                    <tr>
                        <th><fmt:message key="titleTitle"/></th>
                        <th><fmt:message key="start"/></th>
                        <th><fmt:message key="finish"/></th>
                        <th><fmt:message key="active"/></th>
                        <th colspan="3"><fmt:message key="options"/></th>
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
                        <td><%= course.isActive()%></td>
                        <td><a href="/course/<%=course.getId()%>">info</a></td>
                        <td>
                            <a href="/course/<%=course.getId()%>/action/edit/<%=course.getTutorId()%>/">
                                <fmt:message key="edit"/>
                            </a>
                        </td>
                        <td>
                            <a href="/course/action/delete/<%=course.getId()%>/">
                                <fmt:message key="delete"/>
                            </a>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                    </tbody>
                </table>
                <ul class="pagination">
                    <%
                        if (pageNumber > 0) {
                    %>
                    <li class="page-item">
                        <a class="page-link" href="/user/profile?page=${pageNumber - 1}">previous 10</a>
                    </li>
                    <%
                        }
                        if (courseList.size() == pageSize) {
                    %>
                    <li class="page-item">
                        <a class="page-link" href="#"> ... </a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="/user/profile?page=${pageNumber + 1}">next 10</a>
                    </li>
                    <%
                        }
                    %>
                </ul>
            </div>
            <div class="col-sm-1"></div>
        </div>
    </body>
</html>
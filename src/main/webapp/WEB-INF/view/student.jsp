<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.epam.lab.group1.facultative.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page import="java.util.Collections" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${pageContext.response.locale}"/>
<fmt:setBundle basename="bundle.student"/>
<%
    Object userObject = request.getAttribute("user");
    User user = userObject != null ? (User) userObject : null;

    Object courseListObject = request.getAttribute("courseList");
    List<Course> courseList = courseListObject != null ? (List) courseListObject : Collections.emptyList();

    int pageNumber = (int) request.getAttribute("pageNumber");
    int pageSize = 10;
%>
<html>
<head>
    <title><fmt:message key="title"/></title>
    <style> <%@include file="/theme/css/main.css"%> </style>
    <style> <%@include file="/theme/css/table.css"%> </style>
    <%--<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">--%>
</head>
    <body>
        <div class="header">
            <h2><fmt:message key="title"/></h2>
        </div>
        <form action="/course" method="get">
            <div class="input-group">
                <center> <button><fmt:message key="backToCourses"/></button> </center>
            </div>
        </form>

        <form action="/user/profile" method="get">
            <select name="locale">
                <option value="ru_RU">Русский</option>
                <option value="en_US">English</option>
                <option value="es_ES">Español</option>
            </select>
            <input type="submit" value="change language"/>
        </form>

        <sec:authorize access="isAuthenticated()">
            <div class="header">
                <h3>Hello, <%=user.getFirstName() + " " + user.getLastName()%></h3>
            </div>
            <form method="post" action="/logout">
                <sec:csrfInput/>
                <div class="input-group">
                    <center> <button> <fmt:message key="logout"/> </button> </center>
                </div>
            </form>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
            <form action="/authenticator/login">
                <center> <button> Login </button> </center>
            </form>
        </sec:authorize>
        <%
            if (courseList.isEmpty()) {
                %>
                <form>
                    <center> <span><fmt:message key="noCoursesMessage"/></span> </center>
                </form>
                <%
                } else {
                %>
                <form> <center> <fmt:message key="yourCourses"/>: </center> </form>
                <br>
                <table>
                    <tr>
                        <th><fmt:message key="courseName"/></th>
                        <th><fmt:message key="start"/></th>
                        <th><fmt:message key="finish"/></th>
                        <th><fmt:message key="feedback"/></th>
                    </tr>
                    <%
                        for (Course course : courseList) {
                        %>
                        <tr>
                            <td><% out.println(course.getName());%></td>
                            <td><% out.println(course.getStartingDate());%></td>
                            <td><% out.println(course.getFinishingDate());%></td>
                            <td><a href="/course/<%=course.getId()%>">course info</a></td>
                            <td><a href="/feedback/user/<%=user.getId()%>/course/<%=course.getId()%>/"><fmt:message key="seeFeedback"/></a></td>
                        </tr>
                        <%
                        }
                    %>
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
                    %>
                    <li class="page-item">
                        <a class="page-link" href="#"> ... </a>
                    </li>
                    <%
                        if (courseList.size() == pageSize) {
                            %>
                            <li class="page-item">
                                <a class="page-link" href="/user/profile?page=${pageNumber + 1}">next 10</a>
                            </li>
                            <%
                        }
                    %>
                </ul>
                <%
                }
        %>
    </body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="com.epam.lab.group1.facultative.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page import="java.util.Collections" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <title>Students</title>
        <style> <%@include file="/theme/css/main.css"%> </style>
        <style> <%@include file="/theme/css/table.css"%> </style>
        <%--<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">--%>
    </head>
    <body>
        <div class="header"> <h2>Go back to all courses.</h2> </div>
            <form action="/course">
                <div class="input-group">
                    <center> <button>All courses</button> </center>
                </div>
            </form>
        <div class="header">
        <sec:authorize access="isAuthenticated()">
            <h3>Hello, <%=user.getFirstName() + " " + user.getLastName()%></h3>
        </div>
            <form method="post" action="/logout">
                <sec:csrfInput/>
                <div class="input-group">
                    <center> <button> Logout </button> </center>
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
            <center> <span>You are not assigned on any course</span> </center>
        </form>
        <%
        } else {
        %>
        <form> <center> Your courses: </center> </form>
        <br>
        <table class="table-striped table-hover col-sm-12">
            <tr>
                <th>CourseName</th>
                <th>StartingDate</th>
                <th>FinishingDate</th>
                <th>FeedBack</th>
            </tr>
            <%
                for (Course course : courseList) {
            %>
            <tr>
                <td><% out.println(course.getName());%></td>
                <td><% out.println(course.getStartingDate());%></td>
                <td><% out.println(course.getFinishingDate());%></td>
                <td><a href="/course/<%=course.getId()%>">course info</a></td>
                <td><a href="/feedback/user/<%=user.getId()%>/course/<%=course.getId()%>/">See feedback</a></td>
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

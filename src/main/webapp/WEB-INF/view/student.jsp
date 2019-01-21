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
%>
<%

%>
<html>
<head>
    <title>Students</title>
    <style> <%@include file="/theme/css/main.css"%> </style>
    <style> <%@include file="/theme/css/table.css"%> </style>
</head>
<body>
<div class="header">
    <a href="/course">All courses</a>
</div>
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
<form> Your courses: </form>
<table>
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
<%
    }
%>
</body>
</html>

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
%>
<html>
<head>
    <title><fmt:message key="title"/></title>
    <style> <%@include file="/theme/css/main.css"%> </style>
    <style> <%@include file="/theme/css/table.css"%> </style>
</head>
<body>
<div class="header"> <h2><fmt:message key="backToCourses"/></h2> </div>
    <form action="/course">
        <div class="input-group">
            <center> <button><fmt:message key="allCourses"/></button> </center>
        </div>
    </form>
<div class="header">
<sec:authorize access="isAuthenticated()">
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
<%
    }
%>
</body>
</html>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="java.util.List" %>
<%@ page import="com.epam.lab.group1.facultative.security.SecurityContextUser" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page import="java.util.Optional" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    SecurityContextUser principal = null;
%>
<sec:authorize access="isAuthenticated()">
    <%
        principal = (SecurityContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    %>
</sec:authorize>
<html>
<head>
    <title>Edit Course</title>
    <style> <%@include file="/theme/css/main.css"%> </style>
</head>
<body>
<sec:authorize access="isAuthenticated()">
    <%
        int tutorId = (int) request.getAttribute("tutorId");
        Course course = (Course) request.getAttribute("course");
        int courseId = course.getId();

        if (!principal.isStudent() && principal.getUserId() == course.getTutorId()) {

    %>
    <div class="header"> <h2>Edit the current Course</h2> </div>
    <form method="post" action="/course/action/edit/">
        <div class="input-group">
        Course Name:
        <input type="text" name="name"><br><br>
        Starting date yyyy-mm-dd:
        <input type="date" name="startingDate"><br><br>
        Finishing date yyyy-mm-dd:
        <input type="date" name="finishingDate"><br><br>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="tutorId" value="<%=tutorId%>">
        <input type="hidden" name="id" value="<%=courseId%>">
        <input type="submit" value="Submit">
        </div>
    </form>
    <%} else {%> nice try <%}%>
</sec:authorize>
</body>
</html>

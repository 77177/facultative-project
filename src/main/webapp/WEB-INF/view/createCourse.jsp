<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="com.epam.lab.group1.facultative.security.SecurityContextUser" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
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
    <title>Create Course Page</title>
    <style> <%@include file="/theme/css/main.css"%> </style>
</head>
<body>
<%int tutorId = (int) request.getAttribute("tutorId"); %>
<sec:authorize access="isAuthenticated()">
    <%
        if (!principal.isStudent()) {%>
    <div class="header"> <h2>Create a new Course</h2> </div>
    <form method="post" action="/course/action/create/">
        <div class="input-group">
        Course Name:
        <input type="text" name="name"><br><br>
        Starting date yyyy-mm-dd:
        <input type="date" name="startingDate"><br><br>
        Finishing date yyyy-mm-dd:
        <input type="date" name="finishingDate"><br><br>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="tutorId" value="<%=tutorId%>"/>
        <input type="submit" value="Submit">
        </div>
    </form>
    <%} else {%> Better luck next time. <%}%>
</sec:authorize>
</body>
</html>

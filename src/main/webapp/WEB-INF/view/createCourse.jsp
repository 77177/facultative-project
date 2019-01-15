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
</head>
<body>
<%int tutorId = (int) request.getAttribute("tutorId"); %>
<sec:authorize access="isAuthenticated()">
    <%
        if (!principal.isStudent()) {%>
    <h2>Create Course Page</h2>
    <form method="post" action="/course/action/create/">
        Course Name:
        <input type="text" name="courseName"><br>
        Starting date yyyy-mm-dd:
        <input type="date" name="startingDate"><br>
        Finishing date yyyy-mm-dd:
        <input type="date" name="finishingDate"><br>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="tutorId" value="<%=tutorId%>"/>
        <input type="submit" value="Submit">
    </form>
    <%} else {%> nice try <%}%>
</sec:authorize>

<br><br>
</body>
</html>

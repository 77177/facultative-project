<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="com.epam.lab.group1.facultative.security.SecurityContextUser" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
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
        Object errorMessageObject = request.getAttribute("errorMessage");
        if (!principal.isStudent() && principal.getUserId() == course.getTutorId()) {
            %>
            <h2>Edit Course Page</h2>
            <%if(errorMessageObject != null) {
                out.print(errorMessageObject.toString());
            }%>
<div class="input-group">
    <div class="header"> <h2>Edit the current Course</h2> </div>
    <form method="post" action="/course/action/edit/">
        <div class="input-group">
        Course name:
        <input type="text" name="name" value="<%=course.getName()%>" minlength="1" required><br>
        Starting date:
        <input type="date" name="startingDate" value="<%=course.getStartingDate().toString()%>" required><br>
        Finishing date:
        <input type="date" name="finishingDate"  value="<%=course.getFinishingDate().toString()%>" required><br>
            <% if(course.isActive()) {%>
                <input type = "radio" name = "active" value = "true" required checked>Active<br>
                <input type = "radio" name = "active" value = "false" required> Closed <br>
            <%} else {%>
            <input type = "radio" name = "active" value = "true" required>Active<br>
            <input type = "radio" name = "active" value = "false" required checked> Closed <br>
           <% } %>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="tutorId" value="<%=tutorId%>">
        <input type="hidden" name="id" value="<%=course.getId()%>">
        <input type="submit" value="Submit">
        </div>
    </form>
    <%} else {%> nice try <%}%>
</sec:authorize>
</body>
</html>

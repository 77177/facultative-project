<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.epam.lab.group1.facultative.security.SecurityContextUser" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${pageContext.response.locale}"/>
<fmt:setBundle basename="bundle.editCourse"/>
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
    <title><fmt:message key="title"/></title>
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
            <%if(errorMessageObject != null) {
                out.print(errorMessageObject.toString());
            }%>
<div class="input-group">
    <div class="header"> <h2><fmt:message key="title"/></h2> </div>
    <form action="/course/<%=course.getId()%>/action/edit/<%=principal.getUserId()%>" method="get">
        <select name="locale">
            <option value="ru_RU">Русский</option>
            <option value="en_US">English</option>
            <option value="es_ES">Español</option>
        </select>
        <input type="submit" value="change language"/>
    </form>
    <form method="post" action="/course/action/edit/">
        <div class="input-group">
            <fmt:message key="courseName"/>:
        <input type="text" name="name" value="<%=course.getName()%>" minlength="1" required><br>
            <fmt:message key="startDate"/>:
        <input type="date" name="startingDate" value="<%=course.getStartingDate().toString()%>" required><br>
            <fmt:message key="finishDate"/>:
        <input type="date" name="finishingDate"  value="<%=course.getFinishingDate().toString()%>" required><br>
            <% if(course.isActive()) {%>
                <input type = "radio" name = "active" value = "true" required checked><fmt:message key="active"/><br>
                <input type = "radio" name = "active" value = "false" required> <fmt:message key="closed"/> <br>
            <%} else {%>
            <input type = "radio" name = "active" value = "true" required><fmt:message key="active"/><br>
            <input type = "radio" name = "active" value = "false" required checked> <fmt:message key="closed"/> <br>
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

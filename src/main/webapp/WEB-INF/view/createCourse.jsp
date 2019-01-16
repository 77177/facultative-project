<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="com.epam.lab.group1.facultative.security.SecurityContextUser" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Create Course Page</title>
</head>
<body>
<%int tutorId = (int) request.getAttribute("tutorId"); %>
    <h2>Create Course Page</h2>
    <form method="post" action="/course/action/create/">
        Course Name:
        <input type="text" name="name"><br>
        Starting date yyyy-mm-dd:
        <input type="date" name="startingDate"><br>
        Finishing date yyyy-mm-dd:
        <input type="date" name="finishingDate"><br>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="tutorId" value="<%=tutorId%>"/>
        <input type="submit" value="Submit">
    </form>

<br><br>
</body>
</html>

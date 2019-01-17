<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int tutorId = (int) request.getAttribute("tutorId");
    int courseId = (int) request.getAttribute("courseId");
%>
<html>
    <head>
        <title>Edit Course Page</title>
    </head>
    <body>
        <h2>Edit Course Page</h2>
        <form method="post" action="/course/action/edit/">
            Course Name:
            <input type="text" name="name"><br>
            Starting date yyyy-mm-dd:
            <input type="date" name="startingDate"><br>
            Finishing date yyyy-mm-dd:
            <input type="date" name="finishingDate"><br>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" name="tutorId" value="<%=tutorId%>">
            <input type="hidden" name="id" value="<%=courseId%>">
            <input type="submit" value="Submit">
        </form>
    </body>
</html>
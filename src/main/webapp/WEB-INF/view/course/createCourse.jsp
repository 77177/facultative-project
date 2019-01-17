<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int tutorId = (int) request.getAttribute("tutorId");
%>
<html>
<head>
    <title>Create Course Page</title>
</head>
<body>
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

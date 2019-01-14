<%@ page import="org.apache.tomcat.jni.Local" %>
<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int tutorId = 1;
    int courseId = 1;
%>
<html>
<head>
    <title>Create Course Page</title>
</head>
<body>
<h2>Create Course Page</h2>
<form method="post" action="/course/action/editCourse/">
    Course Name:
    <input type="text" name="courseName"><br>
<%--Starting date:
    <input type="date" name="startingDate"><br>
    Finishing date:
    <input type="date" name="finishingDate" pattern="yyyy-MMM-dd"><br>--%>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden" name="tutorId" value="<%=tutorId%>">
    <input type="hidden" name="courseId" value="<%=courseId%>">
    <input type="submit" value="Submit">
</form>

<br><br>
</body>
</html>

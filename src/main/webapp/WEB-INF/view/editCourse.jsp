<%@ page import="org.apache.tomcat.jni.Local" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="javafx.util.Pair" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Edit Course Page</title>
</head>
<body>
<%
    List listInt = (List) request.getAttribute("listInt");
    int tutorId = (int) listInt.get(0);
    int courseId = (int) listInt.get(1);
%>
<h2>Edit Course Page</h2>
<form method="post" action="/course/action/edit/">
    Course Name:
    <input type="text" name="courseName"><br>
    Starting date yyyy-mm-dd:
    <input type="text" name="startingDate" pattern="[0-9]{4}\-[0-9]{2}\-[0-9]{2}"><br>
    Finishing date yyyy-mm-dd:
    <input type="text" name="finishingDate" pattern="[0-9]{4}\-[0-9]{2}\-[0-9]{2}"><br>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden" name="tutorId" value="<%=tutorId%>">
    <input type="hidden" name="courseId" value="<%=courseId%>">
    <input type="submit" value="Submit">
</form>

<br><br>
</body>
</html>

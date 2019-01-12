<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page import="org.apache.coyote.Request" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tutors</title>
</head>
<body>
<h2>Tutor page</h2>
<a href="/logout">Logout</a>
<p></p>

<table style="border: 2px double black; border-spacing: 7px 7px">
    <tr>
        <th>CourseName</th>
        <th>TutorId</th>
        <th>StartingDate</th>
        <th>FinishingDate</th>
        <th>isActive</th>

    </tr>

    <%
        List<Course> list = (List<Course>) request.getAttribute("list");
        for (Course course : list) {
    %>
    <tr>
        <td><% out.println(course.getCourseName());%></td>
        <td><% out.println(course.getTutorId());%></td>
        <td><% out.println(course.getStartingDate());%></td>
        <td><% out.println(course.getFinishingDate());%></td>
        <td><% out.println(course.isActive());%></td>
    </tr>
    <%
        }
    %>
</table>
<a href="/course/">All courses</a>
<a href="/tutor/createCourse">Create</a>

</body>
</html>
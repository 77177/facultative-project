<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Profile</title>
    </head>
    <body>
        <h2>My profile</h2>
        <c:import url="header.jsp"/>
        <table style="border: 2px double black; border-spacing: 7px 7px">
            <tr>
                <th>CourseName</th>
                <th>StartingDate</th>
                <th>FinishingDate</th>
                <th>isActive</th>
                <th>Edit</th>
                <th>Delete</th>

            </tr>
            <%
                List<Course> list = (List<Course>) request.getAttribute("courseList");
                for (Course course : list) {
            %>
            <tr>
                <td><% out.println(course.getCourseName());%></td>
                <td><% out.println(course.getTutorId());%></td>
                <td><% out.println(course.getStartingDate());%></td>
                <td><% out.println(course.getFinishingDate());%></td>
                <td><% out.println(course.isActive());%></td>
                <td><a href="/course/action/editCourse/">Edit</a></td>
                <td><a href="/course/action/delete/<%out.print(course.getCourseId());%>/">delete</a></td>
            </tr>
            <%
                }
            %>
        </table>
        <a href="/course/action/createCourse/">Create</a>
    </body>
</html>
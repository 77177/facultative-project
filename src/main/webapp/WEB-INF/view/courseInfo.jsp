<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page import="com.epam.lab.group1.facultative.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Optional" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Course Info</title>
    </head>
    <body>
        <h2>Course Info</h2>
        <c:import url="header.jsp"/>
        <table style="border: 2px double black; border-spacing: 7px 7px">
            <tr>
                <th>CourseName</th>
                <th>TutorId</th>
                <th>StartingDate</th>
                <th>FinishingDate</th>
                <th>isActive</th>
            </tr>
            <%
                Optional<Course> courseInfo = (Optional<Course>) request.getAttribute("courseInfo");
            %>
            <tr>
                <td><% out.println(courseInfo.get().getCourseName());%></td>
                <td><% out.println(courseInfo.get().getTutorId());%></td>
                <td><% out.println(courseInfo.get().getStartingDate());%></td>
                <td><% out.println(courseInfo.get().getFinishingDate());%></td>
                <td><% out.println(courseInfo.get().isActive());%></td>
            </tr>
        </table>
        <br><br>

        <h3>Student List</h3>
        <table style="border: 2px double black; border-spacing: 7px 7px">
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
            </tr>
            <%
                List<User> users = (List<User>) request.getAttribute("studentList");
                for (User user : users) {
            %>
            <tr>
                <td><% out.println(user.getFirstName());%></td>
                <td><% out.println(user.getLastName());%></td>
            </tr>
            <%
                }
            %>
        </table>
    </body>
</html>
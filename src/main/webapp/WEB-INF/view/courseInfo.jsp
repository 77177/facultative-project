<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
        <c:import url="header.jsp"/>
        <h2>Course Info</h2>
        <table style="border: 2px double black; border-spacing: 7px 7px">
            <tr>
                <th>CourseName</th>
                <th>StartingDate</th>
                <th>FinishingDate</th>
                <th>isActive</th>
            </tr>
            <%
                Optional<Course> courseOptional = (Optional<Course>) request.getAttribute("courseInfo");
                Course course = courseOptional.get();
            %>
            <tr>
                <td><% out.println(course.getName());%></td>
                <td><% out.println(course.getStartingDate());%></td>
                <td><% out.println(course.getFinishingDate());%></td>
                <td><% out.println(course.isActive());%></td>
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
                        <td><a href="/feedback/user/<%=user.getId()%>/course/<%=course.getId()%>/">feedback</a></td>
                    </tr>
                    <%
                }
            %>
        </table>
    </body>
</html>
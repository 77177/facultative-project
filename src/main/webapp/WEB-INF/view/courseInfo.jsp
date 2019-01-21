<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page import="com.epam.lab.group1.facultative.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Optional" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="com.epam.lab.group1.facultative.security.SecurityContextUser" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <title>Course Info</title>
        <style> <%@include file="/theme/css/main.css"%> </style>
        <style> <%@include file="/theme/css/table.css"%> </style>
    </head>
    <body>
        <div class="header"> <h2>Course Info</h2> </div>
        <c:import url="header.jsp"/>
        <br>
        <table>
            <tr>
                <th>CourseName</th>
                <th>TutorId</th>
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
                <td><% out.println(course.getTutorId());%></td>
                <td><% out.println(course.getStartingDate());%></td>
                <td><% out.println(course.getFinishingDate());%></td>
                <td><% out.println(course.isActive());%></td>
            </tr>
        </table>
        <br><br>

        <div class="header2"> <h3>Student List</h3> </div>
        <br>
        <table>
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
                <td><a href="/feedback/user/<%=user.getId()%>/course/<%=course.getId()%>/">feedback</a> </td>
            </tr>
            <%
                }
            %>
        </table>
    </body>
</html>
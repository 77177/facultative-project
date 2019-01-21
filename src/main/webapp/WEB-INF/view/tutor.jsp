<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="com.epam.lab.group1.facultative.security.SecurityContextUser" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
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
        <title>Profile</title>
        <style> <%@include file="/theme/css/main.css"%> </style>
        <style> <%@include file="/theme/css/table.css"%> </style>
    </head>
    <body>
        <div class="header"> <h2>My profile</h2> </div>
        <%
            List<Course> list = (List<Course>) request.getAttribute("courseList");
        %>
        <c:import url="header.jsp"/>
        <br>
        <table>
            <tr>
                <th>CourseName</th>
                <th>StartingDate</th>
                <th>FinishingDate</th>
                <th>isActive</th>
                <th>Options</th>
                <th></th>
            </tr>
            <%
                for (Course course : list) {
            %>
            <tr>
                <td><% out.println(course.getName());%></td>
                <td><% out.println(course.getStartingDate());%></td>
                <td><% out.println(course.getFinishingDate());%></td>
                <td><% out.println(course.isActive());%></td>

                <td><a href="/course/<%=course.getId()%>/action/edit/<%=course.getTutorId()%>/">Edit</a></td>
                <td><a href="/course/action/delete/<%=course.getId()%>/">delete</a></td>
            </tr>
            <%
                }
            %>
        </table>
        <br> <form>
            <center> <button> <a href="/course/action/create/<%=principal.getUserId()%>/">Create a new Course</button> </button> </center>
        </form>
    </body>
</html>
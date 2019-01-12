<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.userdetails.UserDetails" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Object authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String userName = null;
    if (authentication instanceof UserDetails) {
        userName = ((UserDetails)authentication).getUsername();
    }
%>
<html>
    <head>
        <title>Courses</title>
    </head>
    <body>
        <h2>All Courses</h2>
        <% if (userName != null) { %>
        <h3>Hello, <%=userName%></h3>
        <% } %>
        <form method="post" action="/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="submit" value="Logout"/>
        </form>
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
                <td><%= course.getCourseName()%></td>
                <td><%= course.getTutorId()%></td>
                <td><%= course.getStartingDate()%></td>
                <td><%= course.getFinishingDate()%></td>
                <td><%= course.isActive()%></td>
                <td><a href="/course/<%=course.getCourseId()%>">course info</a></td>
            </tr>
            <%
                }
            %>
        </table>
    </body>
</html>
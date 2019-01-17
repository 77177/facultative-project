<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="com.epam.lab.group1.facultative.security.SecurityContextUser" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    SecurityContextUser principal = null;
    Object courseListObject = request.getParameter("courseList");
    List<Course> courseList = courseListObject != null ? (List) courseListObject : null;
%>
<sec:authorize access="isAuthenticated()">
    <%
        principal = (SecurityContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    %>
</sec:authorize>
<html>
    <head>
        <title>Profile</title>
    </head>
    <body>
        <h2>My profile</h2>
        <c:import url="../template/header.jsp"/>
        <table style="border: 2px double black; border-spacing: 7px 7px">
            <tr>
                <th>CourseName</th>
                <th>StartingDate</th>
                <th>FinishingDate</th>
                <th>isActive</th>
                <th>Options</th>
                <th></th>
            </tr>
            <%
                for (Course course : courseList) {
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
        <a href="/course/action/create/<%=principal.getUserId()%>/">Create</a>
    </body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                Optional<Course> courseOptional = (Optional<Course>) request.getAttribute("course");
                Course course = courseOptional.get();
            %>
            <tr>
                <td><% out.println(course.getCourseName());%></td>
                <td><% out.println(course.getTutorId());%></td>
                <td><% out.println(course.getStartingDate());%></td>
                <td><% out.println(course.getFinishingDate());%></td>
                <td><% out.println(course.isActive());%></td>
                <sec:authorize access="isAuthenticated()">
                    <%
                        if (principal.isStudent()) {
                            if (principal.getCourseIdList().contains(course.getCourseId())) {
                    %><td><a href="/user/<%=principal.getUserId()%>/course/<%=course.getCourseId()%>?action=leave">
                    Leave</a>
                </td><%
                } else {
                %><td><a href="/user/<%=principal.getUserId()%>/course/<%=course.getCourseId()%>?action=subscribe">
                    Subscribe</a></td><%
                    }
                } else {
                    if (principal.getCourseIdList().contains(course.getCourseId())) {
                %><td><a href="/user/<%=principal.getUserId()%>/course/<%=course.getCourseId()%>?action=delete">
                    Delete</a>
                </td><%
                        }
                    }
                %>
                </sec:authorize>
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
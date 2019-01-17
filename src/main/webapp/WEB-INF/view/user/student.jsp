<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="com.epam.lab.group1.facultative.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page import="java.util.Collections" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Object userObject = request.getAttribute("user");
    User user = userObject != null ? (User) userObject : null;

    Object courseListObject = request.getParameter("courseList");
    List<Course> courseList = courseListObject != null ? (List) courseListObject : Collections.emptyList();
%>
<%

%>
<html>
    <head>
        <title>Students</title>
    </head>
    <body>
        <div>
            <a href="/course">all courses</a>
        </div>
        <sec:authorize access="isAuthenticated()">
            <h3>Hello, <%=user.getFirstName() + " " + user.getLastName()%></h3>
            <form method="post" action="/logout">
                <sec:csrfInput/>
                <input type="submit" value="Logout"/>
            </form>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
            <a href="/authenticator/login">Login</a>
        </sec:authorize>
        <%
            if (courseList.isEmpty()) {
                %>
                    <span>You are not assigned on any course</span>
                <%
            } else {
                %>
                <div>Your courses:</div>
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
                <%
            }
        %>
    </body>
</html>

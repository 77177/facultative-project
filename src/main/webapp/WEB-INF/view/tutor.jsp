<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="com.epam.lab.group1.facultative.security.SecurityContextUser" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    SecurityContextUser principal = null;
    Object courseListObject = request.getAttribute("courseList");
    List<Course> courseList = courseListObject != null ? (List) courseListObject : Collections.emptyList();
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
        <c:import url="header.jsp"/>
        <h3>My profile</h3>
        <table style="border: 2px double black; border-spacing: 7px 7px">
            <tr>
                <th>Title</th>
                <th>Starting date</th>
                <th>Finishing date</th>
                <th>Active</th>
                <th>Options</th>
            </tr>
            <%
                for (Course course : courseList) {
                    %>
                    <tr>
                        <td><%= course.getName()%></td>
                        <td><%= course.getStartingDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))%></td>
                        <td><%= course.getFinishingDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))%></td>
                        <td><%= course.isActive()%></td>
                        <td><a href="/course/<%=course.getId()%>">info</a></td>
                        <td><a href="/course/<%=course.getId()%>/action/edit/<%=course.getTutorId()%>/">edit</a></td>
                        <td><a href="/course/action/delete/<%=course.getId()%>/">delete</a></td>
                    </tr>
                    <%
                }
            %>
        </table>
        <br><br>
        <div id="createButton">
            <a href="/course/action/create/<%=principal.getUserId()%>/">Create course</a>
        </div>
    </body>
</html>
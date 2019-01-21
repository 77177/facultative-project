<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
        <title>Courses</title>
        <style>
            <%@include file="/theme/css/main.css"%>
        </style> <style>
            <%@include file="/theme/css/table.css"%>
        </style>
    </head>
    <body>
        <div class="header">
            <h2>Go to all Courses</h2>
        </div>
            <c:import url="header.jsp"/>
            <br>
        <%
            Object listObject = request.getAttribute("courseList");
            if (listObject != null) {
                if (listObject instanceof List) { %>
                    <table>
                        <tr>
                            <th>CourseName</th>
                            <th>TutorId</th>
                            <th>StartingDate</th>
                            <th>FinishingDate</th>
                            <th>isActive</th>
                        </tr> <%
                    List<Course> list = (List<Course>) listObject;
                    for (Course course : list) { %>
                        <tr>
                            <td><%= course.getName()%></td>
                            <td><%= course.getTutorId()%></td>
                            <td><%= course.getStartingDate()%></td>
                            <td><%= course.getFinishingDate()%></td>
                            <td><%= course.isActive()%></td>
                            <td><a href="/course/<%=course.getId()%>">course info</a></td>
                            <sec:authorize access="isAuthenticated()">
                                <%
                                    if (principal.isStudent()) {
                                        if (principal.getCourseIdList().contains(course.getId())) {
                                            %><td><a href="/user/<%=principal.getUserId()%>/course/<%=course.getId()%>?action=leave">
                                                        Leave</a>
                                              </td><%
                                        } else {
                                            %><td><a href="/user/<%=principal.getUserId()%>/course/<%=course.getId()%>?action=subscribe">
                                                        Subscribe</a></td><%
                                        }
                                    }
                                %>
                            </sec:authorize>
                        </tr><%
                    }%>
                    </table><br><br> <%
                }
            } else {
                out.print("There is not a single course");
            }%>
    </body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page import="com.epam.lab.group1.facultative.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="com.epam.lab.group1.facultative.security.SecurityContextUser" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="java.time.Period" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    SecurityContextUser principal = null;
    Course course = (Course)request.getAttribute("course");
    List<User> studentList = (List) request.getAttribute("studentList");
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
        <h2><%=course.getName()%></h2>
        <div id="courseInfo">
            starts: <span><%=course.getStartingDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))%></span><br>
            finishes: <span><%=course.getFinishingDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))%></span><br>
            duration:
            <span><%=Period.between(course.getFinishingDate(), course.getFinishingDate()).getDays()%> days.</span>
        </div>
        <sec:authorize access="hasAnyAuthority('student')">
            <div id="studentZone">
                <%
                    if (principal.getCourseIdList().contains(course.getId())) {
                        %>
                            <p>
                                You are going to participate in the <%=course.getName()%> <br>
                                <a href="/user/<%=principal.getUserId()%>/course/<%=course.getId()%>/leave/">Leave course</a>
                            </p>
                            <p>
                                Follow the link to see if you have a feedback from course's tutor.
                                <a href="/feedback/user/<%=principal.getUserId()%>/course/<%=course.getId()%>/">
                                    See feedback
                                </a>
                            </p>
                        <%
                    } else {
                        %>
                            <p>
                                You can take place in the <%=course.getName()%> <br>
                                <a href="/user/<%=principal.getUserId()%>/course/<%=course.getId()%>/subscribe/">Subscribe</a>
                            </p>

                        <%
                    }
                %>
            </div>
        </sec:authorize>
        <sec:authorize access="hasAnyAuthority('tutor')">
            <div id="tutorZone">
                <a href="/course/<%=course.getId()%>/action/edit/<%=course.getTutorId()%>/">edit course</a><br>
                <a href="/course/action/delete/<%=course.getId()%>/">delete course</a><br>
                <br>
                <div class="header2"> <h3>Student List</h3> </div>
                <table style="border: 2px double black; border-spacing: 7px 7px">
                    <tr>
                        <th>First Name</th>
                        <th>Last Name</th>
                    </tr>
                    <%
                        for (User user : studentList) {
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
            </div>
        </sec:authorize>
    </body>
</html>
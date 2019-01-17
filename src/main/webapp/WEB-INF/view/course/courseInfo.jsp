<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page import="com.epam.lab.group1.facultative.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="com.epam.lab.group1.facultative.security.SecurityContextUser" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    SecurityContextUser principal = null;
%>
<sec:authorize access="isAuthenticated()">
    <%
        principal = (SecurityContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    %>
</sec:authorize>
<%
    Object courseObject = request.getAttribute("course");
    Object tutorObject = request.getAttribute("tutor");
    Object studentListObject = request.getAttribute("studentList");

    Course course = courseObject != null ? (Course) courseObject : null;
    User tutor = tutorObject != null ? (User) tutorObject : null;
    List<User> studentList = studentListObject != null ? (List)studentListObject : Collections.emptyList();

    boolean isCourseStarted = course != null ? course.getStartingDate().isBefore(LocalDate.now()) : false;
%>
<html>
    <head>
        <title><%=course.getName()%></title>
    </head>
    <body>
        <c:import url="../template/header.jsp"/>
        <h2><%=course.getName()%></h2>
        author: <span><%=tutor.getFirstName() + " " + tutor.getLastName()%></span><br><br>
        Course starts: <span><%=course.getStartingDate()%></span><br>
        Course finishes: <span><%=course.getFinishingDate()%></span><br>
        <sec:authorize access="isAuthenticated()">
        <%
//            if (!course.isActive()) {
            if (false) {
                out.print("Course is closed. Connect the tutor to get additional information.");
            } else {
                if (principal.isStudent()) {
                    if (!isCourseStarted) {
                        if (course.getUserIds().contains(principal.getUserId())){
                            out.print("You are participating in the course");
                            %>
                            <a href="/user/<%=principal.getUserId()%>/course/<%=course.getId()%>?action=leave">Leave</a>
                            <%
                        } else {
                            %>
                            <a href="/user/<%=principal.getUserId()%>/course/<%=course.getId()%>?action=subscribe">
                            Register on course right now
                            </a>
                            <%
                        }
                    } else {
                        out.print("Course already started. Registration is closed.");
                    }
                } else {
                    if (studentList.isEmpty()) {
                        out.print("No one is participating in the course");
                    } else {
                        %>
                        <h3>Student List</h3>
                        <table style="border: 2px double black; border-spacing: 7px 7px">
                            <tr>
                                <th>First Name</th>
                                <th>Last Name</th>
                            </tr>
                            <%
                                List<User> users = (List<User>) request.getAttribute("studentList");
                                for (User user : users) {
                                    if (user.getPosition().equals("student")) {
                                        %>
                                        <td><% out.println(user.getFirstName());%></td>
                                        <td><% out.println(user.getLastName());%></td>
                                        <sec:authorize access="isAuthenticated()">
                                            <td><a href="/feedback/user/<%=user.getId()%>/course/<%=course.getId()%>">feedback</a></td>
                                        </sec:authorize>
                                        <%
                                    }
                                }
                            %>
                        </table>
                        <%
                    }
                }
            }
        %>
        </sec:authorize>
    </body>
</html>
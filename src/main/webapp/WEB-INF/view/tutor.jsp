<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="com.epam.lab.group1.facultative.security.SecurityContextUser" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${pageContext.response.locale}"/>
<fmt:setBundle basename="bundle.tutor"/>
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
        <title><fmt:message key="title"/></title>
        <style> <%@include file="/theme/css/main.css"%> </style>
        <style> <%@include file="/theme/css/table.css"%> </style>
    </head>
    <body>
        <div class="header"> <h2><fmt:message key="myCourses"/></h2> </div>
        <c:import url="header.jsp"/>
        <br>
        <center><fmt:message key="myProfile"/></center>
        <table>
            <tr>
                <th><fmt:message key="titleTitle"/></th>
                <th><fmt:message key="start"/></th>
                <th><fmt:message key="finish"/></th>
                <th><fmt:message key="active"/></th>
                <th><fmt:message key="options"/></th>
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
                        <td>
                            <a href="/course/<%=course.getId()%>/action/edit/<%=course.getTutorId()%>/">
                                <fmt:message key="edit"/>
                            </a>
                        </td>
                        <td>
                            <a href="/course/action/delete/<%=course.getId()%>/">
                                <fmt:message key="delete"/>
                            </a>
                        </td>
                    </tr>
                    <%
                }
            %>
        </table>
        <br> <form>
            <center>
                <button>
                    <a href="/course/action/create/<%=principal.getUserId()%>"><fmt:message key="createNewCourse"/></a>
                </button>
            </center>
        </form>
    </body>
</html>
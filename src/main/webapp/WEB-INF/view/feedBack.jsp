<%@ page import="com.epam.lab.group1.facultative.security.SecurityContextUser" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="java.io.IOException" %>
<%@ page import="com.epam.lab.group1.facultative.model.FeedBack" %>
<%@ page import="java.security.Principal" %>
<%@ page import="com.epam.lab.group1.facultative.model.User" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page import="java.util.function.IntPredicate" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Optional" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
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
    <title>Feedbacks</title>
    <style> <%@include file="/theme/css/main.css"%> </style>
</head>
<body>

<%
    FeedBack feedBack = (FeedBack) request.getAttribute("feedback");
    Course course = (Course) request.getAttribute("course");
%>
<%--<%!
    public boolean isNotCourseTutor(List<User> userList, SecurityContextUser securityContextUser,FeedBack feedBack, List<Course> courseList) {
        for (Course c : courseList) {
            for (User u: userList) {
                if (!securityContextUser.isStudent() && u.getId() == securityContextUser.getUserId() && feedBack.getCourseId() == c.getId()) {
                    return false;
                }
            }
        }
        return true;
    }
%>--%>
<div class="header"> <h2> Feedback </h2> </div>
<c:import url="header.jsp"/>
<br/>
<sec:authorize access="isAuthenticated()">
    <%--@elvariable id="feedback" type="com.epam.lab.group1.facultative.model.FeedBack"--%>
    <form:form action="/feedback/" method="post" modelAttribute="feedback">
        Feedback:
        <br/>
        <form:textarea path="text" rows="4" cols="50"
                       readonly="<%=!(course.getTutorId()==principal.getUserId())%>"></form:textarea>
        <br/>
        Mark:
        <br/>
        <form:input path="mark" readonly="<%=!(course.getTutorId()==principal.getUserId())%>"/>
        <form:hidden path="courseId"/>
        <form:hidden path="studentId"/>
        <br/>
        <%if(course.getTutorId()==principal.getUserId()) {%>
        <input type="submit" value="submit"/>
        <%}%>
    </form:form>
</sec:authorize>
</body>
</html>

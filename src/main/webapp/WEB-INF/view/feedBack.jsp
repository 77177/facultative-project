<%@ page import="com.epam.lab.group1.facultative.security.SecurityContextUser" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="java.io.IOException" %>
<%@ page import="com.epam.lab.group1.facultative.model.FeedBack" %>
<%@ page import="java.security.Principal" %>
<%@ page import="com.epam.lab.group1.facultative.model.User" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page import="java.util.function.IntPredicate" %>
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
</head>
<body>

<%
    FeedBack feedBack = (FeedBack) request.getAttribute("feedback");
    User user = (User) request.getAttribute("student");
%>
<%!
    public boolean isNotCourseTutor(User user, SecurityContextUser securityContextUser,FeedBack feedBack) {
        for (Course c : user.getCourseList()) {
            for (User u: c.getUsersList()) {
                if (!securityContextUser.isStudent() && u.getId() == securityContextUser.getUserId() && feedBack.getCourseId() == c.getId()) {
                    return false;
                }
            }
        }
        return true;
    }
%>
Feedbacks page
<c:import url="header.jsp"/>
<br/>
<sec:authorize access="isAuthenticated()">
    <%--@elvariable id="feedback" type="com.epam.lab.group1.facultative.model.FeedBack"--%>
    <form:form action="/feedback" method="post" modelAttribute="feedback">
        <form:textarea path="text" rows="4" cols="50"
                       readonly="<%=isNotCourseTutor(user,principal,feedBack)%>"></form:textarea>
        <input type="submit" value="submit"/>
    </form:form>
</sec:authorize>
</body>
</html>

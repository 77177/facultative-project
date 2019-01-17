<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="com.epam.lab.group1.facultative.model.User" %>
<%@ page import="com.epam.lab.group1.facultative.security.SecurityContextUser" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Feedbacks</title>
</head>
<body>
Feedbacks page
<%
    SecurityContextUser principal = null;
%>
<sec:authorize access="isAuthenticated()">
    <%
        principal = (SecurityContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    %>
</sec:authorize>
<c:import url="header.jsp"/>
<br/>
<%--@elvariable id="feedback" type="com.epam.lab.group1.facultative.model.FeedBack"--%>
<form:form action="/feedback/" method="post" modelAttribute="feedback">
<form:textarea path="text" rows="4" cols="50" readonly="<%=principal.isStudent()%>"></form:textarea>
    <input type="submit"/>
</form:form>

</body>
</html>

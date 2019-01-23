<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="com.epam.lab.group1.facultative.security.SecurityContextUser" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${pageContext.response.locale}"/>
<fmt:setBundle basename="bundle.header"/>
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
        <title>header</title>
        <style>
            <%@include file="/theme/css/main.css"%>
        </style>
    </head>
    <body>
    <form action="/course">
        <div class="input-group">
            <center> <button>All courses</button> </center>
        </div>
    </form>
        <sec:authorize access="isAuthenticated()">
            <div class="header">
                <h3>Hello, <sec:authentication property="name"/></h3>
            </div>
            <form action="/user/profile">
                <div class="input-group">
                    <center> <button>My Profile</button> </center>
                </div>
            </form>
            <form method="post" action="/logout">
                <sec:csrfInput/>
                <div class="input-group">
                    <center> <button type="submit" value="Logout"> Logout </button> </center>
                </div>
            </form>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
            <form action="/authenticator/login">
                <div class="input-group">
                    <center> <button>Login</button> </center>
                </div>
            </form>
        </sec:authorize>
    </body>
</html>

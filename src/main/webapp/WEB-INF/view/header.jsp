<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="com.epam.lab.group1.facultative.security.SecurityContextUser" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
        <title>header</title>
    </head>
    <body>
        <div id="allCoursesLink">
            <p><a href="/course">all courses</a></p>
        </div>
        <sec:authorize access="isAuthenticated()">
            <p style="display: inline-block">Hello, <%=principal.getFirstName()%>. </p>
            <a href="/user/profile">My profile</a>
            <form method="post" action="/logout">
                <sec:csrfInput/>
                <input type="submit" value="Logout"/>
            </form>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
            <div id="loginButton">
                <p><a href="/authenticator/login">Login</a></p>
            </div>
        </sec:authorize>
    </body>
</html>

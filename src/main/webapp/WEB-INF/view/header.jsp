<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>header</title>
    </head>
    <body>
    <a href="/course">all courses</a> <br><br>
        <sec:authorize access="isAuthenticated()">
            <h3>Hello, <sec:authentication property="name"/></h3>
            <a href="/profile">my profile</a>
            <br><br>
            <form method="post" action="/logout">
                <sec:csrfInput/>
                <input type="submit" value="Logout"/>
            </form>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
            <a href="/authenticator/login">Login</a> <br><br>
        </sec:authorize>
    </body>
</html>

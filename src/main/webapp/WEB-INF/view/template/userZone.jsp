<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>user zone</title>
    </head>
    <body>
        <sec:authorize access="isAuthenticated()">
            <h3>Hello, <sec:authentication property="name"/></h3>
            <div><a href="/user/profile">my profile</a></div>
            <br>
            <form method="post" action="/logout">
                <sec:csrfInput/>
                <input type="submit" value="Logout"/>
            </form>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
            <a href="/authenticator/login">Login</a>
        </sec:authorize>
    </body>
</html>

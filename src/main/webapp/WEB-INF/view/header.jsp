<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${pageContext.response.locale}"/>
<fmt:setBundle basename="bundle.header"/>
<html>
    <head>
        <title>header</title>
        <style>
        </style>
    </head>
    <body>
        <sec:authorize access="isAuthenticated()">
            <div class="header">
                <h3>Hello, <sec:authentication property="name"/></h3>
            </div>
            <a href="/user/profile">My Profile</a>
            <form method="post" action="/logout">
                <sec:csrfInput/>
                <button type="submit" value="Logout"> Logout </button>
            </form>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
            <a href="/authenticator/login">Login</a>
        </sec:authorize>
    </body>
</html>

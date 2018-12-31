<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="bundle.login" var="pageBundle"/>
<html>
    <head>
        <title>Login to facultative</title>
    </head>
    <body>
        <fmt:message key="welcome"/>
        <form method="post" action="/login">
            <fmt:message key="email"/> :     <input type="text" name="username" placeholder="yourEmail@company.com"/><br>
            <fmt:message key="password"/>:   <input type="password" name="password" placeholder="password"/><br>
            <button type="submit"><fmt:message key="button.loginIn"/></button>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
        <br><br>
        <fmt:message key="noRegistration"/><br>
        <a href="/authenticator/registration">
            <button><fmt:message key="button.register"/></button>
        </a>
    </body>
</html>
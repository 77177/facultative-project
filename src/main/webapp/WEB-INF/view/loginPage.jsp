<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<fmt:setLocale value="${pageContext.request.locale}"/>
<fmt:setBundle basename="bundle.login"/>
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
            <sec:csrfInput/>
        </form>
        <br><br>
        <fmt:message key="noRegistration"/><br>
        <a href="/authenticator/registration">
            <button><fmt:message key="button.register"/></button>
        </a>
    </body>
</html>
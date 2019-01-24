<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<fmt:setLocale value="${pageContext.request.locale}"/>
<fmt:setBundle basename="bundle.login"/>
<html>
    <head>
        <title>Login to facultative</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
              integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
              crossorigin="anonymous"/>
    </head>
    <body>
        <h2><fmt:message key="welcome"/></h2>
        <form method="post" action="/login">
            <fmt:message key="email"/>:
            <input type="text" name="username" placeholder="yourEmail@company.com"/>
            <fmt:message key="password"/>:
            <input type="password" name="password" placeholder="password"/>
            <button type="submit"><fmt:message key="button.loginIn"/></button>
            <sec:csrfInput/>
        </form>

        <form action="/authenticator/login" method="get">
            <select name="locale">
                <option value="ru_RU">Русский</option>
                <option value="en_US">English</option>
                <option value="es_ES">Español</option>
            </select>
            <input type="submit" value="change language"/>
        </form>
        <br><br>

        <fmt:message key="noRegistration"/>
        <a href="/authenticator/registration">
            <fmt:message key="button.register"/>
        </a>
    </body>
</html>
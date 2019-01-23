<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<fmt:setLocale value="${pageContext.request.locale}"/>
<fmt:setBundle basename="bundle.login"/>
<html>
    <head>
        <title>Login to facultative</title>
        <style>
            <%@include file="/theme/css/main.css"%>
        </style>
    </head>
    <body>
        <div class="header">
            <h2><fmt:message key="welcome"/></h2>
        </div>
        <form method="post" action="/login">
            <div class="input-group">
                    <label><fmt:message key="email"/>:</label>     <input type="text" name="username" placeholder="yourEmail@company.com"/>
                </div><br>
                <div class="input-group">
                    <label><fmt:message key="password"/>:</label>   <input type="password" name="password" placeholder="password"/>
                </div><br>
                <div class="input-group">
                    <center><button type="submit"><fmt:message key="button.loginIn"/></button></center>
                    <sec:csrfInput/>
                </div>
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
            <div class="header">
                <fmt:message key="noRegistration"/>
            </div>
            <form action="/authenticator/registration">
                <div class="input-group">
                    <center><button type="submit"><fmt:message key="button.register"/></button></center>
                </div>
            </form>
    </body>
</html>
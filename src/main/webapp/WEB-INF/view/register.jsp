<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${pageContext.request.locale}"/>
<fmt:setBundle basename="bundle.register"/>
<html>
    <head>
        <title>Facultative registration</title>
    </head>
    <body>
        <fmt:message key="welcome"/><br/>
        <br><br>
        <form method="post" action="/login">
            <fmt:message key="firstName"/>:<input type="text"     name="firstName"  placeholder="first name"/><br>
            <fmt:message key="lastName"/>: <input type="text"     name="lastName"   placeholder="last name"/><br>
            <fmt:message key="email"/>:    <input type="text"     name="username"   placeholder="e-mail"/><br>
            <fmt:message key="password"/>: <input type="password" name="password"   placeholder="password"/><br>
            <fmt:message key="position"/>:
            <select name="position">
                <option><fmt:message key="option.student"/></option>
                <option><fmt:message key="option.teacher"/></option>
            </select>
            <input type="hidden" name="registration" value="true"/>
            <button type="submit"><fmt:message key="button.register"/></button>
            <sec:csrfInput/>
        </form>
    </body>
</html>
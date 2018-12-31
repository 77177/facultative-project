<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setBundle basename="bundle.register" var="pageBundle"/>
<html>
    <head>
        <title>Facultative registration</title>
    </head>
    <body>
        New user. <br/>
        <form method="post" action="/authenticator/registration">
            <fmt:message key="firstName"/>:     <input type="text" name="firstName" placeholder="first name"/><br>
            <fmt:message key="lastName"/>:      <input type="text" name="lastName" placeholder="last name"/><br>
            <fmt:message key="email"/>:          <input type="text" name="email" placeholder="e-mail"/><br>
            <fmt:message key="password"/>:       <input type="password" name="password" placeholder="password"/><br>
            <fmt:message key="position"/>:
            <select name="position">
                <option><fmt:message key="option.student"/></option>
                <option><fmt:message key="option.teacher"/></option>
            </select>
            <input type="hidden"
                   name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>
            <button type="submit"><fmt:message key="button.register"/></button>
        </form>
        <br><br>
    </body>
</html>
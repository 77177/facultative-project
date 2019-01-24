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
        <h2><fmt:message key="welcome"/></h2>
        <form action="/authenticator/registration/" method="get">
            <select name="locale">
                <option value="ru_RU">Русский</option>
                <option value="en_US">English</option>
                <option value="es_ES">Español</option>
            </select>
            <input type="submit" value="change language"/>
        </form>
        ${error}
        <form method="post" action="/login">
            <div class="input-group">
                <label><fmt:message key="firstName"/>:</label>  <input type="text"     name="firstName"  placeholder="first name"/><br><br>
            </div>
            <div class="input-group">
                <label><fmt:message key="lastName"/>:</label>   <input type="text"     name="lastName"   placeholder="last name"/><br><br>
            </div>
            <div class="input-group">
                <label><fmt:message key="email"/>:</label>      <input type="text"     name="username"   placeholder="e-mail"/><br><br>
            </div>
            <div class="input-group">
                <label><fmt:message key="password"/>:</label>   <input type="password" name="password"   placeholder="password"/><br><br>
            </div>
            <div class="input-group">
                <label><fmt:message key="position"/>:</label>
            </div>

            <div class="input-group">
                <select name="position">
                    <option><fmt:message key="option.student"/></option>
                    <option><fmt:message key="option.tutor"/></option>
                </select>
                <input type="hidden" name="registration" value="true"/>
                <button type="submit"><fmt:message key="button.register"/></button>
                <sec:csrfInput/>
            </div>
        </form>

    </body>
</html>
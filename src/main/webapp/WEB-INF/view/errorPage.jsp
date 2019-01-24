<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${pageContext.response.locale}"/>
<fmt:setBundle basename="bundle.errorPage"/>
<html>
    <head>
        <title><fmt:message key="title"/></title>
    </head>
    <body>
        <fmt:message key="errorMessage"/>
        <form>
            <fmt:message key="errorInfo"/>. <br>
            <fmt:message key="status"/>:        ${errorStatus}<br>
            <fmt:message key="message"/>:       ${message}<br>
            <fmt:message key="exceptionType"/>: ${exception_type}<br>
            <fmt:message key="exception"/>:     ${exception}<br>
            <fmt:message key="request_Uri"/>:   ${request_uri}<br>
            <fmt:message key="reason"/>:        ${errorReason}<br><br>
        </form>
        <a href="/course/">
            <fmt:message key="allCourses"/>
        </a>
    </body>
</html>

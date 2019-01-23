<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${pageContext.response.locale}"/>
<fmt:setBundle basename="bundle.errorPage"/>
<html>
<head>
    <title><fmt:message key="title"/></title>
    <style> <%@include file="/theme/css/main.css"%> </style>
</head>
<body>
    <div class="error"><fmt:message key="errorMessage"/></div>
    <form> <center>
        <fmt:message key="errorInfo"/>. <br>
        <fmt:message key="status"/>:<%=request.getAttribute("errorStatus")%><br>
        <fmt:message key="message"/>:<%=request.getAttribute("message")%><br>
        <fmt:message key="exceptionType"/>:<%=request.getAttribute("exception_type")%><br>
        <fmt:message key="exception"/>:<%=request.getAttribute("exception")%><br>
        <fmt:message key="request_Uri"/>:<%=request.getAttribute("request_uri")%><br><br>
        <fmt:message key="reason"/>:<%=request.getAttribute("errorReason")%><br><br>
    </center> </form>
    <form action="/course" method="get">
        <div class="input-group">
            <center> <button><fmt:message key="allCourses"/></button> </center>
        </div>
    </form>
</body>
</html>

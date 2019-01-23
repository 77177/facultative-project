<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${pageContext.response.locale}"/>
<fmt:setBundle basename="bundle.errorPage"/>
<html>
<head>
    <title>Something bad happen</title>
    <style> <%@include file="/theme/css/main.css"%> </style>
</head>
<body>
    <div class="error">You have tried to do something prohibited or something unpredictable.</div>
    <form> <center>
    Error info. <br>
    status:<%=request.getAttribute("errorStatus")%><br>
    message:<%=request.getAttribute("message")%><br>
    exception_type:<%=request.getAttribute("exception_type")%><br>
    exception:<%=request.getAttribute("exception")%><br>
    request_uri:<%=request.getAttribute("request_uri")%><br><br>
    <a href="/course">all courses</a>
    reason:<%=request.getAttribute("errorReason")%><br><br>
    </center> </form>
    <form action="/course">
        <div class="input-group">
            <center> <button>All courses</button> <center>
        </div>
    </form>
</body>
</html>

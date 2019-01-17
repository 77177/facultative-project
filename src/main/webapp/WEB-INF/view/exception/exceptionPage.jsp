<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="com.epam.lab.group1.facultative.dto.ErrorDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Object errorObject = request.getAttribute("error");
    ErrorDto error = errorObject != null ? (ErrorDto)errorObject : null;
%>
<html>
    <head>
        <title>facultative</title>
    </head>
    <body>
        Error. <br>
        <%
            out.print("type: " + error.getType());
            out.print("message: " + error.getMessage());
        %>
        <div><a href="/course">all courses</a></div><br>
        <sec:authorize access="isAuthenticated()">
            <div><a href="/user/profile">my profile</a></div>
        </sec:authorize>
    </body>
</html>

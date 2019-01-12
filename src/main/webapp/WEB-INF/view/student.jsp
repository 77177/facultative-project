<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.epam.lab.group1.facultative.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Students</title>
    </head>
    <body>
        Students page
        <c:import url="header.jsp"/>
        <%
            User student = (User) request.getAttribute("student");
            out.print(student);
        %>
    </body>
</html>

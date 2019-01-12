<%@ page import="com.epam.lab.group1.facultative.model.User" %>
<%@ page import="java.util.Optional" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Students</title>
</head>
<body>
Students page
<%
Optional<User> user = (Optional<User>) request.getAttribute("student");
out.print(user);
%>
</body>
</html>

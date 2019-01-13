<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Something bad happen</title>
</head>
<body>
    you have tried to do something prohibited or something unpredictable. <br><br><br>
    Error info. <br>
    status:<%=request.getAttribute("errorStatus")%><br>
    reason:<%=request.getAttribute("errorReason")%><br><br>
    <a href="/course">All courses</a>
</body>
</html>

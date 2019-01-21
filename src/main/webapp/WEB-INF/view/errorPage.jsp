<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Something bad happen</title>
</head>
<body>
    you have tried to do something prohibited or something unpredictable. <br><br><br>
    Error info. <br>
    status:<%=request.getAttribute("errorStatus")%><br>
    message:<%=request.getAttribute("message")%><br>
    exception_type:<%=request.getAttribute("exception_type")%><br>
    exception:<%=request.getAttribute("exception")%><br>
    request_uri:<%=request.getAttribute("request_uri")%><br><br>
    <a href="/course">all courses</a>
</body>
</html>

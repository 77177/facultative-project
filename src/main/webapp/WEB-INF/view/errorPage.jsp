<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    reason:<%=request.getAttribute("errorReason")%><br><br>
    </center> </form>
    <form action="/course">
        <div class="input-group">
            <center> <button>All courses</button> <center>
        </div>
    </form>
</body>
</html>

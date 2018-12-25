<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Students</title>
</head>
<body>
Students page
<jsp:useBean id="student" class="com.epam.lab.group1.facultative.model.Student" scope="page"></jsp:useBean>
<jsp:getProperty name="student" property="name"/>

</body>
</html>

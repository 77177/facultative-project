<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>header</title>
    </head>
    <body>
        <div>
            <a href="/course">all courses</a>
        </div>
        <c:import url="template/userZone.jsp"/>
    </body>
</html>

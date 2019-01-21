<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="com.epam.lab.group1.facultative.security.SecurityContextUser" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    SecurityContextUser principal = null;
%>
<sec:authorize access="isAuthenticated()">
    <%
        principal = (SecurityContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    %>
</sec:authorize>

<html>
    <head>
        <title>Create Course Page</title>
        <style> <%@include file="/theme/css/main.css"%> </style>
    </head>
    <body>
        <%
            int tutorId = (int) request.getAttribute("tutorId");
            Object errorMessageObject = request.getAttribute("error");
        %>
        <sec:authorize access="isAuthenticated()">
            <%
                if (!principal.isStudent()) {%>
            <h2>Create Course Page</h2>
            <%if(errorMessageObject != null) {
                out.print(errorMessageObject.toString());
            }%>
            <form method="post" action="/course/action/create/">
                Course name:
                <input type="text" name="name" minlength="1" required><br>
                Starting date:
                <input type="date" name="startingDate" required><br>
                Finishing date:
                <input type="date" name="finishingDate" required><br>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="tutorId" value="<%=tutorId%>"/>
                <input type="submit" value="Submit">
            </form>
            <%
            }
            %>
        </sec:authorize>
        <br><br>
    </body>
</html>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.epam.lab.group1.facultative.security.SecurityContextUser" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${pageContext.response.locale}"/>
<fmt:setBundle basename="bundle.createCourse"/>
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
        <title><fmt:message key="title"/></title>
        <style> <%@include file="/theme/css/main.css"%> </style>
    </head>
    <body>
        <%
            int tutorId = (int) request.getAttribute("tutorId");
            Object errorMessageObject = request.getAttribute("errorMessage");
        %>
        <a href="/courses">all courses</a>
        <form action="/course/action/create/<%=principal.getUserId()%>" method="get">
            <select name="locale">
                <option value="ru_RU">Русский</option>
                <option value="en_US">English</option>
                <option value="es_ES">Español</option>
            </select>
            <input type="submit" value="change language"/>
        </form>
        <sec:authorize access="isAuthenticated()">
            <%
                if (!principal.isStudent()) {%>
                    <h2><fmt:message key="title"/></h2>
                    <%if(errorMessageObject != null) {
                        out.print(errorMessageObject.toString());
                    }%>
                    <form method="post" action="/course/action/create/">
                        <fmt:message key="courseName"/>:
                        <input type="text" name="name" minlength="1" required><br>
                        <fmt:message key="startDate"/>:
                        <input type="date" name="startingDate" required><br>
                        <fmt:message key="finishDate"/>:
                        <input type="date" name="finishingDate" required><br>
                        <input type="radio" name="active" value="true" required><fmt:message key="active"/><br>
                        <input type="radio" name="active" value="false" required><fmt:message key="closed"/><br>
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

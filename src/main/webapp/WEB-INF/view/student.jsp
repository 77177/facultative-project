<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="com.epam.lab.group1.facultative.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="com.epam.lab.group1.facultative.security.SecurityContextUser" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Object courseListObject = request.getParameter("courseList");
    if (courseListObject != null) {
        List<Course> courseList = (List<Course>) courseListObject;
        pageContext.setAttribute("courseList", courseList);
    }

    Object studentObject = request.getAttribute("student");
    if (studentObject != null) {
        User student= (User) studentObject;
        pageContext.setAttribute("student", student);
    }
%>
<html>
    <head>
        <title>Students</title>
    </head>
    <body>
        Students page
        <c:import url="header.jsp"/>

    </body>
</html>

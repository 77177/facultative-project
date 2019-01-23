<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="com.epam.lab.group1.facultative.security.SecurityContextUser" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${pageContext.response.locale}"/>
<fmt:setBundle basename="bundle.course"/>
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
        <title>Courses</title>
        <style>
            <%@include file="/theme/css/main.css"%>
        </style> <style>
            <%@include file="/theme/css/table.css"%>
        </style>
    </head>
    <body>
        <div class="header">
            <h2><fmt:message key="allCourses"/></h2>
        </div>
            <c:import url="header.jsp"/>

            <br>
            <form action="/course/" method="get">
                <select name="locale">
                  <option value="ru_RU">ru_RU</option>
                  <option value="en_US">en_US</option>
                  <option value="es_ES">es_ES</option>
                </select>
                <input type="submit" value="change language"/>
            </form>
        <%
            Object listObject = request.getAttribute("courseList");
            if (listObject != null) {
                if (listObject instanceof List) {
                    %>
                    <table style="border: 2px solid black; border-spacing: 7px 7px">
                        <tr>
                            <th>Title</th>
                            <th>Starting date</th>
                            <th>Finishing date</th>
                        </tr>
                        <%
                            List<Course> list = (List<Course>) listObject;
                            for (Course course : list) {
                                %>
                                <tr>
                                    <td><%= course.getName()%></td>
                                    <td><%=
                                        course.getStartingDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))%></td>
                                    <td><%=
                                        course.getFinishingDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))%></td>
                                    <td><a href="/course/<%=course.getId()%>">info</a></td>
                                </tr>
                                <%
                            }
                        %>
                    </table>
                    <br><br>
                    <%
                }
            } else { %>
                <p>There is not a single course.</p>
        <% } %> </center> </form>
    </body>
</html>
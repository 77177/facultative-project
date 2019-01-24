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
    List<Course> courseList = (List) request.getAttribute("courseList");
    int pageNumber = (int) request.getAttribute("pageNumber");
    int pageSize = 10;
%>
<sec:authorize access="isAuthenticated()">
    <%
        principal = (SecurityContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    %>
</sec:authorize>
<html>
    <head>
        <title><fmt:message key="title"/></title>
        <style>
            <%@include file="/theme/css/main.css"%>
        </style>
        <style>
            <%@include file="/theme/css/table.css"%>
        </style>
        <%--<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">--%>
    </head>
    <body>
        <div class="header jumbotron">
            <h2><fmt:message key="allCourses"/></h2>
        </div>
        <c:import url="header.jsp"/>
        <br>
        <form action="/course/" method="get">
            <select name="locale">
                <option value="ru_RU">Русский</option>
                <option value="en_US">English</option>
                <option value="es_ES">Español</option>
            </select>
            <input type="submit" value="change language"/>
        </form>
        <%
            if (!courseList.isEmpty()) {
            %>
            <table class="table-striped table-hover col-sm-12">
                <tr>
                    <th><fmt:message key="title"/></th>
                    <th><fmt:message key="startDate"/></th>
                    <th><fmt:message key="finishDate"/></th>
                    <th></th>
                </tr>
                <%
                    for (Course course : courseList) {
                    %>
                    <tr>
                        <td><%= course.getName()%></td>
                        <td><%= course.getStartingDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))%></td>
                        <td><%= course.getFinishingDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))%></td>
                        <td><a href="/course/<%=course.getId()%>">info</a></td>
                    </tr>
                    <%
                    }
                %>
            </table>
            <br><br>
            <ul class="pagination">
                <%
                    if (pageNumber > 0) {
                    %>
                    <li class="page-item">
                        <a class="page-link" href="/course/?page=${pageNumber - 1}">previous 10</a>
                    </li>
                    <%
                    }
                %>
                <li class="page-item">
                    <a class="page-link" href="#"> ... </a>
                </li>
                <%
                    if (courseList.size() == pageSize) {
                    %>
                    <li class="page-item">
                        <a class="page-link" href="/course/?page=${pageNumber + 1}">next 10</a>
                    </li>
                    <%
                    }
                %>
            </ul>
            <%
            } else { %>
                <p><fmt:message key="noCourses"/></p>
        <% } %> </center> </form>
    </body>
</html>
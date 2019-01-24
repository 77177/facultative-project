<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.epam.lab.group1.facultative.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page import="java.util.Collections" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${pageContext.response.locale}"/>
<fmt:setBundle basename="bundle.student"/>
<%
    Object userObject = request.getAttribute("user");
    User user = userObject != null ? (User) userObject : null;

    Object courseListObject = request.getAttribute("courseList");
    List<Course> courseList = courseListObject != null ? (List) courseListObject : Collections.emptyList();

    int pageNumber = (int) request.getAttribute("pageNumber");
    int pageSize = 10;
%>
<html>
    <head>
        <title><fmt:message key="title"/></title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
              integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
              crossorigin="anonymous"/>
    </head>
    <body>
        <h2><fmt:message key="title"/></h2>

        <a href="/course"><fmt:message key="backToCourses"/></a>

        <form action="/user/profile" method="get">
            <select name="locale">
                <option value="ru_RU">Русский</option>
                <option value="en_US">English</option>
                <option value="es_ES">Español</option>
            </select>
            <input type="submit" value="change language"/>
        </form>

        <sec:authorize access="isAuthenticated()">
            <h3>
                <fmt:message key="hello"/>, <%=user.getFirstName() + " " + user.getLastName()%>
            </h3>
            <form method="post" action="/logout">
                <sec:csrfInput/>
                <button> <fmt:message key="logout"/>
            </form>
        </sec:authorize>

        <sec:authorize access="!isAuthenticated()">
            <a href="/authenticator/login"> <fmt:message key="login"/> </a>
        </sec:authorize>
        <%
            if (courseList.isEmpty()) {
                %>
                <div><fmt:message key="noCoursesMessage"/></div>
                <%
                } else {
                %>
                    <form>
                        <fmt:message key="yourCourses"/>: </>
                    </form>

                    <table>
                        <tr>
                            <th><fmt:message key="courseName"/></th>
                            <th><fmt:message key="start"/></th>
                            <th><fmt:message key="finish"/></th>
                            <th><fmt:message key="feedback"/></th>
                        </tr>
                        <%
                            for (Course course : courseList) {
                            %>
                            <tr>
                                <td><% out.println(course.getName());%></td>
                                <td><% out.println(course.getStartingDate());%></td>
                                <td><% out.println(course.getFinishingDate());%></td>
                                <td><a href="/course/<%=course.getId()%>">course info</a></td>
                                <td><a href="/feedback/user/<%=user.getId()%>/course/<%=course.getId()%>/"><fmt:message key="seeFeedback"/></a></td>
                            </tr>
                            <%
                            }
                        %>
                    </table>

                    <ul class="pagination">
                        <%
                            if (pageNumber > 0) {
                                %>
                                <li class="page-item">
                                    <a class="page-link" href="/user/profile?page=${pageNumber - 1}">previous 10</a>
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
                                    <a class="page-link" href="/user/profile?page=${pageNumber + 1}">next 10</a>
                                </li>
                                <%
                            }
                        %>
                    </ul>
                <%
                }
        %>
    </body>
</html>

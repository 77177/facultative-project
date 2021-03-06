<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.epam.lab.group1.facultative.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${pageContext.response.locale}"/>
<fmt:setBundle basename="bundle.common"/>
<%
    Locale locale = pageContext.getResponse().getLocale();
    Object userObject = request.getAttribute("user");
    User user = userObject != null ? (User) userObject : null;

    Object courseListObject = request.getAttribute("courseList");
    List<Course> courseList = courseListObject != null ? (List) courseListObject : Collections.emptyList();

    int pageNumber = (int) request.getAttribute("pageNumber");
    int pageSize = 10;

    String changeLanguageLink = "/user/profile";
%>
<html>
    <head>
        <title>
            <fmt:bundle basename = "bundle.student">
                <fmt:message key="title"/>
            </fmt:bundle>
        </title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="jumbotron">
            <fmt:bundle basename = "bundle.student">
                <h2><fmt:message key="title"/></h2>
                <sec:authorize access="isAuthenticated()">
                    <h4>
                        <fmt:message key="message.hello"/>, <%=user.getFirstName() + " " + user.getLastName()%>
                    </h4>
                </sec:authorize>
            </fmt:bundle>
        </div>
        <nav class="navbar navbar-expand-sm bg-light">
            <ul class="navbar-nav">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                        language
                    </a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="<%out.print(changeLanguageLink);%>?locale=ru_RU">Русский</a>
                        <a class="dropdown-item" href="<%out.print(changeLanguageLink);%>?locale=en_US">English</a>
                        <a class="dropdown-item" href="<%out.print(changeLanguageLink);%>?locale=es_ES">Español</a>
                    </div>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/course/"
                       data-toggle="allCourses" data-placement="top" title="Back to the main facultative page!">
                        <fmt:message key="button.allCourses"/>
                    </a>
                </li>
                <li class="nav-item">
                    <sec:authorize access="!isAuthenticated()">
                        <a class="nav-link" href="/authenticator/login">
                            <fmt:message key="button.login"/>
                        </a>
                    </sec:authorize>
                </li>
                <sec:authorize access="isAuthenticated()">
                    <form class="form-inline justify-content-end" method="post" action="/logout">
                        <button type="submit" class="btn btn-warning">
                            <fmt:message key="button.logout"/>
                        </button>
                        <sec:csrfInput/>
                    </form>
                </sec:authorize>
            </ul>
        </nav>
        <div class="row">
            <div class="col-sm-1"></div>
            <div class="col">
            <%
                if (courseList.isEmpty()) {
                    %>
                    <div>
                        <fmt:bundle basename = "bundle.student">
                            <fmt:message key="message.noCourses"/>
                        </fmt:bundle>
                        <a class="nav-link btn btn-outline-primary btn-lg" href="/course/"
                           data-toggle="allCourses" data-placement="top" title="Back to the main facultative page!">
                            <fmt:message key="button.allCourses"/>
                        </a>
                    </div>
                    <%
                    } else {
                    %>
                    <fmt:bundle basename = "bundle.student">
                    <h5><fmt:message key="message.myCourses"/>:</h5>
                    </fmt:bundle>
                    <table class="table-striped table-hover col">
                        <fmt:bundle basename = "bundle.student">
                        <caption><fmt:message key="message.myCourses"/></caption>
                        </fmt:bundle>
                        <thead>
                        <tr>
                            <th><fmt:message key="form.title"/></th>
                            <th><fmt:message key="form.startDate"/></th>
                            <th><fmt:message key="form.finishDate"/></th>
                            <th><fmt:message key="word.info"/></th>
                            <th><fmt:message key="word.feedback"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            for (Course course : courseList) {
                        %>
                        <tr>
                            <td><%=course.getName()%></td>
                            <td><%=course.getStartingDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy").withLocale(locale))%></td>
                            <td><%=course.getFinishingDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy").withLocale(locale))%></td>
                            <td><a href="/course/<%=course.getId()%>"><fmt:message key="word.info"/></a></td>
                            <td>
                                <a href="/feedback/user/<%=user.getId()%>/course/<%=course.getId()%>/">
                                <fmt:bundle basename = "bundle.courseInfo">
                                    <fmt:message key="message.feedback"/>
                                </fmt:bundle>
                                </a>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>
                    <ul class="pagination">
                        <%
                            if (pageNumber > 0) {
                        %>
                        <li class="page-item">
                            <a class="page-link" href="/course/?page=${pageNumber - 1}">
                                <fmt:message key="button.previous"/> 10
                            </a>
                        </li>
                        <%
                            }
                            if (courseList.size() == pageSize) {
                        %>
                        <li class="page-item">
                            <a class="page-link" href="/course/?page=${pageNumber + 1}">
                                <fmt:message key="button.next"/> 10
                            </a>
                        </li>
                        <%
                            }
                        %>
                    </ul>
                    <%
                }
            %>
            </div>
            <div class="col-sm-1"></div>
        </div>
    </body>
</html>

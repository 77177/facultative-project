<%@ page import="com.epam.lab.group1.facultative.security.SecurityContextUser" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page import="com.epam.lab.group1.facultative.model.User" %>
<%@ page import="com.epam.lab.group1.facultative.model.FeedBack" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${pageContext.response.locale}"/>
<fmt:setBundle basename="bundle.feedback"/>
<fmt:setBundle basename="bundle.common"/>
<%
    SecurityContextUser principal = null;
    Course course = (Course) request.getAttribute("course");
    User student = (User) request.getAttribute("student");
    FeedBack feedBack = (FeedBack) request.getAttribute("feedback");
    String changeLanguageLink = "/feedback/user/" + student.getId() + "/course/" + course.getId();
%>
<sec:authorize access="isAuthenticated()">
    <%
        principal = (SecurityContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    %>
</sec:authorize>
<html>
    <head>
        <title>
            <fmt:bundle basename = "bundle.feedback">
                <fmt:message key="title"/>
            </fmt:bundle>
        </title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="jumbotron">
            <fmt:bundle basename = "bundle.feedback">
                <h2>
                    <fmt:message key="title"/> <%=student.getFirstName() + " " + student.getLastName()%>
                    <fmt:message key="word.forCourse"/> <%=course.getName()%>
                </h2>
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
                    <sec:authorize access="isAuthenticated()">
                        <a class="nav-link" href="/user/profile">
                            <fmt:message key="message.myProfile"/>
                        </a>
                    </sec:authorize>
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
                <fmt:bundle basename = "bundle.feedback">
                <sec:authorize access="hasAnyAuthority('tutor')">
                    <form action="/feedback/" method="post">
                        <span><%=student.getFullName()%></span>,  <span><%=course.getName()%></span><br>
                        <textarea name="text" rows="4" cols="50"
                                <%out.print(course.getTutorId() != principal.getUserId() ? "readonly" : "");%>>${feedback.text}</textarea>
                        <br/>
                        <fmt:message key="word.mark"/>
                        <br/>
                        <input name="mark" type="number" min="0" max="5" value="${feedback.mark}"
                                <%out.print(course.getTutorId() != principal.getUserId() ? "readonly" : "");%>/>
                        <input type="hidden" name="courseId" value="${feedback.courseId}">
                        <input type="hidden" name="studentId" value="${feedback.studentId}">
                        <sec:csrfInput/>
                        <br/>
                        <%if (course.getTutorId() == principal.getUserId()) {%>
                        <br/>
                        <input type="submit" value="submit"/>
                        <%}%>
                    </form>
                </sec:authorize>
                <sec:authorize access="hasAuthority('student')">
                    <h6>
                        <fmt:message key="message.feedbackForCourse"/> <span><%=course.getName()%></span>:
                    </h6>
                    <div class="card col-5">
                        <div class="card-body"><%=feedBack.getText()%></div>
                    </div>
                    <fmt:message key="message.yourMarkForCourse"/>: <%=feedBack.getMark()%>
                </sec:authorize>
                </fmt:bundle>
            </div>
            <div class="col-sm-1"></div>
        </div>
    </body>
</html>

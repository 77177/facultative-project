<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page import="com.epam.lab.group1.facultative.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.epam.lab.group1.facultative.security.SecurityContextUser" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="java.time.Period" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${pageContext.response.locale}"/>
<fmt:setBundle basename="bundle.common"/>
<%
    Locale locale = pageContext.getResponse().getLocale();
    SecurityContextUser principal = null;
    Course course = (Course)request.getAttribute("course");
    List<User> studentList = (List) request.getAttribute("studentList");
    String changeLanguageLink = "/course/" + course.getId();
%>
<sec:authorize access="isAuthenticated()">
    <%
        principal = (SecurityContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    %>
</sec:authorize>
<html>
    <head>
        <title>
            <fmt:bundle basename = "bundle.courseInfo">
                <fmt:message key="title"/>
            </fmt:bundle>
        </title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"></script>
        <script>
            $(document).ready(function(){
                $('[data-toggle="allCourses"]').tooltip();
            });
        </script>
        <script>
            $(document).ready(function(){
                $('[data-toggle="login"]').tooltip();
            });
        </script>
    </head>
    <body>
        <div class="jumbotron">
            <h2>
                <fmt:bundle basename = "bundle.courseInfo">
                    <fmt:message key="title"/>
                </fmt:bundle>
                : <%=course.getName()%>
            </h2>
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
                <li class="nav-item">
                    <sec:authorize access="isAuthenticated()">
                        <a class="nav-link" href="/user/profile">
                            <fmt:message key="message.myProfile"/>
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
                <div id="courseInfo">
                    <fmt:message key="word.tutor"/>: <span>${tutorName}</span><br/>
                    <fmt:message key="form.startDate"/>:
                    <span><%=course.getStartingDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy").withLocale(locale))%></span><br>
                    <fmt:message key="form.finishDate"/>:
                    <span><%=course.getFinishingDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy").withLocale(locale))%></span><br>
                    <fmt:message key="word.status"/>: <span><%=course.isActive()%></span><br>
                    <fmt:bundle basename = "bundle.courseInfo">
                        <fmt:message key="word.duration"/>:
                        <%
                            Period duration = Period.between(course.getStartingDate(), course.getFinishingDate());
                            if (duration.getDays() > 0) {
                                out.print(duration.getDays()); %> <fmt:message key="word.day"/><%
                            }
                            if (duration.getMonths() > 0) {
                                out.print(duration.getMonths()); %> <fmt:message key="word.month"/><%
                            }
                            if (duration.getYears() > 0) {
                                out.print(duration.getYears()); %> <fmt:message key="word.year"/><%
                            }
                        %>
                    </fmt:bundle>
                </div>
                <br>
                <sec:authorize access="hasAnyAuthority('student')">
                    <fmt:bundle basename = "bundle.courseInfo">
                    <div id="studentZone">
                        <%
                            if (principal.getCourseIdList().contains(course.getId())) {
                        %>
                        <p>
                            <a class="btn btn-outline-primary"
                               href="/feedback/user/<%=principal.getUserId()%>/course/<%=course.getId()%>">
                                <fmt:message key="message.feedback"/>
                            </a>
                        </p>
                        <p>
                            <!-- Button to Open the Modal -->
                            <button type="button" class="btn btn-outline-danger rtn-sm" data-toggle="modal"
                                    data-target="#leave">
                                <fmt:message key="option.leaveCourse"/>
                            </button>
                        </p>
                        <%
                        } else {%>
                            <p>
                                <a href="/user/<%=principal.getUserId()%>/course/<%=course.getId()%>/subscribe/">
                                    <fmt:message key="option.subscribe"/>
                                </a>
                            </p>
                        <%
                        }
                        %>
                    </div>
                    </fmt:bundle>
                </sec:authorize>

                <sec:authorize access="hasAnyAuthority('tutor')">
                    <div id="tutorZone">
                        <fmt:bundle basename = "bundle.courseInfo">
                        <%if (principal.getUserId() == course.getTutorId()) {%>
                        <div class="btn-group">
                            <a class="btn btn-outline-primary"  href="/course/action/edit/<%=course.getId()%>/">
                                <fmt:message key="option.editCourse"/>
                            </a>

                            <!-- Button to Open the Modal -->
                            <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                                    data-target="#delete">
                                <fmt:message key="option.deleteCourse"/>
                            </button>
                            <%}%>
                        </div>
                        <br><br>
                        <h3><fmt:message key="word.studentList"/></h3>
                        </fmt:bundle>
                        <table  class="table-striped table-hover col">
                            <fmt:bundle basename = "bundle.courseInfo">
                                <caption><fmt:message key="word.studentList"/></caption>
                            </fmt:bundle>
                            <thead>
                                <tr>
                                    <th><fmt:message key="form.firstName"/></th>
                                    <th><fmt:message key="form.lastName"/></th>
                                    <th><fmt:message key="word.feedback"/></th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    if (studentList != null && !studentList.isEmpty()) {
                                        for (User user : studentList) {
                                        %>
                                        <tr>
                                            <td><% out.println(user.getFirstName());%></td>
                                            <td><% out.println(user.getLastName());%></td>
                                            <%if(principal.getUserId() == course.getTutorId()){%>
                                            <td>
                                                <a href="/feedback/user/<%=user.getId()%>/course/<%=course.getId()%>">
                                                    <fmt:message key="word.feedback"/>
                                                </a>
                                            </td>
                                            <%}%>
                                        </tr>
                                        <%
                                        }
                                    }
                                %>
                            </tbody>
                        </table>
                    </div>
                </sec:authorize>
            </div>
            <div class="col-sm-1"></div>
        </div>
        <sec:authorize access="isAuthenticated()">
            <fmt:bundle basename = "bundle.courseInfo">
            <!-- The leave modal -->
            <div class="modal" id="leave">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title"><fmt:message key="option.leaveCourse"/></h4>
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                        </div>
                        <div class="modal-body ">
                            <fmt:message key="message.leaving"/>
                        </div>
                        <div class="modal-footer">
                            <a class="btn btn-danger"
                               href="/user/<%=principal.getUserId()%>/course/<%=course.getId()%>/leave/">
                                <fmt:message key="option.leaveCourse"/>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <!-- The delete modal -->
            <div class="modal" id="delete">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title"><fmt:message key="option.deleteCourse"/></h4>
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                        </div>
                        <div class="modal-body ">
                            <fmt:message key="message.deleting"/>
                        </div>
                        <div class="modal-footer">
                            <a class="btn btn-danger"
                               href="/course/action/delete/<%=course.getId()%>/">
                                <fmt:message key="option.deleteCourse"/>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            </fmt:bundle>
        </sec:authorize>
    </body>
</html>
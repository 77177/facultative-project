<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page import="com.epam.lab.group1.facultative.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="com.epam.lab.group1.facultative.security.SecurityContextUser" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="java.time.Period" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${pageContext.response.locale}"/>
<fmt:setBundle basename="bundle.courseInfo"/>
<%
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
        <title><fmt:message key="title"/></title>
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
            <h2><fmt:message key="title"/>: <%=course.getName()%></h2>
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
                        all courses
                    </a>
                </li>
                <li class="nav-item">
                    <sec:authorize access="isAuthenticated()">
                        <a class="nav-link" href="/user/profile">My Profile</a>
                    </sec:authorize>
                </li>
                <sec:authorize access="isAuthenticated()">
                    <form class="form-inline" method="post" action="/logout">
                        <input class="btn btn-warning" type="submit" value="Logout"/>
                        <sec:csrfInput/>
                    </form>
                </sec:authorize>
            </ul>
        </nav>
        <div class="row">
            <div class="col-sm-1"></div>
            <div class="col">
                <div id="courseInfo">
                    <fmt:message key="tutor"/>: <span>${tutorName}</span><br/>
                    <fmt:message key="courseStart"/>: <span><%=course.getStartingDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))%></span><br>
                    <fmt:message key="courseFinish"/>: <span><%=course.getFinishingDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))%></span><br>
                    <fmt:message key="duration"/>:
                    <span>
                        <%=Period.between(course.getStartingDate(), course.getFinishingDate()).getDays()%> days.
                    </span>
                </div>
                <sec:authorize access="hasAnyAuthority('student')">
                    <div id="studentZone">
                        <%
                            if (principal.getCourseIdList().contains(course.getId())) {
                        %>
                        <p>
                            <a class="btn btn-outline-primary"
                               href="/feedback/user/<%=principal.getUserId()%>/course/<%=course.getId()%>">
                                <fmt:message key="feedbackMessage"/>
                            </a>
                        </p>
                        <p>
                            <!-- Button to Open the Modal -->
                            <button type="button" class="btn btn-outline-danger rtn-sm" data-toggle="modal"
                                    data-target="#leave">
                                <fmt:message key="leaveCourse"/>
                            </button>
                        </p>
                        <%
                        } else {
                        %>
                        <p>
                            <fmt:message key="subscribeMessage"/> <%=course.getName()%> <br>
                            <a href="/user/<%=principal.getUserId()%>/course/<%=course.getId()%>/subscribe/">
                                <fmt:message key="subscribe"/>
                            </a>
                        </p>

                        <%
                            }
                        %>
                    </div>
                </sec:authorize>

                <sec:authorize access="hasAnyAuthority('tutor')">
                    <div id="tutorZone">
                        <div class="btn-group">
                            <a class="btn btn-outline-primary"  href="/course/<%=course.getId()%>/action/edit/<%=course.getTutorId()%>/">
                                <fmt:message key="editCourse"/>
                            </a><br><br>

                            <!-- Button to Open the Modal -->
                            <%if (principal.getUserId() == course.getTutorId()) {%>

                                <button type="button" class="btn btn-outline-danger rtn-sm" data-toggle="modal"
                                        data-target="#delete">
                                    <fmt:message key="deleteCourse"/>
                                </button>
                            <%}%>
                        </div>
                        <br>
                        <h3><fmt:message key="studentList"/></h3>
                        <table  class="table-striped table-hover col">
                            <caption><fmt:message key="studentList"/></caption>
                            <thead>
                                <tr>
                                    <th><fmt:message key="firstName"/></th>
                                    <th><fmt:message key="lastName"/></th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    for (User user : studentList) {
                                %>
                                <tr>
                                    <td><% out.println(user.getFirstName());%></td>
                                    <td><% out.println(user.getLastName());%></td>
                                    <td><a href="/feedback/user/<%=user.getId()%>/course/<%=course.getId()%>">
                                        <fmt:message key="seeFeedback"/></a></td>
                                </tr>
                                <%
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
            <!-- The leave modal -->
            <div class="modal" id="leave">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Leaving course</h4>
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                        </div>
                        <div class="modal-body ">
                            Are you sure that you want unsubscribe from this course?
                        </div>
                        <div class="modal-footer">
                            <a class="btn btn-danger"
                               href="/user/<%=principal.getUserId()%>/course/<%=course.getId()%>/leave/">
                                <fmt:message key="leaveCourse"/>
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
                            <h4 class="modal-title">Delete course</h4>
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                        </div>
                        <div class="modal-body ">
                            Do you want to delete the course?
                        </div>
                        <div class="modal-footer">
                            <a class="btn btn-danger"
                               href="/course/action/delete/<%=course.getId()%>/">
                                <fmt:message key="deleteCourse"/>
                            </a>
                        </div>
                    </div>
                </div>
            </div>

        </sec:authorize>
    </body>
</html>
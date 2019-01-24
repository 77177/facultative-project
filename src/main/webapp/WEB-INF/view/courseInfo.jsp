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
%>
<sec:authorize access="isAuthenticated()">
    <%
        principal = (SecurityContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    %>
</sec:authorize>
<html>
    <head>
        <title><fmt:message key="title"/></title>
    </head>
    <body>
        <h2>
            <fmt:message key="title"/>
        </h2>

        <c:import url="header.jsp"/>

        <form action="/course/<%=course.getId()%>" method="get">
            <select name="locale">
                <option value="ru_RU">Русский</option>
                <option value="en_US">English</option>
                <option value="es_ES">Español</option>
            </select>
            <input type="submit" value="change language"/>
        </form>

        <h2><%=course.getName()%></h2>

        <div id="courseInfo">
            <fmt:message key="tutor"/>: <span>${tutorName}</span><br/>
            <fmt:message key="courseStart"/>: <span><%=course.getStartingDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))%></span><br>
            <fmt:message key="courseFinish"/>: <span><%=course.getFinishingDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))%></span><br>
            <fmt:message key="duration"/>:
            <span><%=Period.between(course.getStartingDate(), course.getFinishingDate()).getDays()%> days.</span>
        </div>

        <sec:authorize access="hasAnyAuthority('student')">
            <div id="studentZone">
                <%
                    if (principal.getCourseIdList().contains(course.getId())) {
                    %>
                        <p>
                            <fmt:message key="youParticipateInCourse"/> <%=course.getName()%> <br>
                            <a href="/user/<%=principal.getUserId()%>/course/<%=course.getId()%>/leave/">
                                <fmt:message key="leaveCourse"/>
                            </a>
                        </p>
                        <p>
                            <fmt:message key="feedbackMessage"/>
                            <a href="/feedback/user/<%=principal.getUserId()%>/course/<%=course.getId()%>/">
                                <fmt:message key="seeFeedback"/>
                            </a>
                        </p>
                    <%
                    } else {
                    %>
                        <p>
                            <fmt:message key="subscribeMessage"/> <%=course.getName()%> <br>
                            <a href="/user/<%=principal.getUserId()%>/course/<%=course.getId()%>/subscribe /">
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
                <a href="/course/<%=course.getId()%>/action/edit/<%=course.getTutorId()%>/">
                    <fmt:message key="editCourse"/>
                </a><br>

                <a href="/course/action/delete/<%=course.getId()%>/">
                    <fmt:message key="deleteCourse"/>
                </a>
                <br><br>

                <h3><fmt:message key="studentList"/></h3>
                <table style="border: 2px double black; border-spacing: 7px 7px">
                    <tr>
                        <th><fmt:message key="firstName"/></th>
                        <th><fmt:message key="lastName"/></th>
                    </tr>
                    <%
                        for (User user : studentList) {
                            %>
                            <tr>
                                <td><% out.println(user.getFirstName());%></td>
                                <td><% out.println(user.getLastName());%></td>
                                <td><a href="/feedback/user/<%=user.getId()%>/course/<%=course.getId()%>/">
                                    <fmt:message key="seeFeedback"/></a></td>
                            </tr>
                            <%
                        }
                    %>
                </table>
            </div>
        </sec:authorize>
    </body>
</html>
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
<%
    SecurityContextUser principal = null;
    Course course = (Course) request.getAttribute("course");
    User student = (User) request.getAttribute("student");
    FeedBack feedBack = (FeedBack) request.getAttribute("feedback");
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
        <c:import url="header.jsp"/>

        <form action="/feedback/user/<%=student.getId()%>/course/<%=course.getId()%>" method="get">
            <select name="locale">
                <option value="ru_RU">Русский</option>
                <option value="en_US">English</option>
                <option value="es_ES">Español</option>
            </select>
            <input type="submit" value="change language"/>
        </form>
        <br/>

        <sec:authorize access="hasAnyAuthority('tutor')">
            <form action="/feedback/" method="post">
                <span><%=student.getFullName()%></span>, <fmt:message key="feedbackForCourse"/> <span><%=course.getName()%></span><br>

                <textarea name="text" rows="4" cols="50"
                        <%out.print(course.getTutorId() != principal.getUserId() ? "readonly" : "");%>>
                        ${feedback.text}
                </textarea>
                <br/>
                <fmt:message key="mark"/>
                <br/>
                <input name="mark" type="number" min="0" max="5" value="${feedback.mark}"
                        <%out.print(course.getTutorId() != principal.getUserId() ? "readonly" : "");%>/>
                <input type="hidden" name="courseId" value="${feedback.courseId}">
                <input type="hidden" name="studentId" value="${feedback.studentId}">
                <sec:csrfInput/>
                <br/>
                <%if (course.getTutorId() == principal.getUserId()) {%>
                    <input type="submit" value="submit"/>
                <%}%>
            </form>
        </sec:authorize>

        <sec:authorize access="hasAuthority('student')">
            <fmt:message key="feedbackForCourse"/> <span><%=course.getName()%></span>:<br>
            <div>
                <p>
                    <%=feedBack.getText()%>
                </p>
                <p>
                    <br><br>
                    <fmt:message key="yourMarkForCourse"/>: <%=feedBack.getMark()%>
                </p>
            </div>
        </sec:authorize>
    </body>
</html>

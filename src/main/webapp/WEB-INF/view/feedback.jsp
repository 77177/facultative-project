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
        <title>Feedback</title>
    </head>
    <body>
        <c:import url="header.jsp"/>
        <br/>
        <sec:authorize access="hasAnyAuthority('tutor')">
            <%--@elvariable id="feedback" type="com.epam.lab.group1.facultative.model.FeedBack"--%>
            <form:form action="/feedback/" method="post" modelAttribute="feedback">
                Feedback for <span><%=student.getFullName()%></span> for course <span><%=course.getName()%></span><br>
                <br/>
                <form:textarea path="text" rows="4" cols="50"
                               readonly="<%=!(course.getTutorId()==principal.getUserId())%>"></form:textarea>
                <br/>
                Mark
                <br/>
                <form:input path="mark" readonly="<%=!(course.getTutorId()==principal.getUserId())%>"/>
                <form:hidden path="courseId"/>
                <form:hidden path="studentId"/>
                <br/>
                <%if(course.getTutorId()==principal.getUserId()) {%>
                <input type="submit" value="submit"/>
                <%}%>
            </form:form>
        </sec:authorize>
        <sec:authorize access="hasAuthority('student')">
            Feedback for course <span><%=course.getName()%></span>:<br>
            <div>
                <p>
                    <%=feedBack.getText()%>
                </p>
                <p>
                    <br><br>
                    Your mark for this course: <%=feedBack.getMark()%>
                </p>
            </div>
        </sec:authorize>
    </body>
</html>

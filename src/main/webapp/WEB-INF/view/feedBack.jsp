<%@ page import="com.epam.lab.group1.facultative.security.SecurityContextUser" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="java.io.IOException" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    SecurityContextUser principal = null;
    String courseIdObject = request.getParameter("courseId");
    int courseId = courseIdObject == null ? 0 : Integer.parseInt(courseIdObject.toString());
%>
<%!
    void printFeedback(Object request, JspWriter out) throws IOException {
        if (request != null) {
            out.print(request);
        }
    }
%>
<sec:authorize access="isAuthenticated()">
    <%
        principal = (SecurityContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    %>
</sec:authorize>
<html>
    <head>
        <title>Feedbacks</title>
    </head>
    <body>
        Feedbacks page
        <c:import url="header.jsp"/>
        <br/>
        <form action="/feedback" method="post">
            <sec:authorize access="isAuthenticated()">
                <%
                    if (principal.isStudent()) {
                        %>
                            <textarea rows="4" cols="100" readonly>
                            <%
                               printFeedback(request.getAttribute("feedback"), out);
                            %>
                            </textarea><br><br>
                        <%
                    } else if (principal.getCourseIdList().contains(courseId)){
                        %>
                            <textarea rows="4" cols="100">
                            <%
                                printFeedback(request.getAttribute("feedback"), out);
                            %>
                            </textarea><br><br>
                        <%
                    } else {
                        %>
                            <textarea rows="4" cols="100" readonly>
                            <%
                                printFeedback(request.getAttribute("feedback"), out);
                            %>
                            </textarea><br><br>
                        <%
                    }
                %>
            </sec:authorize>
            <input type="submit" value="submit"/>
        </form>
    </body>
</html>

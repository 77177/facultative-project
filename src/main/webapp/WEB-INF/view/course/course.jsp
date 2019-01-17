<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Courses</title>
    </head>
    <body>
        <c:import url="../template/userZone.jsp"/> <br><br>
        <%
            Object listObject = request.getAttribute("courseList");
            if (listObject != null) {
                if (listObject instanceof List) { %>
                    <h4>All Courses</h4>
                    <table style="border: 2px solid black; border-spacing: 7px 7px">
                        <tr>
                            <th>CourseName</th>
                            <th>StartingDate</th>
                            <th>FinishingDate</th>
                        </tr> <%
                    List<Course> list = (List<Course>) listObject;
                    for (Course course : list) { %>
                        <tr>
                            <td><%= course.getName()%></td>
                            <td><%= course.getStartingDate()%></td>
                            <td><%= course.getFinishingDate()%></td>
                            <td><a href="/course/<%=course.getId()%>">course info</a></td>
                        </tr><%
                    }%>
                    </table><br><br> <%
                }
            } else {
                out.print("There is not a single course");
            }%>
    </body>
</html>
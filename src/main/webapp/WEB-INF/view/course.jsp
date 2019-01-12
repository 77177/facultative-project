<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.epam.lab.group1.facultative.model.Course" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Courses</title>
    </head>
    <body>
        <h2>All Courses</h2>
        <c:import url="header.jsp"/>
        <%
            Object listObject = request.getAttribute("courseList");
            if (listObject != null) {
                if (listObject instanceof List) { %>
                    <table style="border: 2px solid black; border-spacing: 7px 7px">
                        <tr>
                            <th>CourseName</th>
                            <th>TutorId</th>
                            <th>StartingDate</th>
                            <th>FinishingDate</th>
                            <th>isActive</th>
                        </tr> <%
                    List<Course> list = (List<Course>) listObject;
                    for (Course course : list) { %>
                        <tr>
                            <td><%= course.getCourseName()%></td>
                            <td><%= course.getTutorId()%></td>
                            <td><%= course.getStartingDate()%></td>
                            <td><%= course.getFinishingDate()%></td>
                            <td><%= course.isActive()%></td>
                            <td><a href="/course/<%=course.getCourseId()%>">course info</a></td>
                        </tr><%
                    }%>
                    </table><br><br> <%
                }
            } else {
                out.print("There is not a single course");
            }%>
    </body>
</html>
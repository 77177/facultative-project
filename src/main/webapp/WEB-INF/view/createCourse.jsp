<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Course Page</title>
</head>
<body>
<h2>Create Course Page</h2>
<form method="post" action="/course/action/createCourse/">
    Course Name:
    <input type="text" name="courseName"><br>
   <%-- Starting date:
    <input type="date" name="startingDate" pattern="yyyy-MMM-dd"><br>
    Finishing date:
    <input type="date" name="finishingDate" pattern="yyyy-MMM-dd"><br>--%>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="submit" value="Submit">
</form>

<br><br>
</body>
</html>

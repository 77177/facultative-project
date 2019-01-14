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
   Starting date yyyy-mm-dd:
    <input type="text" name="startingDate" pattern="[0-9]{4}\-[0-9]{2}\-[0-9]{2}"><br>
    Finishing date yyyy-mm-dd:
    <input type="text" name="finishingDate" pattern="[0-9]{4}\-[0-9]{2}\-[0-9]{2}"><br>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="submit" value="Submit">
</form>

<br><br>
</body>
</html>

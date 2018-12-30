<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
    <head>
        <title>Login to facultative</title>
    </head>
    <body>
        Sign into the Facultative system.
        <form method="post" action="/login">
            Email:      <input type="text" name="name" placeholder="your email"/><br>
            Password:   <input type="password" name="password" placeholder="password"/><br>
            <button type="submit">Login in</button>
        </form>
        <br><br>
        Not registered?<br>
        <a href="/authenticator/registration">
            <button>Register</button>
        </a>
    </body>
</html>
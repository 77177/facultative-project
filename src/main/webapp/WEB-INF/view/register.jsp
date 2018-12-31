<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Facultative registration</title>
    </head>
    <body>
        New user. <br/>
        <form method="post" action="/authenticator/registration">
            First name:     <input type="text" name="firstName" placeholder="first name"/><br>
            Last name:      <input type="text" name="lastName" placeholder="last name"/><br>
            E-mail:          <input type="text" name="email" placeholder="e-mail"/><br>
            Password:       <input type="password" name="password" placeholder="password"/><br>
            Position:
            <select name="position">
                <option>student</option>
                <option>teacher</option>
            </select>
            <input type="hidden"
                   name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>
            <button type="submit">Register</button>
        </form>
        <br><br>
    </body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.core.userdetails.UserDetails" %>
<%
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    UserDetails userDetails = null;
    if (principal instanceof UserDetails) {
        userDetails = (UserDetails) principal;

    }
%>
<html>
    <head>
        <title>header</title>
    </head>
    <body>
    <a href="/course">all courses</a> <br><br>
        <%
            if (userDetails != null) { %>
                <h3>Hello, <%=userDetails.getUsername()%></h3>
                <a href="/myprofile">my profile</a>
                <br><br>
                <form method="post" action="/logout">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="submit" value="Logout"/>
                </form><%
            } else {
                %><a href="/authenticator/login">Login</a> <br><br> <%
            }
        %>
    </body>
</html>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>header</title>
        <style>
            </@include file="/theme/css/main.css"%>
        </style>
    </head>
    <body>
    <form action="/course">
        <div class="input-group">
            <center> <button>All courses</button> </center>
        </div>
    </form>
        <sec:authorize access="isAuthenticated()">
            <div class="header">
                <h3>Hello, <sec:authentication property="name"/></h3>
            </div>
            <form action="/user/profile">
                <div class="input-group">
                    <center> <button>My Profile</button> </center>
                </div>
            </form>
            <form method="post" action="/logout">
                <sec:csrfInput/>
                <div class="input-group">
                    <center> <button type="submit" value="Logout"> Logout </button> </center>
                </div>
            </form>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
            <a href="/authenticator/login">Login</a> <br><br>
        </sec:authorize>
    </body>
</html>

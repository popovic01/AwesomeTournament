<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
<title>Login</title>
</head>

<body>
    <h1>Login</h1>
    <form action="<c:url value="/auth/login" />" method="post">
        <input type="text" id="email" name="email"/>
        <input type="password" id="password" name="password"/>
        <input type="submit" value="login"/>
    </form>
</body>

</html>
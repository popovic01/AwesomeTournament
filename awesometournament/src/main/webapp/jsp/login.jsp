<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh; /* Altezza della viewport */
        }
        .login-form {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            width: 300px;
            padding: 20px;
            background-color: rgba(255, 255, 255, 0.7);
        }
        input[type=email], input[type=password] {
            width: calc(100% - 20px);
            padding: 10px;
            margin-bottom: 10px;
            border: none;
            border-radius: 5px;
        }
        input[type=submit] {
            width: 100%;
            padding: 10px;
            border: none;
            cursor: pointer;
        }
        .signup-text {
            text-align: center;
            margin-top: 15px;
        }
        p.error {
            color: red;
        }
    </style>
</head>
<body>
<div class="login-form">
    <form action="<c:url value="/auth/login" />" method="post">
        <h2 style="text-align: center">LOGIN</h2>
        <input type="email" name="email" placeholder="Email"><br/>
        <input type="password" name="password" placeholder="Password"><br/>
        <p class="error"><c:out value="${error}"/></p>
        <input type="submit" value="Login">
    </form>
    <p class="signup-text">Don't have an account? <a href="/auth/signup">Sign up</a></p>
</div>
</body>
</html>

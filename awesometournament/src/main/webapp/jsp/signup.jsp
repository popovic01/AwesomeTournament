<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Signup</title>

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
                height: 100vh; /* Height of the viewport */
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
    <form action="<c:url value="/auth/signup" />" method="post">
        <h2 style="text-align: center">SIGNUP</h2>
        <input type="email" id="email" name="email" placeholder="Email"/></br>
        <input type="password" id="password" name="password" placeholder="Password"/></br>
        <input type="password" id="passwordcheck" name="passwordcheck" placeholder="Repeat password"/></br>
        <p class="error"><c:out value="${error}"/></p>
        <input type="hidden" name="redirect" value="<c:out value="${redirect}"/>">
        <input type="submit" value="Signup"/>
    </form>
</body>

</html>
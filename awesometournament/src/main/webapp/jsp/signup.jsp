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
            height: 100vh;
            /* Height of the viewport */
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

        input[type=email],
        input[type=password] {
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

        input[type=submit].invalid {
            cursor: not-allowed;
            background-color: #cccccc;
        }

        .signup-text {
            text-align: center;
            margin-top: 15px;
        }

        p.error {
            color: red;
        }
    </style>
    <style>
        .passvalidator {
            background-color: #f8f9fa;
            border: 1px solid #dee2e6;
            border-radius: 5px;
            padding: 20px;
            width: 300px;
            margin: 0 auto;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .passvalidator ul {
            list-style-type: none;
            padding: 0;
            margin: 0;
        }

        .passvalidator li {
            background: #ffffff;
            border: 1px solid #dee2e6;
            border-radius: 3px;
            padding: 10px;
            margin-bottom: 10px;
            font-family: Arial, sans-serif;
            font-size: 14px;
            color: #495057;
            display: flex;
            align-items: center;
        }

        .passvalidator li::before {
            content: "✔";
            color: #28a745;
            font-weight: bold;
            display: inline-block;
            width: 20px;
            text-align: center;
            margin-right: 10px;
        }

        .passvalidator li.invalid::before {
            content: "✖";
            color: #dc3545;
        }
    </style>
</head>

<body>
    <form action="<c:url value=" /auth/signup" />" method="post">
    <h2 style="text-align: center">SIGNUP</h2>
    <input type="email" id="email" name="email" placeholder="Email" /></br>
    <input type="password" id="password" name="password" placeholder="Password" /></br>
    <div class="passvalidator">
        <ul>
            <li class="invalid" id="validchars">
                8 to 20 characters
            </li>
            <li class="invalid" id="validnumber">
                at least one number
            </li>
            <li class="invalid" id="validupper">
                at least one uppercase character
            </li>
        </ul>
    </div>
    <input type="password" id="passwordcheck" name="passwordcheck" placeholder="Repeat password" style="margin-top: 10px;"/></br>
    <p class="error">
        <c:out value="${error}" />
    </p>
    <p class="error" id="passwordmismatch" hidden>
        The passwords don't correspond!
    </p>
    <input type="hidden" name="redirect" value="<c:out value=" ${redirect}" />">
    <input type="submit" value="Signup" id="submit" class="invalid" disabled />
    </form>
</body>

<script>
    const validcharsElement = document.querySelector("#validchars");
    const validnumberElement = document.querySelector("#validnumber");
    const validupperElement = document.querySelector("#validupper");
    const submitElement = document.querySelector("#submit");
    const passwordElement = document.querySelector("#password");
    const passwordCheckElement = document.querySelector("#passwordcheck");
    const mismatchElement = document.querySelector("#passwordmismatch");


    function isValidChars(text) {
        return text.length <= 20 && text.length >= 8;
    }

    function isValidNumber(text) {
        const regex = /\d/;
        return regex.test(text);
    }

    function isValidUpper(text) {
        const regex = /[A-Z]/;
        return regex.test(text);
    }

    function makeElementValid(el) {
        if (el) {
            el.classList.remove('invalid');
            el.classList.add('valid');
        }
    }

    function makeElementInvalid(el) {
        if (el) {
            el.classList.remove('valid');
            el.classList.add('invalid');
        }
    }

    const handler = (event) => {
        const value = passwordElement.value;

        const chars = isValidChars(value);
        const number = isValidNumber(value);
        const upper = isValidUpper(value);

        if (chars) {
            makeElementValid(validcharsElement);
        } else {
            makeElementInvalid(validcharsElement);
        }

        if (number) {
            makeElementValid(validnumberElement);
        } else {
            makeElementInvalid(validnumberElement);
        }

        if (upper) {
            makeElementValid(validupperElement);
        } else {
            makeElementInvalid(validupperElement);
        }

        if (passwordElement.value == passwordCheckElement.value) {
            mismatchElement.hidden = true;
        } else {
            mismatchElement.hidden = false;
        }

        if (chars && number && upper && passwordElement.value == passwordCheckElement.value) {
            makeElementValid(submitElement);
            submitElement.disabled = false;
        } else {
            makeElementInvalid(submitElement);
            submitElement.disabled = true;
        }
    };

    passwordElement.addEventListener("input", handler);
    passwordCheckElement.addEventListener("input", handler);
</script>

</html>
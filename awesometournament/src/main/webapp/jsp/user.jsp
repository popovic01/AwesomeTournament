
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <title>AwesomeTournaments - User</title>
    <c:import url="/jsp/commons/head.jsp" />
    <link rel="stylesheet" type="text/css" href="/css/auth.css" />
</head>

<body>
    <c:import url="/jsp/commons/header.jsp" />
    <div class="container">
        <div class="row">
            <form action="<c:url value="/user" />" method="post">
                <div class="form-group">
                  <label for="email">Email address</label>
                  <input type="email" class="form-control" id="email" aria-describedby="emailHelp" value="${user.getEmail()}" disabled>
                </div>
                <div class="form-group">
                  <label for="password">Change password</label>
                  <input type="password" class="form-control" id="password" placeholder="Change password" name="password">
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
                </div>
                <button type="submit" class="btn btn-primary" id="submit" disabled>Submit</button>
              </form>
        </div>
        <div class="row">
            <p>Creator of:</p>
            <ul>
                <c:forEach var="tournament" items="${tournaments}">
                    <a href="/tournament/${tournament.getId()}">
                        <li>${tournament.getName()}</li>
                    </a>
                </c:forEach>
            </ul>
        </div>
        <div class="row">
            <p>Creator of:</p>
            <ul>
                <c:forEach var="team" items="${teams}">
                    <a href="/team/${team.getId()}">
                        <li>${team.getName()}</li>
                    </a>
                </c:forEach>
            </ul>
        </div>
    </div>
    <c:import url="/jsp/commons/footer.jsp" />
</body>

<script>
const validcharsElement = document.querySelector("#validchars");
const validnumberElement = document.querySelector("#validnumber");
const validupperElement = document.querySelector("#validupper");
const submitElement = document.querySelector("#submit");
const passwordElement = document.querySelector("#password");


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

    if (chars && number && upper ) {
        makeElementValid(submitElement);
        submitElement.disabled = false;
    } else {
        makeElementInvalid(submitElement);
        submitElement.disabled = true;
    }
};

passwordElement.addEventListener("input", handler);
</script>
</html>
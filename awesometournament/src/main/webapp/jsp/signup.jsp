<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <title>Signup</title>
    <c:import url="/jsp/commons/head.jsp" />
    <link rel="stylesheet" type="text/css" href="/css/auth.css" />
</head>

<body>
    <c:import url="/jsp/commons/header.jsp" />
    <div class="container">
        <div class="row">
            <div class="col-md-6 offset-md-3 col-sm-12">
                <form action="<c:url value=" /auth/signup" />" method="post">
                <div class="form-group">
                    <label for="email">Email address</label>
                    <input type="email" class="form-control" id="email" aria-describedby="emailHelp"
                        placeholder="Enter email" name="email">
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" class="form-control" id="password" placeholder="Enter password"
                        name="password">
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
                    <label for="passwordcheck">Repeat Password</label>
                    <input type="password" class="form-control" id="passwordcheck" placeholder="Repeat password">
                </div>
                <p class="error">
                    <c:out value="${error}" />
                </p>
                <p class="error" id="passwordmismatch" hidden>
                    The passwords don't correspond!
                </p>
                <input type="hidden" name="redirect" value="<c:out value=" ${redirect}" />">
                <button type="submit" id="submit" class="btn btn-primary invalid" disabled>Signup</button>
                </form>
            </div>
        </div>
    </div>
    <c:import url="/jsp/commons/footer.jsp" />
</body>

<script type="text/javascript" src="/js/signup.js"></script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Login Page</title>
        <c:import url="/jsp/commons/head.jsp" />
        <style>
            p.error {
                color: red;
            }
        </style>
    </head>


    <body>
        <c:import url="/jsp/commons/header.jsp" />
        <div class="container">
            <div class="row">
                <div class="col-md-6 offset-md-3 col-sm-12">
                    <form action="<c:url value="/auth/login" />" method="post">
                        <div class="form-group">
                            <label for="email">Email address</label>
                            <input type="email" class="form-control" id="email" aria-describedby="emailHelp" placeholder="Enter email" name="email">
                        </div>
                        <div class="form-group">
                            <label for="password">Password</label>
                            <input type="password" class="form-control" id="password" placeholder="Enter password" name="password">
                        </div>
                        <p class="error"><c:out value="${error}"/></p>
                        <input type="hidden" name="redirect" value="<c:out value="${redirect}"/>">
                        <button type="submit" class="btn btn-primary">Login</button>
                        <p class="signup-text">Don't have an account? <a href="/auth/signup?redirect=${redirect}">Sign up</a></p>
                    </form>
                </div>
            </div>
        </div>
        <c:import url="/jsp/commons/footer.jsp" />
    </body>

</html>

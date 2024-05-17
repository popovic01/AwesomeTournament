<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Index</title>
    <c:import url="/jsp/common/head.jsp"/>
    <style>
        .container {
            text-align: center;
        }

        .image-link {
            display: inline-block;
            margin: 50px;
            text-decoration: none;
            color: #333;
        }

        .image-link img {
            width: 150px;
            height: 100px;
            object-fit: cover;
            border-radius: 10px;
        }

        h1{
            text-align: center;
            margin-bottom: 150px;
        }

        .image-link p {
            margin: 10px 0 0;
        }
    </style>
</head>

<body>
<!-- header -->
<c:import url="/jsp/common/header.jsp"/>

<h1>Awesome Tournament</h1>

<c:choose>
    <c:when test="${logged}">
        <h5>Welcome ${email}</h5>
        <div class="container">
            <a class="image-link" href="<c:url value="/home"/>">
                <img src="<c:url value="/media/homepage.png"/>" alt="Homepage image">
                <p>Homepage</p>
            </a>
            <a class="image-link" href="<c:url value="/auth/logout"/>">
                <img src="<c:url value="/media/logout.png"/>" alt="Logout image">
                <p>Logout</p>
            </a>
        </div>
    </c:when>
    <c:otherwise>
        <div class="container">
            <a class="image-link" href="<c:url value="/home"/>">
                <img src="<c:url value="/media/homepage.png"/>" alt="Homepage image">
                <p>Homepage</p>
            </a>
            <a class="image-link" href="<c:url value="/auth/login"/>">
                <img src="<c:url value="/media/login.png"/>" alt="Login image">
                <p>Login</p>
            </a>
            <a class="image-link" href="<c:url value="/auth/signup"/>">
                <img src="<c:url value="/media/signup.png"/>" alt="Signup image">
                <p>Signup</p>
            </a>
        </div>
    </c:otherwise>
</c:choose>

<!-- footer -->
<c:import url="/jsp/common/footer.jsp"/>
</body>

</html>

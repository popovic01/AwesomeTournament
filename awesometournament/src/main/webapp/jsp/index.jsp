<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Awesome Tournaments</title>
    <c:import url="/jsp/commons/head.jsp"/>
    <link rel="stylesheet" type="text/css" href="/css/index.css" />
    <style>
        body {
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            background: url('<c:url value="/media/background.jpg"/>') no-repeat center center fixed;
            background-size: cover;
        }
    </style>
</head>
<body>
<!-- header -->
<c:import url="/jsp/commons/header.jsp"/>

<div class="content">
    <div class="container">
        <h1>Welcome to Awesome Tournaments</h1>
        <p>
            Awesome Tournament is the ultimate platform for creating, managing, and following football tournaments.
            Whether you're an organizer looking to set up a new tournament, a team looking for competitions,
            or a fan who wants to stay updated on the latest results, Awesome Tournaments has got you covered.
            Join us now and be part of the excitement!
        </p>
        <div class="buttons">
            <a href="<c:url value='/tournaments'/>" class="blue-btn">SEE ALL TOURNAMENTS</a>
            <c:choose>
                <c:when test="${logged}">
                    <a href="<c:url value='/auth/logout'/>" class="blue-btn">LOGOUT</a>
                </c:when>
                <c:otherwise>
                    <div class="cta">
                        <a href="<c:url value='/auth/login'/>" class="blue-btn">LOGIN</a>
                        <a href="<c:url value='/auth/signup'/>" class="blue-btn">SIGN UP</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>

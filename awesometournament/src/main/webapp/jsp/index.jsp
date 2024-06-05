<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Awesome Tournament</title>
    <c:import url="/jsp/commons/head.jsp"/>
    <style>
        /* Global Styles */
        body, html {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
            font-family: Arial, sans-serif;
            color: #fff;
            text-align: center;
        }

        body {
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            background: url('<c:url value="/media/background.jpg"/>') no-repeat center center fixed;
            background-size: cover;
        }

        .content {
            display: flex;
            justify-content: center;
            align-items: center;
            flex-grow: 1;
        }

        .container {
            background: rgba(0, 0, 0, 0.7);
            padding: 20px;
            border-radius: 10px;
            width: 80%;
            max-width: 800px;
        }

        .blue-btn {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            margin: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            font-weight: bold;
            display: flex;
            align-items: center;
            justify-content: center;
            text-align: center;
        }

        .blue-btn:hover {
            background-color: #0056b3;
        }

        /* Specific Styles for Buttons */
        .buttons {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-top: 20px;
        }

        .buttons .blue-btn {
            min-width: 150px;
            height: 50px;
        }

        .cta {
            display: flex;
            justify-content: space-between;
            width: 100%;
            max-width: 400px;
        }

        .cta .blue-btn {
            width: 48%;
            height: 50px;
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

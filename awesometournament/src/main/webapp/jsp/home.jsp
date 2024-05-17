<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>AwesomeTournaments - Home</title>

    <c:import url="/jsp/common/head.jsp"/>

    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1.title {
            text-align: center;
            color: #333;
        }
        ul {
            list-style-type: none;
            padding: 0;
        }
        li {
            margin-bottom: 10px;
            padding: 10px;
            background-color: #f9f9f9;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            cursor: pointer;
        }
        li:hover {
            background-color: #e9e9e9;
        }
        .tournament-name {
            font-size: 1.2em;
            font-weight: bold;
            color: #333;
        }
        .tournament-details {
            color: #666;
        }
    </style>
</head>
<body>

    <!-- header -->
    <c:import url="/jsp/common/header.jsp"/>

    <div class="container">
        <h1 class="title">AwesomeTournaments</h1>
        <ul>
            <c:forEach var="tournament" items="${tournaments}">
                <li>
                    <div class="tournament-name">
                        <c:out value="${tournament.getName()}"/>
                    </div>
                    <div class="tournament-details">
                        <c:out value="${tournament.getCreationDate()}"/>
                    </div>
                    <div class="tournament-details">
                        <c:out value="${tournament.getStartingPlayers()}"/> players per team.
                    </div>
                    <c:url value="/tournament/" var="base"/>
                    <div class="tournament-details">
                        <a href = "${base}${tournament.getId()}"/>detail</a>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>

    <!-- footer -->
    <c:import url="/jsp/common/footer.jsp"/>

</body>
</html>

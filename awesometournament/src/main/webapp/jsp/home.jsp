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
        a.list {
            text-decoration: none; /* Rimuove il sottolineato */
            color: inherit; /* Utilizza il colore del testo del genitore */
            display: block; /* Rende il link come un blocco per coprire l'intero <li> */
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
        .logo{
            width: auto;
            height: 60px;
            float: right;
            margin-left: 10px;
        }
        .dropdown {
            position: relative;
            display: inline-block;
        }
    </style>
</head>
<body>

    <!-- header -->
    <c:import url="/jsp/common/header.jsp"/>

    <div class="container">
        <h1 class="title">AwesomeTournaments</h1>
        <div class="dropdown">
            <select id="tournamentFilter" onchange="filterTournaments()">
                <option value="active">Active Tournaments</option>
                <option value="past">Past Tournaments</option>
            </select>
        </div>
        <ul>
            <c:forEach var="tournament" items="${tournaments}">
                <li class="tournament" data-is-finished="${tournament.getIsFinished()}">
                    <a class="list" href="/tournament/${tournament.getId()}">
                        <c:choose>
                            <c:when test="${not empty entry.getLogoString()}">
                                <img src="data:image/jpeg;base64, ${tournament.getLogoString()}" class="logo" alt="tournament logo">
                            </c:when>
                            <c:otherwise>
                                <img src="<c:url value="/media/tournament_logo.png"/>" class="logo" alt="default logo">
                            </c:otherwise>
                        </c:choose>
                        <div class="tournament-name">
                            <c:out value="${tournament.getName()}"/>
                        </div>
                        <div class="tournament-details">
                            <c:out value="${tournament.getOnlyStartDate()}"/>
                        </div>
                        <div class="tournament-details">
                            <c:out value="${tournament.getStartingPlayers()}"/> players per team.
                        </div>
                    </a>
                </li>
            </c:forEach>
        </ul>
    </div>

    <!-- footer -->
    <c:import url="/jsp/common/footer.jsp"/>
    <script>
        function filterTournaments() {
            var filter = document.getElementById("tournamentFilter").value;
            var tournaments = document.querySelectorAll(".tournament");

            tournaments.forEach(function(tournament) {
                var isFinished = tournament.dataset.isFinished === "true";

                if (filter === "active" && !isFinished) {
                    tournament.style.display = "block";
                }
                else if (filter === "past" && isFinished) {
                    tournament.style.display = "block";
                }
                else {
                    tournament.style.display = "none";
                }
            });
        }
    </script>
</body>
</html>

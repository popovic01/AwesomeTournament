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

            .container-center {
                display: flex;
                justify-content: center; /* Centra orizzontalmente */
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
                display: none;
            }

            a.list {
                text-decoration: none;
                color: inherit;
                display: block;
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

            .btn {
                padding: 6px 12px;
                background-color: #007bff;
                color: white;
                text-decoration: none;
                border-radius: 5px;
                border: none;
                cursor: pointer;
            }

            .btn-primary:hover {
                background-color: #0056b3;
            }
        </style>
    </head>

    <body>

        <!-- header -->
        <c:import url="/jsp/common/header.jsp"/>

        <div class="container">
            <h1 class="title">AwesomeTournaments</h1>

            <div class="container-center">
                <button id="btnCreateTournament" class="btn btn-primary">
                    Create a new Tournament
                </button>
            </div>

            <div id="createTournamentForm" style="display: none; margin-top: 20px;">
                <form method="post">
                    <div>
                        <label for="tournamentName">Tournament Name:</label>
                        <input type="text" id="tournamentName" name="tournamentName" required>
                    </div>
                    <div>
                        <label for="startDate">Start Date:</label>
                        <input type="date" id="startDate" name="startDate" required>
                    </div>
                    <div>
                        <label for="startingPlayers">Players per Team:</label>
                        <input type="number" id="startingPlayers" name="startingPlayers" required>
                    </div>
                    <div>
                        <label for="logo">Logo:</label>
                        <input type="file" id="logo" name="logo">
                    </div>
                    <button type="submit" class="btn btn-primary">Submit</button>
                    <button type="button" class="btn" onclick="hideForm()">Cancel</button>
                </form>
            </div>


            <div class="dropdown" style="margin-bottom: 10px;">
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
                                <c:when test="${not empty entry.getBase64Logo()}">
                                    <img src="data:image/jpeg;base64, ${tournament.getBase64Logo()}" class="logo" alt="tournament logo">
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

            function createTournament() {
                var btnCreateTournament = document.getElementById("btnCreateTournament");
                btnCreateTournament.addEventListener("click", function() {
                    document.getElementById("createTournamentForm").style.display = "block";
                    document.getElementById("btnCreateTournament").style.display = "none";
                    document.getElementById("tournamentFilter").style.display = "none";
                    document.querySelector("ul").style.display = "none";
                });
            }

            function hideForm() {
                document.getElementById("createTournamentForm").style.display = "none";
                document.getElementById("btnCreateTournament").style.display = "block";

                document.getElementById("tournamentFilter").style.display = "block";
                document.querySelector("ul").style.display = "block";
            }

            document.addEventListener("DOMContentLoaded", function() {
                filterTournaments();
                createTournament();
            });
        </script>
    </body>

</html>

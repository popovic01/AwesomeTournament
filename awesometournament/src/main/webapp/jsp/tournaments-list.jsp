<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <title>AwesomeTournaments - Home</title>

        <c:import url="/jsp/commons/head.jsp"/>
        <link rel="stylesheet" type="text/css" href="/css/tournaments-list.css"/>
    </head>

    <body>

        <!-- header -->
        <c:import url="/jsp/commons/header.jsp"/>

        <div class="container mt-5" id="addTournamentCard" style="display: none;">
            <div class="card shadow-lg">
                <div class="card-header text-white bg-primary">
                    <h3 class="card-title mb-0">Tournament details</h3>
                </div>
                <div class="card-body">
                    <div class="row">
                        <form id="createTournamentForm">
                            <div>
                                <label for="tournamentName">Tournament Name:</label>
                                <input type="text" id="tournamentName" name="tournamentName" required>
                            </div>
                            <div>
                                <label for="maxTeam">Maximum number of teams:</label>
                                <input type="number" id="maxTeam" name="maxTeam" min="2" max="20" value="2" required>
                            </div>
                            <div>
                                <label for="startingPlayers">Number of starting players per team:</label>
                                <input type="number" id="startingPlayers" name="startingPlayers" min="1" max="11" value="1" required>
                            </div>
                            <div>
                                <label for="minPlayers">Minimum number of players for a team:</label>
                                <input type="number" id="minPlayers" name="minPlayers" min="1" max="11" value="1" required>
                            </div>
                            <div>
                                <label for="maxPlayers">Maximum number of players for a team:</label>
                                <input type="number" id="maxPlayers" name="maxPlayers" min="1" max="25" value="1" required>
                            </div>
                            <div>
                                <label for="startDate">Start date of the tournament:</label>
                                <input type="date" id="startDate" name="startDate" required>
                            </div>
                            <div>
                                <label for="deadline">Deadline for team registration:</label>
                                <input type="date" id="deadline" name="deadline" required>
                            </div>
                            <button type="submit" name="confirm" class="btn btn-primary">Submit</button>
                            <button id="cancelCreateTournament" type="button" name="cancel" class="btn btn-primary">Cancel</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div id="container" class="container">
            <h1 class="title">AwesomeTournaments</h1>

            <c:if test="${logged}">
                <div class="container-center">
                    <button id="btnCreateTournament" class="btn btn-primary">
                        Create a new Tournament
                    </button>
                </div>
            </c:if>

            <div class="dropdown" style="margin-bottom: 10px;">
                <select id="tournamentFilter" onchange="filterTournaments()">
                    <option value="active">Active Tournaments</option>
                    <option value="past">Past Tournaments</option>
                </select>
            </div>

            <ul>
                <c:forEach var="tournament" items="${tournaments}">
                    <li class="tournament" data-is-finished="${tournament.getIsFinished()}" data-deadline="${tournament.getDeadline()}">
                        <a class="list" href="/tournament/${tournament.getId()}">
                            <div class="tournament-element">
                                <div class="tournament-info">
                                    <div class="tournament-name">
                                        <c:out value="${tournament.getName()}"/>
                                    </div>
                                    <div class="tournament-details">
                                        <c:out value="${tournament.getStartingPlayers()}"/> starting players per team.
                                    </div>
                                    <div class="tournament-details">
                                        Starting date: <strong><c:out value="${tournament.getOnlyStartDate()}"/></strong>
                                    </div>
                                    <div class="tournament-details">
                                        Time left for registration: <span class="time-left"></span>
                                    </div>
                                </div>
                                <div class="tournament-logo">
                                    <c:choose>
                                        <c:when test="${not empty tournament.getBase64Logo()}">
                                            <img src="data:image/jpeg;base64, ${tournament.getBase64Logo()}" class="logo" alt="tournament logo">
                                        </c:when>
                                    </c:choose>
                                </div>
                            </div>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <!-- footer -->
        <c:import url="/jsp/commons/footer.jsp"/>

        <script>var userId = "${userId}"</script>
        <script type="text/javascript" src="/js/tournaments-list.js"></script>
    </body>

</html>

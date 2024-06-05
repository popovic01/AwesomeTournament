<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <title>AwesomeTournaments - Tournament</title>
    <c:import url="/jsp/commons/head.jsp" />
    <style>
        .my-container {
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-bottom: 4rem;
        }

        .container-fluid.fh {
            min-height: calc(100vh - 50px);
            display: flex;
            flex-direction: column;
        }

        .row.fh {
            flex: 1;
            display: flex;
            overflow: hidden;
        }

        .col-lg-6.full-height,
        .col-sm-12.full-height {
            height: 100%;
            overflow: hidden;
            padding-bottom: 50px;
        }

        .full-height-content {
            height: 100%;
            overflow: hidden;
        }

        .match-header,
        .button-container {
            padding-top: 20px;
            display: flex;
            margin-bottom: 30px;
        }

        .right-column {
            height: 100%;
            display: flex;
            flex-direction: column;
        }

        .right-column-content {
            flex-grow: 1;
            overflow-y: auto;
            max-height: 60vh;
            padding: 20px;
            background-color: #f9f9f9;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        .logo {
            width: auto;
            height: 60px;
        }

        .logo-img {
            width: auto;
            height: 45px;
        }

        a.link {
            text-decoration: none;
            color: inherit;
        }

        a.link:hover {
            text-decoration: underline;
            color: inherit;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            border-spacing: 0;
            margin-bottom: 20px;
            border-radius: 5px;
            overflow: hidden;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        th,
        td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #13730a;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
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
        }

        li:hover {
            background-color: #e9e9e9;
        }

        .ranking-position {
            font-weight: bold;
            margin-right: 10px;
            font-size: larger;
        }

        ::-webkit-scrollbar {
            width: 10px;
        }

        ::-webkit-scrollbar-thumb {
            background-color: #888;
            border-radius: 5px;
        }

        ::-webkit-scrollbar-thumb:hover {
            background-color: #555;
        }

        ::-webkit-scrollbar-track {
            background-color: #f1f1f1;
        }

        ::-webkit-scrollbar-track:hover {
            background-color: #ddd;
        }

        .match-detail {
            display: flex;
            justify-content: space-between;
            align-items: center;
            width: 100%;
            margin-bottom: 10px;
            padding: 10px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        .team-detail,
        .score {
            margin-top: 12px;
            display: flex;
            flex-direction: column;
            align-items: center;
            flex: 1;
        }

        .team-detail {
            align-items: flex-start;
        }

        .team2-detail {
            align-items: flex-end;
        }

        .score {
            flex: 0 1 200px;
            justify-content: center;
        }

        .team1-detail img {
            margin-right: 15px;
            margin-left: 5px;
        }

        .team2-detail img {
            margin-right: 5px;
            margin-left: 15px;
        }

        .match-date {
            font-size: 0.8em;
            color: grey;
            display: inline;
        }

        #match-link {
            text-decoration: none;
            color: black;
        }

        .inline-container {
            display: flex;
            align-items: center;
        }

        #matchFilter {
            padding: 5px 10px;
            border-radius: 5px;
            background-color: #fff;
            border: 1px solid #ccc;
        }

        .table-container {
            width: 100%;
        }

        #tournamentTable,
        #rankingScorers {
            width: 100%;
            display: table;
        }

        .tournament-info {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 20px;
            margin-top: 20px;
            margin-bottom: 20px;
        }

        .tournament-info img {
            width: 60px;
            height: 60px;
            object-fit: cover;
        }

        .tournament-info .tournament-name {
            margin: 0;
            font-size: 1.5rem;
            color: #333;
        }

        .d-flex button {
            margin-right: 10px;
        }

        .d-flex button:last-child {
            margin-right: 0;
        }
    </style>
</head>

<body>
    <c:import url="/jsp/commons/header.jsp" />


    <div id="editTournamentContainer" class="container mt-5" style="display: none;">
        <div class="card shadow-lg">
            <div class="card-header text-white bg-primary">
                <h3 class="card-title mb-0">Tournament details</h3>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-4 text-center">
                        <c:choose>
                            <c:when test="${not empty tournament.getBase64Logo()}">
                                <img src="data:image/jpg;base64,${tournament.getBase64Logo()}" class="img-fluid rounded-circle mb-3" />
                            </c:when>
                            <c:otherwise>
                                <img src="<c:url value='/media/tournament_logo.png' />" class="img-fluid rounded-circle mb-3" alt="default logo">
                            </c:otherwise>
                        </c:choose>
                        <h5 class="card-subtitle mb-2 text-muted"> <strong>${tournament.getName()}</strong> </h5>
                    </div>
                    <div class="col-md-8">
                        <form id="editTournamentForm">
                            <div>
                                <label for="tournamentName">Tournament Name:</label>
                                <input type="text" id="tournamentName" name="tournamentName" value="${tournament.getName()}" required>
                            </div>
                            <div>
                                <label for="maxTeam">Maximum number of teams:</label>
                                <input type="number" id="maxTeam" name="maxTeam" min="2" max="20" value="${tournament.getMaxTeams()}" required>
                            </div>
                            <div>
                                <label for="startingPlayers">Number of starting players per team:</label>
                                <input type="number" id="startingPlayers" name="startingPlayers" min="1" max="11" value="${tournament.getStartingPlayers()}" required>
                            </div>
                            <div>
                                <label for="minPlayers">Minimum number of players for a team:</label>
                                <input type="number" id="minPlayers" name="minPlayers" min="1" max="11" value="${tournament.getMinPlayers()}" required>
                            </div>
                            <div>
                                <label for="maxPlayers">Maximum number of players for a team:</label>
                                <input type="number" id="maxPlayers" name="maxPlayers" min="1" max="25" value="${tournament.getMaxPlayers()}" required>
                            </div>
                            <div>
                                <label for="startDate">Start date of the tournament:</label>
                                <input type="date" id="startDate" name="startDate" value="${tournament.getOnlyStartDate()}" required>
                            </div>
                            <div>
                                <label for="deadline">Deadline for team registration:</label>
                                <input type="date" id="deadline" name="deadline" value="${tournament.getOnlyDeadline()}" required>
                            </div>
                            <button type="submit" name="confirm" class="btn btn-primary">Edit</button>
                            <button id="cancelEditTournament" type="button" name="cancel" class="btn btn-primary">Cancel</button>
                            <button id="deleteTournament" type="button" name="delete" class="btn btn-danger">Delete Tournament</button>
                        </form>
                    </div>
                </div>
            </div>
            <div class="card-footer text-muted text-right">
                Update logo of the tournament
                <form class="form-row" method="POST" action="/uploadLogo" enctype="multipart/form-data" style="max-width: 400px; margin: auto;">
                    <input type="hidden" name="tournamentId" value="${tournament.getId()}">
                    <div class="input-group">
                        <input type="file" class="form-control" id="inputGroupFile04" name="logo" accept=".png, .jpg, .jpeg" aria-describedby="inputGroupFileAddon04" aria-label="Upload" required>
                        <button class="btn btn-outline-secondary" type="submit" id="inputGroupFileAddon04">Upload</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="container-fluid fh" id="main-container">
        <div class="tournament-info d-flex justify-content-center align-items-center my-4">
            <h1 class="tournament-name m-0">
                <strong>${tournament.getName()}</strong>
            </h1>
            <c:choose>
                <c:when test="${not empty tournament.getBase64Logo()}">
                    <img src="data:image/jpg;base64,${tournament.getBase64Logo()}" class="img-fluid rounded-circle ml-3" alt="tournament logo" />
                </c:when>
                <c:otherwise>
                    <img src="<c:url value='/media/tournament_logo.png' />" class="img-fluid rounded-circle ml-3" alt="default logo">
                </c:otherwise>
            </c:choose>
        </div>

        <div class="d-flex justify-content-center mb-4">
            <c:if test="${owner}">
                <button id="btnEditTournament" class="btn btn-primary mr-3">Edit Tournament</button>
                <c:if test="${empty matches}">
                    <button id="generateMatches" class="btn btn-success">Close Subscriptions and<br>Generate Matches</button>
                </c:if>
            </c:if>
            <c:if test="${logged && !deadlinePassed}">
                <button id="btnAdd" class="btn btn-primary">Add Team</button>
            </c:if>
        </div>
        <div class="row fh">
            <div class="col-lg-6 col-sm-12 full-height">
                <div class="full-height-content">
                    <div class="button-container">
                        <button id="seeTournamentTable" class="btn btn-primary" style="background-color: darkblue">See table</button>
                        <button style="margin-left: 30px;" id="seeRankingScorers" class="btn btn-primary">See top scorers</button>
                    </div>
                    <div class="table-container">
                        <table id="tournamentTable">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Team</th>
                                    <th>Points</th>
                                    <th>Matches Played</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${ranking}" var="entry" varStatus="status">
                                    <tr>
                                            <td><c:out value="${status.index + 1}" /></td>
                                            <td>
                                                <a href="/team/${entry.getTeamId()}" class="link">
                                                    <c:out value="${entry.getTeamName()}" /></a>
                                                    <c:choose>
                                                        <c:when test="${not empty entry.getLogo()}">
                                                            <img src="data:image/jpeg;base64,${entry.getLogo()}" class="logo" alt="team logo" id="logo">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="<c:url value='/media/logo_placeholder.png'/>" class="logo" alt="default logo" id="logo">
                                                        </c:otherwise>
                                                    </c:choose>
                                                </a>
                                            </td>
                                            <td><c:out value="${entry.getPoints()}" /></td>
                                            <td><c:out value="${entry.getMatchesPlayed()}" /></td>
                                    </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                        <table id="rankingScorers" style="display: none">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Player</th>
                                    <th>Team</th>
                                    <th>Goals</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${rankingScorers}" var="entry" varStatus="status">
                                    <tr>
                                        <td><c:out value="${status.index + 1}" /></td>
                                        <td>
                                            <a href="/players/${entry.getPlayerID()}" class="link"><c:out value="${entry.getPlayerName()} ${entry.getPlayerSurname()}" /></a>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty entry.getLogo()}">
                                                    <div>
                                                        <img src="data:image/jpeg;base64,${entry.getLogo()}" class="logo" alt="team logo">
                                                        <a href="/team/${entry.getTeamID()}" class="link"><c:out value="${entry.getTeamName()}" /></a>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div>
                                                        <img src="<c:url value='/media/logo_placeholder.png' />" class="logo" alt="default logo">
                                                        <a href="/team/${entry.getTeamID()}" class="link"><c:out value="${entry.getTeamName()}" /></a>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td><c:out value="${entry.getGoals()}" /></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="col-lg-6 col-sm-12 right-column">
                <div class="match-header">
                    <select id="matchFilter">
                        <option value="past">Past Matches</option>
                        <option value="upcoming">Upcoming Matches</option>
                        <option value="tba">Not Scheduled Yet</option>
                        <option value="all" selected>All Matches</option>
                    </select>
                </div>
                <div class="right-column-content">
                    <c:choose>
                        <c:when test="${not empty matches}">
                            <ul id="matchList">
                                <c:forEach items="${matches}" var="match">
                                    <li is-finished="${match.isFinished}" date="${match.matchDate}">
                                        <c:set var="team1Name" value="" />
                                        <c:set var="team1Logo" value="" />
                                        <c:set var="team2Name" value="" />
                                        <c:set var="team2Logo" value="" />
                                        <c:forEach items="${teams}" var="team">
                                            <c:if test="${team.id == match.team1Id}">
                                                <c:set var="team1Name" value="${team.name}" />
                                                <c:set var="team1Logo" value="${team.getBase64Logo()}" />
                                            </c:if>
                                            <c:if test="${team.id == match.team2Id}">
                                                <c:set var="team2Name" value="${team.name}" />
                                                <c:set var="team2Logo" value="${team.getBase64Logo()}" />
                                            </c:if>
                                        </c:forEach>
                                        <a id="match-link" href="<c:url value='/match/${match.getId()}' />">
                                            <div class="match-detail">
                                                <div class="team-detail team1-detail">
                                                    <c:choose>
                                                        <c:when test="${not empty team1Logo}">
                                                            <img src="data:image/jpeg;base64,${team1Logo}" class="logo-img" alt="team logo">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="<c:url value='/media/logo_placeholder.png' />" class="logo-img" alt="default logo">
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <div>
                                                        <c:choose>
                                                            <c:when test="${match.result == 'TEAM1'}">
                                                                <strong>${team1Name}</strong>
                                                            </c:when>
                                                            <c:otherwise>
                                                                ${team1Name}
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                </div>
                                                <div class="score">
                                                    ${match.team1Score} - ${match.team2Score}
                                                </div>
                                                <div class="team-detail team2-detail">
                                                    <c:choose>
                                                        <c:when test="${not empty team2Logo}">
                                                            <img src="data:image/jpeg;base64,${team2Logo}" class="logo-img" alt="team logo">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="<c:url value='/media/logo_placeholder.png' />" class="logo-img" alt="default logo">
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <div>
                                                        <c:choose>
                                                            <c:when test="${match.result == 'TEAM2'}">
                                                                <strong>${team2Name}</strong>
                                                            </c:when>
                                                            <c:otherwise>
                                                                ${team2Name}
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                </div>
                                            </div>
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:when>
                        <c:otherwise>
                            <p id="noMatchesString" class="text-dark">
                                No matches available at the moment. <br>
                                Come back here when the subscriptions are closed!
                            </p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
    <c:import url="/jsp/commons/footer.jsp" />
</body>

<script>
    window.addEventListener("pageshow", function (event) {
        var historyTraversal = event.persisted ||
            (typeof window.performance != "undefined" &&
                window.performance.navigation.type === 2);
        if (historyTraversal) {
            window.location.reload();
        }
    });

    function setStartingMinPlayersAndMaxPlayers() {
        const maxTeamInput = document.getElementById('maxTeam');
        const startingPlayersInput = document.getElementById('startingPlayers');
        const minPlayersInput = document.getElementById('minPlayers');
        const maxPlayersInput = document.getElementById('maxPlayers');

        maxTeamInput.addEventListener('keydown', function (event) {
            event.preventDefault();
        });

        startingPlayersInput.addEventListener('keydown', function (event) {
            event.preventDefault();
        });

        minPlayersInput.addEventListener('keydown', function (event) {
            event.preventDefault();
        });

        maxPlayersInput.addEventListener('keydown', function (event) {
            event.preventDefault();
        });

        startingPlayersInput.addEventListener('click', function () {
            const startingPlayers = parseInt(startingPlayersInput.value, 10);

            if (startingPlayers > minPlayersInput.value && startingPlayers > maxPlayersInput.value) {
                minPlayersInput.value = startingPlayers;
                maxPlayersInput.value = startingPlayers;
            } else if (startingPlayers > minPlayersInput.value) minPlayersInput.value = startingPlayers;
            if (startingPlayers < 1) startingPlayersInput.value = 1;
        });

        minPlayersInput.addEventListener('click', function () {
            const minPlayers = parseInt(minPlayersInput.value, 10);
            const maxPlayers = parseInt(maxPlayersInput.value, 10);
            const startingPlayers = parseInt(startingPlayersInput.value, 10);

            if (minPlayers < startingPlayers) startingPlayersInput.value = minPlayersInput.value;

            if (minPlayers > maxPlayers) maxPlayersInput.value = minPlayersInput.value
        });

        maxPlayersInput.addEventListener('click', function () {
            const minPlayers = parseInt(minPlayersInput.value, 10);
            const maxPlayers = parseInt(maxPlayersInput.value, 10);
            const startingPlayers = parseInt(startingPlayersInput.value, 10);

            if (maxPlayers < minPlayers && maxPlayers < startingPlayers) {
                startingPlayersInput.value = maxPlayersInput.value;
                minPlayersInput.value = maxPlayersInput.value;
            } else if (maxPlayers < minPlayers) minPlayersInput.value = maxPlayersInput.value;
        })
    }

    function setStartAndDeadlineDate() {
        const date = new Date();
        date.setDate(date.getDate() + 1);
        const tomorrow = date.toISOString().split('T')[0];

        const startDateInput = document.getElementById('startDate');
        const deadlineInput = document.getElementById('deadline');

        startDateInput.min = tomorrow;
        deadlineInput.min = tomorrow;
        deadlineInput.max = startDateInput.value;

        startDateInput.addEventListener('change', function () {
            if (startDateInput.value) {
                deadlineInput.max = startDateInput.value;
                if (!deadlineInput.value || new Date(startDateInput.value) < new Date(deadlineInput.value)) deadlineInput.value = startDateInput.value;
            } else deadlineInput.removeAttribute('max');
        });
    }

    function manageForm() {
        document.getElementById('editTournamentForm').addEventListener('submit', function (event) {
            event.preventDefault();

            var formData = {
                name: document.getElementById("tournamentName").value,
                token: "prova",
                creatorUserId: '${userId}',
                maxTeams: parseInt(document.getElementById("maxTeam").value),
                maxPlayers: parseInt(document.getElementById("maxPlayers").value),
                minPlayers: parseInt(document.getElementById("minPlayers").value),
                startingPlayers: parseInt(document.getElementById("startingPlayers").value),
                maxSubstitutions: 5,
                deadline: document.getElementById("deadline").value,
                startDate: document.getElementById("startDate").value,
                creationDate: new Date().toISOString(),
                isFinished: false
            };

            fetch('/api/tournaments/${tournament.getId()}', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            }).then(response => {
                if (response.ok) {
                    window.location.replace("/tournament/${tournament.getId()}");
                } else throw new Error('Failed to update tournament');
            }).catch(error => {
                console.error('Error:', error);
                alert('Failed to update the tournament. Please try again.');
            });
        });

        document.getElementById('deleteTournament').addEventListener('click', function () {
            if (confirm('Are you sure you want to delete this tournament?')) {
                fetch(`/api/tournaments/${tournament.getId()}`, {
                    method: 'DELETE'
                })
                    .then(response => {
                        if (response.ok) {
                            alert('Tournament deleted successfully');
                            window.location.href = '/tournaments';
                        } else {
                            alert('Failed to delete the tournament');
                            window.location.href = '/tournaments';
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        alert('An error occurred while deleting the tournament');
                    });
            }
        });
    }

    function hideForm() {
        var btnCancelEditTournament = document.getElementById("cancelEditTournament");
        btnCancelEditTournament.addEventListener("click", function () {
            document.getElementById("editTournamentContainer").style.display = "none";
            document.getElementById("main-container").style.display = "block";
            document.getElementById("form-container").style.display = "none";
            document.querySelector("ul").style.display = "block";
        });
    }

    document.addEventListener('DOMContentLoaded', function () {
        var tournamentTable = document.getElementById("tournamentTable");
        var rankingScorers = document.getElementById("rankingScorers");

        document.getElementById("seeTournamentTable").addEventListener('click', function () {
            rankingScorers.style.display = "none";
            tournamentTable.style.display = "table";
            this.style.backgroundColor = "darkblue";
            document.getElementById("seeRankingScorers").style.backgroundColor = "#007bff";
        });

        document.getElementById("seeRankingScorers").addEventListener('click', function () {
            tournamentTable.style.display = "none";
            rankingScorers.style.display = "table";
            this.style.backgroundColor = "darkblue";
            document.getElementById("seeTournamentTable").style.backgroundColor = "#007bff";
        });

        var filterControl = document.getElementById('matchFilter');
        if (filterControl) {
            filterControl.addEventListener('change', function () {
                var selectedFilter = this.value;
                var matches = document.querySelectorAll('#matchList li');
                matches.forEach(function (match) {
                    var isFinished = match.getAttribute('is-finished') === 'true';
                    var matchDate = match.getAttribute('date');
                    var scoreDiv = match.querySelector('.score');
                    if (scoreDiv) {
                        var dateSpan = scoreDiv.querySelector('.match-date');
                        if (!dateSpan) {
                            dateSpan = document.createElement('span');
                            dateSpan.classList.add('match-date');
                            scoreDiv.appendChild(dateSpan);
                        }

                        if (matchDate && !isFinished) {
                            var date = new Date(matchDate);
                            var formattedDate = ('0' + date.getDate()).slice(-2) + '/'
                                + ('0' + (date.getMonth() + 1)).slice(-2) + '/'
                                + date.getFullYear().toString().slice(-2) + ' '
                                + ('0' + date.getHours()).slice(-2) + ':'
                                + ('0' + date.getMinutes()).slice(-2);
                            dateSpan.textContent = ' Date: ' + formattedDate;
                        } else if (!matchDate) {
                            dateSpan.textContent = ' Date: TBA';
                        }
                    }
                    if (selectedFilter === 'all') {
                        match.style.display = '';
                    } else if (selectedFilter === 'upcoming' && matchDate && !isFinished) {
                        match.style.display = '';
                    } else if (selectedFilter === 'past' && isFinished) {
                        match.style.display = '';
                    } else if (selectedFilter === 'tba' && !matchDate) {
                        match.style.display = '';
                    } else {
                        match.style.display = 'none';
                    }
                });
            });
            filterControl.dispatchEvent(new Event('change'));
        }

        var generateMatchesButton = document.getElementById('generateMatches');
        if (generateMatchesButton) {
            generateMatchesButton.addEventListener('click', function () {
                generateMatchesButton.disabled = true;
                generateMatchesButton.textContent = 'Loading...';
                var xhr = new XMLHttpRequest();
                xhr.open('POST', '/matches/tournaments/${tournament.id}', true);
                xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                xhr.onload = function () {
                    if (xhr.status >= 200 && xhr.status < 300) {
                        window.location.reload();
                    }
                };
                xhr.onerror = function () {
                    generateMatchesButton.disabled = false;
                    generateMatchesButton.textContent = 'Close Subscriptions and\nGenerate Matches';
                };
                xhr.send();
            });
        }

        var btnEditTournament = document.getElementById("btnEditTournament");
        btnEditTournament.addEventListener("click", function () {
            document.getElementById("editTournamentContainer").style.display = "block";
            document.getElementById("main-container").style.display = "none";
        });

        setStartingMinPlayersAndMaxPlayers();
        setStartAndDeadlineDate();
        manageForm();
        hideForm();

        var seeTournamentTableBtn = document.getElementById("seeTournamentTable");
        var matches = "${matches}";
        if (matches && matches.length > 0) seeTournamentTableBtn.style.display = "block";
        else seeTournamentTableBtn.style.display = "none";

        var btnAdd = document.getElementById('btnAdd');
        if (btnAdd) {
            btnAdd.addEventListener('click', function () {
                var url = `/tournament/${tournament.id}/add-team`;
                window.location.href = url;
                var xhr = new XMLHttpRequest();
                xhr.open('GET', url, true);
                xhr.onload = function () {
                    if (xhr.status >= 200 && xhr.status < 300) {
                        window.location.reload();
                    }
                };
            });
        } else {
            console.log('btnAdd element not found');
        }
    });

    $(window).bind("pageshow", function () {
        $("#matchFilter").val('all');
    });
</script>

</html>
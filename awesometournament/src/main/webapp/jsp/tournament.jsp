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

        h1 {
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
        }

        li:hover {
            background-color: #e9e9e9;
        }

        p {
            margin: 1rem 0;
        }

        .logo-img {
            height: 2rem;
        }

        .match-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 10px;
        }

        h3 {
            margin: 0;
        }

        #matchFilter {
            padding: 5px 10px;
            border-radius: 5px;
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

        .btn-delete {
            margin-top: 20px;
            background-color: #dc3545;
        }

        .btn-delete:hover {
            background-color: #c82333;
        }

        .matches-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            width: 100%;
        }

        .match-detail {
            display: flex;
            justify-content: space-between;
            align-items: center;
            width: 100%;
            margin-bottom: 10px;
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
            /* Aligns team1 to the left */
        }

        .team2-detail {
            align-items: flex-end;
            /* Aligns team2 to the right */
        }

        .score {
            flex: 0 1 200px;
            justify-content: center;
        }

        /* Add space between logo and name */
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
            /* Ensures it stays on the same line */
        }

        #match-link {
            text-decoration: none;
            color: black;
        }

        .inline-container {
            display: flex;
            align-items: center;
        }

        a.list {
            text-decoration: none;
            color: inherit;
            display: block;
        }

        .ctr {
            height: 100%;
        }

        html,
        body {
            height: 100%;
        }

        .full-height {
            height: 100%;
            overflow-y: auto;
        }

        .fh {
            height: 100%;
        }

        .logo{
            width: auto;
            height: 60px;
        }

        a.link {
            text-decoration: none; /* Remove underlined */
            color: inherit; /* Uses the parent's text color */
        }

        /* Style for team hover links */
        a.link:hover {
            text-decoration: underline; /* Underline text on hover */
            color: inherit; /* Uses the parent's text color */
        }

        table {
            width: 100%;
            border-collapse: collapse;
            border-spacing: 0;
        }

        th, td {
            border: 2px solid black;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
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
    </style>
</head>

<body>
    <!-- header -->
    <div class="my-container" id="form-container" style="display:none;">
        <div id="editTournamentForm" style="display: none; margin-top: 20px;">
            <form>
                <div>
                    <label for="tournamentName">Tournament Name:</label>
                    <input type="text" id="tournamentName" name="tournamentName" value="${tournament.getName()}"
                        required>
                </div>
                <div>
                    <label for="maxTeam">Maximum number of teams:</label>
                    <input type="number" id="maxTeam" name="maxTeam" min="2" max="20"
                        value="${tournament.getMaxTeams()}" required>
                </div>
                <div>
                    <label for="startingPlayers">Number of starting players per team:</label>
                    <input type="number" id="startingPlayers" name="startingPlayers" min="1" max="11"
                        value="${tournament.getStartingPlayers()}" required>
                </div>
                <div>
                    <label for="minPlayers">Minimum number of players for a team:</label>
                    <input type="number" id="minPlayers" name="minPlayers" min="1" max="11"
                        value="${tournament.getMinPlayers()}" required>
                </div>
                <div>
                    <label for="maxPlayers">Maximum number of players for a team:</label>
                    <input type="number" id="maxPlayers" name="maxPlayers" min="1" max="25"
                        value="${tournament.getMaxPlayers()}" required>
                </div>
                <div>
                    <label for="startDate">Start date of the tournament:</label>
                    <input type="date" id="startDate" name="startDate" value="${tournament.getOnlyStartDate()}"
                        required>
                </div>
                <div>
                    <label for="deadline">Deadline for team registration:</label>
                    <input type="date" id="deadline" name="deadline" value="${tournament.getOnlyDeadline()}"
                        required>
                </div>
                <div>
                    <label for="logo">Logo:</label>
                    <input type="file" id="logo" name="logo" accept=".png, .jpg, .jpeg">
                </div>
                <button type="submit" name="confirm" class="btn btn-primary">Edit</button>
                <button id="cancelEditTournament" type="button" name="cancel"
                    class="btn btn-primary">Cancel</button>
                <button id="deleteTournament" type="button" name="delete" class="btn btn-delete">Delete
                    Tournament</button>
            </form>
        </div>
    </div>

    <div class="container-fluid fh" id="main-container">
        <c:import url="/jsp/commons/header.jsp"/>
        <div class="row" style="margin-bottom: 30px">
            <div class="title-logo-wrapper">
                <p class="fs-1 text-dark">
                    <c:out value="${tournament.name}" />
                </p>
                <c:choose>
                    <c:when test="${not empty tournament.base64Logo}">
                        <img src="data:image/jpg;base64,${tournament.base64Logo}" />
                    </c:when>
                    <c:otherwise>
                        <img src="<c:url value="/media/tournament_logo.png" />" alt="default logo">
                    </c:otherwise>
                </c:choose>
                <c:if test="${owner}">
                    <c:if test="${empty matches}">
                        <button id="generateMatches" class="btn btn-primary">Close Subscriptions and<br>Generate
                            Matches</button>
                    </c:if>
                </c:if>
                <c:if test="${owner}">
                    <button id="btnAdd" class="btn btn-primary">
                        Add Team
                    </button>
                    <button id="btnEditTournament" class="btn btn-primary">Edit</button>
                </c:if>
            </div>
        </div>
        <div class="row fh">
            <div class="col-lg-6 col-sm-12 full-height">
                <div style="display: flex; margin-bottom: 30px;">
                    <button id="seeTournamentTable" class="btn btn-primary" style="background-color: darkblue">
                        See table
                    </button>
                    <button style="margin-left: 30px;" id="seeRankingScorers" class="btn btn-primary">
                        See top scorers
                    </button>
                </div>

                <!--<table id="tournamentTable">
                    <tr>
                        <th>Team</th>
                        <th>Points</th>
                        <th>Match played</th>
                    </tr>
                    <tbody>
                    <c:forEach items="${ranking}" var="entry">
                        <tr>
                            <td>
                                <a href="/team/${entry.getTeamID()}" class="link">
                                    <c:out value="${entry.getTeamName()}"/>
                                    <c:choose>
                                        <c:when test="${not empty entry.getLogo()}">
                                            <div>
                                                <img src="data:image/jpeg;base64, ${entry.getLogo()}" class="logo" alt="team logo">
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div>
                                                <img src="<c:url value="/media/logo_placeholder.png"/>" class="logo" alt="default logo">
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </a>
                            </td>
                            <td><c:out value="${entry.getPoints()}"/></td>
                            <td><c:out value="${entry.getMatchesPlayed()}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>-->

                <!--<table id="rankingScorers" class="w-100">
                    <tr>
                        <th>#</th>
                        <th>Player</th>
                        <th>Team</th>
                        <th>Goals</th>
                    </tr>
                    <tbody>
                    <c:forEach items="${rankingScorers}" var="entry" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td>
                                <a href="/players/${entry.getPlayerID()}" class="link">
                                    <c:out value="${entry.getPlayerName()} ${entry.getPlayerSurname()}"/>
                                </a>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty entry.getLogo()}">
                                        <div>
                                            <img src="data:image/jpeg;base64, ${entry.getLogo()}" class="logo" alt="team logo">
                                            <a href="/team/${entry.getTeamID()}" class="link"><c:out value="${entry.getTeamName()}"/></a>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div>
                                            <img src="<c:url value="/media/logo_placeholder.png"/>" class="logo" alt="default logo">
                                            <a href="/team/${entry.getTeamID()}" class="link"><c:out value="${entry.getTeamName()}"/></a>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td><c:out value="${entry.getGoals()}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>-->

                <ol>
                    <c:forEach items="${rankingScorers}" var="entry" varStatus="status">
                        <li>
                                <div class="player-info">
                                    <div class="player-details">
                                        <a href="/players/${entry.getPlayerID()}" class="link">
                                            <c:out value="${entry.getPlayerName()} ${entry.getPlayerSurname()}"/>
                                        </a>
                                    </div>
                                    <div style="display: flex; align-items: center;">
                                        <c:choose>
                                            <c:when test="${not empty entry.getLogo()}">
                                                <div>
                                                    <img src="data:image/jpeg;base64, ${entry.getLogo()}" class="logo" alt="team logo">
                                                    <a href="/team/${entry.getTeamID()}" class="link"><c:out value="${entry.getTeamName()}"/></a>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div>
                                                    <img src="<c:url value="/media/logo_placeholder.png"/>" class="logo" alt="default logo">
                                                    <a href="/team/${entry.getTeamID()}" class="link"><c:out value="${entry.getTeamName()}"/></a>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                        <div style="font-size: large; margin-left: 10px;">
                                            <c:out value="${entry.getGoals()}"/>
                                        </div>
                                    </div>

                                </div>
                        </li>
                    </c:forEach>
                </ol>

            </div>
            <div class="col-lg-6 col-sm-12 fh">
                <div class="full-height">
                    <c:choose>
                        <c:when test="${not empty matches}">
                            <div class="match-header">
                                <h3 id="matchesString">Matches:</h3>
                                <select id="matchFilter">
                                    <option value="past" selected>Past Matches</option>
                                    <option value="upcoming">Upcoming Matches</option>
                                    <option value="tba">Not Scheduled Yet</option>
                                    <option value="all">All Matches</option>
                                </select>
                            </div>
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
                                        <a id="match-link" href="<c:url value=" /match/${match.getId()}" />">
                                        <div class="match-detail">
                                            <div class="team-detail team1-detail">
                                                <c:choose>
                                                    <c:when test="${not empty team1Logo}">
                                                        <img src="data:image/jpeg;base64, ${team1Logo}"
                                                            class="logo-img" alt="team logo">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="<c:url value='/media/logo_placeholder.png' />"
                                                            class="logo-img" alt="default logo">
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
                                                        <img src="data:image/jpeg;base64, ${team2Logo}"
                                                            class="logo-img" alt="team logo">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="<c:url value='/media/logo_placeholder.png' />"
                                                            class="logo-img" alt="default logo">
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
        <c:import url="/jsp/commons/footer.jsp"/>
    </div>

</body>

<script>
    window.addEventListener("pageshow", function (event) {
        var historyTraversal = event.persisted ||
            (typeof window.performance != "undefined" &&
                window.performance.navigation.type === 2);
        if (historyTraversal) {
            // Handle page restore.
            window.location.reload();
        }
    });

    function setStartingMinPlayersAndMaxPlayers() {
        // Get the elements
        const maxTeamInput = document.getElementById('maxTeam');
        const startingPlayersInput = document.getElementById('startingPlayers');
        const minPlayersInput = document.getElementById('minPlayers');
        const maxPlayersInput = document.getElementById('maxPlayers');

        // Deny keyboard input for all 3 input
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
            }
            else if (startingPlayers > minPlayersInput.value) minPlayersInput.value = startingPlayers;
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
            }
            else if (maxPlayers < minPlayers) minPlayersInput.value = maxPlayersInput.value;
        })
    }

    function setStartAndDeadlineDate() {

        // Get tomorrow's date in the format YYYY-MM-DD
        const date = new Date();
        date.setDate(date.getDate() + 1);
        const tomorrow = date.toISOString().split('T')[0];

        // Get the startDate and deadline input element
        const startDateInput = document.getElementById('startDate');
        const deadlineInput = document.getElementById('deadline');

        // Set the min attribute to tomorrow's date and implements logic for startDate and deadline
        startDateInput.min = tomorrow;
        deadlineInput.min = tomorrow;

        startDateInput.addEventListener('change', function () {
            const startDate = startDateInput.value;
            const deadlineDate = deadlineInput.value;
            if (startDate) {
                deadlineInput.setAttribute('max', startDate);
                if (!deadlineDate || new Date(startDate) < new Date(deadlineDate)) deadlineInput.value = startDate;
            }
            else deadlineInput.removeAttribute('max');
        });
    }

    function manageForm() {
        document.getElementById('editTournamentForm').addEventListener('submit', function (event) {
            event.preventDefault(); // Prevent form submission

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

            // Convert the image in base64
            var logoFile = document.getElementById("logo").files[0];
            if (logoFile) {
                var reader = new FileReader();

                reader.onload = function () {
                    formData.append('logo', reader.result);
                };
            }

            // Make AJAX request to create the tournament
            fetch('/api/tournaments/${tournament.getId()}', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            }).then(response => {
                if (response.ok) {
                    // Redirect to another page, replacing the current page in the history
                    window.location.replace("/tournament/${tournament.getId()}");
                }
                else throw new Error('Failed to update tournament');
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
                            window.location.href = '/home';
                        } else {
                            alert('Failed to delete the tournament');
                            window.location.href = '/home';
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
            document.getElementById("editTournamentForm").style.display = "none";
            document.getElementById("btnEditTournament").style.display = "block";
            document.getElementById("main-container").style.display = "block";
            document.getElementById("form-container").style.display = "none";
            document.querySelector("ul").style.display = "block";
        });
    }

    document.addEventListener('DOMContentLoaded', function () {
        document.getElementById("seeTournamentTable").addEventListener('click', function() {
            document.getElementById("rankingScorers").style.display = "none";
            // document.getElementById("tournamentTable").style.display = "block";
            this.style.backgroundColor = "darkblue";
            document.getElementById("seeRankingScorers").style.backgroundColor = "#007bff";
        });

        document.getElementById("seeRankingScorers").addEventListener('click', function() {
            // document.getElementById("tournamentTable").style.display = "none";
            document.getElementById("rankingScorers").style.display = "block";
            // Assicurati che la classe w-100 sia applicata
            document.getElementById("rankingScorers").classList.add("w-100");
            // Applica anche lo stile di larghezza direttamente
            document.getElementById("rankingScorers").style.width = "100%";
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
                    // Find the result div inside the match li
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
                    // Handle the display logic
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
        } else {
            console.log('matchFilter element not found');
        }
        var generateMatchesButton = document.getElementById('generateMatches');
        if (generateMatchesButton) {
            generateMatchesButton.addEventListener('click', function () {
                generateMatchesButton.disabled = true; // Disable the button to prevent multiple clicks
                generateMatchesButton.textContent = 'Loading...'; // Change button text to indicate loading
                var xhr = new XMLHttpRequest();
                xhr.open('POST', '/matches/tournaments/${tournament.id}', true);
                xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                xhr.onload = function () {
                    if (xhr.status >= 200 && xhr.status < 300) {
                        window.location.reload();
                    }
                };
                // Restore original button on error
                xhr.onerror = function () {
                    generateMatchesButton.disabled = false;
                    generateMatchesButton.textContent = 'Close Subscriptions and\nGenerate Matches';
                };
                xhr.send();
            });
        }

        var btnEditTournament = document.getElementById("btnEditTournament");
        btnEditTournament.addEventListener("click", function () {
            document.getElementById("editTournamentForm").style.display = "block";
            document.getElementById("form-container").style.display = "block";
            document.getElementById("btnEditTournament").style.display = "none";
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
        // I need to do that to avoid that back-forward cache (BFCache)
        // mismatches select menu and shown matches
        $("#matchFilter").val('past');
    });
</script>

</html>
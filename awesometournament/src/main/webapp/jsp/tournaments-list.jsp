<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <title>AwesomeTournaments - Home</title>

        <c:import url="/jsp/commons/head.jsp"/>

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

            li.tournament {
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

            li.tournament:hover {
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
                height: 100%;
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

            .error-message {
                display: none;
                color: red;
                border: 1px solid red;
                padding: 5px;
                margin-top: 5px;
            }

            .tournament-element {
                height: 100px;
                display: flex;
                flex-direction: row;
                justify-content: space-between;
            }
        </style>
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
                                        <c:out value="${tournament.getStartingPlayers()}"/> players per team.
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
                                        <c:otherwise>
                                            <!-- or a default -->
                                            <!-- <img src="<c:url value="/media/AT_logo.png"/>" class="logo" alt="default logo"> -->
                                        </c:otherwise>
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

        <script>

            function updateTimers() {
                var tournaments = document.querySelectorAll('.tournament');

                tournaments.forEach(function (tournament) {
                    var deadline = new Date(tournament.getAttribute('data-deadline'));
                    var now = new Date();
                    var timeLeft = deadline - now;

                    if (timeLeft > 0) {
                        var days = Math.floor(timeLeft / (1000 * 60 * 60 * 24));
                        var hours = Math.floor((timeLeft % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                        var minutes = Math.floor((timeLeft % (1000 * 60 * 60)) / (1000 * 60));
                        var seconds = Math.floor((timeLeft % (1000 * 60)) / 1000);

                        tournament.querySelector('.time-left').innerHTML =
                            "<strong>" + days + "d " + hours + "h " + minutes + "m " + seconds + "s</strong>";
                    }
                    else tournament.querySelector('.time-left').innerHTML = "<strong> Registration closed</strong>";
                });
            }

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

                startingPlayersInput.addEventListener('click', function() {
                    const startingPlayers = parseInt(startingPlayersInput.value, 10);

                    if (startingPlayers > minPlayersInput.value && startingPlayers > maxPlayersInput.value) {
                        minPlayersInput.value = startingPlayers;
                        maxPlayersInput.value = startingPlayers;
                    }
                    else if(startingPlayers > minPlayersInput.value) minPlayersInput.value = startingPlayers;
                    if(startingPlayers < 1) startingPlayersInput.value = 1;
                });

                minPlayersInput.addEventListener('click', function() {
                    const minPlayers = parseInt(minPlayersInput.value, 10);
                    const maxPlayers = parseInt(maxPlayersInput.value, 10);
                    const startingPlayers = parseInt(startingPlayersInput.value, 10);

                    if(minPlayers < startingPlayers) startingPlayersInput.value = minPlayersInput.value;

                    if(minPlayers > maxPlayers) maxPlayersInput.value = minPlayersInput.value
                });

                maxPlayersInput.addEventListener('click', function () {
                    const minPlayers = parseInt(minPlayersInput.value, 10);
                    const maxPlayers = parseInt(maxPlayersInput.value, 10);
                    const startingPlayers = parseInt(startingPlayersInput.value, 10);

                    if(maxPlayers < minPlayers && maxPlayers < startingPlayers) {
                        startingPlayersInput.value = maxPlayersInput.value;
                        minPlayersInput.value = maxPlayersInput.value;
                    }
                    else if(maxPlayers < minPlayers) minPlayersInput.value = maxPlayersInput.value;
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

                startDateInput.addEventListener('change', function() {
                    const startDate = startDateInput.value;
                    const deadlineDate = deadlineInput.value;
                    if(startDate) {
                        deadlineInput.setAttribute('max', startDate);
                        if (!deadlineDate || new Date(startDate) < new Date(deadlineDate)) deadlineInput.value = startDate;
                    }
                    else deadlineInput.removeAttribute('max');
                });
            }

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
                    document.getElementById("addTournamentCard").style.display = "block";
                    document.getElementById("container").style.display = "none";
                });
            }

            function hideForm() {
                var btnCancelCreateTournament = document.getElementById("cancelCreateTournament");
                btnCancelCreateTournament.addEventListener("click", function () {
                    document.getElementById("addTournamentCard").style.display = "none";
                    document.getElementById("container").style.display = "block";;
                });
            }
            function manageForm() {
                document.getElementById('createTournamentForm').addEventListener('submit', function(event) {
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

                    // Make AJAX request to create the tournament
                    fetch('/api/tournaments', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(formData)
                    }).then(async response => {
                            if (response.ok) {
                                // Redirect to another page, replacing the current page in the history
                                window.location.replace("/tournaments");
                                let body = await response.json();

                                window.location.replace('/tournament/'+body.data.id);
                            }
                            else throw new Error('Failed to create the tournament');
                    }).catch(error => {
                            console.error('Error:', error);
                            alert('Failed to create the tournament. Please try again.');
                    });
                });
            }

                document.addEventListener("DOMContentLoaded", function() {
                updateTimers();
                setInterval(updateTimers, 1000); // Update every second
                filterTournaments();
                createTournament();
                manageForm();
                setStartingMinPlayersAndMaxPlayers();
                setStartAndDeadlineDate();
                hideForm();
            });
        </script>
    </body>

</html>

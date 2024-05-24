<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AwesomeTournaments - Match</title>

    <c:import url="/jsp/commons/head.jsp" />

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

        h1, h2 {
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
            border-radius: 5px;
            background-color: #f9f9f9;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        li:hover {
            background-color: #e9e9e9;
        }

        .match-detail {
            display: flex;
            justify-content: space-between;
            align-items: center;
            width: 100%;
            max-width: 800px;
            margin-bottom: 10px;
        }

        .team-detail, .score {
            margin-top: 12px;
            display: flex;
            flex-direction: column;
            align-items: center;
            flex: 1;
        }

        .team1-detail {
            align-items: center;
        }

        .team2-detail {
            align-items: center;
        }

        .score {
            flex: 0 1 200px;
            justify-content: center;
        }

        .team1-detail img, .team2-detail img {
            width: 100px;
            height: auto;
            margin-bottom: 10px;
        }

        #team1Name, #team2Name {
            color: black;
            font-size: 1.2em;
            text-align: center;
        }

        .match-info {
            text-align: center;
            margin-top: 20px;
        }

        .match-info p {
            margin: 5px 0;
            color: black;
        }

        .info-container {
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        /* Modal styles */
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgb(0, 0, 0);
            background-color: rgba(0, 0, 0, 0.4);
            padding-top: 60px;
        }

        .modal-content {
            background-color: #fefefe;
            margin: 5% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
            max-width: 600px;
            border-radius: 10px;
        }

        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        .close:hover, .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }

        .modal-content h2 {
            margin-top: 0;
            color: black;
        }

        .modal-content label {
            display: block;
            margin: 5px 0 5px;
            color: #555;
        }

        .modal-content input[type="number"],
        .modal-content input[type="text"],
        .modal-content input[type="submit"] {
            width: calc(100% - 20px);
            padding: 5px;
            margin: 5px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        .modal-content input[type="submit"] {
            background-color: #007bff;
            color: white;
            border: none;
            cursor: pointer;
        }

        .modal-content input[type="submit"]:hover {
            background-color: #0056b3;
        }

        .back-btn {
            background-color: #007bff;
            color: white;
            padding: 7px 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .back-btn:hover {
            background-color: #0056b3;
        }

        .event-list {
            list-style-type: none;
            padding: 0;
        }

        .event-item {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
        }

        .event-time {
            flex: 1;
            text-align: center;
        }

        .event-icon {
            width: 24px;
            height: 24px;
        }

        .delete-button {
            margin-left: 10px;
        }

        .event-item .player {
            margin-left: 10px;
        }

    </style>
</head>

<body>
<!-- header -->
<c:import url="/jsp/commons/header.jsp" />

<div class="container" data-owner="${owner}" data-match-date="${match.matchDate}">
    <button class="back-btn" onclick="window.location.href='/tournament/${match.tournamentId}';">
        <img src="/media/go-back.png" width="30px" height="auto"> Back to tournament
    </button>
    <c:choose>
        <c:when test="${match.isFinished}">
            <h2>Match Result</h2>
        </c:when>
        <c:otherwise>
            <h2>Not Played Yet</h2>
        </c:otherwise>
    </c:choose>

    <div class="match-detail">
        <div class="team-detail team1-detail">
            <a href="<c:url value="/team/${match.team1Id}" />">
                <img id="team1Logo" src="" alt="Team 1 Logo">
            </a>
            <div>
                <c:choose>
                    <c:when test="${match.result == 'TEAM1'}">
                        <strong>
                            <p id="team1Name">Loading...</p>
                        </strong>
                    </c:when>
                    <c:otherwise>
                        <p id="team1Name">Loading...</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="score" style="font-size: 2.2em;">
            ${match.team1Score} - ${match.team2Score}
            <div>
                <button id="update-result" class="btn" style="display: none;"><img src="/media/edit.png"
                                                                                   width="30px" height="auto" style="padding-right: 10px;">Edit Result</button>
            </div>
        </div>

        <div class="team-detail team2-detail">
            <a href="<c:url value="/team/${match.team2Id}" />">
                <img id="team2Logo" src="" alt="Team 2 Logo">
            </a>
            <div>
                <c:choose>
                    <c:when test="${match.result == 'TEAM2'}">
                        <strong>
                            <p id="team2Name">Loading...</p>
                        </strong>
                    </c:when>
                    <c:otherwise>
                        <p id="team2Name">Loading...</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <div class="match-info">
        <div class="info-container">
            <div>
                <p><strong>Referee:</strong> ${match.referee}</p>
                <c:choose>
                    <c:when test="${match.matchDate != null}">
                        <p><strong>Match Date:</strong> <span id="matchDate">${match.matchDate}</span></p>
                    </c:when>
                    <c:otherwise>
                        <p><strong>Match Date:</strong> <span id="matchDate">TBA</span></p>
                    </c:otherwise>
                </c:choose>
            </div>
            <div>
                <button id="update-info" class="btn" style="display: none;">
                    <img src="/media/edit.png" width="20px" height="auto" style="padding-right: 5px;">
                    Edit Info
                </button>
            </div>
        </div>
    </div>

    <div>
        <ul class="event-list">
            <c:forEach items="${events}" var="event">
                <li class="event-item">
                    <c:choose>
                        <c:when test="${event.type == 'GOAL'}">
                            <img src="/media/goal.png" alt="Goal" class="event-icon" />
                            <div href="<c:url value="/players/${event.playerId}"/>" class="player">
                                <c:out value="${event.playerId}" />
                            </div>
                        </c:when>
                        <c:when test="${event.type == 'YELLOW_CARD'}">
                            <img src="/media/yellow_card.png" alt="Yellow Card" class="event-icon" />
                            <div class="player">
                                <c:out value="${event.playerId}" />
                            </div>
                        </c:when>
                        <c:when test="${event.type == 'RED_CARD'}">
                            <img src="/media/red_card.png" alt="Red Card" class="event-icon" />
                            <div class="player">
                                <c:out value="${event.playerId}" />
                            </div>
                        </c:when>
                    </c:choose>
                    <div class="event-time">
                        <c:out value="${event.time}" />'
                    </div>
                    <c:if test="${owner}">
                        <button class="delete-button" onclick="deleteEvent(<c:out value='${event.id}' />)">delete</button>
                    </c:if>
                </li>
            </c:forEach>
        </ul>
    </div>
    <c:if test="${owner}">
        <c:if test="${!goals_coherent}">
            <div class="alert alert-danger" role="alert">
                Attention! the number of goal events is not
                coherent with the saved score!
            </div>
        </c:if>
        <form id="newEvent" style="background-color: rgb(82, 81, 81);">
            <label for="player_id">Player: </label>
            <select name="player_id" id="player_id">
                <c:forEach var="player" items="${players}">
                    <option value="${player.id}">
                        <c:out value="${player.getFullName()}"></c:out>
                    </option>
                </c:forEach>
            </select><br>

            <label for="type">Type of event: </label>
            <select name="type" id="type">
                <c:forEach var="type" items="${types}">
                    <option value="${type}">
                        <c:out value="${type}"></c:out>
                    </option>
                </c:forEach>
            </select><br>
            <label for="time">Time: </label>
            <input type="number" id="time" name="time" min="1" max="90"><br>
            <input type="submit" value="Add">
        </form>
    </c:if>
</div>

<!-- footer -->
<c:import url="/jsp/commons/footer.jsp" />

<!-- Modal to update result -->
<c:import url="/jsp/components/modal-result.jsp" />

<!-- Modal to update info -->
<c:import url="/jsp/components/modal-info.jsp" />

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const team1Id = "${match.team1Id}";
        const team2Id = "${match.team2Id}";

        fetchTeamData(team1Id, 1);
        fetchTeamData(team2Id, 2);

        // Modal functionality
        var resultModal = document.getElementById("resultModal");
        var infoModal = document.getElementById("infoModal");
        var resultBtn = document.getElementById("update-result");
        var infoBtn = document.getElementById("update-info");
        var spanClose = document.getElementsByClassName("close");

        resultBtn.onclick = function () {
            console.log("update-result button clicked");
            resultModal.style.display = "block";
        }

        infoBtn.onclick = function () {
            console.log("update-info button clicked");
            infoModal.style.display = "block";
        }

        for (var i = 0; i < spanClose.length; i++) {
            spanClose[i].onclick = function () {
                console.log("close button clicked");
                resultModal.style.display = "none";
                infoModal.style.display = "none";
            }
        }

        window.onclick = function (event) {
            if (event.target == resultModal) {
                console.log("Click outside resultModal detected");
                resultModal.style.display = "none";
            }
            if (event.target == infoModal) {
                console.log("Click outside infoModal detected");
                infoModal.style.display = "none";
            }
        }

        document.getElementById("updateResultForm").addEventListener("submit", function (event) {
            event.preventDefault();
            console.log("updateResultForm submit event");

            const formData = new FormData(this);
            const jsonObject = {};
            jsonObject["team1Score"] = Number(formData.get("team1Score"));
            jsonObject["team2Score"] = Number(formData.get("team2Score"));
            const jsonData = JSON.stringify(jsonObject);
            const xhr = new XMLHttpRequest();
            console.log("Sending data to /api/matches/${match.id}: ", jsonData)
            xhr.open("PUT", "/api/matches/${match.id}", true);
            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    console.log("Match result updated successfully!");
                    alert("Match result updated successfully!");
                    resultModal.style.display = "none";
                    window.location.reload();
                }
            };
            xhr.send(jsonData);
        });

        document.getElementById("updateInfoForm").addEventListener("submit", function (event) {
            event.preventDefault();
            console.log("updateInfoForm submit event");

            const formData = new FormData(this);
            const jsonObject = {};
            const date = new Date(formData.get("date"));
            const isoDate = toISOString(date);
            jsonObject["referee"] = formData.get("referee");
            jsonObject["matchDate"] = isoDate;
            const jsonData = JSON.stringify(jsonObject);
            const xhr = new XMLHttpRequest();
            console.log("Sending data to /api/matches/${match.id}: ", jsonData)
            xhr.open("PUT", "/api/matches/${match.id}", true);
            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    console.log("Match info updated successfully!");
                    alert("Match info updated successfully!");
                    infoModal.style.display = "none";
                    window.location.reload();
                }
            };
            xhr.send(jsonData);
        });

        // Show edit button if owner and match date is in the past or today
        const container = document.querySelector('.container');
        const matchDate = new Date(container.dataset.matchDate);
        const now = new Date(); // current time
        const owner = container.dataset.owner === 'true';

        if (owner) {
            document.getElementById("update-info").style.display = "block";
            if (matchDate <= now) {
                document.getElementById("update-result").style.display = "block";
            }
        }
    });

    function toISOString(date) {
        const tzo = -date.getTimezoneOffset();
        const dif = tzo >= 0 ? '+' : '-';
        const pad = function (num) {
            return (num < 10 ? '0' : '') + num;
        };
        const padMilliseconds = function (num) {
            return (num < 10 ? '00' : num < 100 ? '0' : '') + num;
        };

        return date.getFullYear() +
            '-' + pad(date.getMonth() + 1) +
            '-' + pad(date.getDate()) +
            'T' + pad(date.getHours()) +
            ':' + pad(date.getMinutes()) +
            ':' + pad(date.getSeconds()) +
            '.' + padMilliseconds(date.getMilliseconds()) +
            '+00:00'
    }

    function deleteEvent(id) {
        const xhr = new XMLHttpRequest();
        xhr.open("DELETE", "/api/events/" + id, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                // TODO delete the item without reloading the page
                window.location.reload();
            }
            console.log(xhr);
        };
        xhr.send();
    }

    document.getElementById("newEvent").addEventListener("submit", function (event) {
        event.preventDefault();
        const formData = new FormData(this);
        const jsonObject = {};
        formData.forEach(function (value, key) {
            jsonObject[key] = value;
        });
        const jsonData = JSON.stringify(jsonObject);
        const xhr = new XMLHttpRequest();
        xhr.open("POST", "/api/matches/${matchId}/events", true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                window.location.reload();
            }
        };
        xhr.send(jsonData);
    });

    // Function to fetch team data and update the DOM
    function fetchTeamData(teamId, teamNumber) {
        const xhr = new XMLHttpRequest();
        xhr.open("GET", `/api/teams/\${teamId}`, true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    const response = JSON.parse(xhr.responseText);
                    if (response.status === "OK" && response.data) {
                        const teamData = response.data;
                        // Update the DOM with the team data
                        document.getElementById(`team\${teamNumber}Name`).innerText = teamData.name;

                        // Handle logo display
                        const logoElement = document.getElementById(`team\${teamNumber}Logo`);
                        if (teamData["base64-logo"] != null) {
                            logoElement.src = `data:image/png;base64,\${teamData["base64-logo"]}`;
                        } else {
                            logoElement.src = "/media/logo_placeholder.png";
                        }
                    } else {
                        console.error(`Error in response data for Team \${teamNumber}:`, response.message);
                        alert(`Failed to fetch team \${teamNumber} data: ` + response.message);
                    }
                } else {
                    console.error(`Error fetching team \${teamNumber} data:`, xhr.statusText);
                    alert(`Failed to fetch team \${teamNumber} data: ` + xhr.statusText);
                }
            }
        };
        xhr.onerror = function () {
            console.error(`Network error while fetching team \{teamNumber} data`);
            alert(`Network error while fetching team \${teamNumber} data`);
        };
        xhr.send();
    }
</script>
</body>

</html>

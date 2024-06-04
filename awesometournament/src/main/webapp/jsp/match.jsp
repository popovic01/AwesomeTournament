<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AwesomeTournaments - Match</title>

    <c:import url="/jsp/commons/head.jsp" />
    <link rel="stylesheet" type="text/css" href="../css/match.css" />
</head>

<body>
<!-- header -->
<c:import url="/jsp/commons/header.jsp" />

<div class="container" data-owner="${owner}" data-match-date="${match.matchDate}">
    <button class="blue-btn" onclick="window.location.href='/tournament/${match.tournamentId}';">
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

    <c:if test="${!goals_coherent}">
        <div class="alert alert-danger" role="alert">
            Attention! the number of goal events is not
            coherent with the saved score!
        </div>
    </c:if>

    <div>
        <ul class="event-list">
            <c:forEach items="${eventdetails}" var="eventdetail">
                <c:set var="event" value="${eventdetail.getEvent()}"/>
                <li
                    class="event-list-container"
                    <c:if test="${eventdetail.getTeam() == 2}">style="justify-content: flex-end;"</c:if>
                    >
                    <div class="event-list-son">
                        <c:if test="${owner && eventdetail.getTeam() == 1}">
                            <button class="blue-btn"
                                onclick="deleteEvent(<c:out value='${event.id}'/>)">
                                <img src="/media/delete.png" width="20px" height="auto" alt="delete icon">
                            </button>
                        </c:if>
                        <c:if test="${eventdetail.getTeam() == 2}">
                            <div class="player-surname">
                                <c:out value="${eventdetail.getName()}" />
                            </div>
                        </c:if>
                        <img src="/media/${event.type}.png" alt="${event.type}" class="event-icon" />
                        <c:if test="${eventdetail.getTeam() == 1}">
                            <div class="player-surname">
                                <c:out value="${eventdetail.getName()}" />
                            </div>
                        </c:if>
                        <c:if test="${owner && eventdetail.getTeam() == 2}">
                            <button class="blue-btn"
                                onclick="deleteEvent(<c:out value='${event.id}'/>)">
                                <img src="/media/delete.png" width="20px" height="auto" alt="delete icon">
                            </button>
                        </c:if>
                    </div>
                    <div class="event-list-middle">
                        <c:out value="${event.time}" />'
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>

    <c:if test="${owner}">
        <div class = "event-btn">
            <button id="add-event" class="blue-btn">
                Add Event
            </button>
        </div>
    </c:if>
</div>

<!-- footer -->
<c:import url="/jsp/commons/footer.jsp" />

<!-- Modal to update result -->
<c:import url="/jsp/components/modal-result.jsp" />

<!-- Modal to update info -->
<c:import url="/jsp/components/modal-info.jsp" />

<!-- Modal to add event -->
<c:import url="/jsp/components/modal-event.jsp" />

<script>
    document.addEventListener("DOMContentLoaded", function () {
        function formatDateWithTimezone(isoString) {
            const date = new Date(isoString);
            const options = {
                year: 'numeric',
                month: 'long',
                day: 'numeric',
                hour: '2-digit',
                minute: '2-digit',
                timeZoneName: 'short'
            };
            return date.toLocaleDateString(undefined, options);
        }

        const matchDateElement = document.getElementById("matchDate");
        if ("${match.matchDate}" != "") {
            matchDateElement.textContent = formatDateWithTimezone("${match.matchDate}");
        } else {
            matchDateElement.textContent = "TBA";
        }

        function formatDateForInput(isoString) {
            const date = new Date(isoString);
            const year = date.getFullYear();
            const month = ('0' + (date.getMonth() + 1)).slice(-2);
            const day = ('0' + date.getDate()).slice(-2);
            const hours = ('0' + date.getHours()).slice(-2);
            const minutes = ('0' + date.getMinutes()).slice(-2);
            return `\${year}-\${month}-\${day}T\${hours}:\${minutes}`;
        }

        const formMatchDateElement = document.getElementById("date");
        const formFormattedDate = formatDateForInput("${match.matchDate}");
        formMatchDateElement.value = formFormattedDate;

        const team1Id = "${match.team1Id}";
        const team2Id = "${match.team2Id}";

        fetchTeamData(team1Id, 1);
        fetchTeamData(team2Id, 2);

        // Modal functionality
        var resultModal = document.getElementById("resultModal");
        var infoModal = document.getElementById("infoModal");
        var eventModal = document.getElementById("eventModal");
        var resultBtn = document.getElementById("update-result");
        var infoBtn = document.getElementById("update-info");
        var eventBtn = document.getElementById("add-event");
        var spanClose = document.getElementsByClassName("close");

        resultBtn.onclick = function () {
            console.log("update-result button clicked");
            resultModal.style.display = "block";
        }

        infoBtn.onclick = function () {
            console.log("update-info button clicked");
            infoModal.style.display = "block";
        }

        eventBtn.onclick = function () {
            console.log("add-event button clicked");
            eventModal.style.display = "block";
        }

        for (var i = 0; i < spanClose.length; i++) {
            spanClose[i].onclick = function () {
                console.log("close button clicked");
                resultModal.style.display = "none";
                infoModal.style.display = "none";
                eventModal.style.display = "none";
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
            if (event.target == eventModal) {
                console.log("Click outside eventModal detected");
                eventModal.style.display = "none";
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
            const isoDate = date.toISOString();
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
                console.log("Match event updated successfully!");
                alert("Match event updated successfully!");
                infoModal.style.display = "none";
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

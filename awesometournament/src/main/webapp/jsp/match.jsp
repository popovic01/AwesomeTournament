<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>AwesomeTournaments - Match</title>
            <c:import url="/jsp/common/head.jsp" />
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

                h1,
                h2 {
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

                .match-detail {
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                    width: 100%;
                    max-width: 800px;
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

                .team1-detail img,
                .team2-detail img {
                    width: 100px;
                    height: auto;
                    margin-bottom: 10px;
                }

                #team1Name,
                #team2Name {
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

                .close:hover,
                .close:focus {
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
            </style>
        </head>

        <body>
            <!-- header -->
            <c:import url="/jsp/common/header.jsp" />
            <div class="container" data-owner="${owner}" data-match-date="${match.matchDate}">
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
                        <img id="team1Logo" src="" alt="Team 1 Logo">
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
                                    width="30px" height="auto" style="padding-right: 10px;">Edit
                                Result</button>
                        </div>
                    </div>
                    <div class="team-detail team2-detail">
                        <img id="team2Logo" src="" alt="Team 2 Logo">
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
                    <ul>
                        <c:forEach items="${events}" var="event">
                            <li>
                                <c:out value="${event}" />
                                <c:if test="${owner}">
                                    <button onclick="deleteEvent(<c:out value='${event.id}' />)">delete</button>
                                </c:if>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <c:if test="${owner}">
                    <div style="color: red;">
                        You are the admin of the tournament this match belongs to
                    </div>
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
            <c:import url="/jsp/common/footer.jsp" />

            <!-- Modal to update result -->
            <div id="resultModal" class="modal">
                <div class="modal-content">
                    <span class="close">&times;</span>
                    <form id="updateResultForm">
                        <h2>Update Match Result</h2>
                        <label for="team1Score">Team 1 Score:</label>
                        <input type="number" id="team1Score" name="team1Score" value="${match.team1Score}" min="0">
                        <label for="team2Score">Team 2 Score:</label>
                        <input type="number" id="team2Score" name="team2Score" value="${match.team2Score}" min="0">
                        <input type="submit" value="Update">
                    </form>
                </div>
            </div>

            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    const team1Id = "${match.team1Id}";
                    const team2Id = "${match.team2Id}";

                    fetchTeamData(team1Id, 1);
                    fetchTeamData(team2Id, 2);

                    // Modal functionality
                    var modal = document.getElementById("resultModal");
                    var btn = document.getElementById("update-result");
                    var span = document.getElementsByClassName("close")[0];

                    btn.onclick = function () {
                        console.log("update-result button clicked");
                        modal.style.display = "block";
                    }

                    span.onclick = function () {
                        console.log("close button clicked");
                        modal.style.display = "none";
                    }

                    window.onclick = function (event) {
                        if (event.target == modal) {
                            console.log("Click outside modal detected");
                            modal.style.display = "none";
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
                                modal.style.display = "none";
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
                    console.log(matchDate)
                    console.log(now)
                    console.log(owner && (matchDate <= now))

                    if (owner && (matchDate <= now)) {
                        document.getElementById("update-result").style.display = "block";
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
                                    if (teamData.logo) {
                                        logoElement.src = teamData.logo;
                                    } else if (teamData["base64-logo"]) {
                                        logoElement.src = `data:image/png;base64,\${teamData["base64-logo"]}`;
                                    } else {
                                        //TODO DEFAULT LOGO MUST BE FIXED THIS IT IS ONLY FOR DEBUG PURPOSES
                                        logoElement.src = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d0/Logo_of_AC_Milan.svg/1306px-Logo_of_AC_Milan.svg.png"; // Path to a placeholder image
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
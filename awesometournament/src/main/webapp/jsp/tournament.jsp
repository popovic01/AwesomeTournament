<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <!DOCTYPE html>
        <html lang="en">

        <head>
            <title>AwesomeTournaments - Tournament</title>
            <c:import url="/jsp/common/head.jsp" />
            <style>
                .container {
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
            </style>
        </head>

        <body>
            <!-- header -->
            <c:import url="/jsp/common/header.jsp" />

            <div class="container">
                <div class="title-logo-wrapper">
                    <p class="fs-1 text-dark">
                        <c:out value="${tournament.name}" />
                    </p>
                    <c:if test="${not empty tournament.base64Logo}">
                        <img src="data:image/jpg;base64,${tournament.base64Logo}" />
                    </c:if>
                </div>

                <div>
                    <div>
                        <c:out value="${tournament}" />
                    </div>
                </div>
                <c:if test="${owner}">
                    <div style="color: red;">
                        You are the admin of this tournament
                    </div>
                    <!-- TODO Will be placed somewhere else -->
                    <c:if test="${empty matches}">
                        <!-- If it is moved out of this <c> block you must check if user is owner -->
                        <button id="generateMatches" class="btn btn-primary">Close Subscriptions and<br>Generate
                            Matches</button>
                    </c:if>
                </c:if>

                <p class="fs-4 text-dark">Teams</p>
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col"></th>
                            <th scope="col">Name</th>
                            <th scope="col">Logo</th>
                            <th scope="col">See Details</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${teams}" var="team" varStatus="i">
                            <tr>
                                <th scope="row">${i.count}</th>
                                <td>
                                    <c:out value="${team.name}" />
                                </td>
                                <td>
                                    <c:if test="${not empty team.base64Logo}">
                                        <img src="data:image/jpg;base64,${team.base64Logo}" class="logo-img" />
                                    </c:if>
                                    <c:if test="${empty team.base64Logo}">
                                        <p class="text-secondary">No logo available</p>
                                    </c:if>
                                </td>
                                <td>
                                    <a href="<c:url value=" /team/${team.getId()}" />">
                                    Details
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <div class="container">
                    <c:choose>
                        <c:when test="${not empty matches}">
                            <div class="match-header">
                                <h3>Matches:</h3>
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
                                                <c:set var="team1Logo" value="${team.logo}" />
                                            </c:if>
                                            <c:if test="${team.id == match.team2Id}">
                                                <c:set var="team2Name" value="${team.name}" />
                                                <c:set var="team2Logo" value="${team.logo}" />
                                            </c:if>
                                        </c:forEach>
                                        <a id="match-link" href="<c:url value=" /match/${match.getId()}" />">
                                        <div class="match-detail">
                                            <div class="team-detail team1-detail">
                                                <!-- TODO LOGO MUST BE FIXED THIS IT IS ONLY FOR DEBUG PURPOSES-->
                                                <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/d/d0/Logo_of_AC_Milan.svg/1306px-Logo_of_AC_Milan.svg.png"
                                                    alt="Logo of ${team1Name}" style="height: 30px;">
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
                                                <!-- TODO LOGO MUST BE FIXED THIS IT IS ONLY FOR DEBUG PURPOSES-->
                                                <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/d/d0/Logo_of_AC_Milan.svg/1306px-Logo_of_AC_Milan.svg.png"
                                                    alt="Logo of ${team2Name}" style="height: 30px;">
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
                            <p class="text-dark">
                                No matches available at the moment. <br>
                                Come back here when the subscriptions are closed!
                            </p>
                        </c:otherwise>
                    </c:choose>
                </div>
                <!-- footer -->
                <c:import url="/jsp/common/footer.jsp" />
        </body>

        </html>

        <script>
            document.addEventListener('DOMContentLoaded', function () {
                var filterControl = document.getElementById('matchFilter');
                if (filterControl) {
                    filterControl.addEventListener('change', function () {
                        var selectedFilter = this.value;
                        var matches = document.querySelectorAll('#matchList li');
                        matches.forEach(function (match) {
                            var isFinished = match.getAttribute('is-finished') === 'true';
                            var matchDate = match.getAttribute('date'); // Corrected attribute

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
            });

            $(window).bind("pageshow", function () {
                // I need to do that to avoid that back-forward cache (BFCache)
                // mismatches select menu and shown matches
                $("#matchFilter").val('past');
            });
        </script>
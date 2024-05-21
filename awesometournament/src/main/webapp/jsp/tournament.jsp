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

            .edit-button {
                position: absolute;
                right: 420px;
                top: 112px;
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

            .inline-container {
                display: flex;
                align-items: center;
            }

            a.list {
                text-decoration: none;
                color: inherit;
                display: block;
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
                <c:choose>
                    <c:when test="${not empty tournament.base64Logo}">
                        <img src="data:image/jpg;base64,${tournament.base64Logo}" />
                    </c:when>
                    <c:otherwise>
                        <img src="<c:url value="/media/tournament_logo.png"/>" alt="default logo">
                    </c:otherwise>
                </c:choose>
                <c:if test="${owner}">
                    <button class="btn btn-primary edit-button">Edit</button>
                </c:if>
            </div>

            <c:if test="${owner}">
            <!-- TODO Will be placed somewhere else -->
                <c:if test="${empty matches}">
                    <!-- If it is moved out of this <c> block you must check if user is owner -->
                    <button id="generateMatches" class="btn btn-primary">Close Subscriptions and<br>Generate
                    Matches</button>
                </c:if>
            </c:if>

            <div class="inline-container">
                <p class="fs-4 text-dark">Teams</p>
                <a href="/ranking/scorers/tournaments/${tournament.getId()}">
                    <button style="margin-left: 30px" id="seeTournamentTable" class="btn btn-primary">
                        See tournament table
                    </button>
                </a>
            </div>
            <ol class="team-list">
                <c:forEach items="${teams}" var="team" varStatus="i">
                    <li>
                        <a class="list" href ="/team/${team.getId()}">
                            <c:out value="${team.name}" />
                            <c:choose>
                                <c:when test="${not empty team.getBase64Logo()}">
                                    <img src="data:image/jpeg;base64, ${team.getBase64Logo()}" class="logo-img" alt="team logo">
                                </c:when>
                                <c:otherwise>
                                    <img src="<c:url value='/media/logo_placeholder.png' />" class="logo-img" alt="default logo">
                                </c:otherwise>
                            </c:choose>
                        </a>
                    </li>
                </c:forEach>
            </ol>

            <c:if test="${owner}">
                    <button id="btnAdd" class="btn btn-secondary">
                        Add Team
                    </button>
                </c:if>

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
                                                        <img src="data:image/jpeg;base64, ${team1Logo}" class="logo-img" alt="team logo">
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
                                                        <img src="data:image/jpeg;base64, ${team2Logo}" class="logo-img" alt="team logo">
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
    window.addEventListener("pageshow", function (event) {
        var historyTraversal = event.persisted ||
            (typeof window.performance != "undefined" &&
                window.performance.navigation.type === 2);
        if (historyTraversal) {
            // Handle page restore.
            window.location.reload();
        }
    });

    document.addEventListener('DOMContentLoaded', function () {
        var btnAdd = document.getElementById('btnAdd');

        if (btnAdd) {
            btnAdd.addEventListener('click', function() {
                var url = `/tournament/${tournament.id}/add-team`;
                window.location.href = url;
                var xhr = new XMLHttpRequest();
                xhr.open('GET', url, true);
                xhr.onload = function() {
                    if (xhr.status >= 200 && xhr.status < 300) {
                        window.location.reload();
                    }
                };
            });
        } else {
            console.log('btnAdd element not found');
        }
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
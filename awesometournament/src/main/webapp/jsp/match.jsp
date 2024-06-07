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
                <a href="<c:url value=" /team/${match.team1Id}" />">
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
            <div class="score">
                ${match.team1Score} - ${match.team2Score}
                <div>
                    <button id="update-result" class="btn">
                        <img src="/media/edit.png" width="25px" height="auto"> Edit Result
                    </button>
                </div>
            </div>

            <div class="team-detail team2-detail">
                <a href="<c:url value=" /team/${match.team2Id}" />">
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
                    <button id="update-info" class="btn">
                        <img src="/media/edit.png" width="25px" height="auto"> Edit Info
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
                    <c:set var="event" value="${eventdetail.getEvent()}" />
                    <li class="event-list-container" <c:if test="${eventdetail.getTeam() == 2}">
                        style="justify-content: flex-end;"</c:if>
                        >
                        <div class="event-list-son">
                            <c:if test="${owner && eventdetail.getTeam() == 1}">
                                <button class="blue-btn" onclick="deleteEvent(<c:out value='${event.id}'/>)">
                                    <img src="/media/delete.png" width="30px" height="auto" alt="delete icon">
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
                                <button class="blue-btn" onclick="deleteEvent(<c:out value='${event.id}'/>)">
                                    <img src="/media/delete.png" width="30px" height="auto" alt="delete icon">
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
            <div class="event-btn">
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
        var id = '${match.id}';
        var matchDate = '${match.matchDate}';
        var team1Id = '${match.team1Id}';
        var team2Id = '${match.team2Id}';
    </script>
    <script src="/js/match.js"></script>
</body>

</html>
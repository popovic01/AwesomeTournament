<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <title>AwesomeTournaments - Tournament</title>
    <c:import url="/jsp/commons/head.jsp" />
    <link rel="stylesheet" type="text/css" href="/css/tournament.css" />
</head>

<body>
    <c:import url="/jsp/commons/header.jsp" />
    <c:import url="/jsp/components/edit-tournament-form.jsp" />

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
                                    <li is-finished="${match.isFinished}" date="${match.matchDate}" class="match">
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
    <script>
        var userId = "${userId}";
        var tournamentId = "${tournament.getId()}";
        var matches_ = "${matches}";
    </script>
    <script type="text/javascript" src="/js/tournament.js"></script>
</body>
</html>
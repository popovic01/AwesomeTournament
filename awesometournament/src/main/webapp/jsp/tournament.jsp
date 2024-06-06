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
            <h1 class="tournament-name m-4">
                <strong>${tournament.getName()}</strong>
            </h1>
            <c:choose>
                <c:when test="${not empty tournament.getBase64Logo()}">
                    <img src="data:image/jpg;base64,${tournament.getBase64Logo()}"
                        class="img-fluid rounded-circle ml-3" alt="tournament logo" />
                </c:when>
                <c:otherwise>
                    <img src="<c:url value='/media/tournament_logo.png' />"
                        class="img-fluid rounded-circle ml-3" alt="default logo">
                </c:otherwise>
            </c:choose>
        </div>

        <div class="custom-container">
            <div class="tabs" id="rads">
                <input type="radio" id="radio-1" name="tabs" checked />
                <label class="tab" for="radio-1">Ranking</label>
                <input type="radio" id="radio-2" name="tabs" />
                <label class="tab" for="radio-2">Scorers Ranking</label>
                <input type="radio" id="radio-3" name="tabs" />
                <label class="tab" for="radio-3">Matches</label>
                <span class="glider"></span>
            </div>
        </div>

        <div class="table-container padding-top">
            <div class="table-responsive">
                <c:import url="/jsp/components/tournament-ranking.jsp" />
                <c:import url="/jsp/components/scorers-ranking.jsp" />
            </div>
            <div class="col-lg-6 col-sm-12" id="matches">
                <div class="match-header">
                    <select id="matchFilter">
                        <option value="past">Past Matches</option>
                        <option value="upcoming">Upcoming Matches</option>
                        <option value="tba">Not Scheduled Yet</option>
                        <option value="all" selected>All Matches</option>
                    </select>
                </div>
                <div class = "table-container padding-top">
                    <c:choose>
                        <c:when test="${not empty matches}">
                            <ul id="matchList">
                                <c:forEach items="${matches}" var="match">
                                    <li is-finished="${match.isFinished}" date="${match.matchDate}"
                                        class="match">
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
                                                            <img src="data:image/jpeg;base64,${team1Logo}"
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
                                                            <img src="data:image/jpeg;base64,${team2Logo}"
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
    </div>
    <c:import url="/jsp/commons/footer.jsp" />
</body>

<script type="text/javascript">
    var rad = document.getElementById('rads').children;

    for (var i = 0; i < rad.length; i++) {
        let local_i = i;

        if (i === 0 && rad[i].checked) {
            document.getElementById('tournamentTable').style.display = 'table';
            document.getElementById('rankingTable').style.display = 'none';
            document.getElementById('matches').style.display = 'none';
        } else if (i === 2 && rad[i].checked) {
            document.getElementById('tournamentTable').style.display = 'none';
            document.getElementById('rankingTable').style.display = 'table';
            document.getElementById('matches').style.display = 'none';
        } else if (i === 4 && rad[i].checked) {
            document.getElementById('tournamentTable').style.display = 'none';
            document.getElementById('rankingTable').style.display = 'none';
            document.getElementById('matches').style.display = 'block';
        }

        rad[i].addEventListener('change', function () {
            if (local_i === 0) {
                document.getElementById('tournamentTable').style.display = 'table';
                document.getElementById('rankingTable').style.display = 'none';
                document.getElementById('matches').style.display = 'none';
            } else if (local_i === 2) {
                document.getElementById('tournamentTable').style.display = 'none';
                document.getElementById('rankingTable').style.display = 'table';
                document.getElementById('matches').style.display = 'none';
            } else if (local_i === 4) {
                document.getElementById('tournamentTable').style.display = 'none';
                document.getElementById('rankingTable').style.display = 'none';
                document.getElementById('matches').style.display = 'block';
            }
        });
    }
</script>

</html>
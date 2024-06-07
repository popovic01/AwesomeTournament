<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="matches">
    <div class="match-header centered-container">
        <select id="matchFilter">
            <option value="past">Past Matches</option>
            <option value="upcoming">Upcoming Matches</option>
            <option value="tba">Not Scheduled Yet</option>
            <option value="all" selected>All Matches</option>
        </select>
    </div>
    <div class="table-container padding-top">
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
                                                <img src="data:image/jpeg;base64,${team1Logo}" class="logo-img"
                                                    alt="team logo">
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
                                                <img src="data:image/jpeg;base64,${team2Logo}" class="logo-img"
                                                    alt="team logo">
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
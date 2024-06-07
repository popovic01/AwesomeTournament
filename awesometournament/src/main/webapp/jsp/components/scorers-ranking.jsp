<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table class="table table-hover" id="rankingTable">
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
                <td>
                    <c:out value="${status.index + 1}" />
                </td>
                <td>
                    <a href="/players/${entry.getPlayerID()}" class="link">
                        <c:out value="${entry.getPlayerName()} ${entry.getPlayerSurname()}" />
                    </a>
                </td>
                <td  class="team-column">
                    <c:choose>
                        <c:when test="${not empty entry.getLogo()}">
                            <div>
                                <img src="data:image/jpeg;base64,${entry.getLogo()}" class="logo" alt="team logo">
                                <a href="/team/${entry.getTeamID()}" class="link">
                                    <c:out value="${entry.getTeamName()}" />
                                </a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div>
                                <img src="<c:url value='/media/logo_placeholder.png' />" class="logo" alt="default logo">
                                <a href="/team/${entry.getTeamID()}" class="link">
                                    <c:out value="${entry.getTeamName()}" />
                                </a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:out value="${entry.getGoals()}" />
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
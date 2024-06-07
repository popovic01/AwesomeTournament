<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table class="table table-hover" id="tournamentTable">
    <thead>
        <tr>
            <th>#</th>
            <th>Team</th>
            <th>Points</th>
            <th>Played</th>
            <!-- TODO ADD MORE STATS-->
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${ranking}" var="entry" varStatus="status">
            <tr>
                <td>
                    <c:out value="${status.index + 1}" />
                </td>
                <td class="team-column">
                    <c:choose>
                        <c:when test="${not empty entry.getLogo()}">
                            <img src="data:image/jpeg;base64,${entry.getLogo()}" class="logo" alt="team logo" id="logo">
                        </c:when>
                        <c:otherwise>
                            <img src="<c:url value='/media/logo_placeholder.png'/>" class="logo" alt="default logo"
                                id="logo">
                        </c:otherwise>
                    </c:choose>
                    <a href="/team/${entry.getTeamId()}" class="link">
                        <c:out value="${entry.getTeamName()}" />
                    </a>
                </td>
                <td>
                    <c:out value="${entry.getPoints()}" />
                </td>
                <td>
                    <c:out value="${entry.getMatchesPlayed()}" />
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
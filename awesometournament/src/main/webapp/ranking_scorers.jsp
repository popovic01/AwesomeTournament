<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Top scorers</title>
</head>
<body>
<h1>Top scorers of tournament
    <c:out value="${tournament_id}" />
</h1>
<table border="1">
    <thead>
    <tr>
        <th>Player Name</th>
        <th>Player Surname</th>
        <th>Goals</th>
        <th>Matches Played</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${ranking}" var="entry">
        <tr>
            <td><c:out value="${entry.getPlayerName()}"/></td>
            <td><c:out value="${entry.getPlayerSurname()}"/></td>
            <td><c:out value="${entry.getGoals()}"/></td>
            <td><c:out value="${entry.getMatchesPlayed()}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>

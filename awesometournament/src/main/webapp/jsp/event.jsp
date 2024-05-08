<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Event Information</title>
</head>
<body>
<h1>Event Information</h1>
<ul>
    <li><b>ID:</b> <c:out value="${event.getId()}"/></li>
    <li><b>Match ID:</b> <c:out value="${event.getMatchId()}"/></li>
    <li><b>Player ID:</b> <c:out value="${event.getPlayerId()}"/></li>
    <li><b>Type:</b> <c:out value="${event.getType()}"/></li>
    <li><b>Time:</b> <c:out value="${event.getTime()}"/></li>
</ul>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Player Information</title>
</head>
<body>
    <h1>Player Information</h1>
    <ul>
        <li><b>ID:</b> <c:out value="${player.getId()}"/></li>
        <li><b>Name:</b> <c:out value="${player.getName()}"/></li>
        <li><b>Surname:</b> <c:out value="${player.getSurname()}"/></li>
        <li><b>Team ID:</b> <c:out value="${player.getTeamId()}"/></li>
        <li><b>Position:</b> <c:out value="${player.getPosition()}"/></li>
        <li><b>Medical Certificate:</b> <c:out value="${player.getMedicalCertificate()}"/></li>
        <li><b>Date of Birth:</b> <c:out value="${player.getDateOfBirth()}"/></li>
    </ul>
</body>
</html>

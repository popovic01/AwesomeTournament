<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="it.unipd.dei.dam.awesometournament.resources.entities.Player" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Player Information</title>
</head>
<body>
    <h1>Player Information</h1>
    
    <%-- Retrieve player information from request attribute --%>
    <% 
        Player player = (Player) request.getAttribute("player");
    %>
    <ul>
        <li><b>ID:</b> <c:out value="${player.id}"/></li>
        <li><b>Name:</b> <c:out value="${player.name}"/></li>
        <li><b>Surname:</b> <c:out value="${player.surname}"/></li>
        <li><b>Team ID:</b> <c:out value="${player.teamId}"/></li>
        <li><b>Position:</b> <c:out value="${player.position}"/></li>
        <li><b>Medical Certificate:</b> <c:out value="${player.medicalCertificate}"/></li>
        <li><b>Date of Birth:</b> <c:out value="${player.dateOfBirth}"/></li>
    </ul>
</body>
</html>

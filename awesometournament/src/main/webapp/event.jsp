<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.unipd.dei.dam.awesometournament.resources.entities.Event" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Player Information</title>
</head>
<body>
<h1>Event Details</h1>
<%
    Event event = (Event) request.getAttribute("event");
    if (event != null) {
%>
<p>Type: <%= event.getType() %></p>
<p>Time: <%= event.getTime() %></p>
<!-- Altri dettagli dell'evento se necessario -->
<%
} else {
%>
<p>Event not find</p>
<%
    }
%>
</body>
</html>

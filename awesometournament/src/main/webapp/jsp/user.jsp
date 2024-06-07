
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <title>AwesomeTournaments - User</title>
    <c:import url="/jsp/commons/head.jsp" />
    <!-- <link rel="stylesheet" type="text/css" href="/css/user.css" /> -->
</head>

<body>
    <c:import url="/jsp/commons/header.jsp" />
    <div class="container">
        <div class="row">
            Private area of user: <c:out value="${user.getEmail()}"></c:out>
        </div>
        <div class="row">
            <p>Creator of:</p>
            <ul>
                <c:forEach var="tournament" items="${tournaments}">
                    <a href="/tournament/${tournament.getId()}">
                        <li>${tournament.getName()}</li>
                    </a>
                </c:forEach>
            </ul>
        </div>
        <div class="row">
            <p>Creator of:</p>
            <ul>
                <c:forEach var="team" items="${teams}">
                    <a href="/team/${team.getId()}">
                        <li>${team.getName()}</li>
                    </a>
                </c:forEach>
            </ul>
        </div>
    </div>
    <c:import url="/jsp/commons/footer.jsp" />
</body>
</html>
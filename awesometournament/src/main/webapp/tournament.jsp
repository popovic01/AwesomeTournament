<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AwesomeTournaments - Tournament</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
            color: #333;
        }
        ul {
            list-style-type: none;
            padding: 0;
        }
        li {
            margin-bottom: 10px;
            padding: 10px;
            background-color: #f9f9f9;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        li:hover {
            background-color: #e9e9e9;
        }
        .tournament-name {
            font-size: 1.2em;
            font-weight: bold;
            color: #333;
        }
        .tournament-details {
            color: #666;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>AwesomeTournaments</h1>
        <div>
            <div>
                <c:out value="${tournament}"/>
            </div>
        </div>
        <c:if test="${owner}">
            <div style="color: red;">
                You are the admin of this tournament
            </div>
        </c:if>
        <div>
            Matches:
            <ul>
                <c:forEach var="match" items="${matches}">
                    <li>
                        <c:out value="${match}"/>
                        <a href="<c:url value="/match/${match.getId()}"/>">more...</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div>
            Teams:
            <ul>
                <c:forEach var="team" items="${teams}">
                    <li>
                        <c:out value="${team}"/>
                        <a href="<c:url value="/team/${team.getId()}"/>">more...</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <form method="POST" action="upload" enctype="multipart/form-data" >
        <input type="hidden" name="tournamentId" value="${tournament.getId()}">
        File:
        <input type="file" name="logo" id="logo" /> <br/>
        <input type="submit" value="Upload" name="upload" id="upload" /> <br/>
    </form>
</body>
</html>

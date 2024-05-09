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
                <c:out value="${match}" />
            </div>
        </div>
        <c:if test="${owner}">
            <div style="color: red;">
                You are the admin of the tournament this match belongs to
            </div>
            <form id="updateForm">
                <label for="team1Score">Team 1 Score:</label><br>
                <input type="text" id="team1Score" name="team1Score"><br>

                <label for="team2Score">Team 2 Score:</label><br>
                <input type="text" id="team2Score" name="team2Score"><br>

                <label for="result">Result:</label><br>
                <input type="text" id="result" name="result"><br>

                <label for="referee">Referee:</label><br>
                <input type="text" id="referee" name="referee"><br>

                <label for="matchDate">Match Date:</label><br>
                <input type="text" id="matchDate" name="matchDate"><br>

                <label for="isFinished">Is Finished:</label><br>
                <input type="text" id="isFinished" name="isFinished"><br>

                <input type="submit" value="Update">
            </form>
            <script>
            document.getElementById("updateForm").addEventListener("submit", function(event) {
                event.preventDefault();
                // Gather form data
                const formData = new FormData(this);
                
                // Convert FormData to JSON
                const jsonObject = {};
                formData.forEach(function(value, key) {
                    jsonObject[key] = value;
                });
                const jsonData = JSON.stringify(jsonObject);
                
                // Send AJAX request to update the backend
                const xhr = new XMLHttpRequest();
                xhr.open("PUT", "/api/matches/${matchId}", true);
                xhr.setRequestHeader("Content-Type", "application/json");
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        alert("Match updated successfully!");
                    }
                };
                xhr.send(jsonData);
            });
            </script>
        </c:if>
    </div>
</body>

</html>
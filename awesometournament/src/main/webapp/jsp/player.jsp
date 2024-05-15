<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Player Information</title>
</head>
<body>
    <h1>Player Information</h1>
    <ul id="playerInformation">
        <li><b>ID:</b> <c:out value="${player.getId()}"/></li>
        <li><b>Name:</b> <c:out value="${player.getName()}"/></li>
        <li><b>Surname:</b> <c:out value="${player.getSurname()}"/></li>
        <li><b>Team ID:</b> <c:out value="${player.getTeamId()}"/></li>
        <li><b>Position:</b> <c:out value="${player.getPosition()}"/></li>
        <li><b>Date of Birth:</b> <c:out value="${player.getDateOfBirth()}"/></li>
    </ul>
    <form id="updateForm" style="display: none;" enctype="multipart/form-data">
        <input type="hidden" id="id" value="${player.getId()}">
        <input type="hidden" id="teamId" value="${player.getTeamId()}">

        <label for="nameInput">Name:</label>
        <input type="text" id="nameInput" value="${player.getName()}"> </br>
        <label for="surnameInput">Surname:</label>
        <input type="text" id="surnameInput" value="${player.getSurname()}"> </br>
        <label for="positionInput">Position:</label>
        <input type="text" id="positionInput" value="${player.getPosition()}"> </br>
        <label for="dateOfBirthInput">Date of Birth:</label>
        <input type="text" id="dateOfBirthInput" value="${player.getDateOfBirth()}"> </br>
        <input type="submit" value="Update">
    </form>
    <c:if test="${authorized}">
        <button id="modify_btn" onclick="modify()">Modify</button>

        <h1>Upload Medical Certificate</h1>
        <form method="POST" action="upload" enctype="multipart/form-data" >
            <input type="hidden" name="playerId" value="${player.getId()}">
            File:
            <input type="file" name="medicalCertificate" id="medicalCertificate" /> <br/>
            <input type="submit" value="Upload" name="upload" id="upload" /> <br/>
        </form>
    </c:if>
    <script>
        function modify() {
            // Hide original data
            document.getElementById("playerInformation").style.display = "none";
            document.getElementById("modify_btn").style.display = "none";

            // Show update form
            document.getElementById("updateForm").style.display = "block";
        }
        document.getElementById('updateForm').addEventListener('submit', function(event) {
            event.preventDefault(); // Prevent form submission
            var formData = {
                id: document.getElementById('id').value,
                name: document.getElementById('nameInput').value,
                surname: document.getElementById('surnameInput').value,
                team_id: document.getElementById('teamId').value,
                position: document.getElementById('positionInput').value.toLowerCase(),
                date_of_birth: document.getElementById('dateOfBirthInput').value
            };
            // Make AJAX request to update player
            fetch('http://localhost:8080/api/players/${player.getId()}', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            })
            .then(response => {
                if (response.ok) {
                    // Redirect to another page, replacing the current page in the history
                    window.location.replace("http://localhost:8080/players/${player.getId()}");
                } else {
                    throw new Error('Failed to update player');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Failed to update player. Please try again later.');
            });
        });
    </script>
</body>
</html>

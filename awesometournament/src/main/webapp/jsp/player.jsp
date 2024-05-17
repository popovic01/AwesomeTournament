<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Player Information</title>
    <style>
        /* Custom CSS for Player Information Page */

        /* Body styling */
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 0;
        }

        /* Header styling */
        h1.title {
            text-align: center;
            color: #333;
            margin-top: 20px;
        }

        /* Player information container styling */
        .player-info-container {
            max-width: 600px;
            margin: 20px auto;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            padding: 20px;
            position: relative;
        }

        /* Player information item styling */
        .player-info-container div {
            margin-bottom: 10px;
        }

        .player-info-container div b {
            font-weight: bold;
        }

        /* Update button styling */
        .show-update-form-button {
            padding: 10px 20px;
            background-color: #3F51B5;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .show-update-form-button:hover {
            background-color: #2c3e50;
        }

        /* Form styling */
        form {
            max-width: 600px;
            margin: 20px auto;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }

        form label {
            display: block;
            margin-bottom: 10px;
        }

        form input[type="text"], form input[type="submit"] {
            width: calc(100% - 22px);
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        form input[type="submit"] {
            width: 100%;
            background-color: #3F51B5;
            color: #fff;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        form input[type="submit"]:hover {
            background-color: #2c3e50;
        }

        /* File upload styling */
        .custom-file-input-container {
            position: relative;
            overflow: hidden;
            margin-top: 10px;
        }

        .custom-file-input {
            position: absolute;
            top: 0;
            right: 0;
            margin: 0;
            padding: 10px; /* Adjust as needed */
            font-size: inherit; /* Inherit font size */
            cursor: pointer;
            opacity: 0;
        }

        .custom-file-input-label {
            display: inline-block;
            padding: 10px 20px;
            background-color: #3F51B5;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .custom-file-input-label:hover {
            background-color: #2c3e50;
        }

        /* Display selected file name */
        .selected-file-name {
            margin-top: 5px;
            color: #555;
        }
    </style>
</head>
<body>
<!-- header -->
<c:import url="/jsp/common/header.jsp"/>

    <h1 class="title">Player Information</h1>
    <div id="playerInfo" class="player-info-container">
        <div><b>ID:</b> <c:out value="${player.getId()}"/></div>
        <div><b>Name:</b> <c:out value="${player.getName()}"/></div>
        <div><b>Surname:</b> <c:out value="${player.getSurname()}"/></div>
        <div><b>Team ID:</b> <c:out value="${player.getTeamId()}"/></div>
        <div><b>Position:</b> <c:out value="${player.getPosition()}"/></div>
        <div><b>Date of Birth:</b> <c:out value="${player.getDateOfBirth()}"/></div>
        <c:if test="${authorized}">
            <button id="showUpdateFormButton" class="show-update-form-button" onclick="showUpdateForm()">Update Player</button>
        </c:if>
    </div>

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
        <input type="submit" name="confirm" value="Confirm">
        <input type="submit" name="cancel" value="Cancel">
    </form>
    <c:if test="${authorized}">
        <h1 class="medical-certificate-header">Upload Medical Certificate</h1>
        <div class="medical-certificate-container">
            <form class="medical-certificate-form" method="POST" action="upload" enctype="multipart/form-data" >
                <input type="hidden" name="playerId" value="${player.getId()}">
                <div class="custom-file-input-container">
                    <label for="medicalCertificate" class="custom-file-input-label">Choose File</label>
                    <input type="file" name="medicalCertificate" id="medicalCertificate" class="custom-file-input" onchange="displayFileName(this)" required/> 
                </div>
                <div class="selected-file-name" id="selectedFileName"></div>
                <br/>
                <input type="submit" value="Upload" name="upload" class="medical-certificate-submit-button" /> <br/>
            </form>
        </div>
    </c:if>
    <script>
        function showUpdateForm() {
            // Hide original data
            document.getElementById("playerInfo").style.display = "none";
            document.getElementById("showUpdateFormButton").style.display = "none";

            // Show update form
            document.getElementById("updateForm").style.display = "block";
        }

        function displayFileName(input) {
            var fileName = input.files[0].name;
            document.getElementById('selectedFileName').innerText = 'Selected File: ' + fileName;
        }

        document.getElementById('updateForm').addEventListener('submit', function(event) {
            event.preventDefault(); // Prevent form submission
            var submitButtonName = event.submitter.name;

            if (submitButtonName === "confirm") {
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
            } else if (submitButtonName === "cancel") {
                // Redirect to another page, replacing the current page in the history
                window.location.replace("http://localhost:8080/players/${player.getId()}");
            }
        });
    </script>
<!-- footer -->
<c:import url="/jsp/common/footer.jsp" />
</body>
</html>

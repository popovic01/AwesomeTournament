<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Player Information</title>
    <c:import url="/jsp/common/head.jsp" />
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
        /* Add separator styling */
        .separator {
            width: 100%;
            margin: 20px 0;
            border: none;
            border-top: 1px solid #ccc;
        }
        /* Modal styling */
        .modal {
            display: none; /* Hidden by default */
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 100%; /* Full height */
            overflow: auto; /* Enable scroll if needed */
            background-color: rgba(0, 0, 0, 0.6); /* Black w/ opacity */
        }

        .modal-content {
            background-color: #fefefe;
            margin: 15% auto; /* 15% from the top and centered */
            padding: 20px;
            border: 1px solid #888;
            border-radius: 8px;
            width: 50%; /* Smaller width */
            max-width: 400px; /* Ensure it doesn't get too large */
            text-align: center; /* Center the text */
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* Add some shadow */
            position: relative; /* Relative positioning for the close button */
        }

        .close {
            position: absolute;
            top: 10px;
            right: 10px;
            color: #aaa;
            font-size: 28px;
            font-weight: bold;
        }

        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }

        #errorMessage {
            margin-top: 40px; /* Add margin to separate from the close button */
            font-size: 18px; /* Increase font size for better readability */
            color: #333; /* Darker color for contrast */
        }    </style>
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
        <h1 class="medical-certificate-header">Medical Certificate</h1>
        <div class="medical-certificate-container">
            <!-- Medical certificate upload form -->
            <form class="medical-certificate-form" method="POST" action="upload" enctype="multipart/form-data" onsubmit="return validateFile()">
                <!-- File input -->
                <div class="custom-file-input-container">
                    <input type="hidden" name="playerId" value="${player.getId()}">
                    <label for="medicalCertificate" class="custom-file-input-label">Choose File</label>
                    <input type="file" name="medicalCertificate" id="medicalCertificate" class="custom-file-input" onchange="displayFileName(this)" required/>
                </div>
                <div class="selected-file-name" id="selectedFileName"></div>
                <br/>
                <input type="submit" value="Upload" name="upload" class="medical-certificate-submit-button" />
                <br/>

                <!-- Separator -->
                <hr class="separator"/>

                <!-- Download button -->
                <button id="download" class="show-update-form-button" style="margin-top: 20px;">Download</button>
                <!-- Error Modal -->
                <div id="errorModal" class="modal">
                    <div class="modal-content">
                        <span class="close">&times;</span>
                        <p id="errorMessage"></p>
                    </div>
                </div>
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

        function validateFile() {
            const fileInput = document.getElementById('medicalCertificate');
            const fileName = fileInput.value.split('\\').pop();
            const fileExtension = fileName.split('.').pop();
            if (fileExtension == 'pdf') {
                return true;
            }
            showErrorMessage('Upload only pdf files');
            return false;
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

        document.getElementById('download').onclick = function(event) {
            event.preventDefault();
            fetch("http://localhost:8080/download_medical_certificate/${player.getId()}", { method: 'get', mode: 'no-cors', referrerPolicy: 'no-referrer' })
            .then(res => res.blob())
            .then(res => {
                if (!res.size) {
                    throw new Error('Medical certificate not found');
                }
                const aElement = document.createElement('a');
                aElement.setAttribute('download', "medical_certificate.pdf");
                const href = URL.createObjectURL(res);
                aElement.href = href;
                aElement.setAttribute('target', '_blank');
                aElement.click();
                URL.revokeObjectURL(href);
            })
            .catch(error => {
                console.error('Error:', error);
                showErrorMessage('Medical certificate not found.');
            });
        };
        // Function to show the error modal with a custom message
        function showErrorMessage(message) {
            var modal = document.getElementById("errorModal");
            var span = document.getElementsByClassName("close")[0];
            var errorMessage = document.getElementById("errorMessage");

            // Set the error message text
            errorMessage.textContent = message;

            // Display the modal
            modal.style.display = "block";

            // When the user clicks on <span> (x), close the modal
            span.onclick = function() {
                modal.style.display = "none";
            }

            // When the user clicks anywhere outside of the modal, close it
            window.onclick = function(event) {
                if (event.target == modal) {
                    modal.style.display = "none";
                }
            }
        }
    </script>
<!-- footer -->
<c:import url="/jsp/common/footer.jsp" />
</body>
</html>

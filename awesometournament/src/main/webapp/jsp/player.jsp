<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Player Information</title>
    <c:import url="/jsp/commons/head.jsp" />
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
        h1 {
            text-align: center;
            color: #333;
            margin-top: 20px;
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

        #message {
            margin-top: 40px; /* Add margin to separate from the close button */
            font-size: 18px; /* Increase font size for better readability */
            color: #333; /* Darker color for contrast */
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <div class="card shadow-lg">
            <div class="card-header text-white bg-primary">
                <h3 class="card-title mb-0">Player Profile</h3>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-4 text-center">
                        <h5 class="card-subtitle mb-2 text-muted">${player.getName()} ${player.getSurname()}</h5>
                    </div>
                    <!-- <div class="col-md-4 text-center"> -->
                        <!-- <img src="${user.profilePicture}" class="img-fluid rounded-circle mb-3" alt="Profile Picture"> -->
                        <!-- <h5 class="card-subtitle mb-2 text-muted">${user.name} ${user.surname}</h5> -->
                    <!-- </div> -->
                    <div class="col-md-8">
                        <ul id="playerInfo" class="list-group list-group-flush">
                            <li class="list-group-item">
                                <strong>Name:</strong> ${player.getName()}
                            </li>
                            <li class="list-group-item">
                                <strong>Surname:</strong> ${player.getSurname()}
                            </li>
                            <li class="list-group-item">
                                <strong>Team:</strong> ${player.getTeamId()}
                            </li>
                            <li class="list-group-item">
                                <strong>Position:</strong> ${player.getPosition()}
                            </li>
                            <li class="list-group-item">
                                <strong>Date of birth:</strong> ${player.getDateOfBirth()}
                            </li>
                            <li class="list-group-item">
                                <button class="btn btn-outline-secondary" type="submit" id="showUpdateFormButton" onclick="showUpdateForm()">Update Player</button>
                            </li>
                        </ul>
                        <form id="updateForm" style="display: none;">
                            <div class="form-group">
                                <label for="formGroupExampleInput">Name:</label>
                                <input type="text" class="form-control" id="nameInput" placeholder="${player.getName()}">
                            </div>
                            <div class="form-group">
                                <label for="formGroupExampleInput2">Surname:</label>
                                <input type="text" class="form-control" id="surnameInput" placeholder="${player.getSurname()}">
                            </div>
                            <div class="form-group">
                                <label class="my-1 mr-2" for="inlineFormCustomSelectPref">Position</label>
                                <select class="custom-select my-1 mr-sm-2" id="positionInput">
                                    <option selected>Choose...</option>
                                    <option value="goalkeeper">Goalkeeper</option>
                                    <option value="defender">Defender</option>
                                    <option value="midfielder">Midfielder</option>
                                    <option value="striker">Striker</option>
                                  </select>
                            </div>
                            <div class="form-group">
                                <label for="formGroupExampleInput3">Date of birth:</label>
                                <input type="text" class="form-control" id="dateInput" placeholder="${player.getDateOfBirth()}">
                            </div>
                            <button type="submit" name="confirm" class="btn btn-primary">Confirm</button>
                            <button type="submit" name="cancel" class="btn btn-primary">Cancel</button>
                        </form>
                    </div>
                </div>
            </div>
            <c:if test="${authorized}">
                <div class="card-footer text-muted text-right">
                    <c:choose>
                        <c:when test="${not empty player.getMedicalCertificate()}">
                            Medical Certitificate: OK
                        </c:when>
                        <c:otherwise>
                            Medical Certificate: Missing
                        </c:otherwise>
                    </c:choose>
                    <form class="form-row" method="POST" action="/uploadMedicalCertificate" enctype="multipart/form-data" onsubmit="return validateFile()">
                        <input type="hidden" name="playerId" value="${player.getId()}">
                        <div class="input-group">
                            <input type="file" class="form-control" id="inputGroupFile04" name="medicalCertificate" aria-describedby="inputGroupFileAddon04" aria-label="Upload" required>
                            <button class="btn btn-outline-secondary" type="submit" id="inputGroupFileAddon04">Upload</button>
                            <button class="btn btn-outline-secondary" type="button" id="download">Download</button>
                        </div>
                    </form>
                </div>
            </c:if>
        </div>
    </div>
    <!-- Error Modal -->
    <div id="modal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <p id="message"></p>
        </div>
    </div>
    <script>
        function showUpdateForm() {
            // Hide original data
            document.getElementById("playerInfo").style.display = "none";
            document.getElementById("showUpdateFormButton").style.display = "none";

            // Show update form
            document.getElementById("updateForm").style.display = "block";
        }

        function validateFile() {
            const fileInput = document.getElementById('inputGroupFile04');
            const filePath = fileInput.value;
            const allowedExtensions = /(\.pdf)$/i;
            if (!allowedExtensions.exec(filePath)) {
                showMessage('Upload only pdf files');
                return false;
            }

            return true;
        }

        document.getElementById('updateForm').addEventListener('submit', function(event) {
            event.preventDefault(); // Prevent form submission
            var submitButtonName = event.submitter.name;

            if (submitButtonName === "confirm") {
                var formData = {
                    id: '${player.getId()}',
                    name: document.getElementById('nameInput').value,
                    surname: document.getElementById('surnameInput').value,
                    team_id: '${player.getTeamId()}',
                    position: document.getElementById('positionInput').value,
                    date_of_birth: document.getElementById('dateInput').value
                };
                // Make AJAX request to update player
                fetch('/api/players/${player.getId()}', {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(formData)
                })
                .then(response => {
                    if (response.ok) {
                        // Redirect to another page, replacing the current page in the history
                        window.location.replace("/players/${player.getId()}");
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
                window.location.replace("/players/${player.getId()}");
            }
        });

        document.getElementById('download').onclick = function(event) {
            event.preventDefault();
            fetch("/download_medical_certificate/${player.getId()}", { method: 'get', mode: 'no-cors', referrerPolicy: 'no-referrer' })
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
                showMessage('Medical certificate not found.');
            });
        };

        // Function to show the modal with a custom message
        function showMessage(message) {
            var modal = document.getElementById("modal");
            var span = document.getElementsByClassName("close")[0];
            var modalMessage = document.getElementById("message");

            // Set the message text
            modalMessage.textContent = message;

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
    <c:if test="${uploaded}">
        <script>
            showMessage("Medical certificate uploaded correctly");
        </script>
    </c:if>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>Player Information</title>

    <c:import url="/jsp/commons/head.jsp" />
    <link rel="stylesheet" type="text/css" href="../css/player.css" />
</head>
<body>
    <c:import url="/jsp/commons/header.jsp" />
    <div class="container mt-5">
        <div class="card shadow-lg">
            <div class="card-header text-white bg-primary">
                <h3 class="card-title mb-0">Player Profile</h3>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-4 text-center">
                        <c:choose>
                            <c:when test="${not empty logo}">
                                <div>
                                    <img src="data:image/jpeg;base64,${logo}" class="img-fluid" alt="Profile Picture" width="100px" height="auto">
                                    <h5 class="card-subtitle mb-2 text-muted">${player.getName()} ${player.getSurname()}</h5>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div>
                                    <img src="<c:url value='/media/logo_placeholder.png' />" class="img-fluid" alt="Profile Picture">
                                    <h5 class="card-subtitle mb-2 text-muted">${player.getName()} ${player.getSurname()}</h5>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-md-8">
                        <ul id="playerInfo" class="list-group list-group-flush">
                            <li class="list-group-item">
                                <strong>Name:</strong> ${player.getName()}
                            </li>
                            <li class="list-group-item">
                                <strong>Surname:</strong> ${player.getSurname()}
                            </li>
                            <li class="list-group-item">
                                <strong>Team:</strong> ${team}
                            </li>
                            <li class="list-group-item" style="text-transform:capitalize">
                                <strong>Position:</strong> ${fn:toLowerCase(player.getPosition())}
                            </li>
                            <li class="list-group-item">
                                <strong>Date of birth:</strong> ${date}
                            </li>
                            <c:if test="${authorized}">
                                <li class="list-group-item">
                                    <button class="btn btn-outline-secondary" type="submit" id="showUpdateFormButton" onclick="showUpdateForm()">Update Player</button>
                                </li>
                            </c:if>
                        </ul>
                        <form id="updateForm">
                            <div class="form-group">
                                <label for="nameInput">Name:</label>
                                <input type="text" class="form-control" id="nameInput" value="${player.getName()}" required>
                            </div>
                            <div class="form-group">
                                <label for="surnameInput">Surname:</label>
                                <input type="text" class="form-control" id="surnameInput" value="${player.getSurname()}" required>
                            </div>
                            <div class="form-group">
                                <label class="my-1 mr-2" for="positionInput">Position</label>
                                <select class="form-select" id="positionInput" required>
                                    <option value="goalkeeper">Goalkeeper</option>
                                    <option value="defender">Defender</option>
                                    <option value="midfielder">Midfielder</option>
                                    <option value="striker">Striker</option>
                                  </select>
                            </div>
                            <br>
                            <div class="form-group">
                                <label for="dateInput">Date of birth:</label>
                                <input type="date" class="form-control" id="dateInput" value="${player.getDateOfBirth()}" required>
                            </div>
                            <br>
                            <button type="submit" name="confirm" class="btn btn-primary">Confirm</button>
                            <button type="button" name="cancel" class="btn btn-primary" onclick="cancelUpdate(${player.getId()})">Cancel</button>
                        </form>
                    </div>
                </div>
            </div>
            <c:if test="${authorized}">
                <div class="card-footer text-muted text-right">
                    <c:choose>
                        <c:when test="${not empty player.getMedicalCertificate()}">
                            Medical Certificate: Present
                        </c:when>
                        <c:otherwise>
                            Medical Certificate: Missing
                        </c:otherwise>
                    </c:choose>
                    <form class="form-row" id="uploadMedicalCertificateForm" method="POST" action="/uploadMedicalCertificate" enctype="multipart/form-data" onsubmit="return validateFile()">
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
    <c:import url="/jsp/commons/footer.jsp" />
    <script>
        var playerPosition = '${player.getPosition()}';
        var playerId = '${player.getId()}';
        var playerTeamId = '${player.getTeamId()}';
    </script>
    <script type="text/javascript" src="/js/player.js"></script>
</body>
</html>

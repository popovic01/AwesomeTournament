<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <title>AwesomeTournaments - Tournament</title>

        <c:import url="/jsp/commons/head.jsp"/>

        <style>
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
            .main-wrapper {
                max-width: 800px;
                margin: 20px auto;
                padding: 20px;
                background-color: #fff;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            #btnEdit {
                margin-right: 1rem;
            }

            #btnEdit, #btnDelete {
                height: 80%;
                width: 40%;
            }

            .title-logo-actions-wrapper {
                display: flex;
                flex-direction: row;
                justify-content: space-between;
                align-items: center;
                height: 4rem;
                margin-bottom: 1rem;
                padding: 0 1rem;
            }
        </style>
    </head>

    <body>
        <!-- header -->
        <c:import url="/jsp/commons/header.jsp"/>

        <div class="main-wrapper" id="teamInformation">
            <div class="title-logo-actions-wrapper">
                <div class="title-logo-wrapper">
                    <p class="fs-1 text-dark">
                        <c:out value="${team.name}"/>
                    </p>

                    <c:if test="${not empty team.base64Logo}">
                        <img src="data:image/jpg;base64,${team.base64Logo}"/>
                    </c:if>
                </div>

                <c:if test="${tournamentOwner || teamOwner}">
                    <div class="d-flex justify-content-end">
                        <button id="btnEdit" class="btn btn-secondary"><i class="fa fa-pencil-square-o"></i></button>
                        <button id="btnDelete" class="btn btn-secondary"><i class="fa fa-trash-o"></i></button>
                    </div>
                </c:if>
            </div>

            <c:if test="${!empty players}">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col"></th>
                        <th scope="col">Full Name</th>
                        <th scope="col">Date of Birth</th>
                        <th scope="col">Position</th>
                        <th scope="col">See Details</th>
                        <c:if test="${tournamentOwner || teamOwner}">
                            <th>
                                Action
                            </th>
                        </c:if>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${players}" var="player" varStatus="i">
                        <tr>
                            <th scope="row">${i.count}</th>
                            <td>
                                <c:out value="${player.name}"/>
                                <c:out value="${player.surname}"/>
                            </td>
                            <td>
                                <c:out value="${player.dateOfBirth}"/>
                            </td>
                            <td style="text-transform:capitalize">
                                <c:out value="${fn:toLowerCase(player.getPosition())}" />
                            </td>
                            <td>
                                <a href="<c:url value="/players/${player.id}"/>">
                                    Details
                                </a>
                            </td>
                            <c:if test="${tournamentOwner || teamOwner}">
                                <td>
                                    <i class="fa fa-trash-o" onclick="deletePlayer(${player.getId()})"></i>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <c:if test="${empty players}">
                <p class="text-secondary text-center">No players added in this team</p>
            </c:if>
            <c:if test="${teamOwner || tournamentOwner}">
                <button class="btn btn-outline-secondary" type="button" id="showUpdateFormButton" onclick="showAddPlayerForm()">Add Player</button>
            </c:if>

        </div>
        <div class="container mt-5" id="addPlayerCard" style="display: none;">
            <div class="card shadow-lg">
                <div class="card-header text-white bg-primary">
                    <h3 class="card-title mb-0">Player Profile</h3>
                </div>
                <div class="card-body">
                    <div class="row">
                        <!-- <div class="col-md-4 text-center"> -->
                            <!-- <img src="${user.profilePicture}" class="img-fluid rounded-circle mb-3" alt="Profile Picture"> -->
                            <!-- <h5 class="card-subtitle mb-2 text-muted">${user.name} ${user.surname}</h5> -->
                        <!-- </div> -->
                        <form id="addPlayerForm">
                            <div class="form-group">
                                <label for="nameInput">Name:</label>
                                <input type="text" class="form-control" id="nameInput" placeholder="Name" required>
                            </div>
                            <div class="form-group">
                                <label for="surnameInput">Surname:</label>
                                <input type="text" class="form-control" id="surnameInput" placeholder="Surname" required>
                            </div>
                            <div class="form-group">
                                <label class="my-1 mr-2" for="positionInput">Position</label>
                                <select class="form-select" id="positionInput" required>
                                    <option label="Choose" value="" selected hidden></option>
                                    <option value="goalkeeper">Goalkeeper</option>
                                    <option value="defender">Defender</option>
                                    <option value="midfielder">Midfielder</option>
                                    <option value="striker">Striker</option>
                                  </select>
                            </div>
                            <br>
                            <div class="form-group">
                                <label for="dateInput">Date of birth:</label>
                                <input type="date" class="form-control" id="dateInput" placeholder="Date of birth" required>
                            </div>
                            <br>
                            <button type="submit" name="confirm" class="btn btn-primary">Confirm</button>
                            <button type="button" name="cancel" class="btn btn-primary" onclick="cancelAdd(${team.getId()})">Cancel</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- footer -->
        <c:import url="/jsp/commons/footer.jsp"/>
    </body>

</html>

<script>
    function showAddPlayerForm() {
        document.getElementById("teamInformation").style.display = "none";
        document.getElementById("addPlayerCard").style.display = "block";
    }

    function cancelAdd(teamId) {
      // Redirect to another page, replacing the current page in the history
      window.location.replace('/team/{teamId}'.replace('{teamId}', teamId));
    }

    document.getElementById('addPlayerForm').addEventListener('submit', function(event) {
        event.preventDefault(); // Prevent form submission
        var formData = {
            name: document.getElementById('nameInput').value,
            surname: document.getElementById('surnameInput').value,
            team_id: '${team.getId()}',
            position: document.getElementById('positionInput').value,
            date_of_birth: document.getElementById('dateInput').value
        };
        // Make AJAX request to update player
        fetch('/api/teams/${team.getId()}/players', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
        .then(response => {
            if (response.ok) {
                // Redirect to another page, replacing the current page in the history
                window.location.replace("/team/${team.getId()}");
            } else {
                throw new Error('Failed to create player');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to create player. Please try again later.');
        });
    });

    document.addEventListener('DOMContentLoaded', function() {
        var btnEdit = document.getElementById('btnEdit');
        var btnDelete = document.getElementById('btnDelete');

        if (btnEdit) {
            btnEdit.addEventListener('click', function() {
                var url = `/tournament/${team.getTournamentId()}/team/${team.id}`;
                window.location.href = url;

                var xhr = new XMLHttpRequest();
                xhr.open('GET', url, true);
                xhr.onload = function() {
                    if (xhr.status >= 200 && xhr.status < 300) {
                        window.location.reload();
                    }
                };
            });
        } else {
            console.log('btnEdit element not found');
        }

        if (btnDelete) {
            btnDelete.addEventListener('click', function() {
                if (confirm('Are you sure you want to delete this team?')) {
                    var url = `/api/teams/${team.id}`;
                    var urlRedirect = `/tournament/${team.getTournamentId()}`;

                    fetch(url, {
                        method: 'DELETE'
                    })
                        .then(response => {
                            window.location.href = urlRedirect;
                        })
                        .catch(error => {
                            console.error('Error:', error);
                        });

                    var xhr = new XMLHttpRequest();
                    xhr.open('DELETE', url, true);
                    xhr.onload = function() {
                        if (xhr.status >= 200 && xhr.status < 300) {
                            window.location.reload();
                        }
                    };
                }
            });
        } else {
            console.log('btnDelete element not found');
        }
    })

    function deletePlayer(playerId) {
        // Make AJAX request to update player
        fetch('/api/players/{playerId}'.replace('{playerId}', playerId), {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
        })
        .then(response => {
            if (response.ok) {
                // Redirect to another page, replacing the current page in the history
                window.location.replace("/team/${team.getId()}");
            } else {
                throw new Error('Failed to delete player');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to delete player. Please try again later.');
        });
    };
</script>

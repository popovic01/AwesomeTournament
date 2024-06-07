<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Team Information</title>

        <c:import url="/jsp/commons/head.jsp"/>
        <link rel="stylesheet" type="text/css" href="../css/team.css" />
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
                        <img src="data:image/jpg;base64,${team.base64Logo}"width="45px" height="auto"/>
                    </c:if>
                </div>

                <c:if test="${(tournamentOwner || teamOwner) && !deadlinePassed}">
                    <div class="d-flex justify-content-end">
                        <button id="btnEdit" class="btn btn-primary"><i class="fa fa-pencil-square-o"></i></button>
                        <button id="btnDelete" class="btn btn-primary"><i class="fa fa-trash-o"></i></button>
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
            <c:if test="${(tournamentOwner || teamOwner) && !deadlinePassed}">
                <button class="btn btn-primary" type="button" id="showUpdateFormButton" onclick="showAddPlayerForm()">Add Player</button>
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

        <script>
            var team = '${team}';
            var teamId = '${team.getId()}';
            var playerId = '${player.getId()}';
            var teamTournamentId = '${team.getTournamentId()}';
        </script>
        <script type="text/javascript" src="/js/team.js"></script>
    </body>

</html>


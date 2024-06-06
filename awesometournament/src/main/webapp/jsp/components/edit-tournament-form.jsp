<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="editTournamentContainer" class="container mt-5" style="display: none;">
    <div class="card shadow-lg">
        <div class="card-header text-white bg-primary">
            <h3 class="card-title mb-0">Tournament details</h3>
        </div>
        <div class="card-body">
            <div class="row">
                <div class="col-md-4 text-center">
                    <c:choose>
                        <c:when test="${not empty tournament.getBase64Logo()}">
                            <img src="data:image/jpg;base64,${tournament.getBase64Logo()}"
                                class="img-fluid rounded-circle mb-3" />
                        </c:when>
                        <c:otherwise>
                            <img src="<c:url value='/media/AT_logo.png' />"
                                class="img-fluid rounded-circle mb-3" alt="default logo">
                        </c:otherwise>
                    </c:choose>
                    <h5 class="card-subtitle mb-2 text-muted"> <strong>${tournament.getName()}</strong> </h5>
                </div>
                <div class="col-md-8">
                    <form id="editTournamentForm">
                        <div>
                            <label for="tournamentName">Tournament Name:</label>
                            <input type="text" id="tournamentName" name="tournamentName" value="${tournament.getName()}"
                                required>
                        </div>
                        <div>
                            <label for="maxTeam">Maximum number of teams:</label>
                            <input type="number" id="maxTeam" name="maxTeam" min="2" max="20"
                                value="${tournament.getMaxTeams()}" required>
                        </div>
                        <div>
                            <label for="startingPlayers">Number of starting players per team:</label>
                            <input type="number" id="startingPlayers" name="startingPlayers" min="1" max="11"
                                value="${tournament.getStartingPlayers()}" required>
                        </div>
                        <div>
                            <label for="minPlayers">Minimum number of players for a team:</label>
                            <input type="number" id="minPlayers" name="minPlayers" min="1" max="11"
                                value="${tournament.getMinPlayers()}" required>
                        </div>
                        <div>
                            <label for="maxPlayers">Maximum number of players for a team:</label>
                            <input type="number" id="maxPlayers" name="maxPlayers" min="1" max="25"
                                value="${tournament.getMaxPlayers()}" required>
                        </div>
                        <div>
                            <label for="startDate">Start date of the tournament:</label>
                            <input type="date" id="startDate" name="startDate" value="${tournament.getOnlyStartDate()}"
                                required>
                        </div>
                        <div>
                            <label for="deadline">Deadline for team registration:</label>
                            <input type="date" id="deadline" name="deadline" value="${tournament.getOnlyDeadline()}"
                                required>
                        </div>
                        <button type="submit" name="confirm" class="btn btn-primary">Edit</button>
                        <button id="cancelEditTournament" type="button" name="cancel"
                            class="btn btn-primary">Cancel</button>
                        <button id="deleteTournament" type="button" name="delete" class="btn btn-danger">Delete
                            Tournament</button>
                    </form>
                </div>
            </div>
        </div>
        <div class="card-footer text-muted text-right">
            Update logo of the tournament
            <form class="form-row" method="POST" action="/uploadLogo" enctype="multipart/form-data"
                style="max-width: 400px; margin: auto;">
                <input type="hidden" name="tournamentId" value="${tournament.getId()}">
                <div class="input-group">
                    <input type="file" class="form-control" id="inputGroupFile04" name="logo" accept=".png, .jpg, .jpeg"
                        aria-describedby="inputGroupFileAddon04" aria-label="Upload" required>
                    <button class="btn btn-outline-secondary" type="submit" id="inputGroupFileAddon04">Upload</button>
                </div>
            </form>
        </div>
    </div>
</div>
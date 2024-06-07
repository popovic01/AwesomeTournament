<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container mt-5" id="info">
  <div class="card shadow-lg">
    <div class="card-body">
        <div class="row">
            <div class="col-md-8">
                <ul id="playerInfo" class="list-group list-group-flush">
                    <li class="list-group-item">
                        <strong>Tournament Name:</strong>
                        <c:out value="${tournament.getName()}" />
                    </li>
                    <li class="list-group-item">
                        <strong>Creator contact:</strong>
                        <c:out value="${creator.getEmail()}" />
                    </li>
                    <li class="list-group-item">
                        <strong>Maximum number of teams:</strong>
                        <c:out value="${tournament.getMaxTeams()}" />
                    </li>
                    <li class="list-group-item">
                        <strong>Maximum number of players per team:</strong>
                        <c:out value="${tournament.getMaxPlayers()}" />
                    </li>
                    <li class="list-group-item">
                        <strong>Minimum number of players per team:</strong>
                        <c:out value="${tournament.getMinPlayers()}" />
                    </li>
                    <li class="list-group-item">
                        <strong>Number of starting players:</strong>
                        <c:out value="${tournament.getStartingPlayers()}" />
                    </li>
                    <li class="list-group-item">
                        <strong>Maximum number of substitution per match:</strong>
                        <c:out value="${tournament.getMaxSubstitutions()}" />
                    </li>
                    <li class="list-group-item">
                        <strong>Registration closing date:</strong>
                        <c:out value="${tournament.getOnlyDeadline()}" />
                    </li>
                    <li class="list-group-item">
                        <strong>Start date:</strong>
                        <c:out value="${tournament.getOnlyStartDate()}" />
                    </li>
                    <li class="list-group-item">
                        <strong>Status:</strong>
                        <c:choose>
                            <c:when test="$tournament.getIsFinished()">
                                Finished 
                            </c:when>    
                            <c:otherwise>
                                In progress
                            </c:otherwise>
                        </c:choose>
                    </li>
                </ul>
            </div>
        </div>
    </div>
  </div>
</div>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <div id="resultModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <form id="updateResultForm">
                <h2>Update Match Result</h2>
                <label for="team1Score">Team 1 Score:</label>
                <input type="number" id="team1Score" name="team1Score" value="${match.team1Score}" min="0">
                <label for="team2Score">Team 2 Score:</label>
                <input type="number" id="team2Score" name="team2Score" value="${match.team2Score}" min="0">
                <input type="submit" value="Update">
            </form>
        </div>
    </div>
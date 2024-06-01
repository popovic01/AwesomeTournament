<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="eventModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <form id="newEvent">
            <label for="player_id">Player: </label>
            <select name="player_id" id="player_id">
                <c:forEach var="player" items="${players}">
                    <option value="${player.id}">
                        <c:out value="${player.getFullName()}"></c:out>
                    </option>
                </c:forEach>
            </select><br>

            <label for="type">Type of event: </label>
            <select name="type" id="type">
                <c:forEach var="type" items="${types}">
                    <option value="${type}">
                        <c:out value="${type}"></c:out>
                    </option>
                </c:forEach>
            </select><br>
            <label for="time">Time: </label>
            <input type="number" id="time" name="time" value = "1" min="1" max="90"><br>
            <input type="submit" value="Add">
        </form>
    </div>
</div>
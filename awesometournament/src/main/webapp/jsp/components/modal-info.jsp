<div id="infoModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <form id="updateInfoForm">
            <h2>Update Match Info</h2>
            <label for="referee">Referee:</label>
            <input type="text" id="referee" name="referee" value="${match.referee}">
            <label for="date">Date:</label>
            <input type="datetime-local" id="date" name="date" value="">
            <input type="submit" value="Update">
        </form>
    </div>
</div>
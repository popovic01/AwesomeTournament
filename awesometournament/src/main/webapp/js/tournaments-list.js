function updateTimers() {
    var tournaments = document.querySelectorAll('.tournament');

    tournaments.forEach(function (tournament) {
        var deadline = new Date(tournament.getAttribute('data-deadline'));
        var now = new Date();
        var timeLeft = deadline - now;

        if (timeLeft > 0) {
            var days = Math.floor(timeLeft / (1000 * 60 * 60 * 24));
            var hours = Math.floor((timeLeft % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
            var minutes = Math.floor((timeLeft % (1000 * 60 * 60)) / (1000 * 60));
            var seconds = Math.floor((timeLeft % (1000 * 60)) / 1000);

            tournament.querySelector('.time-left').innerHTML =
            "<strong>" + days + "d " + hours + "h " + minutes + "m " + seconds + "s</strong>";
        }
        else tournament.querySelector('.time-left').innerHTML = "<strong> Registration closed</strong>";
    });
}

function setStartingMinPlayersAndMaxPlayers() {
    // Get the elements
    const maxTeamInput = document.getElementById('maxTeam');
    const startingPlayersInput = document.getElementById('startingPlayers');
    const minPlayersInput = document.getElementById('minPlayers');
    const maxPlayersInput = document.getElementById('maxPlayers');

    // Deny keyboard input for all 3 input
    maxTeamInput.addEventListener('keydown', function (event) {
        event.preventDefault();
    });

    startingPlayersInput.addEventListener('keydown', function (event) {
        event.preventDefault();
    });

    minPlayersInput.addEventListener('keydown', function (event) {
        event.preventDefault();
    });

    maxPlayersInput.addEventListener('keydown', function (event) {
        event.preventDefault();
    });

    startingPlayersInput.addEventListener('click', function() {
    const startingPlayers = parseInt(startingPlayersInput.value, 10);

        if (startingPlayers > minPlayersInput.value && startingPlayers > maxPlayersInput.value) {
            minPlayersInput.value = startingPlayers;
            maxPlayersInput.value = startingPlayers;
        }
        else if(startingPlayers > minPlayersInput.value) minPlayersInput.value = startingPlayers;
        if(startingPlayers < 1) startingPlayersInput.value = 1;
    });

    minPlayersInput.addEventListener('click', function() {
        const minPlayers = parseInt(minPlayersInput.value, 10);
        const maxPlayers = parseInt(maxPlayersInput.value, 10);
        const startingPlayers = parseInt(startingPlayersInput.value, 10);

        if(minPlayers < startingPlayers) startingPlayersInput.value = minPlayersInput.value;

        if(minPlayers > maxPlayers) maxPlayersInput.value = minPlayersInput.value
    });

    maxPlayersInput.addEventListener('click', function () {
        const minPlayers = parseInt(minPlayersInput.value, 10);
        const maxPlayers = parseInt(maxPlayersInput.value, 10);
        const startingPlayers = parseInt(startingPlayersInput.value, 10);

        if(maxPlayers < minPlayers && maxPlayers < startingPlayers) {
        startingPlayersInput.value = maxPlayersInput.value;
        minPlayersInput.value = maxPlayersInput.value;
        }
        else if(maxPlayers < minPlayers) minPlayersInput.value = maxPlayersInput.value;
    })
}

function setStartAndDeadlineDate() {

    // Get tomorrow's date in the format YYYY-MM-DD
    const date = new Date();
    date.setDate(date.getDate() + 1);
    const tomorrow = date.toISOString().split('T')[0];

    // Get the startDate and deadline input element
    const startDateInput = document.getElementById('startDate');
    const deadlineInput = document.getElementById('deadline');

    // Set the min attribute to tomorrow's date and implements logic for startDate and deadline
    startDateInput.min = tomorrow;
    deadlineInput.min = tomorrow;

    startDateInput.addEventListener('change', function() {
        const startDate = startDateInput.value;
        const deadlineDate = deadlineInput.value;
        if(startDate) {
        deadlineInput.setAttribute('max', startDate);
        if (!deadlineDate || new Date(startDate) < new Date(deadlineDate)) deadlineInput.value = startDate;
    }
        else deadlineInput.removeAttribute('max');
    });
}

function filterTournaments() {
    var filter = document.getElementById("tournamentFilter").value;
    var tournaments = document.querySelectorAll(".tournament");

    tournaments.forEach(function(tournament) {
        var isFinished = tournament.dataset.isFinished === "true";

        if (filter === "active" && !isFinished) tournament.style.display = "block";
        else if (filter === "past" && isFinished) tournament.style.display = "block";
        else tournament.style.display = "none";
    });
}

function createTournament() {
    var btnCreateTournament = document.getElementById("btnCreateTournament");
    btnCreateTournament.addEventListener("click", function() {
        document.getElementById("addTournamentCard").style.display = "block";
        document.getElementById("container").style.display = "none";
    });
}

function hideForm() {
    var btnCancelCreateTournament = document.getElementById("cancelCreateTournament");
    btnCancelCreateTournament.addEventListener("click", function () {
        document.getElementById("addTournamentCard").style.display = "none";
        document.getElementById("container").style.display = "block";;
    });
}
    function manageForm() {
    document.getElementById('createTournamentForm').addEventListener('submit', function(event) {
        event.preventDefault(); // Prevent form submission

        var formData = {
            name: document.getElementById("tournamentName").value,
            token: "prova",
            creatorUserId: userId,
            maxTeams: parseInt(document.getElementById("maxTeam").value),
            maxPlayers: parseInt(document.getElementById("maxPlayers").value),
            minPlayers: parseInt(document.getElementById("minPlayers").value),
            startingPlayers: parseInt(document.getElementById("startingPlayers").value),
            maxSubstitutions: 5,
            deadline: document.getElementById("deadline").value,
            startDate: document.getElementById("startDate").value,
            creationDate: new Date().toISOString(),
            isFinished: false
        };

        // Make AJAX request to create the tournament
        fetch('/api/tournaments', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        }).then(async response => {
            if (response.ok) {
                // Redirect to another page, replacing the current page in the history
                window.location.replace("/tournaments");
                let body = await response.json();

                window.location.replace('/tournament/'+body.data.id);
            }
            else throw new Error('Failed to create the tournament');
        }).catch(error => {
            console.error('Error:', error);
            alert('Failed to create the tournament. Please try again.');
        });
    });
}

document.addEventListener("DOMContentLoaded", function() {
    updateTimers();
    setInterval(updateTimers, 1000); // Update every second
    filterTournaments();
    createTournament();
    manageForm();
    setStartingMinPlayersAndMaxPlayers();
    setStartAndDeadlineDate();
    hideForm();
});
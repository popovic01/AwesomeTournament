window.addEventListener("pageshow", function (event) {
    var historyTraversal = event.persisted ||
        (typeof window.performance != "undefined" &&
            window.performance.navigation.type === 2);
    if (historyTraversal) {
        window.location.reload();
    }
});

function setStartingMinPlayersAndMaxPlayers() {
    const maxTeamInput = document.getElementById('maxTeam');
    const startingPlayersInput = document.getElementById('startingPlayers');
    const minPlayersInput = document.getElementById('minPlayers');
    const maxPlayersInput = document.getElementById('maxPlayers');

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

    startingPlayersInput.addEventListener('click', function () {
        const startingPlayers = parseInt(startingPlayersInput.value, 10);

        if (startingPlayers > minPlayersInput.value && startingPlayers > maxPlayersInput.value) {
            minPlayersInput.value = startingPlayers;
            maxPlayersInput.value = startingPlayers;
        } else if (startingPlayers > minPlayersInput.value) minPlayersInput.value = startingPlayers;
        if (startingPlayers < 1) startingPlayersInput.value = 1;
    });

    minPlayersInput.addEventListener('click', function () {
        const minPlayers = parseInt(minPlayersInput.value, 10);
        const maxPlayers = parseInt(maxPlayersInput.value, 10);
        const startingPlayers = parseInt(startingPlayersInput.value, 10);

        if (minPlayers < startingPlayers) startingPlayersInput.value = minPlayersInput.value;

        if (minPlayers > maxPlayers) maxPlayersInput.value = minPlayersInput.value
    });

    maxPlayersInput.addEventListener('click', function () {
        const minPlayers = parseInt(minPlayersInput.value, 10);
        const maxPlayers = parseInt(maxPlayersInput.value, 10);
        const startingPlayers = parseInt(startingPlayersInput.value, 10);

        if (maxPlayers < minPlayers && maxPlayers < startingPlayers) {
            startingPlayersInput.value = maxPlayersInput.value;
            minPlayersInput.value = maxPlayersInput.value;
        } else if (maxPlayers < minPlayers) minPlayersInput.value = maxPlayersInput.value;
    })
}

function setStartAndDeadlineDate() {
    const date = new Date();
    date.setDate(date.getDate() + 1);
    const tomorrow = date.toISOString().split('T')[0];

    const startDateInput = document.getElementById('startDate');
    const deadlineInput = document.getElementById('deadline');

    startDateInput.min = tomorrow;
    deadlineInput.min = tomorrow;
    deadlineInput.max = startDateInput.value;

    startDateInput.addEventListener('change', function () {
        if (startDateInput.value) {
            deadlineInput.max = startDateInput.value;
            if (!deadlineInput.value || new Date(startDateInput.value) < new Date(deadlineInput.value)) deadlineInput.value = startDateInput.value;
        } else deadlineInput.removeAttribute('max');
    });
}

function manageForm() {
    document.getElementById('editTournamentForm').addEventListener('submit', function (event) {
        event.preventDefault();

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

        fetch('/api/tournaments/'+ tournamentId, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        }).then(response => {
            if (response.ok) {
                window.location.replace("/tournament/" + tournamentId);
            } else throw new Error('Failed to update tournament');
        }).catch(error => {
            console.error('Error:', error);
            alert('Failed to update the tournament. Please try again.');
        });
    });

    document.getElementById('deleteTournament').addEventListener('click', function () {
        if (confirm('Are you sure you want to delete this tournament?')) {
            fetch(`/api/tournaments/`+ tournamentId, {
                method: 'DELETE'
            })
                .then(response => {
                    if (response.ok) {
                        alert('Tournament deleted successfully');
                        window.location.href = '/tournaments';
                    } else {
                        alert('Failed to delete the tournament');
                        window.location.href = '/tournaments';
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('An error occurred while deleting the tournament');
                });
        }
    });
}

function hideForm() {
    var btnCancelEditTournament = document.getElementById("cancelEditTournament");
    btnCancelEditTournament.addEventListener("click", function () {
        document.getElementById("editTournamentContainer").style.display = "none";
        document.getElementById("main-container").style.display = "block";
        document.getElementById("form-container").style.display = "none";
        document.querySelector("ul").style.display = "block";
    });
}

document.addEventListener('DOMContentLoaded', function () {
    var tournamentTable = document.getElementById("tournamentTable");
    var rankingScorers = document.getElementById("rankingScorers");

    document.getElementById("seeTournamentTable").addEventListener('click', function () {
        rankingScorers.style.display = "none";
        tournamentTable.style.display = "table";
        this.style.backgroundColor = "darkblue";
        document.getElementById("seeRankingScorers").style.backgroundColor = "#007bff";
    });

    document.getElementById("seeRankingScorers").addEventListener('click', function () {
        tournamentTable.style.display = "none";
        rankingScorers.style.display = "table";
        this.style.backgroundColor = "darkblue";
        document.getElementById("seeTournamentTable").style.backgroundColor = "#007bff";
    });

    var filterControl = document.getElementById('matchFilter');
    if (filterControl) {
        filterControl.addEventListener('change', function () {
            var selectedFilter = this.value;
            var matches = document.querySelectorAll('#matchList li');
            matches.forEach(function (match) {
                var isFinished = match.getAttribute('is-finished') === 'true';
                var matchDate = match.getAttribute('date');
                var scoreDiv = match.querySelector('.score');
                if (scoreDiv) {
                    var dateSpan = scoreDiv.querySelector('.match-date');
                    if (!dateSpan) {
                        dateSpan = document.createElement('span');
                        dateSpan.classList.add('match-date');
                        scoreDiv.appendChild(dateSpan);
                    }

                    if (matchDate && !isFinished) {
                        var date = new Date(matchDate);
                        var formattedDate = ('0' + date.getDate()).slice(-2) + '/'
                            + ('0' + (date.getMonth() + 1)).slice(-2) + '/'
                            + date.getFullYear().toString().slice(-2) + ' '
                            + ('0' + date.getHours()).slice(-2) + ':'
                            + ('0' + date.getMinutes()).slice(-2);
                        dateSpan.textContent = ' Date: ' + formattedDate;
                    } else if (!matchDate) {
                        dateSpan.textContent = ' Date: TBA';
                    }
                }
                if (selectedFilter === 'all') {
                    match.style.display = '';
                } else if (selectedFilter === 'upcoming' && matchDate && !isFinished) {
                    match.style.display = '';
                } else if (selectedFilter === 'past' && isFinished) {
                    match.style.display = '';
                } else if (selectedFilter === 'tba' && !matchDate) {
                    match.style.display = '';
                } else {
                    match.style.display = 'none';
                }
            });
        });
        filterControl.dispatchEvent(new Event('change'));
    }

    var generateMatchesButton = document.getElementById('generateMatches');
    if (generateMatchesButton) {
        generateMatchesButton.addEventListener('click', function () {
            generateMatchesButton.disabled = true;
            generateMatchesButton.textContent = 'Loading...';
            var xhr = new XMLHttpRequest();
            xhr.open('POST', '/matches/tournaments/' + tournamentId, true);
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.onload = function () {
                if (xhr.status >= 200 && xhr.status < 300) {
                    window.location.reload();
                }
            };
            xhr.onerror = function () {
                generateMatchesButton.disabled = false;
                generateMatchesButton.textContent = 'Close Subscriptions and\nGenerate Matches';
            };
            xhr.send();
        });
    }

    var btnEditTournament = document.getElementById("btnEditTournament");
    btnEditTournament.addEventListener("click", function () {
        document.getElementById("editTournamentContainer").style.display = "block";
        document.getElementById("main-container").style.display = "none";
    });

    setStartingMinPlayersAndMaxPlayers();
    setStartAndDeadlineDate();
    manageForm();
    hideForm();

    var seeTournamentTableBtn = document.getElementById("seeTournamentTable");
    var matches = matches_;
    if (matches && matches.length > 0) seeTournamentTableBtn.style.display = "block";
    else seeTournamentTableBtn.style.display = "none";

    var btnAdd = document.getElementById('btnAdd');
    if (btnAdd) {
        btnAdd.addEventListener('click', function () {
            var url = '/tournament/' + tournamentId + '/add-team';
            window.location.href = url;
            var xhr = new XMLHttpRequest();
            xhr.open('GET', url, true);
            xhr.onload = function () {
                if (xhr.status >= 200 && xhr.status < 300) {
                    window.location.reload();
                }
            };
        });
    } else {
        console.log('btnAdd element not found');
    }
});

$(window).bind("pageshow", function () {
    $("#matchFilter").val('all');
});

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
        team_id: `${teamId}`,
        position: document.getElementById('positionInput').value,
        date_of_birth: document.getElementById('dateInput').value
    };
    // Make AJAX request to update player
    fetch(`/api/teams/${teamId}/players`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
        .then(response => {
            if (response.ok) {
                // Redirect to another page, replacing the current page in the history
                window.location.replace(`/team/${teamId}`);
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
            var url = `/tournament/${teamTournamentId}/team/${teamId}`;
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
                var url = `/api/teams/${teamId}`;
                var urlRedirect = `/tournament/${teamTournamentId}`;

                fetch(url, {
                    method: 'DELETE'
                })
                    .then(response => {
                        if (response.status >= 200 && response.status < 300)
                            window.location.href = urlRedirect;
                        else
                            alert('It is not possible to delete a team after they played a match.');
                    })
                    .catch(error => {
                        alert(error);
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
                window.location.replace(`/team/${teamId}`);
            } else {
                throw new Error('Failed to delete player');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to delete player. Please try again later.');
        });
};
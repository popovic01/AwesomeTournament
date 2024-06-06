document.addEventListener("DOMContentLoaded", function () {
    function formatDateWithTimezone(isoString) {
        const date = new Date(isoString);
        const options = {
            year: 'numeric',
            month: 'long',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit',
            timeZoneName: 'short'
        };
        return date.toLocaleDateString(undefined, options);
    }

    const matchDateElement = document.getElementById("matchDate");
    if (matchDate != "") {
        matchDateElement.textContent = formatDateWithTimezone(matchDate);
    } else {
        matchDateElement.textContent = "TBA";
    }

    function formatDateForInput(isoString) {
        const date = new Date(isoString);
        const year = date.getFullYear();
        const month = ('0' + (date.getMonth() + 1)).slice(-2);
        const day = ('0' + date.getDate()).slice(-2);
        const hours = ('0' + date.getHours()).slice(-2);
        const minutes = ('0' + date.getMinutes()).slice(-2);
        return `${year}-${month}-${day}T${hours}:${minutes}`;
    }

    const formMatchDateElement = document.getElementById("date");
    const formFormattedDate = formatDateForInput(matchDate);
    formMatchDateElement.value = formFormattedDate;

    fetchTeamData(team1Id, 1);
    fetchTeamData(team2Id, 2);

    // Modal functionality
    var resultModal = document.getElementById("resultModal");
    var infoModal = document.getElementById("infoModal");
    var eventModal = document.getElementById("eventModal");
    var resultBtn = document.getElementById("update-result");
    var infoBtn = document.getElementById("update-info");
    var eventBtn = document.getElementById("add-event");
    var spanClose = document.getElementsByClassName("close");

    resultBtn.onclick = function () {
        console.log("update-result button clicked");
        resultModal.style.display = "block";
    }

    infoBtn.onclick = function () {
        console.log("update-info button clicked");
        infoModal.style.display = "block";
    }

    eventBtn.onclick = function () {
        console.log("add-event button clicked");
        eventModal.style.display = "block";
    }

    for (var i = 0; i < spanClose.length; i++) {
        spanClose[i].onclick = function () {
            console.log("close button clicked");
            resultModal.style.display = "none";
            infoModal.style.display = "none";
            eventModal.style.display = "none";
        }
    }

    window.onclick = function (event) {
        if (event.target == resultModal) {
            console.log("Click outside resultModal detected");
            resultModal.style.display = "none";
        }
        if (event.target == infoModal) {
            console.log("Click outside infoModal detected");
            infoModal.style.display = "none";
        }
        if (event.target == eventModal) {
            console.log("Click outside eventModal detected");
            eventModal.style.display = "none";
        }
    }

    document.getElementById("updateResultForm").addEventListener("submit", function (event) {
        event.preventDefault();
        console.log("updateResultForm submit event");

        const formData = new FormData(this);
        const jsonObject = {};
        jsonObject["team1Score"] = Number(formData.get("team1Score"));
        jsonObject["team2Score"] = Number(formData.get("team2Score"));
        const jsonData = JSON.stringify(jsonObject);
        const xhr = new XMLHttpRequest();
        console.log(`Sending data to /api/matches/${id}`, jsonData)
        xhr.open("PUT", `/api/matches/${id}`, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                console.log("Match result updated successfully!");
                alert("Match result updated successfully!");
                resultModal.style.display = "none";
                window.location.reload();
            }
        };
        xhr.send(jsonData);
    });

    document.getElementById("updateInfoForm").addEventListener("submit", function (event) {
        event.preventDefault();
        console.log("updateInfoForm submit event");

        const formData = new FormData(this);
        const jsonObject = {};
        const date = new Date(formData.get("date"));
        const isoDate = date.toISOString();
        jsonObject["referee"] = formData.get("referee");
        jsonObject["matchDate"] = isoDate;
        const jsonData = JSON.stringify(jsonObject);
        const xhr = new XMLHttpRequest();
        console.log(`Sending data to /api/matches/${id}`, jsonData)
        xhr.open("PUT", `/api/matches/${id}`, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                console.log("Match info updated successfully!");
                alert("Match info updated successfully!");
                infoModal.style.display = "none";
                window.location.reload();
            }
        };
        xhr.send(jsonData);
    });

    // Show edit button if owner and match date is in the past or today
    const container = document.querySelector('.container');
    const datasetMatchDate = new Date(container.dataset.matchDate);
    const now = new Date(); // current time
    const owner = container.dataset.owner === 'true';

    if (owner) {
        document.getElementById("update-info").style.display = "block";
        if (datasetMatchDate <= now) {
            document.getElementById("update-result").style.display = "block";
        }
    }

});

function deleteEvent(id) {
    const xhr = new XMLHttpRequest();
    xhr.open("DELETE", "/api/events/" + id, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // TODO delete the item without reloading the page
            window.location.reload();
        }
        console.log(xhr);
    };
    xhr.send();
}

document.getElementById("newEvent").addEventListener("submit", function (event) {
    event.preventDefault();
    const formData = new FormData(this);
    const jsonObject = {};
    formData.forEach(function (value, key) {
        jsonObject[key] = value;
    });
    const jsonData = JSON.stringify(jsonObject);
    const xhr = new XMLHttpRequest();
    xhr.open("POST", `/api/matches/${id}/events`, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log("Match event updated successfully!");
            alert("Match event updated successfully!");
            infoModal.style.display = "none";
            window.location.reload();
        }
    };
    xhr.send(jsonData);
});

// Function to fetch team data and update the DOM
function fetchTeamData(teamId, teamNumber) {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", `/api/teams/${teamId}`, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                const response = JSON.parse(xhr.responseText);
                if (response.status === "OK" && response.data) {
                    const teamData = response.data;
                    // Update the DOM with the team data
                    document.getElementById(`team${teamNumber}Name`).innerText = teamData.name;

                    // Handle logo display
                    const logoElement = document.getElementById(`team${teamNumber}Logo`);
                    if (teamData["base64-logo"] != null) {
                        logoElement.src = `data:image/png;base64,${teamData["base64-logo"]}`;
                    } else {
                        logoElement.src = "/media/logo_placeholder.png";
                    }
                } else {
                    console.error(`Error in response data for Team ${teamNumber}:`, response.message);
                    alert(`Failed to fetch team ${teamNumber} data: ` + response.message);
                }
            } else {
                console.error(`Error fetching team ${teamNumber} data:`, xhr.statusText);
                alert(`Failed to fetch team ${teamNumber} data: ` + xhr.statusText);
            }
        }
    };
    xhr.onerror = function () {
        console.error(`Network error while fetching team {teamNumber} data`);
        alert(`Network error while fetching team ${teamNumber} data`);
    };
    xhr.send();
}

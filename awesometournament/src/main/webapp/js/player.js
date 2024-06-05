function showUpdateForm() {
  // Hide original data
  document.getElementById("playerInfo").style.display = "none";
  document.getElementById("showUpdateFormButton").style.display = "none";

  // Show update form
  document.getElementById("updateForm").style.display = "block";
}

function validateFile() {
  const fileInput = document.getElementById('inputGroupFile04');
  const filePath = fileInput.value;
  const allowedExtensions = /(\.pdf)$/i;
  if (!allowedExtensions.exec(filePath)) {
      showMessage('Upload only pdf files');
      return false;
  }

  return true;
}

function cancelUpdate(playerId) {
  // Redirect to another page, replacing the current page in the history
  window.location.replace('/players/{playerId}'.replace('{playerId}', playerId));
}

// Function to show the modal with a custom message
function showMessage(message) {
  var modal = document.getElementById("modal");
  var span = document.getElementsByClassName("close")[0];
  var modalMessage = document.getElementById("message");

  // Set the message text
  modalMessage.textContent = message;

  // Display the modal
  modal.style.display = "block";

  // When the user clicks on <span> (x), close the modal
  span.onclick = function() {
      modal.style.display = "none";
  }

  // When the user clicks anywhere outside the modal, close it
  window.onclick = function(event) {
      if (event.target == modal) {
          modal.style.display = "none";
      }
  }
}

// Function to set the selected option based on player's position
function setSelectedPosition(playerPosition) {
    const positionSelect = document.getElementById('positionInput');

    for (let option of positionSelect.options) {
        if (option.value.toUpperCase() === playerPosition) {
            option.selected = true;
            break;
        }
    }
}

document.getElementById('uploadMedicalCertificateForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent the default form submission

    // Check if the file input is valid
    if (!validateFile()) {
        return;
    }

    // Create a FormData object from the form element
    const formData = new FormData(this);

    // Use fetch API to send the form data
    fetch('/uploadMedicalCertificate', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (response.ok) {
            return response.json(); // Expecting a JSON response with the redirect URL
        } else {
            throw new Error('Network response was not ok');
        }
    })
    .then(data => {
        console.log('Success:', data);

        var modal = document.getElementById("modal");
        var span = document.getElementsByClassName("close")[0];
        var modalMessage = document.getElementById("message");

        // Set the message text
        modalMessage.textContent = "Medical certificate uploaded correctly";

        // Display the modal
        modal.style.display = "block";

        // When the user clicks on <span> (x), close the modal
        span.onclick = function() {
            modal.style.display = "none";
            window.location.replace(data.redirectUrl);
        }

        // When the user clicks anywhere outside the modal, close it
        window.onclick = function(event) {
            if (event.target == modal) {
                modal.style.display = "none";
                window.location.replace(data.redirectUrl);
            }
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
});

document.getElementById('updateForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent form submission
    var formData = {
        id: playerId,
        name: document.getElementById('nameInput').value,
        surname: document.getElementById('surnameInput').value,
        team_id: playerTeamId,
        position: document.getElementById('positionInput').value,
        date_of_birth: document.getElementById('dateInput').value
    };
    // Make AJAX request to update player
    fetch('/api/players/' + playerId, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
    .then(response => {
        if (response.ok) {
            // Redirect to another page, replacing the current page in the history
            window.location.replace("/players/" + playerId);
        } else {
            throw new Error('Failed to update player');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Failed to update player. Please try again later.');
    });
});

document.getElementById('download').onclick = function(event) {
    event.preventDefault();
    fetch("/download_medical_certificate/" + playerId, { method: 'get', mode: 'no-cors', referrerPolicy: 'no-referrer' })
    .then(res => res.blob())
    .then(res => {
        if (!res.size) {
            throw new Error('Medical certificate not found');
        }
        const aElement = document.createElement('a');
        aElement.setAttribute('download', "medical_certificate.pdf");
        const href = URL.createObjectURL(res);
        aElement.href = href;
        aElement.setAttribute('target', '_blank');
        aElement.click();
        URL.revokeObjectURL(href);
    })
    .catch(error => {
        console.error('Error:', error);
        showMessage('Medical certificate not found.');
    });
};

// Call the function to set the selected position
setSelectedPosition(playerPosition);

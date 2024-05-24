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

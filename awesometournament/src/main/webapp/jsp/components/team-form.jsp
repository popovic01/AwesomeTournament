<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <title>AwesomeTournaments - Tournament</title>

        <c:import url="/jsp/commons/head.jsp"/>
        <link rel="stylesheet" type="text/css" href="/css/team-form.css" />
    </head>

    <body>
        <!-- header -->
        <c:import url="/jsp/commons/header.jsp"/>

        <div class="container mt-4 d-flex justify-content-center align-items-center">
            <form id="teamForm" method="POST" action="" enctype="multipart/form-data" class="row w-30 border p-3">
<%--                <input type="hidden" name="teamId" value="${team.getId()}">--%>
                <div class="d-flex justify-content-center mb-3 w-100">
                    <p class="text-dark">Team Name:</p>
                    <input type="text" name="name" placeholder="Name" id="teamName" class="form-control input-width-70" value="${teamName}">
                </div>
                <div class="d-flex justify-content-center mb-3 w-100">
                    <p class="text-dark">Team Logo:</p>
                    <input type="file" name="file" id="file" class="form-control input-width-70"/>
                </div>
                <div class="d-flex justify-content-center">
                    <input type="submit" value="Edit" name="upload" class="btn btn-primary" id="btnSubmit"/>
                </div>
            </form>
        </div>

        <!-- footer -->
        <c:import url="/jsp/commons/footer.jsp"/>
    </body>
</html>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        var segments = new URL(window.location.href).pathname.split('/');
        const tournamentId = segments[2];
        const lastSegment = segments.pop();
        var teamForm = document.getElementById('teamForm');
        var teamNameInput = document.getElementById('teamName');

        var btnSubmit = document.getElementById('btnSubmit');
        var url = '/api/tournaments/' + tournamentId + '/teams';

        if (isNaN(parseInt(lastSegment))) {
            btnSubmit.value = 'Add';
        } else {
            btnSubmit.value = 'Edit';
        }

        teamForm.addEventListener('submit', function(event) {
            event.preventDefault();

            var formData = new FormData(teamForm);

            if (!teamNameInput.value.trim()) {
                alert('You must enter a team name.');
                return;
            }

            if (isNaN(parseInt(lastSegment))) {
                fetch(url, {
                    method: 'POST',
                    body: formData
                })
                    .then(response => response.json())
                    .then(data => {
                        if (data.message) {
                            alert(data.message);
                        }
                        if (data.redirect) {
                            window.location.href = data.redirect;
                        } else if (data.error) {
                            console.error('Error:', data.error);
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        alert(error)
                    });
            } else {
                formData.set("teamId", lastSegment);
                fetch(url, {
                    method: 'PUT',
                    body: formData
                })
                    .then(response => response.json())
                    .then(data => {
                        if (data.message) {
                            alert(data.message);
                        }
                        if (data.redirect) {
                            window.location.href = data.redirect;
                        } else if (data.error) {
                            console.error('Error:', data.error);
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        alert(error)
                    });
            }
        });
    });


</script>

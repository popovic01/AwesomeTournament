<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <title>AwesomeTournaments - Tournament</title>

        <c:import url="/jsp/common/head.jsp"/>

        <style>
            .main-wrapper {
                max-width: 800px;
                margin: 20px auto;
                padding: 20px;
                background-color: #fff;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            #btnEdit {
                margin-right: 1rem;
            }
        </style>
    </head>

    <body>
        <!-- header -->
        <c:import url="/jsp/common/header.jsp"/>

        <div class="main-wrapper">
            <div class="title-logo-wrapper">
                <p class="fs-1 text-dark">
                    <c:out value="${team.name}"/>
                </p>
                <c:if test="${not empty team.base64Logo}">
                    <img src="data:image/jpg;base64,${team.base64Logo}"/>
                </c:if>
            </div>

            <c:if test="${tournamentOwner || teamOwner}">
                <div class="d-flex justify-content-end mb-2">
                    <button id="btnEdit" class="btn btn-secondary">Edit</button>
                    <button id="btnDelete" class="btn btn-secondary">Delete</button>
                </div>
            </c:if>

            <c:if test="${!empty players}">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col"></th>
                        <th scope="col">Full Name</th>
                        <th scope="col">Date of Birth</th>
                        <th scope="col">Position</th>
                        <th scope="col">See Details</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${players}" var="player" varStatus="i">
                        <tr>
                            <th scope="row">${i.count}</th>
                            <td>
                                <c:out value="${player.name}"/>
                                <c:out value="${player.surname}"/>
                            </td>
                            <td>
                                <c:out value="${player.dateOfBirth}"/>
                            </td>
                            <td>
                                <c:out value="${player.position}"/>
                            </td>
                            <td>
                                <a href="<c:url value="/players/${player.id}"/>">
                                    Details
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <c:if test="${empty players}">
                <p class="text-secondary text-center">No players added in this team</p>
            </c:if>
            <c:if test="${teamOwner || tournamentOwner}">
                <a href="<c:url value=""/>">
                    Add Players to this team
                </a>
            </c:if>

        </div>

        <!-- footer -->
        <c:import url="/jsp/common/footer.jsp"/>
    </body>

</html>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        var btnEdit = document.getElementById('btnEdit');
        var btnDelete = document.getElementById('btnDelete');

        if (btnEdit) {
            btnEdit.addEventListener('click', function() {
                var url = `/tournament/${team.getTournamentId()}/team/${team.id}`;
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
                    var url = `/api/teams/${team.id}`;
                    var urlRedirect = `/tournament/${team.getTournamentId()}`;

                    fetch(url, {
                        method: 'DELETE'
                    })
                        .then(response => {
                            window.location.href = urlRedirect;
                        })
                        .catch(error => {
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
</script>

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

            .input-wrapper {
                display: flex;
                flex-direction: row;
                justify-content: start;
                gap: 1rem;
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
                <form method="POST" action="" enctype="multipart/form-data" >
                    <input type="hidden" name="teamId" value="${team.getId()}">
                    <div class="input-wrapper">
                        <p class="text-dark">Team Name:</p>
                        <input type="text" name="name" placeholder="Name">
                    </div>
                    <div class="input-wrapper">
                        <p class="text-dark">Team Logo:</p>
                        <input type="file" name="file" id="file"/>
                    </div>
                    <input type="submit" value="Edit" name="upload1"/>
                </form>
            </c:if>

            <c:if test="${tournamentOwner}">
                <div>
                    You are the admin of the tournament this team belongs to
                </div>
            </c:if>
            <c:if test="${teamOwner}">
                <div>
                    You are the admin of this team
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
                        <th scope="col">Medical Certificate</th>
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
                                <button onclick="showMedCertificate()" class="hyperlink">
                                    Show
                                </button>
                                    <%--                                <c:out value="${player.medicalCertificate}"/>--%>
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

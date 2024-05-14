<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        var filterControl = document.getElementById('matchFilter');
        if (filterControl) {
            filterControl.addEventListener('change', function() {
                var selectedFilter = this.value;
                var matches = document.querySelectorAll('#matchList li');
                matches.forEach(function(match) {
                    var isFinished = match.getAttribute('is-finished') === 'true';
                    if (selectedFilter === 'all') {
                        match.style.display = '';
                    } else if (selectedFilter === 'upcoming' && !isFinished) {
                        match.style.display = '';
                    } else if (selectedFilter === 'past' && isFinished) {
                        match.style.display = '';
                    } else {
                        match.style.display = 'none';
                    }
                });
            });
        } else {
            console.log('matchFilter element not found');
        }
    });
</script>

<html lang="en">
    <head>
        <title>AwesomeTournaments - Tournament</title>

        <c:import url="/jsp/common/head.jsp"/>

        <style>
            .container {
                max-width: 800px;
                margin: 20px auto;
                padding: 20px;
                background-color: #fff;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                margin-bottom: 4rem;
            }

            h1 {
                text-align: center;
                color: #333;
            }

            ul {
                list-style-type: none;
                padding: 0;
            }

            li {
                margin-bottom: 10px;
                padding: 10px;
                background-color: #f9f9f9;
                border-radius: 5px;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            }

            li:hover {
                background-color: #e9e9e9;
            }

            p {
                margin: 1rem 0;
            }

            .logo-img {
                height: 2rem;
            }
        </style>
    </head>
    <body>
        <!-- header -->
        <c:import url="/jsp/common/header.jsp"/>

        <div class="container">
            <div class="title-logo-wrapper">
                <p class="fs-1 text-dark">
                    <c:out value="${tournament.name}"/>
                </p>
                <img src="data:image/jpg;base64,${tournament.base64Logo}"/>
            </div>

            <div>
                <div>
                    <c:out value="${tournament}"/>
                </div>
            </div>
            <c:if test="${owner}">
                <div style="color: red;">
                    You are the admin of this tournament
                </div>
            </c:if>

            <p class="fs-4 text-dark">Teams</p>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col"></th>
                    <th scope="col">Name</th>
                    <th scope="col">Logo</th>
                    <th scope="col">Actions</th>
                    <th scope="col">See Details</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${teams}" var="team" varStatus="i">
                    <tr>
                        <th scope="row">${i.count}</th>
                        <td>
                            <c:out value="${team.name}"/>
                        </td>
                        <td>
<%--                            <button onclick="showLogo()" class="hyperlink">--%>
<%--                                Show--%>
<%--                            </button>--%>
                            <img src="data:image/jpg;base64,${team.base64Logo}"
                                class="logo-img"/>
                        </td>
                        <td>
                            <i class="fa fa-pencil-square-o"></i>
                            <i class="fa fa-trash-o"></i>
                        </td>
                        <td>
                            <a href="<c:url value="/team/${team.getId()}"/>">
                                Details
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <div class="container">
                <c:choose>
                    <c:when test="${not empty matches}">
                        <div class="match-header">
                            <h3>Matches:</h3>
                            <select id="matchFilter">
                                <option value="all">All Matches</option>
                                <option value="upcoming">Upcoming Matches</option>
                                <option value="past">Past Matches</option>
                            </select>
                        </div>
                        <ul id="matchList">
                            <c:forEach var="match" items="${matches}">
                                <li is-finished="${match.isFinished}">
                                    <c:out value="${match}"/> -
                                    <a href="<c:url value="/match/${match.getId()}"/>">more...</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </c:when>
                        <c:otherwise>
                            <p class="text-dark">
                                No matches available at the moment. <br>
                                Come back here when the subscriptions are
                                closed!
                            </p>
                        </c:otherwise>
                </c:choose>
            </div>
        <!-- footer -->
        <c:import url="/jsp/common/footer.jsp"/>
    </body>
</html>
<style>
    .match-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 10px;
    }

    h3 {
        margin: 0;
    }

    #matchFilter {
        padding: 5px 10px;
        border-radius: 5px;
    }
</style>
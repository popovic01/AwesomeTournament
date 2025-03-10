<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Top Scorers Ranking</title>

        <c:import url="/jsp/commons/head.jsp"/>

        <style>
            body {
                text-align: center;
            }

            table {
                width: 50%;
                border-collapse: collapse;
                border-spacing: 0;
                margin: auto auto 30px;
            }

            th, td {
                border: 2px solid black;
                padding: 8px;
                text-align: left;
            }

            th {
                background-color: #f2f2f2;
            }

            .logo{
                width: auto;
                height: 60px;
            }

            a.link {
                text-decoration: none; /* Remove underlined */
                color: inherit; /* Uses the parent's text color */
            }

            /* Style for team hover links */
            a.link:hover {
                text-decoration: underline; /* Underline text on hover */
                color: inherit; /* Uses the parent's text color */
            }
        </style>
    </head>

    <body>
        <!-- header -->
        <c:import url="/jsp/commons/header.jsp"/>

        <h2>Top Scorers Ranking</h2>

        <table>
            <tr>
                <th>Player</th>
                <th>Team</th>
                <th>Goals</th>
            </tr>
            <tbody>
                <c:forEach items="${ranking}" var="entry">
                    <tr>
                        <td>
                            <a href="/players/${entry.getPlayerID()}" class="link">
                                <c:out value="${entry.getPlayerName()} ${entry.getPlayerSurname()}"/>
                            </a>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty entry.getLogo()}">
                                    <div>
                                        <img src="data:image/jpeg;base64, ${entry.getLogo()}" class="logo" alt="team logo">
                                        <a href="/team/${entry.getTeamID()}" class="link"><c:out value="${entry.getTeamName()}"/></a>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div>
                                        <img src="<c:url value="/media/logo_placeholder.png"/>" class="logo" alt="default logo">
                                        <a href="/team/${entry.getTeamID()}" class="link"><c:out value="${entry.getTeamName()}"/></a>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td><c:out value="${entry.getGoals()}"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- footer -->
        <c:import url="/jsp/commons/footer.jsp"/>
    </body>
</html>



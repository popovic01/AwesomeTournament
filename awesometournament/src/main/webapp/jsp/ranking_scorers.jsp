<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>

        <c:import url="/jsp/common/head.jsp"/>

        <title>Top Scorers Ranking</title>

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
                text-decoration: none; /* Rimuove il sottolineato */
                color: inherit; /* Utilizza il colore del testo del genitore */
            }

            /* Stile per i link del team al passaggio del mouse */
            a.link:hover {
                text-decoration: underline; /* Sottolinea il testo al passaggio del mouse */
                color: inherit; /* Utilizza il colore del testo del genitore */
            }
        </style>
    </head>

    <body>

        <!-- header -->
        <c:import url="/jsp/common/header.jsp"/>

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
                                        <img src="<c:url value="/media/tournament_logo.png"/>" class="logo" alt="default logo">
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
        <div id="footer">
            <c:import url="/jsp/common/footer.jsp"/>
        </div>
    </body>
</html>



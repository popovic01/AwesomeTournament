<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!DOCTYPE html>
    <html>

    <head>
      <title>Ranking Page</title>
    </head>

    <body>
      <h1>Ranking Tournament 
        <c:out value="${tournament_id}" />
      </h1>
      <table border="1">
          <thead>
              <tr>
                  <th>Team Name</th>
                  <th>Points</th>
                  <th>Matches Played</th>
              </tr>
          </thead>
          <tbody>
              <c:forEach items="${ranking}" var="entry">
                  <tr>
                      <td><c:out value="${entry.getTeamName()}"/></td>
                      <td><c:out value="${entry.getPoints()}"/></td>
                      <td><c:out value="${entry.getMatchesPlayed()}"/></td>
                  </tr>
              </c:forEach>
          </tbody>
      </table>

    </body>

    </html>

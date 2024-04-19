<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.unipd.dei.dam.awesometournament.utils.RankingEntry" %>
<%@ page import="java.util.ArrayList" %>

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
      <%
          ArrayList<RankingEntry> ranking = (ArrayList<RankingEntry>) request.getAttribute("ranking");
      %>
      <table border="1">
          <thead>
              <tr>
                  <th>Team Name</th>
                  <th>Points</th>
                  <th>Matches Played</th>
              </tr>
          </thead>
          <tbody>
              <% for (RankingEntry entry : ranking) { %>
                  <tr>
                      <td><%= entry.getTeamName() %></td>
                      <td><%= entry.getPoints() %></td>
                      <td><%= entry.getMatchesPlayed() %></td>
                  </tr>
              <% } %>
          </tbody>
      </table>

    </body>

    </html>

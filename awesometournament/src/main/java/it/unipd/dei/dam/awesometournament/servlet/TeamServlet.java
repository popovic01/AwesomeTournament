package it.unipd.dei.dam.awesometournament.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import it.unipd.dei.dam.awesometournament.database.DeleteTeamDAO;
import it.unipd.dei.dam.awesometournament.database.UpdateTeamDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.unipd.dei.dam.awesometournament.database.GetTeamDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Team;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TeamServlet extends AbstractDatabaseServlet{
    protected final static Logger LOGGER = LogManager.getLogger(TeamServlet.class,
            StringFormatterMessageFactory.INSTANCE);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.GET_TEAM);

        String url = req.getPathInfo();
        if (url != null) {
            String[] urlParts = url.split("/"); // urlParts[0] = ""
            if (urlParts.length != 2) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
            } else {
                try {
                    int teamId = Integer.parseInt(urlParts[1]);
                    Connection connection = getConnection();

                    GetTeamDAO getTeamDAO = new GetTeamDAO(connection, teamId);
                    getTeamDAO.access();
                    Team team = getTeamDAO.getOutputParam();

                    if(team != null) {
                        ObjectMapper om = new ObjectMapper();
                        resp.getWriter().print(om.writeValueAsString(team));
                    } else {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Team not found");
                    }

                } catch (NumberFormatException e) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Team ID must be an integer");
                } catch (SQLException e) {
                    resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                }
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.PUT_TEAM);

        LOGGER.info("Received put request");
        String url = req.getPathInfo();
        if (url != null) {
            String[] urlParts = url.split("/");
            if (urlParts.length != 2) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
            } else {
                try {
                    int teamId = Integer.parseInt(urlParts[1]);
                    ObjectMapper objectMapper = new ObjectMapper();
                    StringBuilder requestBody = new StringBuilder();
                    try (BufferedReader reader = req.getReader()) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            requestBody.append(line);
                        }
                    }
                    Team team = objectMapper.readValue(requestBody.toString(), Team.class);
                    team.setId(teamId);
                    Connection connection = getConnection();
                    UpdateTeamDAO dao = new UpdateTeamDAO(connection, team);
                    dao.access();

                    int result = dao.getOutputParam();
                    if (result == 1) {
                        resp.getWriter().print("Team " + team.getName() + " successfully updated");
                    } else {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Team not found");
                    }
                } catch (NumberFormatException e) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Team ID must be an integer");
                } catch (SQLException e) {
                    resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                }
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.DELETE_TEAM);

        LOGGER.info("Received delete request");
        String url = req.getPathInfo();
        if (url != null) {
            String[] urlParts = url.split("/");
            if (urlParts.length != 2) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
            } else {
                try {
                    int id = Integer.parseInt(urlParts[1]);
                    Connection connection = getConnection();

                    DeleteTeamDAO dao = new DeleteTeamDAO(connection, id);
                    dao.access();

                    int result = dao.getOutputParam();
                    if (result == 1) {
                        resp.getWriter().print("Team successfully deleted");
                    } else {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Team not found");
                    }
                } catch (NumberFormatException e) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Team ID must be an integer");
                } catch (SQLException e) {
                    resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                }
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
        }
    }
}

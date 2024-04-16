package it.unipd.dei.dam.awesometournament.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import it.unipd.dei.dam.awesometournament.database.GetPlayerDAO;
import it.unipd.dei.dam.awesometournament.database.UpdatePlayerDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Player;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PlayerServlet extends AbstractDatabaseServlet {
    protected final static Logger LOGGER = LogManager.getLogger(PlayerServlet.class,
            StringFormatterMessageFactory.INSTANCE);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.GET_PLAYER);

        LOGGER.info("Received get request");

        String url = req.getPathInfo();
        if (url != null) {
            String[] urlParts = url.split("/"); // urlParts[0] = ""
            if (urlParts.length != 2) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
            } else {
                try {
                    int playerId = Integer.parseInt(urlParts[1]);
                    Connection connection = getConnection();
                    GetPlayerDAO getPlayerDAO = new GetPlayerDAO(connection, playerId);
                    List<Object> result = (ArrayList<Object>) getPlayerDAO.access().getOutputParam();
                    if (result != null) {
                        req.setAttribute("player", (Player) result.get(0));
                        req.setAttribute("teamName", (String) result.get(1));
                        req.getRequestDispatcher("/player.jsp").forward(req, resp);
                    } else {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "The player doesn't exist");
                    }
                } catch (NumberFormatException e) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Player ID must be an integer");
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
        LogContext.setAction(Actions.PUT_PLAYER);

        LOGGER.info("Received put request");
        String url = req.getPathInfo();
        if (url != null) {
            String[] urlParts = url.split("/"); // urlParts[0] = ""
            if (urlParts.length != 2) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
            } else {
                try {
                    int playerId = Integer.parseInt(urlParts[1]);
                    ObjectMapper objectMapper = new ObjectMapper();
                    StringBuilder requestBody = new StringBuilder();
                    try (BufferedReader reader = req.getReader()) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            requestBody.append(line);
                        }
                    }
                    LOGGER.info(requestBody.toString());
                    Player player = (Player) objectMapper.readValue(requestBody.toString(), Player.class); // se rompe qua
                    player.setId(playerId);
                    LOGGER.info(player.toString());
                    Connection connection = getConnection();
                    UpdatePlayerDAO updatePlayerDAO = new UpdatePlayerDAO(connection, player);
                    updatePlayerDAO.access();
                } catch (NumberFormatException e) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Player ID must be an integer");
                } catch (SQLException e) {
                    resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                }
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
        }
    }
}

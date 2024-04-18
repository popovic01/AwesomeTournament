package it.unipd.dei.dam.awesometournament.servlet.handler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.BufferedReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.*;
import it.unipd.dei.dam.awesometournament.database.GetPlayerDAO;
import it.unipd.dei.dam.awesometournament.database.UpdatePlayerDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Player;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

public class PlayerHandler implements Handler {
    protected final static Logger LOGGER = LogManager.getLogger(PlayerHandler.class,
            StringFormatterMessageFactory.INSTANCE);
    @Override
    public Result handle(Method method, HttpServletRequest req, HttpServletResponse res, Connection connection,
            String[] params) throws ServletException, IOException {
            LogContext.setIPAddress(req.getRemoteAddr());
            switch (method) {
                case GET:
                    LogContext.setAction(Actions.GET_PLAYER);
                    LOGGER.info("Received GET request");
                    try {
                        int playerId = Integer.parseInt(params[0]);
                        GetPlayerDAO getPlayerDAO = new GetPlayerDAO(connection, playerId);
                        Player player = (Player) getPlayerDAO.access().getOutputParam();
                        if (player != null) {
                            ObjectMapper objectMapper = new ObjectMapper();
                            objectMapper.setDateFormat(new StdDateFormat());
                            res.setContentType("application/json");
                            res.getWriter().println(objectMapper.writeValueAsString(player));
                        } else {
                            res.sendError(HttpServletResponse.SC_NOT_FOUND, "The player doesn't exist");
                        }
                    } catch (NumberFormatException e) {
                        res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Player ID must be an integer");
                    } catch (SQLException e) {
                        res.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                    }
                    return Result.CONTINUE;
                case PUT:
                    LogContext.setAction(Actions.PUT_PLAYER);
                    LOGGER.info("Received PUT request");
                    try {
                        int playerId = Integer.parseInt(params[0]);
                        ObjectMapper objectMapper = new ObjectMapper();
                        StringBuilder requestBody = new StringBuilder();
                        try (BufferedReader reader = req.getReader()) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                requestBody.append(line);
                            }
                        }
                        LOGGER.info(requestBody.toString());
                        Player player = (Player) objectMapper.readValue(requestBody.toString(), Player.class);
                        player.setId(playerId);
                        LOGGER.info(player.toString());
                        UpdatePlayerDAO updatePlayerDAO = new UpdatePlayerDAO(connection, player);
                        updatePlayerDAO.access();
                    } catch (NumberFormatException e) {
                        res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Player ID must be an integer");
                    } catch (SQLException e) {
                        res.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                    }
                default:
                    return Result.STOP;
            }
        }
}

package it.unipd.dei.dam.awesometournament.servlet.handler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.BufferedReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.*;
import it.unipd.dei.dam.awesometournament.database.DeletePlayerDAO;
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

    String getRequestBody(HttpServletRequest req) throws IOException{
        StringBuilder requestBody = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        return requestBody.toString();
    }

    @Override
    public Result handle(Method method, HttpServletRequest req, HttpServletResponse res, Connection connection,
            String[] params) throws ServletException, IOException {

            LogContext.setIPAddress(req.getRemoteAddr());

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setDateFormat(new StdDateFormat());
            int playerId = Integer.parseInt(params[0]);
            Player player;
            Integer result;

            try {
                switch (method) {
                    case GET:
                        LogContext.setAction(Actions.GET_PLAYER);
                        LOGGER.info("Received GET request");
                        GetPlayerDAO getPlayerDAO = new GetPlayerDAO(connection, playerId);
                        player = (Player) getPlayerDAO.access().getOutputParam();
                        if (player != null) {
                            res.setContentType("application/json");
                            res.getWriter().println(objectMapper.writeValueAsString(player));
                        } else {
                            res.sendError(HttpServletResponse.SC_NOT_FOUND, "The player doesn't exist");
                        }
                        break;
                    case PUT:
                        LogContext.setAction(Actions.PUT_PLAYER);
                        LOGGER.info("Received PUT request");
                        String requestBody = getRequestBody(req);
                        LOGGER.info(requestBody);
                        player = (Player) objectMapper.readValue(requestBody, Player.class);
                        player.setId(playerId);
                        LOGGER.info(player.toString());
                        UpdatePlayerDAO updatePlayerDAO = new UpdatePlayerDAO(connection, player);
                        result = (Integer) updatePlayerDAO.access().getOutputParam();
                        if (result == 1) {
                            res.setStatus(HttpServletResponse.SC_OK);
                        } else {
                            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        }
                        break;
                    case DELETE:
                        LogContext.setAction(Actions.DELETE_PLAYER);
                        LOGGER.info("Received DELETE request");
                        DeletePlayerDAO deletePlayerDAO = new DeletePlayerDAO(connection, playerId);
                        result = (Integer) deletePlayerDAO.access().getOutputParam();
                        if (result == 1) {
                            res.setStatus(HttpServletResponse.SC_OK);
                        } else {
                            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        }
                        break;
                    default:
                        return Result.STOP;
                }
            } catch (NumberFormatException e) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Player ID must be an integer");
            } catch (SQLException e) {
                res.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            }
            return Result.CONTINUE;
        }
}

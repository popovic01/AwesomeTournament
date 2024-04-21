package it.unipd.dei.dam.awesometournament.servlet.handler;

import java.io.IOException;
import java.sql.SQLException;
import java.io.BufferedReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import it.unipd.dei.dam.awesometournament.servlet.RestMatcherHandler;
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

public class PlayerHandler extends RestMatcherHandler{
    protected final static Logger LOGGER = LogManager.getLogger(PlayerHandler.class,
            StringFormatterMessageFactory.INSTANCE);

    void getPlayer (HttpServletRequest req, HttpServletResponse res, int playerId) throws ServletException, IOException, SQLException{
        LogContext.setAction(Actions.GET_PLAYER);
        LOGGER.info("Received GET request");
        GetPlayerDAO getPlayerDAO = new GetPlayerDAO(getConnection(), playerId);
        Player player = (Player) getPlayerDAO.access().getOutputParam();
        if (player != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setDateFormat(new StdDateFormat());
            res.setContentType("application/json");
            res.getWriter().println(objectMapper.writeValueAsString(player));
        } else {
            res.sendError(HttpServletResponse.SC_NOT_FOUND, "The player doesn't exist");
        }
    }
    
    void putPlayer (HttpServletRequest req, HttpServletResponse res, int playerId) throws ServletException, IOException, SQLException{
        LogContext.setAction(Actions.PUT_PLAYER);
        LOGGER.info("Received PUT request");
        String requestBody = getRequestBody(req);
        LOGGER.info(requestBody);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new StdDateFormat());
        Player player = (Player) objectMapper.readValue(requestBody, Player.class);
        player.setId(playerId);
        LOGGER.info(player.toString());
        UpdatePlayerDAO updatePlayerDAO = new UpdatePlayerDAO(getConnection(), player);
        Integer result = (Integer) updatePlayerDAO.access().getOutputParam();
        if (result == 1) {
            res.setStatus(HttpServletResponse.SC_OK);
        } else {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    void deletePlayer (HttpServletRequest req, HttpServletResponse res, int playerId) throws ServletException, IOException, SQLException{
        LogContext.setAction(Actions.DELETE_PLAYER);
        LOGGER.info("Received DELETE request");
        DeletePlayerDAO deletePlayerDAO = new DeletePlayerDAO(getConnection(), playerId);
        Integer result = (Integer) deletePlayerDAO.access().getOutputParam();
        if (result == 1) {
            res.setStatus(HttpServletResponse.SC_OK);
        } else {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Result handle(Method method, HttpServletRequest req, HttpServletResponse res,
            String[] params) throws ServletException, IOException {

            LogContext.setIPAddress(req.getRemoteAddr());

            int playerId = Integer.parseInt(params[0]);

            try {
                switch (method) {
                    case GET:
                        getPlayer(req, res, playerId);
                        break;
                    case PUT:
                        putPlayer(req, res, playerId);
                        break;
                    case DELETE:
                        deletePlayer(req, res, playerId);
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

package it.unipd.dei.dam.awesometournament.servlet.handler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.io.BufferedReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.*;
import it.unipd.dei.dam.awesometournament.database.GetTeamPlayerDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Player;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

public class TeamPlayerHandler implements Handler {
    protected final static Logger LOGGER = LogManager.getLogger(PlayerHandler.class,
            StringFormatterMessageFactory.INSTANCE);

    @Override
    public Result handle(Method method, HttpServletRequest req, HttpServletResponse res, Connection connection,
            String[] params) throws ServletException, IOException {

            LogContext.setIPAddress(req.getRemoteAddr());

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setDateFormat(new StdDateFormat());
            int teamId = Integer.parseInt(params[0]);

            try {
                switch (method) {
                    case GET:
                        LogContext.setAction(Actions.GET_PLAYER);
                        LOGGER.info("Received GET request");
                        GetTeamPlayerDAO getTeamPlayerDAO = new GetTeamPlayerDAO(connection, teamId);
                        ArrayList<Player> players = getTeamPlayerDAO.access().getOutputParam();
                        if (players.size() != 0) {
                            res.setContentType("application/json");
                            res.getWriter().println(objectMapper.writeValueAsString(players));
                        } else {
                            res.sendError(HttpServletResponse.SC_NOT_FOUND, "No players in team " + teamId);
                        }
                        break;
                    default:
                        return Result.STOP;
                }
            } catch (NumberFormatException e) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Team ID must be an integer");
            } catch (SQLException e) {
                res.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            }
            return Result.CONTINUE;
        }
}

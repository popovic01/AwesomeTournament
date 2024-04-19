package it.unipd.dei.dam.awesometournament.servlet.handler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.io.BufferedReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.*;
import it.unipd.dei.dam.awesometournament.database.CreateTeamPlayerDAO;
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

    String getRequestBody(HttpServletRequest req) throws IOException{
        StringBuilder requestBody = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        return requestBody.toString();
    }
    
    void getPlayersFromTeam (HttpServletResponse res, Connection connection, int teamId) throws ServletException, IOException, SQLException{
        LogContext.setAction(Actions.GET_TEAM_PLAYER);
        LOGGER.info("Received GET request");
        GetTeamPlayerDAO getTeamPlayerDAO = new GetTeamPlayerDAO(connection, teamId);
        ArrayList<Player> players = getTeamPlayerDAO.access().getOutputParam();
        if (players.size() != 0) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setDateFormat(new StdDateFormat());
            res.setContentType("application/json");
            res.getWriter().println(objectMapper.writeValueAsString(players));
        } else {
            res.sendError(HttpServletResponse.SC_NOT_FOUND, "No players in team " + teamId);
        }
    }

    void postPlayer (HttpServletRequest req, HttpServletResponse res, Connection connection, int teamId) throws ServletException, IOException, SQLException{
        LogContext.setAction(Actions.POST_TEAM_PLAYER);
        LOGGER.info("Received POST request");
        String requestBody = getRequestBody(req);
        LOGGER.info(requestBody);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new StdDateFormat());
        Player player = (Player) objectMapper.readValue(requestBody, Player.class);
        player.setTeamId(teamId);
        LOGGER.info(player.toString());
        CreateTeamPlayerDAO createTeamPlayerDAO = new CreateTeamPlayerDAO(connection, player);
        Integer newId = (Integer) createTeamPlayerDAO.access().getOutputParam();
        if (newId != null) {
            LOGGER.info("Player created with id %d", newId);
            res.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Result handle(Method method, HttpServletRequest req, HttpServletResponse res, Connection connection,
            String[] params) throws ServletException, IOException {

            LogContext.setIPAddress(req.getRemoteAddr());

            int teamId = Integer.parseInt(params[0]);

            try {
                switch (method) {
                    case GET:
                        getPlayersFromTeam(res, connection, teamId);
                        break;
                    case POST:
                        postPlayer(req, res, connection, teamId);
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

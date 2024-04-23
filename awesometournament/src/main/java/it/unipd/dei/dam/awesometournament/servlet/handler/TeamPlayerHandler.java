package it.unipd.dei.dam.awesometournament.servlet.handler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import it.unipd.dei.dam.awesometournament.servlet.RestMatcherHandler;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.*;
import it.unipd.dei.dam.awesometournament.utils.BodyTools;
import it.unipd.dei.dam.awesometournament.utils.SessionHelpers;
import it.unipd.dei.dam.awesometournament.database.CreateTeamPlayerDAO;
import it.unipd.dei.dam.awesometournament.database.GetTeamDAO;
import it.unipd.dei.dam.awesometournament.database.GetTeamPlayerDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Player;
import it.unipd.dei.dam.awesometournament.resources.entities.Team;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

public class TeamPlayerHandler extends RestMatcherHandler {
    protected final static Logger LOGGER = LogManager.getLogger(TeamPlayerHandler.class,
            StringFormatterMessageFactory.INSTANCE);
    
    void getPlayersFromTeam (HttpServletRequest req, HttpServletResponse res, int teamId) throws ServletException, IOException, SQLException{
        LogContext.setAction(Actions.GET_TEAM_PLAYER);
        LOGGER.info("Received GET request");
        GetTeamPlayerDAO getTeamPlayerDAO = new GetTeamPlayerDAO(getConnection(), teamId);
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

    void postPlayer (HttpServletRequest req, HttpServletResponse res, int teamId) throws ServletException, IOException, SQLException{
        LogContext.setAction(Actions.POST_TEAM_PLAYER);
        LOGGER.info("Received POST request");
        String requestBody = BodyTools.getRequestBody(req);
        LOGGER.info(requestBody);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new StdDateFormat());
        Player player = (Player) objectMapper.readValue(requestBody, Player.class);
        player.setTeamId(teamId);
        LOGGER.info(player.toString());
        CreateTeamPlayerDAO createTeamPlayerDAO = new CreateTeamPlayerDAO(getConnection(), player);
        Integer newId = (Integer) createTeamPlayerDAO.access().getOutputParam();
        if (newId != null) {
            LOGGER.info("Player created with id %d", newId);
            res.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private boolean isUserAuthorized(HttpServletRequest req, int teamId) throws SQLException {
        if (!SessionHelpers.isLogged(req))
            return false;

        LOGGER.info("User logged");
        int userId = SessionHelpers.getId(req);
        GetTeamDAO getTeamDAO = new GetTeamDAO(getConnection(), teamId);
        Team team = (Team) getTeamDAO.access().getOutputParam();

        if (team.getCreatorUserId() != userId)
            return false;

        LOGGER.info("User authorized");
        return true;
    }

    @Override
    public Result handle(Method method, HttpServletRequest req, HttpServletResponse res,
            String[] params) throws ServletException, IOException {

            LogContext.setIPAddress(req.getRemoteAddr());

            int teamId = Integer.parseInt(params[0]);

            try {
                switch (method) {
                    case GET:
                        getPlayersFromTeam(req, res, teamId);
                        break;
                    case POST:
                        if (!isUserAuthorized(req, teamId)) {
                            LOGGER.info("User unauthorized");
                            res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                            return Result.STOP;
                        }
                        postPlayer(req, res, teamId);
                        break;
                    default:
                        res.sendError(HttpServletResponse.SC_NOT_FOUND, "Method not found");
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

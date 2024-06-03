package it.unipd.dei.dam.awesometournament.servlet.handler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import it.unipd.dei.dam.awesometournament.servlet.RestMatcherHandler;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.*;
import it.unipd.dei.dam.awesometournament.utils.BodyTools;
import it.unipd.dei.dam.awesometournament.utils.ResponsePackageNoData;
import it.unipd.dei.dam.awesometournament.utils.ResponsePackage;
import it.unipd.dei.dam.awesometournament.utils.ResponseStatus;
import it.unipd.dei.dam.awesometournament.utils.SessionHelpers;
import it.unipd.dei.dam.awesometournament.database.CreateTeamPlayerDAO;
import it.unipd.dei.dam.awesometournament.database.GetTeamDAO;
import it.unipd.dei.dam.awesometournament.database.GetTeamPlayersDAO;
import it.unipd.dei.dam.awesometournament.database.GetTournamentByIdDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Player;
import it.unipd.dei.dam.awesometournament.resources.entities.Team;
import it.unipd.dei.dam.awesometournament.resources.entities.Tournament;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

/**
 * Handles requests related to players within a team.
 */
public class TeamPlayerHandler extends RestMatcherHandler {

    /**
     * Logger for logging messages related to player handling.
     */
    protected final static Logger LOGGER = LogManager.getLogger(PlayerHandler.class,
            StringFormatterMessageFactory.INSTANCE);

    /**
     * Object mapper for JSON serialization/deserialization.
     */
    ObjectMapper om;

    /**
     * Response package for sending responses.
     */
    ResponsePackageNoData response;

    /**
     * Retrieves the list of players belonging to a specific team.
     *
     * @param req The HTTP request object.
     * @param res The HTTP response object.
     * @param teamId The ID of the team.
     * @throws ServletException If there is a servlet-related problem.
     * @throws IOException If there is an I/O problem.
     * @throws SQLException If there is an SQL-related problem.
     */
    void getPlayersFromTeam (HttpServletRequest req, HttpServletResponse res, int teamId) throws ServletException, IOException, SQLException{
        LogContext.setAction(Actions.GET_TEAM_PLAYER);
        LOGGER.info("Received GET request");
        GetTeamPlayersDAO getTeamPlayerDAO = new GetTeamPlayersDAO(getConnection(), teamId);
        ArrayList<Player> players = getTeamPlayerDAO.access().getOutputParam();
        if (players.size() != 0) {
            om.setDateFormat(new StdDateFormat());
            response = new ResponsePackage<ArrayList<Player>>
                    (players, ResponseStatus.OK, "Players found");
            res.getWriter().print(om.writeValueAsString(response));
        } else {
            res.sendError(HttpServletResponse.SC_NOT_FOUND, "No players in team " + teamId);
            response = new ResponsePackageNoData
                    (ResponseStatus.NOT_FOUND, "No players in team " + teamId);
            res.getWriter().print(om.writeValueAsString(response));
        }
    }

    /**
     * Adds a player to a specific team.
     *
     * @param req The HTTP request object.
     * @param res The HTTP response object.
     * @param teamId The ID of the team.
     * @throws ServletException If there is a servlet-related problem.
     * @throws IOException If there is an I/O problem.
     * @throws SQLException If there is an SQL-related problem.
     */
    void postPlayer (HttpServletRequest req, HttpServletResponse res, int teamId) throws ServletException, IOException, SQLException{
        LogContext.setAction(Actions.POST_TEAM_PLAYER);
        LOGGER.info("Received POST request");
        String requestBody = BodyTools.getRequestBody(req);
        LOGGER.info(requestBody);
        om.setDateFormat(new StdDateFormat());
        Player player = null;
        try {
            player = (Player) om.readValue(requestBody, Player.class);
        } catch (InvalidFormatException e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            response = new ResponsePackageNoData
                    (ResponseStatus.BAD_REQUEST,
                            "Something went wrong");
            res.getWriter().print(om.writeValueAsString(response));
            return;
        }
        player.setTeamId(teamId);
        LOGGER.info(player.toString());
        CreateTeamPlayerDAO createTeamPlayerDAO = new CreateTeamPlayerDAO(getConnection(), player);
        Integer newId = (Integer) createTeamPlayerDAO.access().getOutputParam();
        if (newId != null) {
            LOGGER.info("Player created with id %d", newId);
            response = new ResponsePackage<Player>
                    (player, ResponseStatus.CREATED, "Player created");
            res.getWriter().print(om.writeValueAsString(response));
        } else {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response = new ResponsePackageNoData
                    (ResponseStatus.INTERNAL_SERVER_ERROR,
                            "Something went wrong");
            res.getWriter().print(om.writeValueAsString(response));
        }
    }

    /**
     * Checks if the user is logged and authorized to add a player to the team.
     *
     * @param req The HTTP request object.
     * @param teamId The ID of the team.
     * @return True if the user is authorized, false otherwise.
     * @throws SQLException If there is an SQL-related problem.
     */
    private boolean isUserAuthorized(HttpServletRequest req, int teamId) throws SQLException {
        if (!SessionHelpers.isLogged(req))
            return false;

        LOGGER.info("User logged");
        int userId = SessionHelpers.getId(req);
        GetTeamDAO getTeamDAO = new GetTeamDAO(getConnection(), teamId);
        Team team = (Team) getTeamDAO.access().getOutputParam();
        GetTournamentByIdDAO getTournamentByIdDAO = new GetTournamentByIdDAO(getConnection(), team.getTournamentId());
        Tournament tournament = (Tournament) getTournamentByIdDAO.access().getOutputParam();

        if (team.getCreatorUserId() != userId && tournament.getCreatorUserId() != userId)
            return false;

        LOGGER.info("User authorized");
        return true;
    }

    /**
     * Handles incoming HTTP requests for player-related operations within a team.
     *
     * @param method The HTTP method of the request.
     * @param req The HTTP request object.
     * @param res The HTTP response object.
     * @param params Additional parameters extracted from the URL.
     * @throws ServletException If there is a servlet-related problem.
     * @throws IOException If there is an I/O problem.
     */
    @Override
    public Result handle(Method method, HttpServletRequest req, HttpServletResponse res,
            String[] params) throws ServletException, IOException {

            LogContext.setIPAddress(req.getRemoteAddr());
            om = new ObjectMapper();
            om.setDateFormat(new StdDateFormat());

            int teamId = Integer.parseInt(params[0]);

            try {
                switch (method) {
                    case GET:
                        getPlayersFromTeam(req, res, teamId);
                        break;
                    case POST:
                        if (!isUserAuthorized(req, teamId)) {
                            LOGGER.info("User unauthorized");
                            res.sendError(HttpServletResponse.SC_FORBIDDEN);
                            response = new ResponsePackageNoData(ResponseStatus.FORBIDDEN,
                                    "User unauthorized");
                            res.getWriter().print(om.writeValueAsString(response));
                            return Result.STOP;
                        }
                        postPlayer(req, res, teamId);
                        break;
                    default:
                        res.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                        response = new ResponsePackageNoData(ResponseStatus.METHOD_NOT_ALLOWED,
                                "Method not allowed");
                        res.getWriter().print(om.writeValueAsString(response));
                        return Result.STOP;
                }
            } catch (NumberFormatException e) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                response = new ResponsePackageNoData
                        (ResponseStatus.BAD_REQUEST, "ID must be an integer");
                res.getWriter().print(om.writeValueAsString(response));
            } catch (SQLException e) {
                res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response = new ResponsePackageNoData
                        (ResponseStatus.INTERNAL_SERVER_ERROR, "Something went wrong: " + e.getMessage());
                res.getWriter().print(om.writeValueAsString(response));
            }
            return Result.CONTINUE;
        }
}

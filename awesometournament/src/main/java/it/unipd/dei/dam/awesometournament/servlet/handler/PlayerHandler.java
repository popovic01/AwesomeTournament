package it.unipd.dei.dam.awesometournament.servlet.handler;

import java.io.IOException;
import java.sql.SQLException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import it.unipd.dei.dam.awesometournament.servlet.RestMatcherHandler;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.*;
import it.unipd.dei.dam.awesometournament.database.DeletePlayerDAO;
import it.unipd.dei.dam.awesometournament.database.GetPlayerDAO;
import it.unipd.dei.dam.awesometournament.database.GetTeamDAO;
import it.unipd.dei.dam.awesometournament.database.GetTournamentByIdDAO;
import it.unipd.dei.dam.awesometournament.database.UpdatePlayerDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Player;
import it.unipd.dei.dam.awesometournament.resources.entities.Team;
import it.unipd.dei.dam.awesometournament.resources.entities.Tournament;
import it.unipd.dei.dam.awesometournament.utils.BodyTools;
import it.unipd.dei.dam.awesometournament.utils.ResponsePackage;
import it.unipd.dei.dam.awesometournament.utils.ResponsePackageNoData;
import it.unipd.dei.dam.awesometournament.utils.ResponseStatus;
import it.unipd.dei.dam.awesometournament.utils.SessionHelpers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

/**
 * Handles requests related to players.
 */
public class PlayerHandler extends RestMatcherHandler{

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
     * Retrieves information about a specific player.
     *
     * @param req The HTTP request object.
     * @param res The HTTP response object.
     * @param playerId The ID of the player to retrieve.
     * @throws ServletException If there is a servlet-related problem.
     * @throws IOException If there is an I/O problem.
     * @throws SQLException If there is an SQL-related problem.
     */
    void getPlayer (HttpServletRequest req, HttpServletResponse res, int playerId) throws ServletException, IOException, SQLException{
        LogContext.setAction(Actions.GET_PLAYER);
        LOGGER.info("Received GET request");
        GetPlayerDAO getPlayerDAO = new GetPlayerDAO(getConnection(), playerId);
        Player player = (Player) getPlayerDAO.access().getOutputParam();
        if (player != null) {
            response = new ResponsePackage<Player>(player, ResponseStatus.OK,
                    "Player found");
            res.getWriter().print(om.writeValueAsString(response));
        } else {
            res.sendError(HttpServletResponse.SC_NOT_FOUND, "The player doesn't exist");
            response = new ResponsePackageNoData(ResponseStatus.NOT_FOUND,
                    "Player not found");
            res.getWriter().print(om.writeValueAsString(response));
        }
    }

    /**
     * Updates information about a specific player.
     *
     * @param req The HTTP request object.
     * @param res The HTTP response object.
     * @param playerId The ID of the player to update.
     * @throws ServletException If there is a servlet-related problem.
     * @throws IOException If there is an I/O problem.
     * @throws SQLException If there is an SQL-related problem.
     */
    void putPlayer (HttpServletRequest req, HttpServletResponse res, int playerId) throws ServletException, IOException, SQLException{
        LogContext.setAction(Actions.PUT_PLAYER);
        LOGGER.info("Received PUT request");
        String requestBody = BodyTools.getRequestBody(req);
        LOGGER.info(requestBody);
        Player player = (Player) om.readValue(requestBody, Player.class);
        player.setId(playerId);

        // otherwise medical certificate becomes null!
        GetPlayerDAO getPlayerDAO = new GetPlayerDAO(getConnection(), playerId);
        player.setMedicalCertificate(((Player)getPlayerDAO.access().getOutputParam()).getMedicalCertificate());

        LOGGER.info(player.toString());
        UpdatePlayerDAO updatePlayerDAO = new UpdatePlayerDAO(getConnection(), player);
        Integer result = (Integer) updatePlayerDAO.access().getOutputParam();
        if (result == 1) {
            response = new ResponsePackageNoData(ResponseStatus.OK,
                    "Player updated");
            res.getWriter().print(om.writeValueAsString(response));
        } else {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response = new ResponsePackageNoData(ResponseStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong");
            res.getWriter().print(om.writeValueAsString(response));
        }
    }

    /**
     * Deletes a specific player.
     *
     * @param req The HTTP request object.
     * @param res The HTTP response object.
     * @param playerId The ID of the player to delete.
     * @throws ServletException If there is a servlet-related problem.
     * @throws IOException If there is an I/O problem.
     * @throws SQLException If there is an SQL-related problem.
     */
    void deletePlayer (HttpServletRequest req, HttpServletResponse res, int playerId) throws ServletException, IOException, SQLException{
        LogContext.setAction(Actions.DELETE_PLAYER);
        LOGGER.info("Received DELETE request");
        DeletePlayerDAO deletePlayerDAO = new DeletePlayerDAO(getConnection(), playerId);
        Integer result = (Integer) deletePlayerDAO.access().getOutputParam();
        if (result == 1) {
            response = new ResponsePackageNoData(ResponseStatus.OK,
                    "Player deleted");
            res.getWriter().print(om.writeValueAsString(response));
        } else {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response = new ResponsePackageNoData(ResponseStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong");
            res.getWriter().print(om.writeValueAsString(response));
        }
    }

    /**
     * Checks if the user is logged and authorized to perform actions on a player.
     *
     * @param req The HTTP request object.
     * @param playerId The ID of the player.
     * @return True if the user is authorized, false otherwise.
     * @throws SQLException If there is an SQL-related problem.
     */
    private boolean isUserAuthorized(HttpServletRequest req, int playerId) throws SQLException {
        if (!SessionHelpers.isLogged(req))
            return false;

        LOGGER.info("User logged");
        int userId = SessionHelpers.getId(req);
        GetPlayerDAO getPlayerDAO = new GetPlayerDAO(getConnection(), playerId);
        Player player = (Player) getPlayerDAO.access().getOutputParam();
        GetTeamDAO getTeamDAO = new GetTeamDAO(getConnection(), player.getTeamId());
        Team team = (Team) getTeamDAO.access().getOutputParam();
        GetTournamentByIdDAO getTournamentByIdDAO = new GetTournamentByIdDAO(getConnection(), team.getTournamentId());
        Tournament tournament = (Tournament) getTournamentByIdDAO.access().getOutputParam();

        if (team.getCreatorUserId() != userId && tournament.getCreatorUserId() != userId)
            return false;

        LOGGER.info("User authorized");
        return true;
    }

    /**
     * Handles incoming HTTP requests for player-related operations.
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

            int playerId = Integer.parseInt(params[0]);

            try {
                switch (method) {
                    case GET:
                        getPlayer(req, res, playerId);
                        break;
                    case PUT:
                        if (!isUserAuthorized(req, playerId)) {
                            LOGGER.info("User unauthorized");
                            res.sendError(HttpServletResponse.SC_FORBIDDEN);
                            response = new ResponsePackageNoData(ResponseStatus.FORBIDDEN,
                                    "User unauthorized");
                            res.getWriter().print(om.writeValueAsString(response));
                            return Result.STOP;
                        }
                        putPlayer(req, res, playerId);
                        break;
                    case DELETE:
                        if (!isUserAuthorized(req, playerId)) {
                            LOGGER.info("User unauthorized");
                            res.sendError(HttpServletResponse.SC_FORBIDDEN);
                            response = new ResponsePackageNoData(ResponseStatus.FORBIDDEN,
                                    "User unauthorized");
                            res.getWriter().print(om.writeValueAsString(response));
                            return Result.STOP;
                        }
                        deletePlayer(req, res, playerId);
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
                response = new ResponsePackageNoData(ResponseStatus.BAD_REQUEST,
                        "ID must be an integer");
                res.getWriter().print(om.writeValueAsString(response));
            } catch (SQLException e) {
                res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response = new ResponsePackageNoData(ResponseStatus.INTERNAL_SERVER_ERROR,
                        "Something went wrong: " + e.getMessage());
                res.getWriter().print(om.writeValueAsString(response));
            }
            return Result.CONTINUE;
        }
}

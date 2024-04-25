package it.unipd.dei.dam.awesometournament.servlet.handler;

import java.io.IOException;
import java.sql.SQLException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.unipd.dei.dam.awesometournament.servlet.RestMatcherHandler;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.*;
import it.unipd.dei.dam.awesometournament.database.DeletePlayerDAO;
import it.unipd.dei.dam.awesometournament.database.GetPlayerDAO;
import it.unipd.dei.dam.awesometournament.database.GetTeamDAO;
import it.unipd.dei.dam.awesometournament.database.UpdatePlayerDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Player;
import it.unipd.dei.dam.awesometournament.resources.entities.Team;
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

public class PlayerHandler extends RestMatcherHandler{
    protected final static Logger LOGGER = LogManager.getLogger(PlayerHandler.class,
            StringFormatterMessageFactory.INSTANCE);
    ObjectMapper om;
    ResponsePackageNoData response;

    void getPlayer (HttpServletRequest req, HttpServletResponse res, int playerId) throws ServletException, IOException, SQLException{
        LogContext.setAction(Actions.GET_PLAYER);
        LOGGER.info("Received GET request");
        GetPlayerDAO getPlayerDAO = new GetPlayerDAO(getConnection(), playerId);
        Player player = (Player) getPlayerDAO.access().getOutputParam();
        if (player != null) {
            response = new ResponsePackage<>(player, ResponseStatus.OK,
                    "Player found");
            res.getWriter().print(om.writeValueAsString(response));
        } else {
            response = new ResponsePackageNoData(ResponseStatus.NOT_FOUND,
                    "Player not found");
            res.getWriter().print(om.writeValueAsString(response));
        }
    }
    
    void putPlayer (HttpServletRequest req, HttpServletResponse res, int playerId) throws ServletException, IOException, SQLException{
        LogContext.setAction(Actions.PUT_PLAYER);
        LOGGER.info("Received PUT request");
        String requestBody = BodyTools.getRequestBody(req);
        LOGGER.info(requestBody);
        Player player = (Player) om.readValue(requestBody, Player.class);
        player.setId(playerId);
        LOGGER.info(player.toString());
        UpdatePlayerDAO updatePlayerDAO = new UpdatePlayerDAO(getConnection(), player);
        Integer result = (Integer) updatePlayerDAO.access().getOutputParam();
        if (result == 1) {
            response = new ResponsePackageNoData(ResponseStatus.OK,
                    "Player updated");
            res.getWriter().print(om.writeValueAsString(response));
        } else {
            response = new ResponsePackageNoData(ResponseStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong");
            res.getWriter().print(om.writeValueAsString(response));
        }
    }

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
            response = new ResponsePackageNoData(ResponseStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong");
            res.getWriter().print(om.writeValueAsString(response));
        }
    }

    private boolean isUserAuthorized(HttpServletRequest req, int playerId) throws SQLException {
        if (!SessionHelpers.isLogged(req))
            return false;

        LOGGER.info("User logged");
        int userId = SessionHelpers.getId(req);
        GetPlayerDAO getPlayerDAO = new GetPlayerDAO(getConnection(), playerId);
        Player player = (Player) getPlayerDAO.access().getOutputParam();
        GetTeamDAO getTeamDAO = new GetTeamDAO(getConnection(), player.getTeamId());
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
            om = new ObjectMapper();

            int playerId = Integer.parseInt(params[0]);

            try {
                switch (method) {
                    case GET:
                        getPlayer(req, res, playerId);
                        break;
                    case PUT:
                        if (!isUserAuthorized(req, playerId)) {
                            LOGGER.info("User unauthorized");
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
                            response = new ResponsePackageNoData(ResponseStatus.FORBIDDEN,
                                    "User unauthorized");
                            res.getWriter().print(om.writeValueAsString(response));                            return Result.STOP;
                        }
                        deletePlayer(req, res, playerId);
                        break;
                    default:
                        response = new ResponsePackageNoData(ResponseStatus.METHOD_NOT_ALLOWED,
                                "Method not allowed");
                        res.getWriter().print(om.writeValueAsString(response));
                        return Result.STOP;
                }
            } catch (NumberFormatException e) {
                response = new ResponsePackageNoData(ResponseStatus.BAD_REQUEST,
                        "ID must be an integer");
                res.getWriter().print(om.writeValueAsString(response));
            } catch (SQLException e) {
                response = new ResponsePackageNoData(ResponseStatus.INTERNAL_SERVER_ERROR,
                        "Something went wrong: " + e.getMessage());
                res.getWriter().print(om.writeValueAsString(response));
            }
            return Result.CONTINUE;
        }
}

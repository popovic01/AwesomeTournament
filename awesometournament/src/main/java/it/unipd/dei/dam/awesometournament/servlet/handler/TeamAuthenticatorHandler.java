package it.unipd.dei.dam.awesometournament.servlet.handler;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.unipd.dei.dam.awesometournament.database.GetTeamDAO;
import it.unipd.dei.dam.awesometournament.database.GetTournamentByIdDAO;
import it.unipd.dei.dam.awesometournament.resources.entities.Team;
import it.unipd.dei.dam.awesometournament.resources.entities.Tournament;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherHandler;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.Method;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.Result;
import it.unipd.dei.dam.awesometournament.utils.ResponsePackageNoData;
import it.unipd.dei.dam.awesometournament.utils.ResponseStatus;
import it.unipd.dei.dam.awesometournament.utils.SessionHelpers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import java.io.IOException;
import java.sql.SQLException;

public class TeamAuthenticatorHandler extends RestMatcherHandler {

    protected static final Logger LOGGER = LogManager.getLogger(TeamAuthenticatorHandler.class,
            StringFormatterMessageFactory.INSTANCE);
    ObjectMapper om;
    ResponsePackageNoData response;
    int loggedUserId;

    @Override
    public Result handle(Method method, HttpServletRequest req, HttpServletResponse res,
                                            String[] params) throws ServletException, IOException {
        om = new ObjectMapper();

        switch (method) {
            case GET:
                return Result.CONTINUE;
            case DELETE:
                if (!SessionHelpers.isLogged(req)) {
                    LOGGER.info("User not logged in");
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    response = new ResponsePackageNoData(ResponseStatus.UNAUTHORIZED, "User not logged in");
                    res.getWriter().print(om.writeValueAsString(response));
                    return Result.STOP;
                }

                loggedUserId = SessionHelpers.getId(req);
                LOGGER.info("User's id is " + loggedUserId);
                try {
                    int id = Integer.parseInt(params[0]);
                    //get the team for delete
                    GetTeamDAO getTeamDAO = new GetTeamDAO(getConnection(), id);
                    Team team = getTeamDAO.access().getOutputParam();
                    //get the tournament to witch the team belongs
                    GetTournamentByIdDAO getTournamentByIdDAO = new GetTournamentByIdDAO(getConnection(), team.getTournamentId());
                    Tournament tournament = getTournamentByIdDAO.access().getOutputParam();
                    //only a creator of the tournament can delete a team
                    if (tournament.getCreatorUserId() != loggedUserId) {
                        LOGGER.info("User not authorized");
                        res.sendError(HttpServletResponse.SC_FORBIDDEN);
                        response = new ResponsePackageNoData(ResponseStatus.FORBIDDEN, "User not authorized");
                        res.getWriter().print(om.writeValueAsString(response));
                        return Result.STOP;
                    }
                    return Result.CONTINUE;

                } catch (SQLException e) {
                    res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response = new ResponsePackageNoData
                            (ResponseStatus.INTERNAL_SERVER_ERROR, "Something went wrong: " + e.getMessage());
                    res.getWriter().print(om.writeValueAsString(response));
                    return Result.STOP;
                }
            case PUT: {
                if (!SessionHelpers.isLogged(req)) {
                    LOGGER.info("User not authorized");
                    res.sendError(HttpServletResponse.SC_FORBIDDEN);
                    response = new ResponsePackageNoData(ResponseStatus.FORBIDDEN, "User not authorized");
                    res.getWriter().print(om.writeValueAsString(response));
                    return Result.STOP;
                }

                loggedUserId = SessionHelpers.getId(req);
                LOGGER.info("User's id is " + loggedUserId);
                try {
                    int id = Integer.parseInt(params[0]);
                    //get the team for update
                    GetTeamDAO getTeamDAO = new GetTeamDAO(getConnection(), id);
                    Team team = getTeamDAO.access().getOutputParam();
                    //get the tournament to witch the team belongs
                    GetTournamentByIdDAO getTournamentByIdDAO = new GetTournamentByIdDAO(getConnection(), team.getTournamentId());
                    Tournament tournament = getTournamentByIdDAO.access().getOutputParam();
                    //only a creator of the team or a creator of the tournament can update a team
                    if (tournament.getCreatorUserId() != loggedUserId
                            && team.getCreatorUserId() != loggedUserId) {
                        LOGGER.info("User not authorized");
                        res.sendError(HttpServletResponse.SC_FORBIDDEN);
                        response = new ResponsePackageNoData(ResponseStatus.FORBIDDEN, "User not authorized");
                        res.getWriter().print(om.writeValueAsString(response));
                        return Result.STOP;
                    }
                    return Result.CONTINUE;

                } catch (SQLException e) {
                    res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response = new ResponsePackageNoData
                            (ResponseStatus.INTERNAL_SERVER_ERROR, "Something went wrong: " + e.getMessage());
                    res.getWriter().print(om.writeValueAsString(response));
                    return Result.STOP;
                }
            }
            default:
                break;
        }
        return Result.STOP;
    }

}

package it.unipd.dei.dam.awesometournament.servlet.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unipd.dei.dam.awesometournament.database.GetTeamDAO;
import it.unipd.dei.dam.awesometournament.database.GetTournamentByIdDAO;
import it.unipd.dei.dam.awesometournament.resources.entities.Team;
import it.unipd.dei.dam.awesometournament.resources.entities.Tournament;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherHandler;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet;
import it.unipd.dei.dam.awesometournament.utils.ResponsePackage;
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
    ObjectMapper om = new ObjectMapper();
    ResponsePackage response;
    int loggedUserId;

    @Override
    public RestMatcherServlet.Result handle(RestMatcherServlet.Method method, HttpServletRequest req, HttpServletResponse res,
                                            String[] params) throws ServletException, IOException {
        switch (method) {
            case GET:
                return RestMatcherServlet.Result.CONTINUE;
            case DELETE:
                if (!SessionHelpers.isLogged(req)) {
                    LOGGER.info("User not logged in");
                    response = new ResponsePackage(ResponseStatus.UNAUTHORIZED, "User not logged in");
                    res.getWriter().print(om.writeValueAsString(response));
                    return RestMatcherServlet.Result.STOP;
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
                    //only a creator the tournament can delete a team
                    if (tournament.getCreatorUserId() != loggedUserId) {
                        LOGGER.info("User not authorized");
                        response = new ResponsePackage(ResponseStatus.FORBIDDEN, "User not authorized");
                        res.getWriter().print(om.writeValueAsString(response));
                        return RestMatcherServlet.Result.STOP;
                    }
                    return RestMatcherServlet.Result.CONTINUE;

                } catch (SQLException e) {
                    response = new ResponsePackage
                            (ResponseStatus.INTERNAL_SERVER_ERROR, "Something went wrong: " + e.getMessage());
                    res.getWriter().print(om.writeValueAsString(response));
                    return RestMatcherServlet.Result.STOP;
                }
            case PUT: {
                if (!SessionHelpers.isLogged(req)) {
                    LOGGER.info("User not authorized");
                    response = new ResponsePackage(ResponseStatus.UNAUTHORIZED, "User not authorized");
                    res.getWriter().print(om.writeValueAsString(response));
                    return RestMatcherServlet.Result.STOP;
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
                        response = new ResponsePackage(ResponseStatus.FORBIDDEN, "User not authorized");
                        res.getWriter().print(om.writeValueAsString(response));
                        return RestMatcherServlet.Result.STOP;
                    }
                    return RestMatcherServlet.Result.CONTINUE;

                } catch (SQLException e) {
                    response = new ResponsePackage
                            (ResponseStatus.INTERNAL_SERVER_ERROR, "Something went wrong: " + e.getMessage());
                    res.getWriter().print(om.writeValueAsString(response));
                    return RestMatcherServlet.Result.STOP;
                }
            }

        }
        return RestMatcherServlet.Result.STOP;
    }

}

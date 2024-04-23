package it.unipd.dei.dam.awesometournament.servlet.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import it.unipd.dei.dam.awesometournament.database.CreateMatchEventDAO;
import it.unipd.dei.dam.awesometournament.database.GetMatchDAO;
import it.unipd.dei.dam.awesometournament.database.GetMatchEventsDAO;
import it.unipd.dei.dam.awesometournament.database.GetTeamDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Event;
import it.unipd.dei.dam.awesometournament.resources.entities.Match;
import it.unipd.dei.dam.awesometournament.resources.entities.Team;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherHandler;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet;
import it.unipd.dei.dam.awesometournament.utils.BodyTools;
import it.unipd.dei.dam.awesometournament.utils.SessionHelpers;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Handles HTTP requests related to match events.
 */
public class MatchEventHandler extends RestMatcherHandler {
    protected final static Logger LOGGER = LogManager.getLogger(TeamPlayerHandler.class,
            StringFormatterMessageFactory.INSTANCE);

    /**
     * Retrieves events associated with a specific match.
     *
     * @param req     The HTTP servlet request object.
     * @param res     The HTTP servlet response object.
     * @param matchId The ID of the match to retrieve events for.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     * @throws SQLException     If a SQL error occurs.
     */
    void getEventsFromMatch (HttpServletRequest req, HttpServletResponse res, int matchId) throws ServletException, IOException, SQLException {
        LogContext.setAction(Actions.GET_MATCH_EVENT);
        LOGGER.info("Received GET request");
        GetMatchEventsDAO getMatchEventDAO = new GetMatchEventsDAO(getConnection(), matchId);
        ArrayList<Event> events = getMatchEventDAO.access().getOutputParam();
        if (events.size() != 0) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setDateFormat(new StdDateFormat());
            res.setContentType("application/json");
            res.getWriter().println(objectMapper.writeValueAsString(events));
        } else {
            res.sendError(HttpServletResponse.SC_NOT_FOUND, "No events in match " + matchId);
        }
    }

    /**
     * Creates a new event for a specific match.
     *
     * @param req     The HTTP servlet request object.
     * @param res     The HTTP servlet response object.
     * @param matchId The ID of the match to create the event for.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     * @throws SQLException     If a SQL error occurs.
     */
    void postEvent (HttpServletRequest req, HttpServletResponse res, int matchId) throws ServletException, IOException, SQLException{
        LogContext.setAction(Actions.POST_MATCH_EVENT);
        LOGGER.info("Received POST request");
        String requestBody = BodyTools.getRequestBody(req);
        LOGGER.info(requestBody);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new StdDateFormat());
        Event event = (Event) objectMapper.readValue(requestBody, Event.class);
        event.setMatchId(matchId);
        LOGGER.info(event.toString());
        CreateMatchEventDAO createMatchEventDAO = new CreateMatchEventDAO(getConnection(), event);
        Integer newId = (Integer) createMatchEventDAO.access().getOutputParam();
        if (newId != null) {
            LOGGER.info("Event created with id %d", newId);
            res.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Checks if the user is authorized to perform actions on the match.
     *
     * @param req     The HTTP servlet request object.
     * @param matchId The ID of the match to check authorization for.
     * @return True if the user is authorized, false otherwise.
     * @throws SQLException If a SQL error occurs.
     */
    private boolean isUserAuthorized(HttpServletRequest req, int matchId) throws SQLException {
        if (!SessionHelpers.isLogged(req))
            return false;

        LOGGER.info("User logged");
        int userId = SessionHelpers.getId(req);
        GetMatchDAO getMatchDAO = new GetMatchDAO(getConnection(), matchId);
        Match match = (Match) getMatchDAO.access().getOutputParam();

        int team1Id = match.getTeam1Id();
        GetTeamDAO getTeamDAO1 = new GetTeamDAO(getConnection(), team1Id);
        Team team1 = (Team) getTeamDAO1.access().getOutputParam();

        int team2Id = match.getTeam2Id();
        GetTeamDAO getTeamDAO2 = new GetTeamDAO(getConnection(), team2Id);
        Team team2 = (Team) getTeamDAO2.access().getOutputParam();

        //Checks if the user is the creator of one of the teams
        if (team1.getCreatorUserId() != userId && team2.getCreatorUserId() != userId)
            return false;

        LOGGER.info("User authorized");
        return true;
    }

    /**
     * Handles HTTP requests for match events.
     *
     * @param method The HTTP method (GET, POST, etc.).
     * @param req    The HTTP servlet request object.
     * @param res    The HTTP servlet response object.
     * @param params An array of parameters extracted from the request URL.
     * @return The result of handling the request.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    @Override
    public RestMatcherServlet.Result handle(RestMatcherServlet.Method method, HttpServletRequest req, HttpServletResponse res,
                                            String[] params) throws ServletException, IOException {

        LogContext.setIPAddress(req.getRemoteAddr());

        int matchId = Integer.parseInt(params[0]);

        try {
            switch (method) {
                case GET:
                    getEventsFromMatch(req, res, matchId);
                    break;
                case POST:
                    if (!isUserAuthorized(req, matchId)) {
                        LOGGER.info("User unauthorized");
                        res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                        return RestMatcherServlet.Result.STOP;
                    }
                    postEvent(req, res, matchId);
                    break;
                default:
                    res.sendError(HttpServletResponse.SC_NOT_FOUND, "Method not found");
                    return RestMatcherServlet.Result.STOP;
            }
        } catch (NumberFormatException e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Match ID must be an integer");
        } catch (SQLException e) {
            res.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }
        return RestMatcherServlet.Result.CONTINUE;
    }
}

package it.unipd.dei.dam.awesometournament.servlet.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import it.unipd.dei.dam.awesometournament.database.CreateMatchEventDAO;
import it.unipd.dei.dam.awesometournament.database.GetMatchDAO;
import it.unipd.dei.dam.awesometournament.database.GetMatchEventsDAO;
import it.unipd.dei.dam.awesometournament.database.GetTeamDAO;
import it.unipd.dei.dam.awesometournament.database.GetTournamentByIdDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Event;
import it.unipd.dei.dam.awesometournament.resources.entities.Match;
import it.unipd.dei.dam.awesometournament.resources.entities.Team;
import it.unipd.dei.dam.awesometournament.resources.entities.Tournament;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherHandler;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.Method;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.Result;
import it.unipd.dei.dam.awesometournament.utils.BodyTools;
import it.unipd.dei.dam.awesometournament.utils.ResponsePackageNoData;
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
import java.util.ArrayList;

/**
 * Handles HTTP requests related to match events.
 */
public class MatchEventHandler extends RestMatcherHandler {
    protected final static Logger LOGGER = LogManager.getLogger(MatchEventHandler.class,
            StringFormatterMessageFactory.INSTANCE);
    ObjectMapper om;
    ResponsePackageNoData response;

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
    void getMatchEvents (HttpServletRequest req, HttpServletResponse res, int matchId) throws ServletException, IOException, SQLException {
        LogContext.setAction(Actions.GET_MATCH_EVENT);
        LOGGER.info("Received GET request");
        GetMatchEventsDAO getMatchEventDAO = new GetMatchEventsDAO(getConnection(), matchId);
        ArrayList<Event> events = getMatchEventDAO.access().getOutputParam();
        if (events.size() != 0) {
            om.setDateFormat(new StdDateFormat());
            response = new ResponsePackage<ArrayList<Event>>(events, ResponseStatus.OK,
                    "Events found");
            res.getWriter().print(om.writeValueAsString(response));
        } else {
            response = new ResponsePackageNoData(ResponseStatus.NOT_FOUND,
                    "No events in match " + matchId);
            res.getWriter().print(om.writeValueAsString(response));
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
        om.setDateFormat(new StdDateFormat());
        Event event = (Event) om.readValue(requestBody, Event.class);
        event.setMatchId(matchId);
        LOGGER.info(event.toString());
        CreateMatchEventDAO createMatchEventDAO = new CreateMatchEventDAO(getConnection(), event);
        Integer newId = (Integer) createMatchEventDAO.access().getOutputParam();
        if (newId != null) {
            LOGGER.info("Event created with id %d", newId);
            response = new ResponsePackage<Event>(event, ResponseStatus.CREATED,
                    "Event created");
            res.getWriter().print(om.writeValueAsString(response));
        } else {
            response = new ResponsePackageNoData(ResponseStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong");
            res.getWriter().print(om.writeValueAsString(response));
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

        GetTournamentByIdDAO getTournamentByIdDAO = new GetTournamentByIdDAO(getConnection(), match.getTournamentId());
        getTournamentByIdDAO.access();
        Tournament tournament = getTournamentByIdDAO.getOutputParam();

        //Checks if the user is the creator of the tournament
        if (tournament.getCreatorUserId() != userId)
            return false;

        LOGGER.info("User authorized");
        return true;
    }

    /**
     * Handles HTTP requests for match events based on the method type.
     *
     * @param method The HTTP method (GET, POST, etc.).
     * @param req    The HttpServletRequest object representing the request.
     * @param res    The HttpServletResponse object representing the response.
     * @param params An array of parameters extracted from the request URI.
     * @return The result of handling the request.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs while processing the request.
     */
    @Override
    public Result handle(Method method, HttpServletRequest req, HttpServletResponse res,
                                            String[] params) throws ServletException, IOException {

        LogContext.setIPAddress(req.getRemoteAddr());
        om = new ObjectMapper();

        int matchId = Integer.parseInt(params[0]);

        try {
            switch (method) {
                case GET:
                    getMatchEvents(req, res, matchId);
                    break;
                case POST:
                    if (!isUserAuthorized(req, matchId)) {
                        LOGGER.info("User unauthorized");
                        response = new ResponsePackageNoData(ResponseStatus.FORBIDDEN,
                                "User unauthorized");
                        res.getWriter().print(om.writeValueAsString(response));
                        return Result.STOP;
                    }
                    postEvent(req, res, matchId);
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
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (InvalidFormatException e) {
            response = new ResponsePackageNoData(ResponseStatus.BAD_REQUEST,
                    e.getMessage());
            res.getWriter().print(om.writeValueAsString(response));
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            response = new ResponsePackageNoData(ResponseStatus.INTERNAL_SERVER_ERROR,
                    "Something in SQL went wrong: " + e.getMessage());
            res.getWriter().print(om.writeValueAsString(response));
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
        } catch (Exception e) {
            e.printStackTrace();
            response = new ResponsePackageNoData(ResponseStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong: " + e.getMessage());
            res.getWriter().print(om.writeValueAsString(response));
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return Result.CONTINUE;
    }
}

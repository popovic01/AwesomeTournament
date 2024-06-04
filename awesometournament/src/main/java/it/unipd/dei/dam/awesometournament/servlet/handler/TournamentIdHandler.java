package it.unipd.dei.dam.awesometournament.servlet.handler;

import java.io.IOException;
import java.sql.SQLException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import it.unipd.dei.dam.awesometournament.database.*;
import it.unipd.dei.dam.awesometournament.resources.entities.Tournament;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherHandler;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.*;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
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

/**
 * Handler class for managing requests related to specific tournaments identified by ID in the REST API.
 * This class handles GET, PUT, and DELETE requests for retrieving, updating, and deleting tournaments.
 */
public class TournamentIdHandler extends RestMatcherHandler {
    /**
     * Logger for logging handler activities.
     */
    protected final static Logger LOGGER = LogManager.getLogger(TournamentIdHandler.class,
            StringFormatterMessageFactory.INSTANCE);
    /**
     * ObjectMapper for JSON serialization and deserialization.
     */
    ObjectMapper om;
    /**
     * Response package for sending responses to clients.
     */
    ResponsePackageNoData response;

    /**
     * Handles HTTP GET requests for retrieving a specific tournament by its ID.
     *
     * @param req         the HttpServletRequest object containing the request parameters and attributes
     * @param res         the HttpServletResponse object for sending the response
     * @param tournamentID the ID of the tournament to retrieve
     * @throws IOException  if an I/O error occurs
     * @throws SQLException if a SQL exception occurs
     */
    void getTournament (HttpServletRequest req, HttpServletResponse res, int tournamentID) throws IOException, SQLException{
        // Setting log context
        LogContext.setAction(Actions.GET_TOURNAMENT);
        LOGGER.info("Received GET request");

        // Retrieving tournament by ID from the database
        GetTournamentByIdDAO getTournamentDAO = new GetTournamentByIdDAO(getConnection(), tournamentID);
        Tournament tournament = getTournamentDAO.access().getOutputParam();

        // Sending response to client
        if (tournament != null) {
            response = new ResponsePackage<Tournament>(tournament, ResponseStatus.OK, "Tournament found");
            res.getWriter().print(om.writeValueAsString(response));
        }
        else {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            response = new ResponsePackageNoData(ResponseStatus.NOT_FOUND, "Tournament not found");
            res.getWriter().print(om.writeValueAsString(response));
        }
    }

    /**
     * Handles HTTP PUT requests for updating a specific tournament identified by its ID.
     *
     * @param req         the HttpServletRequest object containing the request parameters and attributes
     * @param res         the HttpServletResponse object for sending the response
     * @param tournamentId the ID of the tournament to update
     * @throws IOException  if an I/O error occurs
     * @throws SQLException if a SQL exception occurs
     */
    void putTournament (HttpServletRequest req, HttpServletResponse res, int tournamentId) throws IOException, SQLException {
        // Setting log context
        LogContext.setAction(Actions.PUT_TOURNAMENT);
        LOGGER.info("Received PUT request");

        // Retrieving request body
        String requestBody = BodyTools.getRequestBody(req);
        LOGGER.info(requestBody);

        // Deserializing request body to Tournament object
        om.setDateFormat(new StdDateFormat());
        Tournament tournament = om.readValue(requestBody, Tournament.class);
        tournament.setId(tournamentId);

        LOGGER.info(tournament.toString());

        // Updating the tournament in the database
        UpdateTournamentDAO updateTournamentDAO = new UpdateTournamentDAO(getConnection(), tournament);
        Integer result = updateTournamentDAO.access().getOutputParam();

        // Sending response to client
        if (result == 1) {
            response = new ResponsePackageNoData(ResponseStatus.OK, "Tournament updated");
            res.getWriter().print(om.writeValueAsString(response));
        }
        else {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response = new ResponsePackageNoData(ResponseStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
            res.getWriter().print(om.writeValueAsString(response));
        }
    }

    /**
     * Handles HTTP DELETE requests for deleting a specific tournament identified by its ID.
     *
     * @param req         the HttpServletRequest object containing the request parameters and attributes
     * @param res         the HttpServletResponse object for sending the response
     * @param tournamentId the ID of the tournament to delete
     * @throws IOException  if an I/O error occurs
     * @throws SQLException if a SQL exception occurs
     */
    void deleteTournament (HttpServletRequest req, HttpServletResponse res, int tournamentId) throws IOException, SQLException{
        // Setting log context
        LogContext.setAction(Actions.DELETE_TOURNAMENT);
        LOGGER.info("Received DELETE request");

        // Deleting the tournament from the database
        DeleteTournamentDAO deleteTournamentDAO = new DeleteTournamentDAO(getConnection(), tournamentId);
        Integer result = deleteTournamentDAO.access().getOutputParam();

        // Sending response to client
        if (result == 1) {
            response = new ResponsePackageNoData(ResponseStatus.OK, "Tournament deleted");
            res.getWriter().print(om.writeValueAsString(response));
        }
        else {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response = new ResponsePackageNoData(ResponseStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
            res.getWriter().print(om.writeValueAsString(response));
        }
    }

    /**
     * Checks if the user is authorized to perform an operation on a specific tournament.
     *
     * @param req         the HttpServletRequest object containing the request parameters and attributes
     * @param tournamentId the ID of the tournament on which the user wants to perform the operation
     * @return true if the user is authorized, false otherwise
     * @throws SQLException if an SQL exception occurs while accessing the database
     */
    private boolean isUserAuthorized(HttpServletRequest req, int tournamentId) throws SQLException {
        // Check if the user is authenticated
        if (!SessionHelpers.isLogged(req)) return false;
        LOGGER.info("User logged");

        // Get the ID of the authenticated user
        int userId = SessionHelpers.getId(req);

        // Retrieve tournament information from the database using the provided ID
        GetTournamentByIdDAO getTournamentByIdDAO = new GetTournamentByIdDAO(getConnection(), tournamentId);
        Tournament tournament = getTournamentByIdDAO.access().getOutputParam();

        // Check if the user is the creator of the tournament
        if (tournament.getCreatorUserId() != userId) return false;

        // The user is authorized because they are the creator of the tournament
        LOGGER.info("User authorized");
        return true;
    }

    /**
     * Handles incoming HTTP requests and delegates to appropriate methods based on the request method.
     *
     * @param method the HTTP request method (GET, PUT, DELETE, etc.)
     * @param req    the HttpServletRequest object containing the request parameters and attributes
     * @param res    the HttpServletResponse object for sending the response
     * @param params an array of parameters extracted from the request URL
     * @return the Result enum indicating whether the request should continue or stop processing
     * @throws ServletException if an exception occurs during servlet processing
     * @throws IOException      if an I/O error occurs
     */
    @Override
    public Result handle(Method method, HttpServletRequest req, HttpServletResponse res,
                         String[] params) throws ServletException, IOException {

        // Setting log context
        LogContext.setIPAddress(req.getRemoteAddr());
        om = new ObjectMapper();
        om = om.registerModule(new JavaTimeModule());
        int tournamentId = Integer.parseInt(params[0]);

        try {
            switch (method) {
                // Handling request based on method
                case GET:
                    getTournament(req, res, tournamentId);
                    break;
                case PUT:
                    // Checking user authorization
                    if (!isUserAuthorized(req, tournamentId)) {
                        LOGGER.info("User unauthorized");
                        res.sendError(HttpServletResponse.SC_FORBIDDEN);
                        response = new ResponsePackageNoData(ResponseStatus.FORBIDDEN, "User unauthorized");
                        res.getWriter().print(om.writeValueAsString(response));
                        return Result.STOP;
                    }
                    putTournament(req, res, tournamentId);
                    break;
                case DELETE:
                    // Checking user authorization
                    if (!isUserAuthorized(req, tournamentId)) {
                        LOGGER.info("User unauthorized");
                        res.sendError(HttpServletResponse.SC_FORBIDDEN);
                        response = new ResponsePackageNoData(ResponseStatus.FORBIDDEN, "User unauthorized");
                        res.getWriter().print(om.writeValueAsString(response));
                        return Result.STOP;
                    }
                    deleteTournament(req, res, tournamentId);
                    break;
                default:
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
                    "Something went wrong" + e.getMessage());
            res.getWriter().print(om.writeValueAsString(response));
        }
        return Result.CONTINUE;
    }
}
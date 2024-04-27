package it.unipd.dei.dam.awesometournament.servlet.handler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import it.unipd.dei.dam.awesometournament.database.*;
import it.unipd.dei.dam.awesometournament.resources.entities.Tournament;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherHandler;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.*;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.utils.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

/**
 * Handler class for managing tournament-related requests in the REST API.
 * This class handles POST and GET requests for creating and retrieving tournaments.
 */
public class TournamentHandler extends RestMatcherHandler {
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
     */ResponsePackageNoData response;

    /**
     * Handles HTTP POST requests for creating a new tournament.
     *
     * @param req the HttpServletRequest object containing the request parameters and attributes
     * @param res the HttpServletResponse object for sending the response
     * @throws IOException  if an I/O error occurs
     * @throws SQLException if a SQL exception occurs
     */
     void postTournament (HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException{
         // Setting log context
         LogContext.setAction(Actions.POST_TOURNAMENT);
         LOGGER.info("Received POST request");

         // Retrieving request body
         String requestBody = BodyTools.getRequestBody(req);
         LOGGER.info(requestBody);

         // Deserializing request body to Tournament object
         om.setDateFormat(new StdDateFormat());
         Tournament tournament = om.readValue(requestBody, Tournament.class);
         LOGGER.info(tournament.toString());

         // Creating a new tournament in the database
         CreateTournamentDAO createTournamentDAO = new CreateTournamentDAO(getConnection(), tournament);
         Integer newId = createTournamentDAO.access().getOutputParam();

         // Sending response to client
         if (newId != null) {
             LOGGER.info("Tournament created with id %d", newId);
             response = new ResponsePackage<Tournament>(tournament, ResponseStatus.CREATED, "Tournament created");
             res.getWriter().print(om.writeValueAsString(response));
         }
         else {
             response = new ResponsePackageNoData(ResponseStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
             res.getWriter().print(om.writeValueAsString(response));
         }
     }

    /**
     * Handles HTTP GET requests for retrieving all tournaments.
     *
     * @param req the HttpServletRequest object containing the request parameters and attributes
     * @param res the HttpServletResponse object for sending the response
     * @throws IOException  if an I/O error occurs
     * @throws SQLException if a SQL exception occurs
     */
    void getTournament (HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException{
        // Setting log context
        LogContext.setAction(Actions.GET_TOURNAMENT);
        LOGGER.info("Received GET request");

        // Retrieving all tournaments from the database
        GetTournamentsDAO getTournamentDAO = new GetTournamentsDAO(getConnection());
        List<Tournament> tournaments = getTournamentDAO.access().getOutputParam();

        // Sending response to client
        if (!tournaments.isEmpty()) {
            om.setDateFormat(new StdDateFormat());
            response = new ResponsePackage<List<Tournament>>(tournaments, ResponseStatus.OK, "Tournaments found");
            res.getWriter().print(om.writeValueAsString(response));
        }
        else {
            LOGGER.info("No tournaments in the database");
            response = new ResponsePackageNoData(ResponseStatus.NOT_FOUND, "No tournaments in the database");
            res.getWriter().print(om.writeValueAsString(response));
        }
    }

    /**
     * Handles incoming HTTP requests and delegates to appropriate methods based on the request method.
     *
     * @param method the HTTP request method (GET, POST, etc.)
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

        try {
            // Handling request based on method
            switch (method) {
                case GET:
                    getTournament(req, res);
                    break;
                case POST:
                    postTournament(req, res);
                    break;
                default:
                    response = new ResponsePackageNoData(ResponseStatus.METHOD_NOT_ALLOWED, "Method not allowed");
                    res.getWriter().print(om.writeValueAsString(response));
                    return Result.STOP;
            }
        } catch (NumberFormatException e) {
            response = new ResponsePackageNoData(ResponseStatus.BAD_REQUEST, "ID must be an integer");
            res.getWriter().print(om.writeValueAsString(response));
        } catch (SQLException e) {
            response = new ResponsePackageNoData(ResponseStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong: " + e.getMessage());
            res.getWriter().print(om.writeValueAsString(response));
        }
        return Result.CONTINUE;
    }
}
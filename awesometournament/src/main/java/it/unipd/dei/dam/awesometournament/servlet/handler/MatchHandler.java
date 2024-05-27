package it.unipd.dei.dam.awesometournament.servlet.handler;

import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import it.unipd.dei.dam.awesometournament.database.GetMatchDAO;
import it.unipd.dei.dam.awesometournament.database.UpdateMatchDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Match;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherHandler;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.Method;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.Result;
import it.unipd.dei.dam.awesometournament.utils.BodyTools;

import it.unipd.dei.dam.awesometournament.utils.ResponsePackage;
import it.unipd.dei.dam.awesometournament.utils.ResponsePackageNoData;
import it.unipd.dei.dam.awesometournament.utils.ResponseStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

/**
 * Handles HTTP requests related to matches.
 */
public class MatchHandler extends RestMatcherHandler{

    protected final static Logger LOGGER = LogManager.getLogger(PlayerHandler.class,
            StringFormatterMessageFactory.INSTANCE);
    ObjectMapper om;
    ResponsePackageNoData response;

    /**
     * Handles the request based on the provided HTTP method.
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
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        int matchId = Integer.parseInt(params[0]);

        try {
            switch (method) {
                case GET:
                    getMatch(req, res, matchId);
                    break;
                case PUT:
                    updateMatch(req, res, matchId);
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
                    "Something went wrong: " + e.getMessage());
            res.getWriter().print(om.writeValueAsString(response));
        }
        return Result.CONTINUE;
    }

    /**
     * Retrieves a match.
     *
     * @param req     The HTTP servlet request object.
     * @param res     The HTTP servlet response object.
     * @param matchId The ID of the match to retrieve
     * @throws SQLException            If a database error occurs
     * @throws JsonProcessingException If an error occurs during JSON processing
     * @throws IOException             If an I/O error occurs
     */
    private void getMatch(HttpServletRequest req, HttpServletResponse res, int matchId)
            throws SQLException, JsonProcessingException, IOException {
        LogContext.setAction(Actions.GET_MATCH);
        LOGGER.info("Received GET request");
        om.setDateFormat(new StdDateFormat());

        GetMatchDAO getMatchDAO = new GetMatchDAO(getConnection(), matchId);
        Match match = (Match) getMatchDAO.access().getOutputParam();

        if (match != null) {
            res.getWriter().println(om.writeValueAsString(match));
            response = new ResponsePackage<Match>(match, ResponseStatus.OK,
                    "Match found");
        } else {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            res.getWriter().println(om.writeValueAsString(match));
            response = new ResponsePackageNoData(ResponseStatus.NOT_FOUND,
                    "Match not found");
        }
    }

    /**
     * Updates a match.
     *
     * @param req     The HTTP servlet request object.
     * @param res     The HTTP servlet response object.
     * @param matchId The ID of the match to update
     * @throws SQLException            If a database error occurs
     * @throws JsonProcessingException If an error occurs during JSON processing
     * @throws IOException             If an I/O error occurs
     */
    private void updateMatch(HttpServletRequest req, HttpServletResponse res, int matchId)
            throws SQLException, JsonProcessingException, IOException {
        LogContext.setAction(Actions.PUT_MATCH);
        LOGGER.info("Received PUT request");
        om.setDateFormat(new StdDateFormat());

        String requestBody = BodyTools.getRequestBody(req);
        LOGGER.info(requestBody);

        Match match = (Match) om.readValue(requestBody, Match.class);
        match.setId(matchId);
        match.setResult(null);
        LOGGER.info(match.toString());

        UpdateMatchDAO updateMatchDAO = new UpdateMatchDAO(getConnection(), match);
        Integer result = (Integer) updateMatchDAO.access().getOutputParam();

        if (result == 1) {
            response = new ResponsePackageNoData(ResponseStatus.OK,
                    "Match updated");
            res.getWriter().println(om.writeValueAsString(response));
        } else {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response = new ResponsePackageNoData(ResponseStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong");
            res.getWriter().println(om.writeValueAsString(response));
        }
    }
}

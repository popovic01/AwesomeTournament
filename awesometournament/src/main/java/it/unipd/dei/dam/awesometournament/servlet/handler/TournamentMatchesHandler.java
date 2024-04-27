package it.unipd.dei.dam.awesometournament.servlet.handler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import it.unipd.dei.dam.awesometournament.utils.ResponsePackageNoData;
import it.unipd.dei.dam.awesometournament.utils.ResponsePackage;
import it.unipd.dei.dam.awesometournament.utils.ResponseStatus;
import it.unipd.dei.dam.awesometournament.database.GetTournamentMatchesByIsFinishedDAO;
import it.unipd.dei.dam.awesometournament.database.GetTournamentMatchesDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Match;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherHandler;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.Method;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.Result;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Handles HTTP requests related to tournament matches.
 * Extends the RestMatcherHandler class.
 */
public class TournamentMatchesHandler extends RestMatcherHandler {
    protected final static Logger LOGGER = LogManager.getLogger(PlayerHandler.class,
            StringFormatterMessageFactory.INSTANCE);
    ObjectMapper om;
    ResponsePackageNoData response;

    /**
     * Handles HTTP requests for tournament matches.
     *
     * @param method   The HTTP method of the request.
     * @param req      The HttpServletRequest object representing the request.
     * @param res      The HttpServletResponse object representing the response.
     * @param params   An array of String objects representing request parameters.
     * @return Result.CONTINUE if the operation should continue, Result.STOP otherwise.
     * @throws ServletException If an exception occurs while processing the request.
     * @throws IOException      If an I/O exception occurs while processing the request.
     */
    @Override
    public Result handle(Method method, HttpServletRequest req, HttpServletResponse res,
            String[] params) throws ServletException, IOException {
        
            LogContext.setIPAddress(req.getRemoteAddr());
            om = new ObjectMapper();

            int tournamentId = Integer.parseInt(params[0]);
            try {
                switch (method) {
                    case GET:
                        getTournamentMatches(req, res, tournamentId);
                        break;
                    default: {
                        response = new ResponsePackageNoData(ResponseStatus.METHOD_NOT_ALLOWED,
                                "Method not allowed");
                        res.getWriter().print(om.writeValueAsString(response));
                        return Result.STOP;
                    }
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

    /**
     * Retrieves tournament matches.
     *
     * @param req         The HttpServletRequest object representing the request.
     * @param res         The HttpServletResponse object representing the response.
     * @param tournamentId The ID of the tournament for which matches are to be retrieved.
     * @throws ServletException If an exception occurs while processing the request.
     * @throws IOException      If an I/O exception occurs while processing the request.
     * @throws SQLException     If a database access error occurs while processing the request.
     */
    private void getTournamentMatches(HttpServletRequest req, HttpServletResponse res, int tournamentId)
            throws ServletException, IOException, SQLException {
        om.setDateFormat(new StdDateFormat());

        LogContext.setAction(Actions.GET_TOURNAMENT_MATCHES);
        LOGGER.info("Received GET request");

        List<Match> matches = null;

        Boolean isFinished = req.getParameter("isFinished") != null ? Boolean.parseBoolean(req.getParameter("isFinished")) : null;
        
        if (isFinished != null) {
            GetTournamentMatchesByIsFinishedDAO getTournamentMatchesByIsFinishedDAO = new GetTournamentMatchesByIsFinishedDAO(
                    getConnection(), tournamentId, isFinished);
            matches = getTournamentMatchesByIsFinishedDAO.access().getOutputParam();
        } else {
            GetTournamentMatchesDAO getTournamentMatchesDAO = new GetTournamentMatchesDAO(getConnection(),
                    tournamentId);
            matches = getTournamentMatchesDAO.access().getOutputParam();
        }

        if (matches.size() != 0) {
            response = new ResponsePackage<List<Match>>(matches, ResponseStatus.OK,
                    "Matches found");
            res.getWriter().print(om.writeValueAsString(response));
        } else {
            response = new ResponsePackageNoData(ResponseStatus.NOT_FOUND,
                    "Matches not found");
            res.getWriter().print(om.writeValueAsString(response));
        }
    }
}

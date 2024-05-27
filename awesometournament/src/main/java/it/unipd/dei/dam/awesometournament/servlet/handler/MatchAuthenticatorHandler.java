package it.unipd.dei.dam.awesometournament.servlet.handler;

import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unipd.dei.dam.awesometournament.utils.ResponsePackageNoData;
import it.unipd.dei.dam.awesometournament.utils.ResponseStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import it.unipd.dei.dam.awesometournament.database.GetMatchDAO;
import it.unipd.dei.dam.awesometournament.database.GetTournamentByIdDAO;
import it.unipd.dei.dam.awesometournament.resources.entities.Match;
import it.unipd.dei.dam.awesometournament.resources.entities.Tournament;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherHandler;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.Method;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.Result;
import it.unipd.dei.dam.awesometournament.utils.SessionHelpers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Handles HTTP requests related to match authentication.
 * Extends the RestMatcherHandler class.
 */
public class MatchAuthenticatorHandler extends RestMatcherHandler{

	protected static final Logger LOGGER = LogManager.getLogger(MatchAuthenticatorHandler.class,
			StringFormatterMessageFactory.INSTANCE);
    ObjectMapper om;
    ResponsePackageNoData response;

    /**
     * Handles HTTP requests related to match authentication.
     * Extends the RestMatcherHandler class.
     *
     * @param method The HTTP method of the request.
     * @param req    The HttpServletRequest object representing the request.
     * @param res    The HttpServletResponse object representing the response.
     * @param params An array of String objects representing request parameters.
     * @return Result.CONTINUE if the operation should continue, Result.STOP otherwise.
     * @throws ServletException If an exception occurs while processing the request.
     * @throws IOException      If an I/O exception occurs while processing the request.
     */
    @Override
    public Result handle(Method method, HttpServletRequest req, HttpServletResponse res,
            String[] params) throws ServletException, IOException {

        om = new ObjectMapper();

        switch (method) {
            case GET:
                return Result.CONTINUE;
            case DELETE:
            case POST:
            case PUT: {
                if (!SessionHelpers.isLogged(req)) {
                    LOGGER.info("user is not logged!");
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    response = new ResponsePackageNoData(ResponseStatus.UNAUTHORIZED,
                            "User not logged in");
                    res.getWriter().print(om.writeValueAsString(response));
                    return Result.STOP;
                }

                int loggedId = SessionHelpers.getId(req);
                LOGGER.info("user id is "+loggedId);
                try {
                    int matchId = Integer.parseInt(params[0]);
                    GetMatchDAO dao = new GetMatchDAO(getConnection(), matchId);
                    dao.access();
                    Match m = dao.getOutputParam();
                    LOGGER.info("match is "+m);
                    GetTournamentByIdDAO dao2 = new GetTournamentByIdDAO(getConnection(), m.getTournamentId());
                    dao2.access();
                    Tournament t = dao2.getOutputParam();
                    LOGGER.info("tournament is "+t);
                    if (t.getCreatorUserId() != loggedId) {
                        res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                        response = new ResponsePackageNoData(ResponseStatus.FORBIDDEN,
                                "User unauthorized");
                        res.getWriter().print(om.writeValueAsString(response));
                        return Result.STOP;
                    }
                    return Result.CONTINUE;

                } catch (SQLException e) {
                    return Result.STOP;
                }
            }
        }
        return Result.STOP;
    }
}

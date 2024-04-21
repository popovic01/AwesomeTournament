package it.unipd.dei.dam.awesometournament.servlet.handler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import it.unipd.dei.dam.awesometournament.database.GetTournamentMatchesDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Match;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherHandler;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.Method;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.Result;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TournamentMatchesHandler extends RestMatcherHandler {
    protected final static Logger LOGGER = LogManager.getLogger(PlayerHandler.class,
            StringFormatterMessageFactory.INSTANCE);

    @Override
    public Result handle(Method method, HttpServletRequest req, HttpServletResponse res,
            String[] params) throws ServletException, IOException {
        
            LogContext.setIPAddress(req.getRemoteAddr());

            int tournamentId = Integer.parseInt(params[0]);
            try {
                switch (method) {
                    case GET:
                        getTournamentMatches(req, res, tournamentId);
                        break;
                    default:
                        return Result.STOP;
                }
            } catch (NumberFormatException e) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Team ID must be an integer");
            } catch (SQLException e) {
                res.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            }
            return Result.CONTINUE;
    }
    

    private void getTournamentMatches(HttpServletRequest req, HttpServletResponse res, int tournamentId)
            throws ServletException, IOException, SQLException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new StdDateFormat());

        LogContext.setAction(Actions.GET_TOURNAMENT_MATCHES);
        LOGGER.info("Received GET request");

        GetTournamentMatchesDAO getTournamentMatchesDAO = new GetTournamentMatchesDAO(getConnection(), tournamentId);
        List<Match> matches = getTournamentMatchesDAO.access().getOutputParam();

        if (matches.size() != 0) {
            res.setContentType("application/json");
            res.getWriter().println(objectMapper.writeValueAsString(matches));
        } else {
            res.sendError(HttpServletResponse.SC_NOT_FOUND, "No matches in tournament " + tournamentId);
        }
    }
}

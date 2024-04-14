package it.unipd.dei.dam.awesometournament.servlet;

import it.unipd.dei.dam.awesometournament.database.GetTournamentMatchesDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Match;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TournamentMatchesServlet extends AbstractDatabaseServlet{

    protected final static Logger LOGGER = LogManager.getLogger(PlayerServlet.class,
            StringFormatterMessageFactory.INSTANCE);

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.GET_TOURNAMENT_MATCHES);

        LOGGER.info("Received get request aaa");

        String url = req.getRequestURI();
        String[] urlParts = url.split("/");

        if(urlParts.length > 4){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
        } else {
            try {
                int tournamentId = Integer.parseInt(urlParts[3]);
                Connection connection = getConnection();

                GetTournamentMatchesDAO getTournamentMatchesDAO = new GetTournamentMatchesDAO(connection, tournamentId);
                List<Match> tournamentMatches = getTournamentMatchesDAO.getOutputParam();

                /*
                 * Should I go deeper and return Team name instead
                 * of the ID? Do that directly in the DAO?
                 */
                if (tournamentMatches != null) {
                    for(Match match: tournamentMatches){
                        resp.getWriter().println(match.getId());
                        resp.getWriter().println(match.getTeam1Id());
                        resp.getWriter().println(match.getTeam2Id());
                        //Just to be consistent, I could hard code TournamentID here.
                        resp.getWriter().println(match.getTournamentId());
                        resp.getWriter().println(match.getTeam1Score());
                        resp.getWriter().println(match.getTeam2Score());
                        resp.getWriter().println(match.getResult());
                        resp.getWriter().println(match.getReferee());
                        resp.getWriter().println(match.getMatchDate());
                        resp.getWriter().println(match.getIsFinished());
                    }
                    resp.getWriter().println("--");
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tournament ID must be an integer");
            } catch (SQLException e) {
                resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            }
        }
    }
}

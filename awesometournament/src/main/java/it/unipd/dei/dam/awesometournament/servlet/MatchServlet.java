package it.unipd.dei.dam.awesometournament.servlet;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import it.unipd.dei.dam.awesometournament.database.GetMatchDAO;
import it.unipd.dei.dam.awesometournament.database.GetTournamentByIdDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Match;
import it.unipd.dei.dam.awesometournament.resources.entities.Tournament;
import it.unipd.dei.dam.awesometournament.utils.SessionHelpers;

/**
 * Servlet responsible for handling requests related to matches
 */
public class MatchServlet extends AbstractDatabaseServlet{
    protected final static Logger LOGGER = LogManager.getLogger(MatchServlet.class,
            StringFormatterMessageFactory.INSTANCE);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.GET_MATCH);

        String path = req.getPathInfo();

        String[] parts = path.split("/");

        if(parts.length == 2) {
            int id = Integer.parseInt(parts[1]);
            try {
                GetMatchDAO dao = new GetMatchDAO(getConnection(), id);
                dao.access();
                Match match = dao.getOutputParam();
                if(match == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                req.setAttribute("match", match);

                if(SessionHelpers.isLogged(req)) {
                    // check if the user is the admin of the tournament
                    GetTournamentByIdDAO tournamentByIdDAO = new GetTournamentByIdDAO(getConnection(), match.getTournamentId());
                    tournamentByIdDAO.access();
                    Tournament tournament = tournamentByIdDAO.getOutputParam();

                    int userId = SessionHelpers.getId(req);
                    if(userId == tournament.getCreatorUserId()) {
                        req.setAttribute("owner", true);
                        req.setAttribute("matchId", match.getId());
                    }
                }

                req.getRequestDispatcher("/jsp/match.jsp").forward(req, resp);
            } catch (SQLException e) {
                LOGGER.info(e.getMessage());
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
}

package it.unipd.dei.dam.awesometournament.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import it.unipd.dei.dam.awesometournament.database.GetTournamentByIdDAO;
import it.unipd.dei.dam.awesometournament.database.GetTournamentMatchesDAO;
import it.unipd.dei.dam.awesometournament.database.GetTournamentTeamsDAO;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Match;
import it.unipd.dei.dam.awesometournament.resources.entities.Team;
import it.unipd.dei.dam.awesometournament.resources.entities.Tournament;

public class TournamentServlet extends AbstractDatabaseServlet{
    protected final static Logger LOGGER = LogManager.getLogger(TournamentServlet.class,
            StringFormatterMessageFactory.INSTANCE);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());

        String path = req.getPathInfo();

        String[] parts = path.split("/");

        if(parts.length == 2) {
            int id = Integer.parseInt(parts[1]);
            try {
                GetTournamentByIdDAO dao = new GetTournamentByIdDAO(getConnection(), id);
                dao.access();
                Tournament tournament = dao.getOutputParam();
                if(tournament == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                GetTournamentMatchesDAO matchesDAO = new GetTournamentMatchesDAO(getConnection(), id);
                matchesDAO.access();
                List<Match> matches = matchesDAO.getOutputParam();

                GetTournamentTeamsDAO teamsDao = new GetTournamentTeamsDAO(getConnection(), id);
                teamsDao.access();
                List<Team> teams = teamsDao.getOutputParam();

                req.setAttribute("tournament", tournament);
                req.setAttribute("matches", matches);
                req.setAttribute("teams", teams);

                req.getRequestDispatcher("/tournament.jsp").forward(req, resp);
            } catch (SQLException e) {
                LOGGER.info(e.getMessage());
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
}

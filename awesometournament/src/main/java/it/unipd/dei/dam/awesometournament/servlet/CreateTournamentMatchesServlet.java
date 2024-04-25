package it.unipd.dei.dam.awesometournament.servlet;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import it.unipd.dei.dam.awesometournament.database.GetTournamentByIdDAO;
import it.unipd.dei.dam.awesometournament.jobs.MatchCreatorJob;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Tournament;
import it.unipd.dei.dam.awesometournament.utils.SessionHelpers;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CreateTournamentMatchesServlet extends AbstractDatabaseServlet {

    protected final static Logger LOGGER = LogManager.getLogger(CreateTournamentMatchesServlet.class,
            StringFormatterMessageFactory.INSTANCE);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.PUT_MATCHES);

        LOGGER.info("Received POST request");
        String url = req.getPathInfo();
        if (url != null) {
            String[] urlParts = url.split("/"); // urlParts[0] = ""
            if (urlParts.length != 2) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
            } else {

                if (!SessionHelpers.isLogged(req)) {
                    LOGGER.info("user is not logged!");
                    resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }

                int loggedId = SessionHelpers.getId(req);
                LOGGER.info("user id is " + loggedId);
                
                int tournamentId = Integer.parseInt(urlParts[1]);

                try {
                    GetTournamentByIdDAO getTournamentByIdDAO = new GetTournamentByIdDAO(getConnection(), tournamentId);
                    Tournament tournament = getTournamentByIdDAO.access().getOutputParam();

                    if (tournament.getCreatorUserId() != loggedId) {
                        LOGGER.info("user has no access to this tournament!");
                        resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                        throw new ServletException("user has no access to this tournament");
                    }
                } catch (SQLException e) {
                    LOGGER.info(e);
                }

                try {
                    MatchCreatorJob.execute(tournamentId);
                    resp.getWriter().println("Match creation job has been executed successfully.");
                } catch (Exception e) {
                    resp.getWriter().println("Error executing match creation job: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}

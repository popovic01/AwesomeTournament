package it.unipd.dei.dam.awesometournament.servlet;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import it.unipd.dei.dam.awesometournament.jobs.MatchCreatorJob;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CreateTournamentMatchesServlet extends AbstractDatabaseServlet {

    protected final static Logger LOGGER = LogManager.getLogger(CreateTournamentMatchesServlet.class,
            StringFormatterMessageFactory.INSTANCE);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.PUT_MATCHES);

        LOGGER.info("Received job request");
        String url = req.getPathInfo();
        if (url != null) {
            String[] urlParts = url.split("/"); // urlParts[0] = ""
            if (urlParts.length != 2) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
            } else {

                int tournamentId = Integer.parseInt(urlParts[1]);
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

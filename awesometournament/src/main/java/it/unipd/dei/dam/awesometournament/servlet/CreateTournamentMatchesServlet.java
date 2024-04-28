package it.unipd.dei.dam.awesometournament.servlet;

import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unipd.dei.dam.awesometournament.utils.ResponsePackageNoData;
import it.unipd.dei.dam.awesometournament.utils.ResponseStatus;
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

/**
 * Servlet implementation for creating matches for a tournament.
 */
public class CreateTournamentMatchesServlet extends AbstractDatabaseServlet {

    protected final static Logger LOGGER = LogManager.getLogger(CreateTournamentMatchesServlet.class,
            StringFormatterMessageFactory.INSTANCE);
    ObjectMapper om;
    ResponsePackageNoData response;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.POST_MATCHES);
        om = new ObjectMapper();

        LOGGER.info("Received POST request");
        String url = req.getPathInfo();
        if (url != null) {
            String[] urlParts = url.split("/"); // urlParts[0] = ""
            if (urlParts.length != 2) {
                response = new ResponsePackageNoData(ResponseStatus.BAD_REQUEST,
                        "Invalid URL format");
                resp.getWriter().print(om.writeValueAsString(response));
            } else {

                if (!SessionHelpers.isLogged(req)) {
                    LOGGER.info("user is not logged!");
                    response = new ResponsePackageNoData(ResponseStatus.UNAUTHORIZED,
                            "User not logged in");
                    resp.getWriter().print(om.writeValueAsString(response));
                }

                int loggedId = SessionHelpers.getId(req);
                LOGGER.info("user id is " + loggedId);
                
                int tournamentId = Integer.parseInt(urlParts[1]);

                try {
                    GetTournamentByIdDAO getTournamentByIdDAO = new GetTournamentByIdDAO(getConnection(), tournamentId);
                    Tournament tournament = getTournamentByIdDAO.access().getOutputParam();

                    if (tournament.getCreatorUserId() != loggedId) {
                        LOGGER.info("user has no access to this tournament!");
                        response = new ResponsePackageNoData(ResponseStatus.FORBIDDEN,
                                "User unauthorized");
                        resp.getWriter().print(om.writeValueAsString(response));
                        throw new ServletException("user has no access to this tournament");
                    }
                } catch (SQLException e) {
                    LOGGER.info(e);
                }

                try {
                    MatchCreatorJob.execute(tournamentId);
                    response = new ResponsePackageNoData(ResponseStatus.OK,
                            "Match creation job has been executed successfully");
                    resp.getWriter().print(om.writeValueAsString(response));
                } catch (Exception e) {
                    response = new ResponsePackageNoData(ResponseStatus.INTERNAL_SERVER_ERROR,
                            "Error executing match creation job: " + e.getMessage());
                    resp.getWriter().print(om.writeValueAsString(response));
                    e.printStackTrace();
                }
            }
        }
    }
}

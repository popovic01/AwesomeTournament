package it.unipd.dei.dam.awesometournament.servlet;

import it.unipd.dei.dam.awesometournament.database.GetTournamentMatchesDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Match;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
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

    protected final static Logger LOGGER = LogManager.getLogger(PlayerServlet.class, StringFormatterMessageFactory.INSTANCE);

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.GET_TOURNAMENT_MATCHES);

        LOGGER.info("Received get request");

        String[] urlParts = req.getPathInfo().split("/");

        if(urlParts.length  != 2){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
        } else {
            try {
                String tournamentToken = urlParts[1];
                Connection connection = getConnection();

                GetTournamentMatchesDAO getTournamentMatchesDAO = new GetTournamentMatchesDAO(connection, tournamentToken);
                List<Match> tournamentMatches = getTournamentMatchesDAO.access().getOutputParam();

                if (tournamentMatches != null) {
                    String matchesJson = convertMatchesToJson(tournamentMatches);
                    resp.getWriter().println(matchesJson);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tournament ID must be an integer");
            } catch (SQLException e) {
                LOGGER.info(e);
                resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            }
        }
    }

    private static String convertMatchesToJson(List<Match> tournamentMatches) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(tournamentMatches);
    }
}

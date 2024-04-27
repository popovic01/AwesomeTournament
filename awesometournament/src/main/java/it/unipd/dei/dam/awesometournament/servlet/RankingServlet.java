package it.unipd.dei.dam.awesometournament.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import it.unipd.dei.dam.awesometournament.database.GetRankingDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.utils.RankingEntry;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation for the Ranking.
 * This servlets generates a ranking for the tournament.
 */
public class RankingServlet extends AbstractDatabaseServlet {
    protected final static Logger LOGGER = LogManager.getLogger(PlayerServlet.class,
            StringFormatterMessageFactory.INSTANCE);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.GET_RANKING);

        LOGGER.info("Received GET request");

        String url = req.getPathInfo();
        if (url != null) {
            String[] urlParts = url.split("/"); // urlParts[0] = ""
            if (urlParts.length != 2) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
            } else {
                try {
                    int tournamentId = Integer.parseInt(urlParts[1]);
                    Connection connection = getConnection();
                    GetRankingDAO getRankingDAO = new GetRankingDAO(connection, tournamentId);
                    ArrayList<RankingEntry> ranking = (ArrayList<RankingEntry>) getRankingDAO.access().getOutputParam();
                    if (ranking.size() != 0) {
                        req.setAttribute("ranking", ranking);
                        req.setAttribute("tournament_id", tournamentId);
                        req.getRequestDispatcher("/ranking.jsp").forward(req, resp);
                    } else {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "No ranking in tournament " + tournamentId);
                    }
                } catch (NumberFormatException e) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tournament ID must be an integer");
                } catch (SQLException e) {
                    resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                }
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
        }

    }
}

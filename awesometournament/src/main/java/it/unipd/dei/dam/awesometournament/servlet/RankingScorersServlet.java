package it.unipd.dei.dam.awesometournament.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


import it.unipd.dei.dam.awesometournament.database.GetRankingScorersDAO;
import it.unipd.dei.dam.awesometournament.utils.RankingScorersEntry;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RankingScorersServlet extends AbstractDatabaseServlet{
    protected final static Logger LOGGER = LogManager.getLogger(RankingScorersServlet.class,
            StringFormatterMessageFactory.INSTANCE);

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.GET_RANKING_SCORERS);

        LOGGER.info("Received GET request");

        String url = req.getPathInfo();
        if (url != null) {
            String[] urlParts = url.split("/"); // urlParts[0] = ""
            if (urlParts.length != 2) resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
            else {
                try {
                    int tournamentId = Integer.parseInt(urlParts[1]);
                    GetRankingScorersDAO getRankingScorersDAO = new GetRankingScorersDAO(getConnection(), tournamentId);
                    ArrayList<RankingScorersEntry> ranking = getRankingScorersDAO.access().getOutputParam();
                    if (!ranking.isEmpty()) {
                        req.setAttribute("ranking", ranking);
                        req.setAttribute("tournament_id", tournamentId);
                        req.getRequestDispatcher("/ranking_scorers.jsp").forward(req, resp);
                    }
                    else resp.sendError(HttpServletResponse.SC_NOT_FOUND, "No ranking in tournament " + tournamentId);
                } catch (NumberFormatException e) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tournament ID must be an integer");
                } catch (SQLException e) {
                    resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                }
            }
        }
        else resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
    }
}

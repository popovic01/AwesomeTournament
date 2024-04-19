package it.unipd.dei.dam.awesometournament.servlet.handler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.*;
import it.unipd.dei.dam.awesometournament.utils.RankingEntry;
import it.unipd.dei.dam.awesometournament.database.GetRankingDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

public class RankingHandler implements Handler {
    protected final static Logger LOGGER = LogManager.getLogger(PlayerHandler.class,
            StringFormatterMessageFactory.INSTANCE);

    @Override
    public Result handle(Method method, HttpServletRequest req, HttpServletResponse res, Connection connection,
            String[] params) throws ServletException, IOException {

            LogContext.setIPAddress(req.getRemoteAddr());

            int tournamentId = Integer.parseInt(params[0]);

            try {
                switch (method) {
                    case GET:
                        LogContext.setAction(Actions.GET_RANKING);
                        LOGGER.info("Received GET request");
                        GetRankingDAO getRankingDAO = new GetRankingDAO(connection, tournamentId);
                        ArrayList<RankingEntry> ranking = (ArrayList<RankingEntry>) getRankingDAO.access().getOutputParam();
                        if (ranking.size() != 0) {
                            req.setAttribute("ranking", ranking);
                            req.setAttribute("tournament_id", tournamentId);
                            req.getRequestDispatcher("ranking.jsp").forward(req, res);
                        } else {
                            res.sendError(HttpServletResponse.SC_NOT_FOUND, "No ranking in tournament " + tournamentId);
                        }
                        break;
                    default:
                        return Result.STOP;
                }
            } catch (NumberFormatException e) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tournament ID must be an integer");
            } catch (SQLException e) {
                res.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            }
            return Result.CONTINUE;
        }
}

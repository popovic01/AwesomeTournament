package it.unipd.dei.dam.awesometournament.servlet;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import it.unipd.dei.dam.awesometournament.database.GetTournamentByIdDAO;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
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
                req.setAttribute("tournament", tournament);
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

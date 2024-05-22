package it.unipd.dei.dam.awesometournament.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import it.unipd.dei.dam.awesometournament.utils.SessionHelpers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import it.unipd.dei.dam.awesometournament.database.GetTournamentsDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Tournament;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation for handling requests to the home page
 */
public class HomeServlet extends AbstractDatabaseServlet{
    protected final static Logger LOGGER = LogManager.getLogger(HomeServlet.class,
            StringFormatterMessageFactory.INSTANCE);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.GET_HOMEPAGE);

        try {
            GetTournamentsDAO dao = new GetTournamentsDAO(getConnection());
            dao.access();
            List<Tournament> tournaments = dao.getOutputParam();
            if(SessionHelpers.isLogged(req)) {
                int userId = SessionHelpers.getId(req);
                req.setAttribute("logged", true);
                req.setAttribute("userId", userId);
            }
            req.setAttribute("tournaments", tournaments);
            req.getRequestDispatcher("/jsp/home.jsp").forward(req, resp);
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
}

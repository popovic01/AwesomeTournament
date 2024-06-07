package it.unipd.dei.dam.awesometournament.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import it.unipd.dei.dam.awesometournament.database.GetTeamsByCreatorDAO;
import it.unipd.dei.dam.awesometournament.database.GetTournamentByIdDAO;
import it.unipd.dei.dam.awesometournament.database.GetTournamentsByCreatorDAO;
import it.unipd.dei.dam.awesometournament.database.GetUserDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Team;
import it.unipd.dei.dam.awesometournament.resources.entities.Tournament;
import it.unipd.dei.dam.awesometournament.resources.entities.User;
import it.unipd.dei.dam.awesometournament.utils.SessionHelpers;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation for handling user-related operations
 */
public class UserServlet extends AbstractDatabaseServlet {
    protected final static Logger LOGGER = LogManager.getLogger(UserServlet.class,
            StringFormatterMessageFactory.INSTANCE);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.GET_USER);

        if(!SessionHelpers.isLogged(req)) {
            resp.sendRedirect("/");
            return;
        }

        try {
            int id = SessionHelpers.getId(req);
            Connection connection = getConnection();
            GetUserDAO dao = new GetUserDAO(connection, id);
            dao.access();
            User result = (User) dao.getOutputParam();
            req.setAttribute("user", result);

            GetTournamentsByCreatorDAO getTournamentsByCreatorDAO = new GetTournamentsByCreatorDAO(getConnection(), id);
            getTournamentsByCreatorDAO.access();
            GetTeamsByCreatorDAO getTeamsByCreatorDAO = new GetTeamsByCreatorDAO(getConnection(), id);
            getTeamsByCreatorDAO.access();

            List<Tournament> tournaments = getTournamentsByCreatorDAO.getOutputParam();
            List<Team> teams = getTeamsByCreatorDAO.getOutputParam();

            req.setAttribute("tournaments", tournaments);
            req.setAttribute("teams", teams);

            if(result != null) {
                req.getRequestDispatcher("/jsp/user.jsp").forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID must be an integer");
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }
    }
}

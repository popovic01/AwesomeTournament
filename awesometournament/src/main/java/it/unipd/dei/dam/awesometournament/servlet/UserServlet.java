package it.unipd.dei.dam.awesometournament.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import it.unipd.dei.dam.awesometournament.database.GetUserDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation for handling user-related operations.
 */
public class UserServlet extends AbstractDatabaseServlet {
    protected final static Logger LOGGER = LogManager.getLogger(UserServlet.class,
            StringFormatterMessageFactory.INSTANCE);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Setting context for logging
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.GET_PLAYER);

        // Handling URL parsing
        String url = req.getPathInfo();
        if (url == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
            return;
        }
        String[] urlParts = url.split("/"); // urlParts[0] = ""
        if (urlParts.length != 2) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
        } else {
            try {
                int userId = Integer.parseInt(urlParts[1]);
                Connection connection = getConnection();
                GetUserDAO dao = new GetUserDAO(connection, userId);
                dao.access();
                User result = (User) dao.getOutputParam();
                if(result != null) {

                resp.getWriter().println(result);
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

}

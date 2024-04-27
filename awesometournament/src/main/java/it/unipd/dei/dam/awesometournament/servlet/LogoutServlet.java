package it.unipd.dei.dam.awesometournament.servlet;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.utils.SessionHelpers;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation for user logout functionality.
 */
public class LogoutServlet extends AbstractDatabaseServlet {
    protected final static Logger LOGGER = LogManager.getLogger(LogoutServlet.class,
            StringFormatterMessageFactory.INSTANCE);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());

        LOGGER.info("logout");

        // Get a session for the user if one exists
        HttpSession session = req.getSession(false);
        if(SessionHelpers.isLogged(req)) {
            // If the user is logged we invalidate his session
            session.invalidate();
            LOGGER.info("session invalidated");
        }
        // Try to redirect the user to the page he came from
        String referer = req.getHeader("referer");
        if (referer != null && !referer.isEmpty()) {
            resp.sendRedirect(referer);
        } else {
            resp.sendRedirect("/");
        }
    }
}
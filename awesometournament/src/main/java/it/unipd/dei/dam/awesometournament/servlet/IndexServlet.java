package it.unipd.dei.dam.awesometournament.servlet;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.utils.SessionHelpers;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class IndexServlet extends AbstractDatabaseServlet {
    protected final static Logger LOGGER = LogManager.getLogger(IndexServlet.class,
            StringFormatterMessageFactory.INSTANCE);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setAction(Actions.GET_INDEX);
        LogContext.setIPAddress(req.getRemoteAddr());
        HttpSession session = req.getSession(false);
        boolean logged = false;

        if(SessionHelpers.isLogged(req)) {
            logged = true;
            req.setAttribute("email", (String) session.getAttribute("email"));
        }

        req.setAttribute("logged", logged);
        req.getRequestDispatcher("/jsp/index.jsp").forward(req, resp);;
    }
}
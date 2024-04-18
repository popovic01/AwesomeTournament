package it.unipd.dei.dam.awesometournament.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import it.unipd.dei.dam.awesometournament.database.GetUserDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.User;
import it.unipd.dei.dam.awesometournament.utils.BodyTools;
import it.unipd.dei.dam.awesometournament.utils.Hashing;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginServlet extends AbstractDatabaseServlet {
    protected final static Logger LOGGER = LogManager.getLogger(LoginServlet.class,
            StringFormatterMessageFactory.INSTANCE);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());

        LOGGER.info("get");

        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.GET_PLAYER);

        // extract email and password from the body of the request

        Map<String, String> map;
        try {
            map = BodyTools.parsePostBody(req.getReader());
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "malformed body");
            return;
        }

        String email = map.get("email");
        String password = map.get("password");

        if (email == null || password == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "email or password not provided");
            return;
        }

        LOGGER.info("email: " + email);
        LOGGER.info("password: " + password);

        // try to authenticate the user

        try {
            // try to get a corrsponding user in the database
            GetUserDAO dao = new GetUserDAO(getConnection(), email);
            dao.access();
            User user = dao.getOutputParam();
            LOGGER.info(user);
            if(user == null) {
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "user not found");
                return;
            }
            // compare the password
            String hashedinput = Hashing.hashPassword(password);
            if(!hashedinput.equals(user.getPassword())) {
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "wrong password");
                return;
            }
            // create session for the user
            HttpSession session = req.getSession(true);
            session.setAttribute("id", user.getId());
            session.setAttribute("email", user.getEmail());
            resp.getWriter().println("Succesfully logged in");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
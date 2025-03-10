package it.unipd.dei.dam.awesometournament.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import it.unipd.dei.dam.awesometournament.database.CreateUserDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.User;
import it.unipd.dei.dam.awesometournament.utils.BodyTools;
import it.unipd.dei.dam.awesometournament.utils.Hashing;
import it.unipd.dei.dam.awesometournament.utils.SessionHelpers;
import it.unipd.dei.dam.awesometournament.utils.Validators;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation for user registration (signup) functionality
 */
public class SignupServlet extends AbstractDatabaseServlet {
    protected final static Logger LOGGER = LogManager.getLogger(SignupServlet.class,
            StringFormatterMessageFactory.INSTANCE);

    String getRedirect(HttpServletRequest req) {
        String redirect = null;

        String referer = req.getHeader("Referer");
        if (referer != null)
            redirect = referer;

        // If the referer is passed as a query argument, it overrides the one passed as
        // the header.
        String refererparam = req.getParameter("redirect");
        if (refererparam != null)
            redirect = refererparam;

        return redirect;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.USER_SIGNUP);

        if (SessionHelpers.isLogged(req)) {
            resp.sendRedirect("/");
            return;
        }

        req.setAttribute("redirect", getRedirect(req));

        req.getRequestDispatcher("/jsp/signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.USER_SIGNUP);

        // invalidate any previous session

        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

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

        String redirect = map.get("redirect");

        if (email == null || password == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "email or password not provided");
            return;
        }

        LOGGER.info("email: " + email);
        // LOGGER.info("password: " + password); for debug only, clear passwords should
        // not be present in logs

        // try to register the user

        if (!Validators.isEmail(email)) {
            // the email is not valid
            req.setAttribute("error", "Invalid email!\n");
            req.setAttribute("redirect", redirect);
            req.getRequestDispatcher("/jsp/signup.jsp").forward(req, resp);
            return;
        }

        if (!Validators.isPasswordValid(password)) {
            // the password is not valid
            req.setAttribute("error", "Invalid password!\n");
            req.setAttribute("redirect", redirect);
            req.getRequestDispatcher("/jsp/signup.jsp").forward(req, resp);
            return;
        }

        try {
            // try to create a user in the database
            String hashedPassword = Hashing.hashPassword(password);
            User user = new User(-1, email, hashedPassword);
            CreateUserDAO dao = new CreateUserDAO(getConnection(), user);
            dao.access();
            Integer createdUserID = dao.getOutputParam();
            if (createdUserID == null) {
                req.setAttribute("error", "User already exists!\n");
                req.setAttribute("redirect", redirect);
                req.getRequestDispatcher("/jsp/signup.jsp").forward(req, resp);
                return;
            }

            LOGGER.info("Sending redirect to /auth/login?redirect="+redirect);
            resp.sendRedirect("/auth/login?redirect=" + redirect);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
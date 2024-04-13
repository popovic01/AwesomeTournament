package it.unipd.dei.dam.awesometournament.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginServlet extends AbstractDatabaseServlet {
    protected final static Logger LOGGER = LogManager.getLogger(LoginServlet.class,
            StringFormatterMessageFactory.INSTANCE);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.GET_PLAYER);

        LOGGER.info("get");

        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.GET_PLAYER);

        LOGGER.info("post");

        Map<String, String> map = new HashMap<>();

        BufferedReader br = req.getReader();
        String line = br.readLine();
        if(line == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "please provide a body");
        }
        String[] params = line.split("&");
        for(String param: params) {
            LOGGER.info(param);
            String[] parts = param.split("=");
            if (parts.length != 2) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "malformed body");
                return;
            }
            map.put(parts[0], parts[1]);
        }

        String email = map.get("email");
        String password = map.get("password");

        if (email == null || password == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "email or password not provided");
            return;
        }

        LOGGER.info("email: " + email);
        LOGGER.info("password: " + password);

        resp.getWriter().println("received request!");
    }
}

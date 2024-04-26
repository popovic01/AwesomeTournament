package it.unipd.dei.dam.awesometournament.servlet.handler;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unipd.dei.dam.awesometournament.utils.ResponsePackageNoData;
import it.unipd.dei.dam.awesometournament.utils.ResponseStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import it.unipd.dei.dam.awesometournament.servlet.RestMatcherHandler;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.Method;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.Result;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SessionHandler extends RestMatcherHandler {
    protected static final Logger LOGGER = LogManager.getLogger(SessionHandler.class,
            StringFormatterMessageFactory.INSTANCE);

    @Override
    public Result handle(Method method, HttpServletRequest req, HttpServletResponse res,
            String[] params) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        ObjectMapper om = new ObjectMapper();
        ResponsePackageNoData response;

        try {
            if (session != null) {
                Integer sessionId = (Integer) session.getAttribute("id");
                String sessionEmail = (String) session.getAttribute("email");
                LOGGER.info("Found session id=" + sessionId + " email=" + sessionEmail);
                req.setAttribute("session_id", sessionId);
                req.setAttribute("session_email", sessionEmail);
            }
            return Result.CONTINUE;
        } catch (Exception e) {
            LOGGER.error("Something went wrong: " + e.getMessage());
            response = new ResponsePackageNoData(ResponseStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            res.getWriter().print(om.writeValueAsString(response));
            return Result.STOP;
        }
    }
}

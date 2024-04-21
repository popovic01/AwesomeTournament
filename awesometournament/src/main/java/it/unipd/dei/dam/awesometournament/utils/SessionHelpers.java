package it.unipd.dei.dam.awesometournament.utils;

import it.unipd.dei.dam.awesometournament.servlet.handler.SessionHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

public class SessionHelpers {
    static class NotLoggedException extends RuntimeException{}

    protected static final Logger LOGGER = LogManager.getLogger(SessionHandler.class,
            StringFormatterMessageFactory.INSTANCE);

    public static boolean isLogged(HttpServletRequest req) {
        LOGGER.info(req.getAttribute("session_id")); //set in SessionHandler
        LOGGER.info(req.getAttribute("session_email")); //set in SessionHandler
        if (req.getAttribute("session_id") != null) {
            return true;
        }
        return false;
    }

    public static int getId(HttpServletRequest req) throws NotLoggedException {
        if (!isLogged(req)) {
            throw new NotLoggedException();
        }
        return (int) req.getAttribute("session_id");
    }
}

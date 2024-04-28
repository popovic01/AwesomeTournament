package it.unipd.dei.dam.awesometournament.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Provides utility methods for handling session-related operations.
 */
public class SessionHelpers {
    /**
     * Exception thrown when attempting to perform an operation that requires the user to be logged in,
     * but the user is not logged in.
     */
    static class NotLoggedException extends RuntimeException{}

    /**
     * Checks if a user is logged in.
     *
     * @param req The HttpServletRequest object representing the HTTP request.
     * @return true if the user is logged in, false otherwise.
     */
    public static boolean isLogged(HttpServletRequest req) {
        HttpSession session = req.getSession();
        if(session != null && session.getAttribute("id") != null) {
            return true;
        }
        return false;
    }

    /**
     * Retrieves the ID of the logged-in user.
     *
     * @param req The HttpServletRequest object representing the HTTP request.
     * @return The ID of the logged-in user.
     * @throws NotLoggedException If the user is not logged in.
     */
    public static int getId(HttpServletRequest req) throws NotLoggedException {
        if(!isLogged(req)) {
            throw new NotLoggedException();
        }

        HttpSession session = req.getSession();
        return (int) session.getAttribute("id");
    }
}

package it.unipd.dei.dam.awesometournament.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionHelpers {
    static class NotLoggedException extends RuntimeException{}

    public static boolean isLogged(HttpServletRequest req) {
        HttpSession session = req.getSession();
        if(session != null && session.getAttribute("id") != null) {
            return true;
        }
        return false;
    }

    public static int getId(HttpServletRequest req) throws NotLoggedException {
        if(!isLogged(req)) {
            throw new NotLoggedException();
        }

        HttpSession session = req.getSession();
        return (int) session.getAttribute("id");
    }
}

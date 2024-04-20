package it.unipd.dei.dam.awesometournament.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionHelpers {
    public static boolean isLogged(HttpServletRequest req) {
        HttpSession session = req.getSession();
        if(session != null && session.getAttribute("id") != null) {
            return true;
        }
        return false;
    }
}

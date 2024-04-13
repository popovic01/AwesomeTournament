package it.unipd.dei.dam.awesometournament.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import it.unipd.dei.dam.awesometournament.database.GetPlayerDAO;
import it.unipd.dei.dam.awesometournament.resources.entities.Player;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PlayerServlet extends AbstractDatabaseServlet{
    protected final static Logger LOGGER = LogManager.getLogger(PlayerServlet.class, StringFormatterMessageFactory.INSTANCE);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Received get request");

        String url = req.getRequestURI();
        String[] urlParts = url.split("/");

        if (urlParts.length > 4) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
        } else {
            try {
                int playerId = Integer.parseInt(urlParts[3]);
                Connection connection = getConnection();
                GetPlayerDAO getPlayerDAO = new GetPlayerDAO(connection, playerId);
                Player player = (Player)getPlayerDAO.access().getOutputParam();
                if (player != null) {
                    resp.getWriter().println(player.getName());
                    resp.getWriter().println(player.getSurname());
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Player ID must be an integer");
            } catch (SQLException e) {
                resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            }
        }
    }
}

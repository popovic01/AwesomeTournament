package it.unipd.dei.dam.awesometournament.servlet.handler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.*;
import it.unipd.dei.dam.awesometournament.database.GetPlayerDAO;
import it.unipd.dei.dam.awesometournament.database.UpdatePlayerDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Player;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PlayerHandler implements Handler {
    @Override
    public Result handle(Method method, HttpServletRequest req, HttpServletResponse res, Connection connection,
            String[] params) throws ServletException, IOException {
                try {
                    int playerId = Integer.parseInt(params[0]);
                    GetPlayerDAO getPlayerDAO = new GetPlayerDAO(connection, playerId);
                    Player player = (Player) getPlayerDAO.access().getOutputParam();
                    if (player != null) {
                        req.setAttribute("player", player);
                        req.getRequestDispatcher("/player.jsp").forward(req, res);
                    } else {
                        res.sendError(HttpServletResponse.SC_NOT_FOUND, "The player doesn't exist");
                    }
                } catch (NumberFormatException e) {
                    res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Player ID must be an integer");
                } catch (SQLException e) {
                    res.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                }
                return Result.CONTINUE;
        }
}

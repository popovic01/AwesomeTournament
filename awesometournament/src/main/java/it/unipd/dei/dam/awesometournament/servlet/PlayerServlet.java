package it.unipd.dei.dam.awesometournament.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import it.unipd.dei.dam.awesometournament.database.GetPlayerDAO;
import it.unipd.dei.dam.awesometournament.database.GetTeamDAO;
import it.unipd.dei.dam.awesometournament.database.GetTournamentByIdDAO;
import it.unipd.dei.dam.awesometournament.database.UpdatePlayerDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Player;
import it.unipd.dei.dam.awesometournament.resources.entities.Team;
import it.unipd.dei.dam.awesometournament.resources.entities.Tournament;
import it.unipd.dei.dam.awesometournament.utils.SessionHelpers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 * Servlet handling requests related to players
 */
@WebServlet(name = "MedicalCertificateUploadServlet", urlPatterns = {"/uploadMedicalCertificate"})
@MultipartConfig
public class PlayerServlet extends AbstractDatabaseServlet {

    /**
     * Logger for the PlayerServlet class
     */
    protected final static Logger LOGGER = LogManager.getLogger(PlayerServlet.class,
            StringFormatterMessageFactory.INSTANCE);

    /**
     * Checks if the user logged and authorized to perform actions on the specified player.
     *
     * @param req           The HTTP servlet request
     * @param playerId      The ID of the player
     * @return              True if the user is authorized, false otherwise
     * @throws SQLException If a database access error occurs
     */
    private boolean isUserAuthorized(HttpServletRequest req, int playerId) throws SQLException {
        if (!SessionHelpers.isLogged(req))
            return false;

        LOGGER.info("User logged");
        int userId = SessionHelpers.getId(req);
        GetPlayerDAO getPlayerDAO = new GetPlayerDAO(getConnection(), playerId);
        Player player = (Player) getPlayerDAO.access().getOutputParam();
        GetTeamDAO getTeamDAO = new GetTeamDAO(getConnection(), player.getTeamId());
        Team team = (Team) getTeamDAO.access().getOutputParam();
        GetTournamentByIdDAO getTournamentByIdDAO = new GetTournamentByIdDAO(getConnection(), team.getTournamentId());
        Tournament tournament = (Tournament) getTournamentByIdDAO.access().getOutputParam();

        if (team.getCreatorUserId() != userId && tournament.getCreatorUserId() != userId)
            return false;

        LOGGER.info("User authorized");
        return true;
    }

    /**
     * Handles the GET request to retrieve player information.
     *
     * @param req               The HTTP servlet request
     * @param resp              The HTTP servlet response
     * @throws ServletException If the servlet encounters difficulty
     * @throws IOException      If an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.GET_PLAYER);

        LOGGER.info("Received GET request");

        String url = req.getPathInfo();
        if (url != null) {
            String[] urlParts = url.split("/"); // urlParts[0] = ""
            if (urlParts.length != 2) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
            } else {
                try {
                    int playerId = Integer.parseInt(urlParts[1]);
                    req.setAttribute("authorized", false);
                    if (isUserAuthorized(req, playerId)) {
                        req.setAttribute("authorized", true);
                    }
                    Connection connection = getConnection();
                    GetPlayerDAO getPlayerDAO = new GetPlayerDAO(connection, playerId);
                    Player player = (Player) getPlayerDAO.access().getOutputParam();
                    if (player != null) {
                        GetTeamDAO getTeamDAO = new GetTeamDAO(getConnection(), player.getTeamId());
                        Team team = getTeamDAO.access().getOutputParam();
                        req.setAttribute("team", team.getName());
                        req.setAttribute("player", player);
                        req.getRequestDispatcher("/jsp/player.jsp").forward(req, resp);
                    } else {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "The player doesn't exist");
                    }
                } catch (NumberFormatException e) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Player ID must be an integer");
                } catch (SQLException e) {
                    resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                }
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
        }

    }

    /**
     * Handles the POST request to upload a medical certificate for a player.
     *
     * @param req               The HTTP servlet request
     * @param resp              The HTTP servlet response
     * @throws ServletException If the servlet encounters difficulty
     * @throws IOException      If an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.POST_PLAYER);

        InputStream inputStream = null; // input stream of the upload file

        // obtains the upload file part in this multipart request
        Part medicalCertificate = req.getPart("medicalCertificate");
        if (medicalCertificate != null) {
            // prints out some information for debugging
            LOGGER.info(medicalCertificate.getName());
            LOGGER.info(medicalCertificate.getSize());
            LOGGER.info(medicalCertificate.getContentType());

            // obtains input stream of the upload file
            inputStream = medicalCertificate.getInputStream();
        }

        try {
            int playerId = Integer.parseInt(req.getParameter("playerId"));
            if (!isUserAuthorized(req, playerId)) {
                LOGGER.info("User unauthorized");
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            GetPlayerDAO getPlayerDAO = new GetPlayerDAO(getConnection(), playerId);
            Player player = getPlayerDAO.access().getOutputParam();
            player.setMedicalCertificate(inputStream);
            UpdatePlayerDAO updatePlayerDAO = new UpdatePlayerDAO(getConnection(), player);
            Integer result = (Integer) updatePlayerDAO.access().getOutputParam();
            if (result == 1) {
                // to pass an attribute with redirect
                req.getSession().setAttribute("uploaded", true);
                resp.sendRedirect("/players/" + playerId);
            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Player ID must be an integer");
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }
    }
}

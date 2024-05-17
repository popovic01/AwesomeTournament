package it.unipd.dei.dam.awesometournament.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import it.unipd.dei.dam.awesometournament.database.GetPlayerDAO;
import it.unipd.dei.dam.awesometournament.database.GetTeamDAO;
import it.unipd.dei.dam.awesometournament.database.GetTournamentByIdDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Player;
import it.unipd.dei.dam.awesometournament.resources.entities.Team;
import it.unipd.dei.dam.awesometournament.resources.entities.Tournament;
import it.unipd.dei.dam.awesometournament.utils.SessionHelpers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/download_medical_certificate/*")
public class MedicalCertificateServlet extends AbstractDatabaseServlet {

    /**
     * Logger for the PlayerServlet class
     */
    protected final static Logger LOGGER = LogManager.getLogger(MedicalCertificateServlet.class,
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
        LogContext.setAction(Actions.GET_MEDICAL_CERTIFICATE);

        LOGGER.info("GET request to download medical certificate");

        String url = req.getPathInfo();
        if (url != null) {
            String[] urlParts = url.split("/"); // urlParts[0] = ""
            if (urlParts.length != 2) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
            } else {
                try {
                    int playerId = Integer.parseInt(urlParts[1]);
                    if (!isUserAuthorized(req, playerId)) {
                        resp.sendError(HttpServletResponse.SC_FORBIDDEN, "User not authorized");
                        return;
                    }
                    Connection connection = getConnection();
                    GetPlayerDAO getPlayerDAO = new GetPlayerDAO(connection, playerId);
                    Player player = (Player) getPlayerDAO.access().getOutputParam();
                    if (player != null) {
                        resp.setContentType("application/octet-stream");
                        resp.setHeader("Content-disposition", "attachment; filename=medical_certificate.pdf");

                        try(InputStream in = player.getMedicalCertificate(); OutputStream out = resp.getOutputStream()) {

                            byte[] buffer = new byte[1024];

                            int numBytesRead;
                            while ((numBytesRead = in.read(buffer)) > 0) {
                                out.write(buffer, 0, numBytesRead);
                            }
                        }
                    } else {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Player not found");
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
}

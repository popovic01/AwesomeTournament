package it.unipd.dei.dam.awesometournament.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import it.unipd.dei.dam.awesometournament.database.DeletePlayerDAO;
import it.unipd.dei.dam.awesometournament.database.GetPlayerDAO;
import it.unipd.dei.dam.awesometournament.database.GetTeamDAO;
import it.unipd.dei.dam.awesometournament.database.UpdatePlayerDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Player;
import it.unipd.dei.dam.awesometournament.resources.entities.Team;
import it.unipd.dei.dam.awesometournament.utils.BodyTools;
import it.unipd.dei.dam.awesometournament.utils.SessionHelpers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 * Servlet handling requests related to players.
 */
@WebServlet(name = "MedicalCertificateUploadServlet", urlPatterns = {"/uploadMedicalCertificate"})
@MultipartConfig
public class PlayerServlet extends AbstractDatabaseServlet {
    protected final static Logger LOGGER = LogManager.getLogger(PlayerServlet.class,
            StringFormatterMessageFactory.INSTANCE);

    private boolean isUserAuthorized(HttpServletRequest req, int playerId) throws SQLException {
        if (!SessionHelpers.isLogged(req))
            return false;

        LOGGER.info("User logged");
        int userId = SessionHelpers.getId(req);
        GetPlayerDAO getPlayerDAO = new GetPlayerDAO(getConnection(), playerId);
        Player player = (Player) getPlayerDAO.access().getOutputParam();
        GetTeamDAO getTeamDAO = new GetTeamDAO(getConnection(), player.getTeamId());
        Team team = (Team) getTeamDAO.access().getOutputParam();

        if (team.getCreatorUserId() != userId)
            return false;

        LOGGER.info("User authorized");
        return true;
    }

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
                    Connection connection = getConnection();
                    GetPlayerDAO getPlayerDAO = new GetPlayerDAO(connection, playerId);
                    Player player = (Player) getPlayerDAO.access().getOutputParam();
                    if (player != null) {
                        req.setAttribute("player", player);
                        req.getRequestDispatcher("/player.jsp").forward(req, resp);
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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.PUT_PLAYER);

        LOGGER.info("Received PUT request");
        String url = req.getPathInfo();
        if (url != null) {
            String[] urlParts = url.split("/"); // urlParts[0] = ""
            if (urlParts.length != 2) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
            } else {
                try {
                    int playerId = Integer.parseInt(urlParts[1]);
                    if (!isUserAuthorized(req, playerId)) {
                        LOGGER.info("User unauthorized");
                        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        return;
                    }
                    String requestBody = BodyTools.getRequestBody(req);
                    LOGGER.info(requestBody);
                    ObjectMapper om = new ObjectMapper();
                    om.setDateFormat(new StdDateFormat());
                    Player player = (Player) om.readValue(requestBody.toString(), Player.class);
                    player.setId(playerId);
                    LOGGER.info(player.toString());
                    Connection connection = getConnection();
                    UpdatePlayerDAO updatePlayerDAO = new UpdatePlayerDAO(connection, player);
                    Integer result = (Integer) updatePlayerDAO.access().getOutputParam();
                    if (result == 1) {
                        resp.setStatus(HttpServletResponse.SC_OK);
                    } else {
                        resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            GetPlayerDAO getPlayerDAO = new GetPlayerDAO(getConnection(), playerId);
            Player player = getPlayerDAO.access().getOutputParam();
            player.setMedicalCertificate(inputStream);
            UpdatePlayerDAO updatePlayerDAO = new UpdatePlayerDAO(getConnection(), player);
            Integer result = (Integer) updatePlayerDAO.access().getOutputParam();
            if (result == 1) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().print("Medical Certificate Uploaded!");
            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Player ID must be an integer");
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }

    }

    @Override
    protected void doDelete (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.DELETE_PLAYER);

        LOGGER.info("Received DELETE request");
        String url = req.getPathInfo();
        if (url != null) {
            String[] urlParts = url.split("/"); // urlParts[0] = ""
            if (urlParts.length != 2) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
            } else {
                try {
                    int playerId = Integer.parseInt(urlParts[1]);
                    if (!isUserAuthorized(req, playerId)) {
                        LOGGER.info("User unauthorized");
                        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        return;
                    }
                    Connection connection = getConnection();
                    DeletePlayerDAO deletePlayerDAO = new DeletePlayerDAO(connection, playerId);
                    Integer result = (Integer) deletePlayerDAO.access().getOutputParam();
                    if (result == 1) {
                        resp.setStatus(HttpServletResponse.SC_OK);
                    } else {
                        resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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

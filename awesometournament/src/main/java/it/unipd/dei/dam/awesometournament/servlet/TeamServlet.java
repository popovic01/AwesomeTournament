package it.unipd.dei.dam.awesometournament.servlet;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import java.sql.Connection;

import it.unipd.dei.dam.awesometournament.database.GetTeamDAO;
import it.unipd.dei.dam.awesometournament.database.GetTeamPlayersDAO;
import it.unipd.dei.dam.awesometournament.database.GetTournamentByIdDAO;
import it.unipd.dei.dam.awesometournament.database.UpdateTeamDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Team;
import it.unipd.dei.dam.awesometournament.resources.entities.Tournament;
import it.unipd.dei.dam.awesometournament.resources.entities.Player;
import it.unipd.dei.dam.awesometournament.utils.SessionHelpers;

/**
 * Servlet responsible for handling requests related to teams.
 */
@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload"})
@MultipartConfig
public class TeamServlet extends AbstractDatabaseServlet{
    protected final static Logger LOGGER = LogManager.getLogger(TeamServlet.class,
            StringFormatterMessageFactory.INSTANCE);

    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.GET_TEAM);

        String path = req.getPathInfo();

        String[] parts = path.split("/");

        if(parts.length == 2) {
            int id = Integer.parseInt(parts[1]);
            try {
                GetTeamDAO dao = new GetTeamDAO(getConnection(), id);
                dao.access();
                Team team = dao.getOutputParam();
                if(team == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                req.setAttribute("team", team);

                if(SessionHelpers.isLogged(req)) {
                    GetTournamentByIdDAO tournamentByIdDAO = new GetTournamentByIdDAO(getConnection(), team.getTournamentId());
                    tournamentByIdDAO.access();
                    Tournament tournament = tournamentByIdDAO.getOutputParam();

                    int userId = SessionHelpers.getId(req);
                    if(userId == tournament.getCreatorUserId()) {
                        // the user is the admin of the tournament
                        req.setAttribute("tournamentOwner", true);
                    }
                    
                    if(userId == team.getCreatorUserId()) {
                        // the user is the creator of the team
                        req.setAttribute("teamOwner", true);
                    }
                }

                GetTeamPlayersDAO getTeamPlayersDAO = new GetTeamPlayersDAO(getConnection(), id);
                getTeamPlayersDAO.access();
                List<Player> players = getTeamPlayersDAO.getOutputParam();
                LOGGER.info("found players: "+players.size());
                req.setAttribute("players", players);

                req.getRequestDispatcher("/team.jsp").forward(req, resp);
            } catch (SQLException e) {
                LOGGER.info(e.getMessage());
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        InputStream inputStream = null; // input stream of the upload file

        // obtains the upload file part in this multipart request
        Part filePart = req.getPart("file");
        if (filePart != null) {
            // prints out some information for debugging
            LOGGER.info(filePart.getName());
            LOGGER.info(filePart.getSize());
            LOGGER.info(filePart.getContentType());

            // obtains input stream of the upload file
            inputStream = filePart.getInputStream();
        }

        try {
            int teamId = Integer.parseInt(req.getParameter("teamId"));
            GetTeamDAO getTeamDAO = new GetTeamDAO(getConnection(), teamId);
            Team team = getTeamDAO.access().getOutputParam();
            team.setLogo(inputStream);
            UpdateTeamDAO updateTeamDAO = new UpdateTeamDAO(getConnection(), team);
            Integer result = (Integer) updateTeamDAO.access().getOutputParam();
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
}

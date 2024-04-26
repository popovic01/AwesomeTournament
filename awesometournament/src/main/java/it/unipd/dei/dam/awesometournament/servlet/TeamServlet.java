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

import it.unipd.dei.dam.awesometournament.database.GetTeamDAO;
import it.unipd.dei.dam.awesometournament.database.GetTeamPlayersDAO;
import it.unipd.dei.dam.awesometournament.database.GetTournamentByIdDAO;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Team;
import it.unipd.dei.dam.awesometournament.resources.entities.Tournament;
import it.unipd.dei.dam.awesometournament.resources.entities.Player;
import it.unipd.dei.dam.awesometournament.utils.SessionHelpers;

@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload"})
@MultipartConfig
public class TeamServlet extends AbstractDatabaseServlet{
    protected final static Logger LOGGER = LogManager.getLogger(TeamServlet.class,
            StringFormatterMessageFactory.INSTANCE);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Create path components to save the file
        final Part filePart = request.getPart("file");
        final String fileName = getFileName(filePart);
        LOGGER.info("file name: " + fileName);

        OutputStream out = null;
        InputStream filecontent = null;
        final PrintWriter writer = response.getWriter();
        //destination path
        String path = "C:\\Users\\Milica Popovic\\IdeaProjects\\wa2324-dam\\awesometournament\\src\\main\\resources\\uploads";
        File folder = new File(path);
        LOGGER.info(path);
        Files.createDirectories(Paths.get(path));

        // Check if the folder exists
        if (!folder.exists()) {
            LOGGER.info("Folder does not exist.");
            return;
        }

        // Check if the folder is a directory
        if (!folder.isDirectory()) {
            LOGGER.info("Path is not a folder.");
            return;
        }

        // Check if the folder is readable
        if (!folder.canRead()) {
            LOGGER.info("Folder is not readable.");
            return;
        }

        // Check if the folder is writable
        if (!folder.canWrite()) {
            LOGGER.info("Folder is not writable.");
            return;
        }
        try {
            out = new FileOutputStream(path + "\\"
                    + fileName);
            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];
            LOGGER.info(filecontent);
            LOGGER.info(bytes.length);

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
                LOGGER.info(read);
            }
            writer.println("New file " + fileName + " created at " + path);
            LOGGER.info("File uploaded");
        } catch (Exception e) {
            LOGGER.info("You either did not specify a file to upload or are "
                    + "trying to upload a file to a protected or nonexistent "
                    + "location.");
            LOGGER.info("<br/> ERROR: " + e.getMessage());

            LOGGER.info("Problems during file upload. Error: " +
                    e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }

    private String getFileName(final Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}

package it.unipd.dei.dam.awesometournament.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Match;
import it.unipd.dei.dam.awesometournament.resources.entities.Team;
import it.unipd.dei.dam.awesometournament.resources.entities.Tournament;
import it.unipd.dei.dam.awesometournament.utils.SessionHelpers;
import it.unipd.dei.dam.awesometournament.database.*;
import it.unipd.dei.dam.awesometournament.utils.RankingEntry;
import it.unipd.dei.dam.awesometournament.utils.RankingScorersEntry;

/**
 * Servlet implementation for handling tournament-related operations
 */
@WebServlet(name = "LogoUploadServlet", urlPatterns = {"/uploadLogo"})
@MultipartConfig
public class TournamentServlet extends AbstractDatabaseServlet{
    protected final static Logger LOGGER = LogManager.getLogger(TournamentServlet.class,
            StringFormatterMessageFactory.INSTANCE);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.GET_TOURNAMENT);
        String path = req.getPathInfo();
        LOGGER.info("get");

        String[] parts = path.split("/");

        if (path.contains("team")) {
            try {
                String regex = ".*/(\\d+)$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(req.getRequestURI());

                if (matcher.matches()) {
                    int teamId = Integer.parseInt(matcher.group(1));
                    GetTeamDAO getTeamDAO = new GetTeamDAO(getConnection(), teamId);
                    var result = getTeamDAO.access().getOutputParam();
                    req.setAttribute("teamName", result.getName());
                }
                req.getRequestDispatcher("/jsp/components/team-form.jsp").forward(req, resp);
                return;
            } catch (Exception e) {
                LOGGER.error(e);
            }
        }

        if(parts.length == 2) {
            int id = Integer.parseInt(parts[1]);
            try {
                GetTournamentByIdDAO dao = new GetTournamentByIdDAO(getConnection(), id);
                dao.access();
                Tournament tournament = dao.getOutputParam();
                if(tournament == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                if(SessionHelpers.isLogged(req)) {
                    int loggedId = SessionHelpers.getId(req);
                    req.setAttribute("logged", true);
                    if(loggedId == tournament.getCreatorUserId()) {
                        req.setAttribute("owner", true);
                    }

                    var now = new Date();
                    var deadline = new Date(tournament.getDeadline().getTime());
                    var timeLeft = new Date(
                            deadline.getTime() -
                                    now.getTime());
                    var deadlinePassed = timeLeft.getTime() <= 0;
                    req.setAttribute("deadlinePassed", deadlinePassed);
                }

                GetTournamentMatchesDAO matchesDAO = new GetTournamentMatchesDAO(getConnection(), id);
                matchesDAO.access();
                List<Match> matches = matchesDAO.getOutputParam();

                GetTournamentTeamsDAO teamsDao = new GetTournamentTeamsDAO(getConnection(), id);
                teamsDao.access();
                List<Team> teams = teamsDao.getOutputParam();

                GetRankingDAO getRankingDAO = new GetRankingDAO(getConnection(), id);
                ArrayList<RankingEntry> ranking = getRankingDAO.access().getOutputParam();

                GetRankingScorersDAO getRankingScorersDAO = new GetRankingScorersDAO(getConnection(), id);
                ArrayList<RankingScorersEntry> rankingScorers = getRankingScorersDAO.access().getOutputParam();

                req.setAttribute("tournament", tournament);
                req.setAttribute("matches", matches);
                req.setAttribute("teams", teams);
                req.setAttribute("ranking", ranking);
                req.setAttribute("rankingScorers", rankingScorers);

                if(SessionHelpers.isLogged(req)) {
                    int userId = SessionHelpers.getId(req);
                    req.setAttribute("logged", true);
                    req.setAttribute("userId", userId);
                }

                req.getRequestDispatcher("/jsp/tournament.jsp").forward(req, resp);
            } catch (SQLException e) {
                LOGGER.info(e.getMessage());
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.POST_TOURNAMENT);

        InputStream inputStream = null; // input stream of the upload file

        // obtains the upload file part in this multipart request
        Part logo = req.getPart("logo");
        if (logo != null) {
            // prints out some information for debugging
            LOGGER.info(logo.getName());
            LOGGER.info(logo.getSize());
            LOGGER.info(logo.getContentType());

            // obtains input stream of the upload file
            inputStream = logo.getInputStream();
        }

        try {
            int tournamentId = Integer.parseInt(req.getParameter("tournamentId"));
            if (!SessionHelpers.isLogged(req)) {
                LOGGER.info("User unauthorized");
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            GetTournamentByIdDAO getTournamentByIdDAO = new GetTournamentByIdDAO(getConnection(), tournamentId);
            Tournament tournament = getTournamentByIdDAO.access().getOutputParam();
            tournament.setLogo(inputStream);
            UpdateTournamentDAO updateTournamentDAO = new UpdateTournamentDAO(getConnection(), tournament);
            Integer result = (Integer) updateTournamentDAO.access().getOutputParam();
            if (result == 1) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.sendRedirect("/tournament/" + tournamentId);
            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tournament ID must be an integer");
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }

    }
    
}

package it.unipd.dei.dam.awesometournament.servlet.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import it.unipd.dei.dam.awesometournament.database.*;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Team;
import it.unipd.dei.dam.awesometournament.resources.entities.Tournament;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherHandler;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.Method;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.Result;
import it.unipd.dei.dam.awesometournament.utils.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * A handler responsible for handling requests related to teams in a tournament.
 */
@MultipartConfig
public class TournamentTeamHandler extends RestMatcherHandler {

    protected final static Logger LOGGER = LogManager.getLogger(TournamentTeamHandler.class,
            StringFormatterMessageFactory.INSTANCE);
    private ResponsePackageNoData response;
    private ObjectMapper om;

    /**
     * A method responsible for handling requests for creating a team in the database belonging to a tournament with particular id.
     * @param req A body of a request which contains data for creating a team.
     * @param res
     * @param tournamentId An id of a tournament for which a team should be created in the database.
     * @throws IOException
     * @throws SQLException
     */
    void postTournamentTeam (HttpServletRequest req, HttpServletResponse res,
                                int tournamentId) throws IOException, SQLException {

        LogContext.setAction(Actions.POST_TOURNAMENT_TEAM);
        LOGGER.info("Received POST request");

        InputStream inputStream = null; // input stream of the upload file

        // obtains the upload file part in this multipart request
        Part filePart = null;
        try {
            filePart = req.getPart("file");
        }
        catch (Exception e) {
            LOGGER.error(e);
        }

        if (filePart != null) {
            if (filePart.getSize() > 0) {
                inputStream = filePart.getInputStream();
            }
        }

        try {
            //check if already exists a team with the same name for the tournament
            GetTournamentTeamsDAO getTeamsDao = new GetTournamentTeamsDAO(getConnection(), tournamentId);
            List<Team> teams = getTeamsDao.access().getOutputParam();
            if (teams.stream().anyMatch(x -> x.getName().toLowerCase().equals(req.getParameter("name").toLowerCase()))) {
                response = new ResponsePackageNoData(ResponseStatus.BAD_REQUEST,
                        "Team with the same name already exists for this tournament");
                res.getWriter().print(om.writeValueAsString(response));
                res.setStatus(HttpServletResponse.SC_CONFLICT);
                return;
            }
            //get the number of maxTeams for the tournament
            GetTournamentByIdDAO getTournamentByIdDAO = new GetTournamentByIdDAO(getConnection(), tournamentId);
            Tournament tournament = getTournamentByIdDAO.access().getOutputParam();
            int maxTeams = tournament.getMaxTeams();
            LOGGER.info("MAX TEAMS: " + maxTeams);
            if(teams.size() == maxTeams) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "Reached maximum number of teams");
            }
            else {
                Team team = new Team(0, req.getParameter("name"), inputStream, getIdOfLoggedInUser(req), tournamentId);
                CreateTeamDAO createTeamDAOTeamDAO = new CreateTeamDAO(getConnection(), team);
                LOGGER.info(team.toString());
                Integer result = createTeamDAOTeamDAO.access().getOutputParam();
                res.setContentType("application/json");
                PrintWriter out = res.getWriter();
                if (result != 0) {
                    //route to tournament/id page
                    try {
                        String redirectUrl = req.getContextPath() + "/tournament/" + tournamentId;
                        out.print("{ \"redirect\": \"" + redirectUrl + "\" }");
                        out.flush();
                    } catch (Exception e) {
                        LOGGER.error(e);
                    }
                } else {
                    res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }
        } catch (NumberFormatException e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Team ID must be an integer");
        } catch (SQLException e) {
            res.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }
    }

    /**
     * A method responsible for handling requests for updating a team in the database belonging to a tournament with particular id.
     * @param req A body of a request which contains data for updating a team.
     * @param res
     * @param tournamentId An id of a tournament for which a team should be updated in the database.
     * @throws IOException
     * @throws SQLException
     */
    void putTournamentTeam (HttpServletRequest req, HttpServletResponse res,
                             int tournamentId) throws IOException, SQLException {

        LogContext.setAction(Actions.PUT_TEAM);
        LOGGER.info("Received PUT request");

        InputStream inputStream = null; // input stream of the upload file
        PrintWriter out = res.getWriter();
        int teamId = Integer.parseInt(req.getParameter("teamId"));

        // obtains the upload file part in this multipart request
        Part filePart = null;
        try {
            filePart = req.getPart("file");
        }
        catch (Exception e) {
            LOGGER.error(e);
        }

        if (filePart != null) {
            if (filePart.getSize() > 0) {
                inputStream = filePart.getInputStream();
            }
        }

        try {
            //check if already exists a team with the same name for the tournament
            GetTournamentTeamsDAO getTeamsDao = new GetTournamentTeamsDAO(getConnection(), tournamentId);
            List<Team> teams = getTeamsDao.access().getOutputParam();
            Optional<Team> teamFromDb = teams.stream()
                    .filter(x -> x.getName().equalsIgnoreCase(req.getParameter("name")))
                    .findFirst();
            if (teamFromDb.isPresent() && teamFromDb.get().getId() != teamId) {
                response = new ResponsePackageNoData(ResponseStatus.BAD_REQUEST,
                        "Team with the same name already exists for this tournament");
                res.getWriter().print(om.writeValueAsString(response));
                res.setStatus(HttpServletResponse.SC_CONFLICT);
                return;
            }
            Team team = new Team(teamId, req.getParameter("name"), inputStream);
            UpdateTeamDAO updateTeamDAO = new UpdateTeamDAO(getConnection(), team);
            LOGGER.info(teamId);
            LOGGER.info(team.toString());
            Integer result = updateTeamDAO.access().getOutputParam();
            if (result == 1 || result == -1) {
                res.setContentType("application/json");
                String redirectUrl = req.getContextPath() + "/tournament/" + tournamentId;
                out.print("{ \"redirect\": \"" + redirectUrl + "\" }");
                out.flush();
            } else {
                res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (NumberFormatException e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Team ID must be an integer");
        } catch (SQLException e) {
            res.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }
    }

    private int getIdOfLoggedInUser(HttpServletRequest req) throws SQLException {
        if (!SessionHelpers.isLogged(req))
            return -1;

        LOGGER.info("User logged in");
        return SessionHelpers.getId(req);
    }

    @Override
    public Result handle(Method method, HttpServletRequest req, HttpServletResponse res,
                                            String[] params) throws ServletException, IOException {

        LogContext.setIPAddress(req.getRemoteAddr());
        om = new ObjectMapper();

        try {
            int tournamentId = Integer.parseInt(params[0]);
            switch (method) {
                case POST:
                    //only logged-in users can add a team
                    if (getIdOfLoggedInUser(req) == -1) {
                        LOGGER.info("User not logged in");
                        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response = new ResponsePackageNoData(ResponseStatus.UNAUTHORIZED,
                                "User not logged in");
                        res.getWriter().print(om.writeValueAsString(response));
                        return Result.STOP;
                    }
                    postTournamentTeam(req, res, tournamentId);
                    break;
                case PUT:
                    //only logged-in users can add a team
                    if (getIdOfLoggedInUser(req) == -1) {
                        LOGGER.info("User not logged in");
                        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response = new ResponsePackageNoData(ResponseStatus.UNAUTHORIZED,
                                "User not logged in");
                        res.getWriter().print(om.writeValueAsString(response));
                        return Result.STOP;
                    }
                    putTournamentTeam(req, res, tournamentId);
                    break;
                default:
                    return Result.STOP;
            }
        } catch (NumberFormatException e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            response = new ResponsePackageNoData(ResponseStatus.BAD_REQUEST,
                    "Something went wrong: " + e.getMessage());
            res.getWriter().print(om.writeValueAsString(response));
        } catch (InvalidFormatException e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            response = new ResponsePackageNoData(ResponseStatus.BAD_REQUEST,
                    "Something went wrong: " + e.getMessage());
            res.getWriter().print(om.writeValueAsString(response));
        } catch (SQLException e) {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response = new ResponsePackageNoData(ResponseStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong: " + e.getMessage())  ;
            res.getWriter().print(om.writeValueAsString(response));
        }
        return Result.CONTINUE;
    }
}

package it.unipd.dei.dam.awesometournament.servlet.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import it.unipd.dei.dam.awesometournament.database.*;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Team;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherHandler;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.Method;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.Result;
import it.unipd.dei.dam.awesometournament.utils.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * A handler responsible for handling requests related to teams in a tournament.
 */
public class TournamentTeamHandler extends RestMatcherHandler {

    protected final static Logger LOGGER = LogManager.getLogger(TournamentTeamHandler.class,
            StringFormatterMessageFactory.INSTANCE);
    private ResponsePackageNoData response;
    private ObjectMapper om;

    /**
     * A method responsible for handling requests for retrieving all the team from the database belonging to a tournament with particular id.
     * @param res
     * @param tournamentId An id of a tournament for which all the teams should be retrieved from the database.
     * @throws IOException
     * @throws SQLException
     */
    void getTournamentTeams(HttpServletResponse res, int tournamentId) throws IOException, SQLException {
        LOGGER.info("Received GET request");
        GetTournamentTeamsDAO dao = new GetTournamentTeamsDAO(getConnection(), tournamentId);
        List<Team> teams = dao.access().getOutputParam();

        om = new ObjectMapper();

        if (!teams.isEmpty()) {
            response = new ResponsePackage<List<Team>>(teams, ResponseStatus.OK,
                    "Teams for the tournament found");
        } else {
            response = new ResponsePackageNoData(ResponseStatus.NOT_FOUND,
                    "No teams in the tournament");
        }
        res.getWriter().print(om.writeValueAsString(response));
    }

    /**
     * A method responsible for handling requests for creating a team in the database belonging to a tournament with particular id.
     * @param req A body of a request which contains data for creating a team.
     * @param res
     * @param tournamentId An id of a tournament for which a team should be created in the database.
     * @param userId An id of a logged-in user.
     * @throws IOException
     * @throws SQLException
     */
    void postTournamentTeam (HttpServletRequest req, HttpServletResponse res,
                                int tournamentId, int userId)
            throws IOException, SQLException {
        LOGGER.info("Received POST request");
        String requestBody = BodyTools.getRequestBody(req);
        LOGGER.info(requestBody);
        om = new ObjectMapper();

        //team from the request body
        Team team = om.readValue(requestBody, Team.class);

        //check if already exists a team with the same name for the tournament
        GetTournamentTeamsDAO getTeamsDao = new GetTournamentTeamsDAO(getConnection(), tournamentId);
        List<Team> teams = getTeamsDao.access().getOutputParam();
        if (teams.stream().anyMatch(x -> x.getName().equals(team.getName()))) {
            response = new ResponsePackageNoData(ResponseStatus.BAD_REQUEST,
                    "Team with the same name already exists for this tournament");
            res.getWriter().print(om.writeValueAsString(response));
            return;
        }

        team.setTournamentId(tournamentId);
        team.setCreatorUserId(userId);
        LOGGER.info(team.toString());
        CreateTeamDAO dao = new CreateTeamDAO(getConnection(), team);
        Integer result = dao.access().getOutputParam();
        team.setId(result);
        if (result != 0) {
            LOGGER.info("Team created with id: " + result);
            response = new ResponsePackage<Team>(team, ResponseStatus.CREATED,
                    "Team successfully added");
        } else {
            response = new ResponsePackageNoData(ResponseStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong");
        }
        res.getWriter().print(om.writeValueAsString(response));
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
                case GET:
                    getTournamentTeams(res, tournamentId);
                    break;
                case POST:
                    //only logged-in users can add a team
                    if (getIdOfLoggedInUser(req) == -1) {
                        LOGGER.info("User not logged in");
                        response = new ResponsePackageNoData(ResponseStatus.UNAUTHORIZED,
                                "User not logged in");
                        res.getWriter().print(om.writeValueAsString(response));
                        return Result.STOP;
                    }
                    postTournamentTeam(req, res, tournamentId, getIdOfLoggedInUser(req));
                    break;
                default:
                    return Result.STOP;
            }
        } catch (NumberFormatException e) {
            response = new ResponsePackageNoData(ResponseStatus.BAD_REQUEST,
                    "Something went wrong: " + e.getMessage());
            res.getWriter().print(om.writeValueAsString(response));
        } catch (InvalidFormatException e) {
            response = new ResponsePackageNoData(ResponseStatus.BAD_REQUEST,
                    "Something went wrong: " + e.getMessage());
            res.getWriter().print(om.writeValueAsString(response));
        } catch (SQLException e) {
            response = new ResponsePackageNoData(ResponseStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong: " + e.getMessage())  ;
            res.getWriter().print(om.writeValueAsString(response));
        }
        return Result.CONTINUE;
    }
}

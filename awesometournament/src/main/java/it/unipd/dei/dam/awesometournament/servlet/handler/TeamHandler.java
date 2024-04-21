package it.unipd.dei.dam.awesometournament.servlet.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unipd.dei.dam.awesometournament.database.DeleteTeamDAO;
import it.unipd.dei.dam.awesometournament.database.GetTeamDAO;
import it.unipd.dei.dam.awesometournament.database.UpdateTeamDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Team;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherHandler;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet;
import it.unipd.dei.dam.awesometournament.utils.ResponsePackage;
import it.unipd.dei.dam.awesometournament.utils.ResponseStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import java.io.IOException;
import java.sql.SQLException;

public class TeamHandler extends RestMatcherHandler {

    protected final static Logger LOGGER = LogManager.getLogger(TeamHandler.class,
            StringFormatterMessageFactory.INSTANCE);
    private ResponsePackage response;
    private ObjectMapper om;

    void getTeam (HttpServletRequest req, HttpServletResponse res, int teamId) throws ServletException, IOException, SQLException {
        LOGGER.info("Received GET request");
        om = new ObjectMapper();

        GetTeamDAO dao = new GetTeamDAO(getConnection(), teamId);
        Team team = dao.access().getOutputParam();

        if (team != null) {
            response = new ResponsePackage(team, ResponseStatus.OK,
                    "Team found");
        } else {
            response = new ResponsePackage(ResponseStatus.NOT_FOUND,
                    "Team not found");
        }
        res.getWriter().print(om.writeValueAsString(response));
    }

    void putTeam (HttpServletRequest req, HttpServletResponse res, int teamId) throws ServletException, IOException, SQLException{
        LOGGER.info("Received PUT request");
        String requestBody = getRequestBody(req);
        LOGGER.info(requestBody);
        om = new ObjectMapper();
        Team team = om.readValue(requestBody, Team.class);
        team.setId(teamId);
        LOGGER.info(team.toString());
        UpdateTeamDAO dao = new UpdateTeamDAO(getConnection(), team);
        Integer result = dao.access().getOutputParam();

        if (result == 1) {
            response = new ResponsePackage(ResponseStatus.OK,
                    "Team " + team.getName() + " successfully updated");
        } else {
            response = new ResponsePackage(ResponseStatus.NOT_FOUND,
                    "Team not found");
        }
        res.getWriter().print(om.writeValueAsString(response));
    }

    void deleteTeam (HttpServletRequest req, HttpServletResponse res, int teamId) throws ServletException, IOException, SQLException{
        LOGGER.info("Received DELETE request");
        DeleteTeamDAO deleteTeamDAO = new DeleteTeamDAO(getConnection(), teamId);
        Integer result = deleteTeamDAO.access().getOutputParam();

        if (result == 1) {
            response = new ResponsePackage(ResponseStatus.OK,
                    "Team successfully deleted");
        } else {
            response = new ResponsePackage(ResponseStatus.NOT_FOUND,
                    "Team not found");
        }
        res.getWriter().print(om.writeValueAsString(response));
    }

    @Override
    public RestMatcherServlet.Result handle(RestMatcherServlet.Method method, HttpServletRequest req, HttpServletResponse res,
                                            String[] params) throws ServletException, IOException {

        LogContext.setIPAddress(req.getRemoteAddr());
        om = new ObjectMapper();

        int teamId = Integer.parseInt(params[0]);

        try {
            switch (method) {
                case GET:
                    getTeam(req, res, teamId);
                    LogContext.setAction(Actions.GET_TEAM);
                    break;
                case PUT:
                    putTeam(req, res, teamId);
                    LogContext.setAction(Actions.PUT_TEAM);
                    break;
                case DELETE:
                    deleteTeam(req, res, teamId);
                    LogContext.setAction(Actions.DELETE_TEAM);
                    break;
                default:
                    return RestMatcherServlet.Result.STOP;
            }
        } catch (NumberFormatException e) {
            response = new ResponsePackage(ResponseStatus.BAD_REQUEST,
                    "Team ID must be an integer");
            res.getWriter().print(om.writeValueAsString(response));
        } catch (SQLException e) {
            response = new ResponsePackage(ResponseStatus.SERVICE_UNAVAILABLE,
                    "Something went wrong: " + e.getMessage())  ;
            res.getWriter().print(om.writeValueAsString(response));
        }
        return RestMatcherServlet.Result.CONTINUE;
    }
}

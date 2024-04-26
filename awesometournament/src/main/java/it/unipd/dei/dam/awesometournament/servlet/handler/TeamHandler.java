package it.unipd.dei.dam.awesometournament.servlet.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import it.unipd.dei.dam.awesometournament.database.DeleteTeamDAO;
import it.unipd.dei.dam.awesometournament.database.GetTeamDAO;
import it.unipd.dei.dam.awesometournament.database.UpdateTeamDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Team;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherHandler;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.Method;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.Result;
import it.unipd.dei.dam.awesometournament.utils.BodyTools;
import it.unipd.dei.dam.awesometournament.utils.ResponsePackage;
import it.unipd.dei.dam.awesometournament.utils.ResponsePackageNoData;
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
    private ResponsePackageNoData response;
    private ObjectMapper om;

    void getTeam (HttpServletRequest req, HttpServletResponse res, int teamId) throws ServletException, IOException, SQLException {
        LOGGER.info("Received GET request");
        om = new ObjectMapper();

        GetTeamDAO dao = new GetTeamDAO(getConnection(), teamId);
        Team team = dao.access().getOutputParam();

        if (team != null) {
            response = new ResponsePackage<Team>(team, ResponseStatus.OK,
                    "Team found");
        } else {
            response = new ResponsePackageNoData(ResponseStatus.NOT_FOUND,
                    "Team not found");
        }
        res.getWriter().print(om.writeValueAsString(response));
    }

    void putTeam (HttpServletRequest req, HttpServletResponse res, int teamId) throws ServletException, IOException, SQLException{
        LOGGER.info("Received PUT request");
        String requestBody = BodyTools.getRequestBody(req);
        LOGGER.info(requestBody);
        om = new ObjectMapper();

        Team team = om.readValue(requestBody, Team.class);
        team.setId(teamId);

        UpdateTeamDAO dao = new UpdateTeamDAO(getConnection(), team);
        Integer result = dao.access().getOutputParam();

        if (result == 1) {
            response = new ResponsePackageNoData(ResponseStatus.OK,
                    "Team successfully updated");
        } else {
            response = new ResponsePackageNoData(ResponseStatus.NOT_FOUND,
                    "Team not found");
        }
        res.getWriter().print(om.writeValueAsString(response));
    }

    void deleteTeam (HttpServletRequest req, HttpServletResponse res, int teamId) throws ServletException, IOException, SQLException{
        LOGGER.info("Received DELETE request");
        DeleteTeamDAO deleteTeamDAO = new DeleteTeamDAO(getConnection(), teamId);
        Integer result = deleteTeamDAO.access().getOutputParam();

        if (result == 1) {
            response = new ResponsePackageNoData(ResponseStatus.OK,
                    "Team successfully deleted");
        } else {
            response = new ResponsePackageNoData(ResponseStatus.NOT_FOUND,
                    "Team not found");
        }
        res.getWriter().print(om.writeValueAsString(response));
    }

    @Override
    public Result handle(Method method, HttpServletRequest req, HttpServletResponse res,
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
                    return Result.STOP;
            }
        } catch (NumberFormatException | InvalidFormatException | SQLException e) {
            response = new ResponsePackageNoData(ResponseStatus.BAD_REQUEST,
                    "Something went wrong" + e.getMessage());
            res.getWriter().print(om.writeValueAsString(response));
        }

        return Result.CONTINUE;
    }
}

package it.unipd.dei.dam.awesometournament.servlet.handler;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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

/**
 * A handler responsible for handling requests related to teams.
 */
public class TeamHandler extends RestMatcherHandler {

    protected final static Logger LOGGER = LogManager.getLogger(TeamHandler.class,
            StringFormatterMessageFactory.INSTANCE);
    private ResponsePackageNoData response;
    private ObjectMapper om;

    /**
     * A method responsible for handling requests for retrieving a team from the database with particular id.
     * @param req
     * @param res
     * @param teamId An id of a team which should be retrieved from the database.
     * @throws ServletException
     * @throws IOException
     * @throws SQLException
     */
    void getTeam (HttpServletRequest req, HttpServletResponse res, int teamId) throws ServletException, IOException, SQLException {
        LogContext.setAction(Actions.GET_TEAM);
        LOGGER.info("Received GET request");
        om = new ObjectMapper();
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
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
    /**
     * A method responsible for handling requests for updating a team in the database with particular id.
     * @param req A body of a request which contains data for updating a team.
     * @param res
     * @param teamId An id of a team which should be updated in the database.
     * @throws ServletException
     * @throws IOException
     * @throws SQLException
     */
    void putTeam (HttpServletRequest req, HttpServletResponse res, int teamId) throws ServletException, IOException, SQLException{
        LogContext.setAction(Actions.PUT_TEAM);
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

    /**
     * A method responsible for handling requests for deleting a team in the database with particular id.
     * @param req
     * @param res
     * @param teamId An id of a team which should be deleted from the database.
     * @throws ServletException
     * @throws IOException
     * @throws SQLException
     */
    void deleteTeam (HttpServletRequest req, HttpServletResponse res, int teamId) throws ServletException, IOException, SQLException{
        LogContext.setAction(Actions.DELETE_TEAM);
        LOGGER.info("Received DELETE request");
        DeleteTeamDAO deleteTeamDAO = new DeleteTeamDAO(getConnection(), teamId);
        Integer result = 0;
        try {
            result = deleteTeamDAO.access().getOutputParam();
        } catch (Exception e) {
            res.sendError( 500);
            response = new ResponsePackageNoData
                    (ResponseStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
            res.getWriter().print(om.writeValueAsString(response));
        }

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
                case DELETE:
                    deleteTeam(req, res, teamId);
                    break;
                case GET:
                    getTeam(req, res, teamId);
                    break;
                case PUT:
                    putTeam(req, res, teamId);
                    break;

                default:
                    res.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                    response = new ResponsePackageNoData(ResponseStatus.METHOD_NOT_ALLOWED,
                            "Method not allowed");
                    res.getWriter().print(om.writeValueAsString(response));
                    return Result.STOP;
            }
        } catch (NumberFormatException e) {
            response = new ResponsePackageNoData(ResponseStatus.BAD_REQUEST,
                    "Something went wrong" + e.getMessage());
            res.getWriter().print(om.writeValueAsString(response));
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (InvalidFormatException e) {
            response = new ResponsePackageNoData(ResponseStatus.BAD_REQUEST,
                    "Something went wrong: " + e.getMessage());
            res.getWriter().print(om.writeValueAsString(response));
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (SQLException e) {
            response = new ResponsePackageNoData(ResponseStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong: " + e.getMessage())  ;
            res.getWriter().print(om.writeValueAsString(response));
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return Result.CONTINUE;
    }
}

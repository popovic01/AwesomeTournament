package it.unipd.dei.dam.awesometournament.servlet.handler;

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import java.io.IOException;
import java.sql.SQLException;

/**
 * A handler responsible for handling requests related to teams.
 */
public class TeamHandler extends RestMatcherHandler {

    protected final static Logger LOGGER = LogManager.getLogger(TeamHandler.class,
            StringFormatterMessageFactory.INSTANCE);
    private ResponsePackageNoData response;
    private ObjectMapper om;

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
                case DELETE:
                    deleteTeam(req, res, teamId);
                    break;
                default:
                    return Result.STOP;
            }
        } catch (NumberFormatException e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            response = new ResponsePackageNoData(ResponseStatus.BAD_REQUEST,
                    "Something went wrong" + e.getMessage());
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

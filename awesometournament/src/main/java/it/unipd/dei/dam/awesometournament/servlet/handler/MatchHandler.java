package it.unipd.dei.dam.awesometournament.servlet.handler;

import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import it.unipd.dei.dam.awesometournament.database.GetMatchDAO;
import it.unipd.dei.dam.awesometournament.database.UpdateMatchDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Match;
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

public class MatchHandler extends RestMatcherHandler{

    protected final static Logger LOGGER = LogManager.getLogger(PlayerHandler.class,
            StringFormatterMessageFactory.INSTANCE);
    ObjectMapper om;
    ResponsePackageNoData response;

    @Override
    public Result handle(Method method, HttpServletRequest req, HttpServletResponse res,
            String[] params) throws ServletException, IOException {

        LogContext.setIPAddress(req.getRemoteAddr());
        om = new ObjectMapper();
        int matchId = Integer.parseInt(params[0]);

        try {
            switch (method) {
                case GET:
                    getMatch(req, res, matchId);
                    break;
                case PUT:
                    updateMatch(req, res, matchId);
                    break;
                default:
                    return Result.STOP;
            }
        } catch (NumberFormatException e) {
            response = new ResponsePackageNoData(ResponseStatus.BAD_REQUEST,
                    "ID must be an integer");
            res.getWriter().print(om.writeValueAsString(response));
        } catch (SQLException e) {
            response = new ResponsePackageNoData(ResponseStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong: " + e.getMessage());
            res.getWriter().print(om.writeValueAsString(response));
        }
        return Result.CONTINUE;
    }

    private void getMatch(HttpServletRequest req, HttpServletResponse res, int matchId)
            throws SQLException, JsonProcessingException, IOException {
        LogContext.setAction(Actions.PUT_MATCH);
        LOGGER.info("Received GET request");
        om.setDateFormat(new StdDateFormat());

        GetMatchDAO getMatchDAO = new GetMatchDAO(getConnection(), matchId);
        Match match = (Match) getMatchDAO.access().getOutputParam();

        if (match != null) {
            res.getWriter().println(om.writeValueAsString(match));
            response = new ResponsePackage<>(match, ResponseStatus.OK,
                    "Match found");
        } else {
            res.getWriter().println(om.writeValueAsString(match));
            response = new ResponsePackageNoData(ResponseStatus.NOT_FOUND,
                    "Match not found");
        }
    }

    private void updateMatch(HttpServletRequest req, HttpServletResponse res, int matchId)
            throws SQLException, JsonProcessingException, IOException {
        LogContext.setAction(Actions.PUT_MATCH);
        LOGGER.info("Received PUT request");
        om.setDateFormat(new StdDateFormat());

        String requestBody = BodyTools.getRequestBody(req);
        LOGGER.info(requestBody);

        Match match = (Match) om.readValue(requestBody, Match.class);
        match.setId(matchId);
        match.setResult(null);
        LOGGER.info(match.toString());

        UpdateMatchDAO updateMatchDAO = new UpdateMatchDAO(getConnection(), match);
        Integer result = (Integer) updateMatchDAO.access().getOutputParam();

        if (result == 1) {
            response = new ResponsePackageNoData(ResponseStatus.OK,
                    "Match updated");
            res.getWriter().println(om.writeValueAsString(response));
        } else {
            response = new ResponsePackageNoData(ResponseStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong");
            res.getWriter().println(om.writeValueAsString(response));
        }
    }
}

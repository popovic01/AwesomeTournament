package it.unipd.dei.dam.awesometournament.servlet.handler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.BufferedReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import it.unipd.dei.dam.awesometournament.database.GetMatchDAO;
import it.unipd.dei.dam.awesometournament.database.UpdateMatchDAO;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Match;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.Handler;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.Method;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

public class MatchHandler implements Handler{

    protected final static Logger LOGGER = LogManager.getLogger(PlayerHandler.class,
            StringFormatterMessageFactory.INSTANCE);

    String getRequestBody(HttpServletRequest req) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        return requestBody.toString();
    }
    
    @Override
    public Result handle(Method method, HttpServletRequest req, HttpServletResponse res, Connection connection,
            String[] params) throws ServletException, IOException {
        
        LogContext.setIPAddress(req.getRemoteAddr());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new StdDateFormat());
        int matchId = Integer.parseInt(params[0]);
        Match match;
        Integer result;

        try {
                switch (method) {
                    case GET:
                        LogContext.setAction(Actions.PUT_MATCH);
                        LOGGER.info("Received GET request");

                        GetMatchDAO getMatchDAO = new GetMatchDAO(connection, matchId);
                        match = (Match) getMatchDAO.access().getOutputParam();

                        if (match != null) {
                            res.setContentType("application/json");
                            res.getWriter().println(objectMapper.writeValueAsString(match));
                        } else {
                            res.sendError(HttpServletResponse.SC_NOT_FOUND, "The match doesn't exist");
                        }

                        break;
                    case PUT:
                        LogContext.setAction(Actions.PUT_MATCH);
                        LOGGER.info("Received PUT request");

                        String requestBody = getRequestBody(req);
                        LOGGER.info(requestBody);

                        match = (Match) objectMapper.readValue(requestBody, Match.class);
                        match.setId(matchId);
                        match.setResult(null);  
                        LOGGER.info(match.toString());

                        UpdateMatchDAO updateMatchDAO = new UpdateMatchDAO(connection, match);
                        result = (Integer) updateMatchDAO.access().getOutputParam();

                        if (result == 1) {
                            res.setStatus(HttpServletResponse.SC_OK);
                        } else {
                            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        }

                        break;
                    case DELETE:
                        break;
                    default:
                        return Result.STOP;
                }
            } catch (NumberFormatException e) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Match ID must be an integer");
            } catch (SQLException e) {
                res.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            }
            return Result.CONTINUE;
    }
    
}
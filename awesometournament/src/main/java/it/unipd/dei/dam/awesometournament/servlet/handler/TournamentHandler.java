package it.unipd.dei.dam.awesometournament.servlet.handler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import it.unipd.dei.dam.awesometournament.database.*;
import it.unipd.dei.dam.awesometournament.resources.entities.Tournament;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherHandler;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.*;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.utils.BodyTools;
import it.unipd.dei.dam.awesometournament.utils.SessionHelpers;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

public class TournamentHandler extends RestMatcherHandler {
    protected final static Logger LOGGER = LogManager.getLogger(TournamentIdHandler.class,
            StringFormatterMessageFactory.INSTANCE);

    void postTournament (HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException{
        LogContext.setAction(Actions.POST_TOURNAMENT);
        LOGGER.info("Received POST request");

        String requestBody = BodyTools.getRequestBody(req);
        LOGGER.info(requestBody);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new StdDateFormat());

        Tournament tournament = objectMapper.readValue(requestBody, Tournament.class);
        LOGGER.info(tournament.toString());

        CreateTournamentDAO createTournamentDAO = new CreateTournamentDAO(getConnection(), tournament);
        Integer newId = createTournamentDAO.access().getOutputParam();
        if (newId != null) {
            LOGGER.info("Tournament created with id %d", newId);
            res.setStatus(HttpServletResponse.SC_CREATED);
        }
        else res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    void getTournament (HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException{
        LogContext.setAction(Actions.GET_TOURNAMENT);
        LOGGER.info("Received GET request");

        GetTournamentDAO getTournamentDAO = new GetTournamentDAO(getConnection());
        ArrayList<Tournament> tournaments = getTournamentDAO.access().getOutputParam();

        if (!tournaments.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setDateFormat(new StdDateFormat());
            res.setContentType("application/json");
            res.getWriter().println(objectMapper.writeValueAsString(tournaments));
        }
        else LOGGER.info("No tournaments in the database");

    }

    @Override
    public Result handle(Method method, HttpServletRequest req, HttpServletResponse res,
                         String[] params) throws ServletException, IOException {

        LogContext.setIPAddress(req.getRemoteAddr());

        try {
            switch (method) {
                case GET:
                    getTournament(req, res);
                    break;
                case POST:
                    postTournament(req, res);
                    break;
                default:
                    return Result.STOP;
            }
        } catch (NumberFormatException e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tournament ID must be an integer");
        } catch (SQLException e) {
            res.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }
        return Result.CONTINUE;
    }
}
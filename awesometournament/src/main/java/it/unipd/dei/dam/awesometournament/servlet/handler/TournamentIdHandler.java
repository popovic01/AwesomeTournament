package it.unipd.dei.dam.awesometournament.servlet.handler;

import java.io.IOException;
import java.sql.SQLException;
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

public class TournamentIdHandler extends RestMatcherHandler {
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

    void getTournament (HttpServletRequest req, HttpServletResponse res, int tournamentID) throws IOException, SQLException{
        LogContext.setAction(Actions.GET_TOURNAMENT);
        LOGGER.info("Received GET request");

        GetTournamentByIdDAO getTournamentDAO = new GetTournamentByIdDAO(getConnection(), tournamentID);
        Tournament tournament = getTournamentDAO.access().getOutputParam();

        if (tournament != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            res.setContentType("application/json");
            res.getWriter().println(objectMapper.writeValueAsString(tournament));
        }
        else res.sendError(HttpServletResponse.SC_NOT_FOUND, "The tournament doesn't exist");
    }

    void putTournament (HttpServletRequest req, HttpServletResponse res, int tournamentId) throws IOException, SQLException {
        LogContext.setAction(Actions.PUT_TOURNAMENT);
        LOGGER.info("Received PUT request");

        String requestBody = BodyTools.getRequestBody(req);
        LOGGER.info(requestBody);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new StdDateFormat());

        Tournament tournament = objectMapper.readValue(requestBody, Tournament.class);
        tournament.setId(tournamentId);

        LOGGER.info(tournament.toString());

        UpdateTournamentDAO updateTournamentDAO = new UpdateTournamentDAO(getConnection(), tournament);
        Integer result = updateTournamentDAO.access().getOutputParam();
        if (result == 1) res.setStatus(HttpServletResponse.SC_OK);
        else res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    void deleteTournament (HttpServletRequest req, HttpServletResponse res, int tournamentId) throws IOException, SQLException{
        LogContext.setAction(Actions.DELETE_TOURNAMENT);
        LOGGER.info("Received DELETE request");
        DeleteTournamentDAO deleteTournamentDAO = new DeleteTournamentDAO(getConnection(), tournamentId);
        Integer result = deleteTournamentDAO.access().getOutputParam();
        if (result == 1) res.setStatus(HttpServletResponse.SC_OK);
        else res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    private boolean isUserAuthorized(HttpServletRequest req, int tournamentId) throws SQLException {
        if (!SessionHelpers.isLogged(req)) return false;

        LOGGER.info("User logged");
        int userId = SessionHelpers.getId(req);
        GetTournamentByIdDAO getTournamentByIdDAO = new GetTournamentByIdDAO(getConnection(), tournamentId);
        Tournament tournament = getTournamentByIdDAO.access().getOutputParam();

        if (tournament.getCreatorUserId() != userId) return false;
        LOGGER.info("User authorized");
        return true;
    }

    @Override
    public Result handle(Method method, HttpServletRequest req, HttpServletResponse res,
                         String[] params) throws ServletException, IOException {

        LogContext.setIPAddress(req.getRemoteAddr());
        int tournamentId = Integer.parseInt(params[0]);

        try {
            switch (method) {
                case GET:
                    getTournament(req, res, tournamentId);
                    break;
                case PUT:
                    if (!isUserAuthorized(req, tournamentId)) {
                        LOGGER.info("User unauthorized");
                        res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                        return Result.STOP;
                    }
                    putTournament(req, res, tournamentId);
                    break;
                case DELETE:
                    if (!isUserAuthorized(req, tournamentId)) {
                        LOGGER.info("User unauthorized");
                        res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                        return Result.STOP;
                    }
                    deleteTournament(req, res, tournamentId);
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
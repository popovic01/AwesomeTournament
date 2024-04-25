package it.unipd.dei.dam.awesometournament.servlet.handler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import it.unipd.dei.dam.awesometournament.database.*;
import it.unipd.dei.dam.awesometournament.resources.entities.Tournament;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherHandler;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.*;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.utils.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

public class TournamentHandler extends RestMatcherHandler {
    protected final static Logger LOGGER = LogManager.getLogger(TournamentIdHandler.class,
            StringFormatterMessageFactory.INSTANCE);
    ObjectMapper om;
    ResponsePackageNoData response;

    void postTournament (HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException{
        LogContext.setAction(Actions.POST_TOURNAMENT);
        LOGGER.info("Received POST request");

        String requestBody = BodyTools.getRequestBody(req);
        LOGGER.info(requestBody);

        om.setDateFormat(new StdDateFormat());

        Tournament tournament = om.readValue(requestBody, Tournament.class);
        LOGGER.info(tournament.toString());

        CreateTournamentDAO createTournamentDAO = new CreateTournamentDAO(getConnection(), tournament);
        Integer newId = createTournamentDAO.access().getOutputParam();
        if (newId != null) {
            LOGGER.info("Tournament created with id %d", newId);
            response = new ResponsePackage<>(tournament, ResponseStatus.CREATED, "Tournament created");
            res.getWriter().print(om.writeValueAsString(response));
        }
        else {
            response = new ResponsePackageNoData(ResponseStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
            res.getWriter().print(om.writeValueAsString(response));        }
    }

    void getTournament (HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException{
        LogContext.setAction(Actions.GET_TOURNAMENT);
        LOGGER.info("Received GET request");

        GetTournamentsDAO getTournamentDAO = new GetTournamentsDAO(getConnection());
        List<Tournament> tournaments = getTournamentDAO.access().getOutputParam();

        if (!tournaments.isEmpty()) {
            om.setDateFormat(new StdDateFormat());
            response = new ResponsePackage<>(tournaments, ResponseStatus.OK, "Tournaments found");
            res.getWriter().print(om.writeValueAsString(response));
        }
        else {
            LOGGER.info("No tournaments in the database");
            response = new ResponsePackageNoData(ResponseStatus.OK, "No tournaments in the database");
            res.getWriter().print(om.writeValueAsString(response));
        }
    }

    @Override
    public Result handle(Method method, HttpServletRequest req, HttpServletResponse res,
                         String[] params) throws ServletException, IOException {

        LogContext.setIPAddress(req.getRemoteAddr());
        om = new ObjectMapper();

        try {
            switch (method) {
                case GET:
                    getTournament(req, res);
                    break;
                case POST:
                    postTournament(req, res);
                    break;
                default:
                    response = new ResponsePackageNoData(ResponseStatus.METHOD_NOT_ALLOWED, "Method not allowed");
                    res.getWriter().print(om.writeValueAsString(response));
                    return Result.STOP;
            }
        } catch (NumberFormatException e) {
            response = new ResponsePackageNoData(ResponseStatus.BAD_REQUEST, "ID must be an integer");
            res.getWriter().print(om.writeValueAsString(response));
        } catch (SQLException e) {
            response = new ResponsePackageNoData(ResponseStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong: " + e.getMessage());
            res.getWriter().print(om.writeValueAsString(response));
        }
        return Result.CONTINUE;
    }
}
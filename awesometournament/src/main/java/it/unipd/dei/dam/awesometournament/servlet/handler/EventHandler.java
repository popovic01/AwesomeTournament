package it.unipd.dei.dam.awesometournament.servlet.handler;

import java.io.IOException;
import java.sql.SQLException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherHandler;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.*;
import it.unipd.dei.dam.awesometournament.utils.BodyTools;
import it.unipd.dei.dam.awesometournament.database.DeleteEventDAO;
import it.unipd.dei.dam.awesometournament.database.GetEventDAO;
import it.unipd.dei.dam.awesometournament.database.UpdateEventDAO;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Event;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EventHandler extends RestMatcherHandler {

    void getEvent (HttpServletRequest req, HttpServletResponse res, int id) throws ServletException, IOException, SQLException{
        LogContext.setAction(Actions.GET_EVENT);
        GetEventDAO getEventDAO = new GetEventDAO(getConnection(), id);
        Event event = (Event) getEventDAO.access().getOutputParam();

        if (event != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setDateFormat(new StdDateFormat());
            res.setContentType("application/json");
            res.getWriter().println(objectMapper.writeValueAsString(event));
        } else {
            res.sendError(HttpServletResponse.SC_NOT_FOUND, "The event doesn't exist");
        }
    }

    void putEvent (HttpServletRequest req, HttpServletResponse res, int id) throws ServletException, IOException, SQLException{
        LogContext.setAction(Actions.PUT_EVENT);
        String requestBody = BodyTools.getRequestBody(req);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new StdDateFormat());
        Event event = (Event) objectMapper.readValue(requestBody, Event.class);
        event.setId(id);
        UpdateEventDAO updateEventDAO = new UpdateEventDAO(getConnection(), event);
        Integer result = (Integer) updateEventDAO.access().getOutputParam();
        if (result == 1) {
            res.setStatus(HttpServletResponse.SC_OK);
        } else {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    void deleteEvent (HttpServletRequest req, HttpServletResponse res, int id) throws ServletException, IOException, SQLException{
        LogContext.setAction(Actions.DELETE_EVENT);
        DeleteEventDAO deleteEventDAO = new DeleteEventDAO(getConnection(), id);
        Integer result = (Integer) deleteEventDAO.access().getOutputParam();
        if (result == 1) {
            res.setStatus(HttpServletResponse.SC_OK);
        } else {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Result handle(Method method, HttpServletRequest req, HttpServletResponse res,
                         String[] params) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());        
        int eventId = Integer.parseInt(params[0]);

        try {
            switch (method) {
                case GET:
                    getEvent(req, res, eventId);
                    break;
                case PUT:
                    putEvent(req, res, eventId);
                    break;
                case DELETE:
                    deleteEvent(req, res, eventId);
                    break;
                default:
                    return Result.STOP;
            }
        } catch (NumberFormatException e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID must be an integer");
        } catch (SQLException e) {
            res.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }
        return Result.CONTINUE;
    }
}

package it.unipd.dei.dam.awesometournament.servlet.handler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.BufferedReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import it.unipd.dei.dam.awesometournament.servlet.RestMatcherHandler;
import it.unipd.dei.dam.awesometournament.servlet.RestMatcherServlet.*;
import it.unipd.dei.dam.awesometournament.database.DeleteEventDAO;
import it.unipd.dei.dam.awesometournament.database.GetEventDAO;
import it.unipd.dei.dam.awesometournament.database.UpdateEventDAO;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Event;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EventHandler extends RestMatcherHandler {

    String getRequestBody(HttpServletRequest req) throws IOException{
        StringBuilder requestBody = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        return requestBody.toString();
    }

    void getEvent (HttpServletRequest req, HttpServletResponse res, int id) throws ServletException, IOException, SQLException{
        GetEventDAO getEventDAO = new GetEventDAO(getConnection(), id);
        Event event = (Event) getEventDAO.access().getOutputParam();
        if (event != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setDateFormat(new StdDateFormat());
            res.setContentType("application/json");
            res.getWriter().println(objectMapper.writeValueAsString(event));
        } else {
            res.sendError(HttpServletResponse.SC_NOT_FOUND, "The player doesn't exist");
        }
    }

    void putEvent (HttpServletRequest req, HttpServletResponse res, int id) throws ServletException, IOException, SQLException{
        String requestBody = getRequestBody(req);
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
        int playerId = Integer.parseInt(params[0]);
        try {
            switch (method) {
                case GET:
                    getEvent(req, res, playerId);
                    break;
                case PUT:
                    putEvent(req, res, playerId);
                    break;
                case DELETE:
                    deleteEvent(req, res, playerId);
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

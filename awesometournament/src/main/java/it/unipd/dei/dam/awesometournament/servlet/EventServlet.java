package it.unipd.dei.dam.awesometournament.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.annotation.WebServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import it.unipd.dei.dam.awesometournament.database.GetEventDAO;
import it.unipd.dei.dam.awesometournament.database.UpdateEventDAO;
import it.unipd.dei.dam.awesometournament.resources.entities.Event;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "EventServlet", urlPatterns = {"/events/*"})
public class EventServlet extends AbstractDatabaseServlet {
    protected final static Logger LOGGER = LogManager.getLogger(EventServlet.class,
            StringFormatterMessageFactory.INSTANCE);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getPathInfo();
        if (url != null) {
            String[] urlParts = url.split("/"); // urlParts[0] = ""
            if (urlParts.length != 2) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
            } else {
                try {
                    int eventId = Integer.parseInt(urlParts[1]);
                    Connection connection = getConnection();
                    GetEventDAO getEventDAO = new GetEventDAO(connection, eventId);
                    Event event = (Event) getEventDAO.access().getOutputParam();
                    if (event != null) {
                        req.setAttribute("event", event); // Passa l'oggetto evento alla JSP
                        req.getRequestDispatcher("/event.jsp").forward(req, resp); // Inoltra la richiesta alla JSP
                    } else {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "The event doesn't exist");
                    }
                } catch (NumberFormatException e) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Event ID must be an integer");
                } catch (SQLException e) {
                    resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                }
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String url = req.getPathInfo();
        if (url != null) {
            String[] urlParts = url.split("/"); // urlParts[0] = ""
            if (urlParts.length != 2) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
            } else {
                try {
                    int eventId = Integer.parseInt(urlParts[1]);
                    ObjectMapper objectMapper = new ObjectMapper();
                    Event updatedEvent = objectMapper.readValue(req.getReader(), Event.class);
                    updatedEvent.setId(eventId);
                    Connection connection = getConnection();
                    UpdateEventDAO updateEventDAO = new UpdateEventDAO(connection, updatedEvent);
                    updateEventDAO.access();

                } catch (NumberFormatException e) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Event ID must be an integer");
                } catch (SQLException e) {
                    resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                }
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
        }
    }
}

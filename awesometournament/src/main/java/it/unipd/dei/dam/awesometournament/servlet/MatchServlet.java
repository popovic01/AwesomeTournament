package it.unipd.dei.dam.awesometournament.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import it.unipd.dei.dam.awesometournament.database.GetMatchDAO;
import it.unipd.dei.dam.awesometournament.database.GetMatchEventsByDAO;
import it.unipd.dei.dam.awesometournament.database.GetMatchEventsDAO;
import it.unipd.dei.dam.awesometournament.database.GetMatchEventsDetailDAO;
import it.unipd.dei.dam.awesometournament.database.GetTeamPlayersDAO;
import it.unipd.dei.dam.awesometournament.database.GetTournamentByIdDAO;
import it.unipd.dei.dam.awesometournament.database.GetMatchEventsDetailDAO.EventDetails;
import it.unipd.dei.dam.awesometournament.resources.Actions;
import it.unipd.dei.dam.awesometournament.resources.LogContext;
import it.unipd.dei.dam.awesometournament.resources.entities.Event;
import it.unipd.dei.dam.awesometournament.resources.entities.Match;
import it.unipd.dei.dam.awesometournament.resources.entities.Player;
import it.unipd.dei.dam.awesometournament.resources.entities.Tournament;
import it.unipd.dei.dam.awesometournament.resources.enums.EventType;
import it.unipd.dei.dam.awesometournament.utils.SessionHelpers;

/**
 * Servlet responsible for handling requests related to matches
 */
public class MatchServlet extends AbstractDatabaseServlet {
    protected final static Logger LOGGER = LogManager.getLogger(MatchServlet.class,
            StringFormatterMessageFactory.INSTANCE);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());
        LogContext.setAction(Actions.GET_MATCH);

        String path = req.getPathInfo();

        String[] parts = path.split("/");

        if (parts.length == 2) {
            int id = Integer.parseInt(parts[1]);
            try {
                // get the main Match object
                GetMatchDAO dao = new GetMatchDAO(getConnection(), id);
                dao.access();
                Match match = dao.getOutputParam();
                if (match == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                req.setAttribute("match", match);

                // check if the logged user is the admin of the tournament
                if (SessionHelpers.isLogged(req)) {
                    GetTournamentByIdDAO tournamentByIdDAO = new GetTournamentByIdDAO(getConnection(),
                            match.getTournamentId());
                    tournamentByIdDAO.access();
                    Tournament tournament = tournamentByIdDAO.getOutputParam();

                    int userId = SessionHelpers.getId(req);
                    if (userId == tournament.getCreatorUserId()) {
                        req.setAttribute("owner", true);
                        req.setAttribute("matchId", match.getId());
                    }
                }

                // get the events of this match
                GetMatchEventsDAO matchEventsDAO = new GetMatchEventsDAO(getConnection(), match.getId());
                matchEventsDAO.access();
                List<Event> events = matchEventsDAO.getOutputParam();
                req.setAttribute("events", events);

                // get the eventdetails of this match
                GetMatchEventsDetailDAO matchEventsDetailDAO = new GetMatchEventsDetailDAO(getConnection(), match.getId());
                matchEventsDetailDAO.access();
                List<EventDetails> eventdetails = matchEventsDetailDAO.getOutputParam();
                req.setAttribute("eventdetails", eventdetails);

                // get the players taking part in this match
                List<Player> players = new ArrayList<>();

                GetTeamPlayersDAO playersDAO1 = new GetTeamPlayersDAO(getConnection(), match.getTeam1Id());
                playersDAO1.access();
                playersDAO1.getOutputParam().forEach(players::add);

                GetTeamPlayersDAO playersDAO2 = new GetTeamPlayersDAO(getConnection(), match.getTeam2Id());
                playersDAO2.access();
                playersDAO2.getOutputParam().forEach(players::add);

                req.setAttribute("players", players);

                // get the types of possible events
                List<String> types = new ArrayList<>();
                for (EventType type : EventType.values()) {
                    types.add(EventType.enum2db(type));
                }

                req.setAttribute("types", types);

                // check if the number of goals is coherent

                int score1 = match.getTeam1Score();
                int score2 = match.getTeam2Score();

                // count saved goal events for team1
                GetMatchEventsByDAO getMatchEventsByDAO = new GetMatchEventsByDAO(getConnection(),
                        id,
                        EventType.GOAL,
                        match.getTeam1Id());
                
                getMatchEventsByDAO.access();
                int goals1 = getMatchEventsByDAO.getOutputParam().size();

                getMatchEventsByDAO = new GetMatchEventsByDAO(getConnection(),
                        id,
                        EventType.GOAL,
                        match.getTeam2Id());
                
                getMatchEventsByDAO.access();
                int goals2 = getMatchEventsByDAO.getOutputParam().size();

                req.setAttribute("goals_coherent", score1 == goals1 && score2 == goals2);

                

                req.getRequestDispatcher("/jsp/match.jsp").forward(req, resp);
            } catch (SQLException e) {
                LOGGER.info(e.getMessage());
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}

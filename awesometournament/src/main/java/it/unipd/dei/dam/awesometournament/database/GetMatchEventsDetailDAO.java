package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.ZoneId;
import java.util.ArrayList;

import it.unipd.dei.dam.awesometournament.resources.entities.Event;
import it.unipd.dei.dam.awesometournament.resources.entities.Match;
import it.unipd.dei.dam.awesometournament.resources.enums.EventType;
import it.unipd.dei.dam.awesometournament.resources.enums.MatchResult;

/**
 * DAO class for retrieving the events of a match from the database
 * with filters.
 */
public class GetMatchEventsDetailDAO extends AbstractDAO<ArrayList<GetMatchEventsDetailDAO.EventDetails>> {
    private static String STATEMENT = "SELECT e.*, p.team_id, p.surname FROM public.events e " +
            "INNER JOIN public.players p ON e.player_id = p.id " +
            "WHERE e.match_id = ? ORDER BY e.time";

    public String statement;

    private final int match_id;

    public class EventDetails {
        private Event event;
        private int team; // 1 if team1, 2 if team2
        private String name;

        public EventDetails(Event event, int team, String name) {
            this.event = event;
            this.team = team;
            this.name = name;
        }
        public Event getEvent() {
            return event;
        }
        public void setEvent(Event event) {
            this.event = event;
        }
        public int getTeam() {
            return team;
        }
        public void setTeam(int team) {
            this.team = team;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }

    public GetMatchEventsDetailDAO(
            final Connection con,
            final int match_id) {
        super(con);
        this.match_id = match_id;

        this.statement = STATEMENT;

        LOGGER.info("Created statement is "+this.statement);
    }

    @Override
    protected void doAccess() throws Exception {

        PreparedStatement mp = null;
        mp = con.prepareStatement("SELECT * FROM public.matches WHERE id = ?");
        mp.setInt(1, match_id);
        ResultSet rs = mp.executeQuery();
        Match match = null;
        if(rs.next()) {
            match = new Match(
                rs.getInt("id"),
                rs.getInt("team1_id"),
                rs.getInt("team2_id"),
                rs.getInt("tournament_id"),
                rs.getInt("team1_score"),
                rs.getInt("team2_score"),
                rs.getString("result") != null ? MatchResult.db2enum(rs.getString("result")) : null,
                rs.getString("referee"),
                rs.getTimestamp("match_date") != null ? rs.getTimestamp("match_date").toInstant().atZone(ZoneId.systemDefault()).toOffsetDateTime() : null,
                rs.getBoolean("is_finished")
            );
        }
        if(match == null) {
            throw new Exception("Can't find related match");
        }


        PreparedStatement p = null;
        rs = null;
        ArrayList<EventDetails> events = new ArrayList<>();

        try {
            p = con.prepareStatement(this.statement);
            p.setInt(1, this.match_id);

            LOGGER.info("Ready statement is "+p);

            rs = p.executeQuery();
            while (rs.next()) {
                for(int i=0 ; i<rs.getMetaData().getColumnCount() ; i++) {
                    LOGGER.info(rs.getMetaData().getColumnName(i+1));
                }
                Event e = new Event(
                        rs.getInt("id"),
                        rs.getInt("match_id"),
                        rs.getInt("player_id"),
                        EventType.db2enum(rs.getString("type")),
                        rs.getInt("time"));

                EventDetails details = new EventDetails(e, -1, rs.getString("surname"));

                if(rs.getInt("team_id") == match.getTeam1Id()) {
                    details.setTeam(1);
                }
                else if(rs.getInt("team_id") == match.getTeam2Id()) {
                    details.setTeam(2);
                }
                else {
                    throw new Exception("Inconsistency");
                }

                events.add(details);

                LOGGER.info("Event in match %d found", this.match_id);
            }
        } finally {
            if (p != null)
                p.close();
            if (rs != null)
                rs.close();
        }
        this.outputParam = events;
    }
}

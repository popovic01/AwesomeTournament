package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import it.unipd.dei.dam.awesometournament.resources.entities.Event;
import it.unipd.dei.dam.awesometournament.resources.enums.EventType;

/**
 * DAO class for retrieving the events of a match from the database
 * with filters.
 */
public class GetMatchEventsByDAO extends AbstractDAO<ArrayList<Event>> {
    private static String STATEMENT = "SELECT * FROM public.events " +
            "WHERE match_id = ?";

    public String statement;

    private final int match_id;
    private final EventType type;
    private final Integer team_id;

    public GetMatchEventsByDAO(
            final Connection con,
            final int match_id,
            final EventType type,
            final Integer team_id) {
        super(con);
        this.match_id = match_id;
        this.type = type;
        this.team_id = team_id;

        this.statement = STATEMENT;

        if (this.type != null) {
            this.statement = this.statement +
            " AND type = ?::event_type";
        }

        if(this.team_id != null) {
            this.statement = this.statement +
            " AND player_id IN (SELECT p.id AS id FROM players p WHERE p.team_id = ?)";
        }

        LOGGER.info("Created statement is "+this.statement);
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = null;
        ResultSet rs = null;
        ArrayList<Event> events = new ArrayList<>();

        try {
            p = con.prepareStatement(this.statement);
            p.setInt(1, this.match_id);

            int counter = 1;
            if (this.type != null)
                p.setString(++counter, EventType.enum2db(this.type));

            if (this.team_id != null)
                p.setInt(++counter, this.team_id);

            LOGGER.info("Ready statement is "+p);

            rs = p.executeQuery();
            while (rs.next()) {
                events.add(new Event(
                        rs.getInt("id"),
                        rs.getInt("match_id"),
                        rs.getInt("player_id"),
                        EventType.db2enum(rs.getString("type")),
                        rs.getInt("time")));

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

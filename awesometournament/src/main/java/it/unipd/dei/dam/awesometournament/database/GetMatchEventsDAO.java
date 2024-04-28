package it.unipd.dei.dam.awesometournament.database;

import it.unipd.dei.dam.awesometournament.resources.entities.Event;
import it.unipd.dei.dam.awesometournament.resources.enums.EventType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * DAO class for retrieving the events of a match from the database.
 */
public class GetMatchEventsDAO extends AbstractDAO<ArrayList<Event>>{
    /**
     * The SQL statement used to retrieve match's events from the database.
     */
    private static final String STATEMENT = "SELECT * FROM public.events " +
            "WHERE match_id = ?";

    private final int match_id;

    /**
     * Constructs a new GetMatchEventsDAO object with the specified connection and id.
     * @param con A connection to the database.
     * @param match_id An id of a match which should be retrieved from the database.
     */
    public GetMatchEventsDAO(final Connection con, final int match_id){
        super(con);
        this.match_id = match_id;
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = null;
        ResultSet rs = null;
        ArrayList<Event> events = new ArrayList<>();

        try {
            p = con.prepareStatement(STATEMENT);
            p.setInt(1, this.match_id);
            rs = p.executeQuery();
            while (rs.next()) {
                events.add(new Event(
                        rs.getInt("id"),
                        rs.getInt("match_id"),
                        rs.getInt("player_id"),
                        EventType.db2enum(rs.getString("type")),
                        rs.getInt("time")
                ));

                LOGGER.info("Event in match %d found", this.match_id);
            }
        } finally {
            if (p != null) p.close();
            if (rs != null) rs.close();
        }
        this.outputParam = events;
    }
}

package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unipd.dei.dam.awesometournament.resources.entities.Event;
import it.unipd.dei.dam.awesometournament.resources.enums.EventType;

/**
 * DAO class for retrieving an event from the database.
 */
public class GetEventDAO extends AbstractDAO<Event> {
    /**
     * The SQL statement used to retrieve an event from the database.
     */
    private static final String STATEMENT = "SELECT * FROM public.events " +
            "WHERE id = ?";

    private final int id;

    /**
     * Constructs a new GetEventDAO object with the specified connection and id.
     * @param con A connection to the database.
     * @param id An id of an event which should be retrieved from the database.
     */
    public GetEventDAO(final Connection con, final int id) {
        super(con);
        this.id = id;
    }

    @Override
    protected void doAccess() throws SQLException {
        PreparedStatement statement = null;
        ResultSet rs = null;
        Event event = null;

        try {
            statement = con.prepareStatement(STATEMENT);
            statement.setInt(1, id);
            rs = statement.executeQuery();
            if (rs.next()) {
                event = new Event(
                        rs.getInt("id"),
                        rs.getInt("match_id"),
                        rs.getInt("player_id"),
                        EventType.db2enum(rs.getString("type")),
                        rs.getInt("time")
                );
                LOGGER.info("Event with id {} found", id);
            } else {
                LOGGER.info("Event with id {} doesn't exist", id);
            }
        } finally {
            if (statement != null) statement.close();
            if (rs != null) rs.close();
        }
        this.outputParam = event;
    }
}

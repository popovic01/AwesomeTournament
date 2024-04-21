package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import it.unipd.dei.dam.awesometournament.resources.entities.Event;
import it.unipd.dei.dam.awesometournament.resources.enums.EventType;

public class UpdateEventDAO extends AbstractDAO<Integer> {

    private static final String STATEMENT = "UPDATE public.events " +
            "SET match_id = ?, player_id = ?, type = ?, time = ? " +
            "WHERE id = ?";

    private final Event event;

    public UpdateEventDAO(final Connection con,final Event event) {
        super(con);
        if (event == null) {
            LOGGER.error("The event cannot be null.");
            throw new NullPointerException("The event cannot be null.");
        }
        this.event = event;
    }

    @Override
    protected void doAccess() throws SQLException {
        PreparedStatement p = null;

        try {
            p = con.prepareStatement(STATEMENT);
            p.setInt(1, event.getMatchId());
            p.setInt(2, event.getPlayerId());
            p.setObject(3, EventType.enum2db(event.getType()), Types.OTHER);
            p.setInt(4, event.getTime());
            p.setInt(5, event.getId());

            int result = p.executeUpdate();
            if (result == 1) {
                LOGGER.info("Event successfully updated");
            } else {
                LOGGER.info("Something went wrong during event update: {}", result);
            }
            this.outputParam = result; // 1 if success, 0 otherwise
        } finally {
            if (p != null) p.close();
        }
    }
}

package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.unipd.dei.dam.awesometournament.resources.entities.Event;

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
        PreparedStatement statement = null;

        try {
            statement = con.prepareStatement(STATEMENT);
            statement.setInt(1, event.getMatchId());
            statement.setInt(2, event.getPlayerId());
            statement.setString(3, event.getType().name());
            statement.setInt(4, event.getTime());
            statement.setInt(5, event.getId());

            int result = statement.executeUpdate();
            if (result == 1) {
                LOGGER.info("Event successfully updated");
            } else {
                LOGGER.info("Something went wrong during event update: {}", result);
            }
            outputParam = result; // 1 if success, 0 otherwise
        } finally {
            if (statement != null) statement.close();
        }
    }
}

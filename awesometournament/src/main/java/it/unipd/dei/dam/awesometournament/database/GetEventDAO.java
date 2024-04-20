package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unipd.dei.dam.awesometournament.resources.entities.Event;
import it.unipd.dei.dam.awesometournament.resources.enums.EventType;

public class GetEventDAO extends AbstractDAO<Event> {

    private static final String STATEMENT = "SELECT * FROM public.events " +
            "WHERE id = ?";

    private final int id;

    public GetEventDAO(final Connection con, final int id) {
        super(con);
        this.id = id;
    }

    @Override
    protected void doAccess() throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Event event = null;

        try {
            statement = con.prepareStatement(STATEMENT);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                event = new Event(
                        resultSet.getInt("id"),
                        resultSet.getInt("match_id"),
                        resultSet.getInt("player_id"),
                        EventType.valueOf(resultSet.getString("type")),
                        resultSet.getInt("time")
                );
                LOGGER.info("Event with id {} found", id);
            } else {
                LOGGER.info("Event with id {} doesn't exist", id);
            }
        } finally {
            if (statement != null) statement.close();
            if (resultSet != null) resultSet.close();
        }
        outputParam = event;
    }
}

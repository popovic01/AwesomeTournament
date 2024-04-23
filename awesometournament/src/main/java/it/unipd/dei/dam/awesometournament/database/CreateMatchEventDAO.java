package it.unipd.dei.dam.awesometournament.database;

import it.unipd.dei.dam.awesometournament.resources.entities.Event;
import it.unipd.dei.dam.awesometournament.resources.enums.EventType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

public class CreateMatchEventDAO extends AbstractDAO<Integer>{

    private static final String STATEMENT = "INSERT INTO public.events " +
            "(match_id, player_id, type, time) " +
            "VALUES (?, ?, ?, ?) RETURNING *";

    private final Event event;

    public CreateMatchEventDAO(final Connection con, final Event event) {
        super(con);
        if (event == null) {
            LOGGER.error("The event cannot be null.");
            throw new NullPointerException("The event cannot be null.");
        }
        this.event = event;
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = null;
        ResultSet rs = null;

        try {
            p = con.prepareStatement(STATEMENT);
            p.setInt(1, event.getId());
            p.setInt(2, event.getMatchId());
            p.setInt(3, event.getPlayerId());
            p.setObject(4, EventType.enum2db(event.getType()), Types.OTHER);
            p.setInt(5, event.getTime());
            rs = p.executeQuery();
            if (rs.next()) {
                this.outputParam = rs.getInt("id");
                LOGGER.info("Event successfully created");
            } else {
                LOGGER.info("Something went wrong");
                this.outputParam = null;
            }
        } finally {
            if (p != null) p.close();
            if (rs != null) rs.close();
        }
    }
}

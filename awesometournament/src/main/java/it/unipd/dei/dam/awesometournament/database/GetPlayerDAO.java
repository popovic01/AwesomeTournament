package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import it.unipd.dei.dam.awesometournament.resources.entities.Player;
import it.unipd.dei.dam.awesometournament.resources.enums.PlayerPosition;

/**
 * This won't be part of the final project, users need
 * to be created with a sign-up procedure. It is just an example
 */
public class GetPlayerDAO extends AbstractDAO {

    private static final String STATEMENT = "SELECT * FROM public.\"player\" WHERE id = (?)";

    private final int id;

    public GetPlayerDAO(final Connection con, final int id) {
        super(con);
        this.id = id;
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = con.prepareStatement(STATEMENT);
        p.setInt(1, this.id);
        ResultSet rs = p.executeQuery();
        Player player = null;
        if (rs.next()) {
            player = new Player(rs.getInt("id"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getInt("team_id"),
                (PlayerPosition)rs.getObject("position"),
                rs.getString("medical_certificate"),
                rs.getDate("date_of_birth"));
        }
        LOGGER.info("Player with id %d successfully returned", this.id);
        p.close();
        rs.close();
        this.outputParam = player;
    }

}

package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import it.unipd.dei.dam.awesometournament.resources.entities.Player;
import it.unipd.dei.dam.awesometournament.resources.enums.PlayerPosition;

/**
 * DAO class for retrieving a player from the database.
 */
public class GetPlayerDAO extends AbstractDAO<Player> {
    /**
     * The SQL statement used to retrieve a player from the database.
     */
    private static final String STATEMENT = "SELECT * FROM public.players " +
                                            "WHERE id = ?";

    /**
     * Id of the player to be retrieved.
     */
    private final int id;

    /**
     * Constructs a new GetPlayerDAO object with the specified connection and player id.
     * @param con A connection to the database.
     * @param id An id of a player which should be retrieved from the database.
     */
    public GetPlayerDAO(final Connection con, final int id) {
        super(con);
        this.id = id;
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = null;
        ResultSet rs = null;
        Player player = null;

        try {
            p = con.prepareStatement(STATEMENT);
            p.setInt(1, this.id);
            rs = p.executeQuery();
            if (rs.next()) {
                player = new Player(rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getInt("team_id"),
                    PlayerPosition.db2enum(rs.getString("position")),
                    rs.getBinaryStream("medical_certificate"),
                    rs.getDate("date_of_birth"));

                LOGGER.info("Player with id %d found", this.id);
            } else {
                LOGGER.info("Player with id %d doesn't exist", this.id);
            }
        } finally {
            if (p != null) p.close();
            if (rs != null) rs.close();
        }
        this.outputParam = player;
    }

}

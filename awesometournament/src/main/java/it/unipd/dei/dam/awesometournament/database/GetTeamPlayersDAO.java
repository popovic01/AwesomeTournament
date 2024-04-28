package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import it.unipd.dei.dam.awesometournament.resources.entities.Player;
import it.unipd.dei.dam.awesometournament.resources.enums.PlayerPosition;

/**
 * DAO class for retrieving players of a team from the database.
 */
public class GetTeamPlayersDAO extends AbstractDAO<ArrayList<Player>> {
    /**
     * The SQL statement used to retrieve team's players from the database.
     */
    private static final String STATEMENT = "SELECT * FROM public.players " +
                                            "WHERE team_id = ?";

    private final int team_id;

    /**
     * Constructs a new GetTeamPlayersDAO object with the specified connection and id.
     * @param con A connection to the database.
     * @param team_id An id of a team which should be retrieved from the database.
     */
    public GetTeamPlayersDAO(final Connection con, final int team_id) {
        super(con);
        this.team_id = team_id;
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = null;
        ResultSet rs = null;
        ArrayList<Player> players = new ArrayList<>();

        try {
            p = con.prepareStatement(STATEMENT);
            p.setInt(1, this.team_id);
            rs = p.executeQuery();
            while (rs.next()) {
                players.add(new Player(rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getInt("team_id"),
                    PlayerPosition.db2enum(rs.getString("position")),
                    rs.getBinaryStream("medical_certificate"),
                    rs.getDate("date_of_birth")));

                LOGGER.info("Player in team %d found", this.team_id);
            }
        } finally {
            if (p != null) p.close();
            if (rs != null) rs.close();
        }
        this.outputParam = players;
    }

}

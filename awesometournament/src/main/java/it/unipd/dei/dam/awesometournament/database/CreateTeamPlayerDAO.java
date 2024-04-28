package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import it.unipd.dei.dam.awesometournament.resources.entities.Player;
import it.unipd.dei.dam.awesometournament.resources.enums.PlayerPosition;

/**
 * DAO class for creating a player in the database.
 */
public class CreateTeamPlayerDAO extends AbstractDAO<Integer> {
    /**
     * The SQL statement for inserting a new player into the database.
     */
    private static final String STATEMENT = "INSERT INTO public.players " +
                                            "(name, surname, team_id, position, medical_certificate, date_of_birth) " +
                                            "VALUES (?, ?, ?, ?, ?, ?) RETURNING *";

    private final Player player;

    /**
     * Constructs a CreateTeamPlayerDAO object with the specified database connection and player.
     *
     * @param con The database connection
     * @param player The player object to be created
     * @throws NullPointerException If the player object is null
     */
    public CreateTeamPlayerDAO(final Connection con, final Player player) {
        super(con);
        if (player == null) {
            LOGGER.error("The player cannot be null.");
    		throw new NullPointerException("The player cannot be null.");
    	}
        this.player = player;
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = null;
        ResultSet rs = null;

        try {
            p = con.prepareStatement(STATEMENT);
            p.setString(1, player.getName());
            p.setString(2, player.getSurname());
            p.setInt(3, player.getTeamId());
            p.setObject(4, PlayerPosition.enum2db(player.getPosition()), Types.OTHER);
            p.setBinaryStream(5, player.getMedicalCertificate());
            p.setDate(6, player.getDateOfBirth());
            rs = p.executeQuery();
            if (rs.next()) {
                this.outputParam = rs.getInt("id");
                LOGGER.info("Player successfully created");
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

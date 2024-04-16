package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import it.unipd.dei.dam.awesometournament.resources.entities.Player;
import it.unipd.dei.dam.awesometournament.resources.enums.PlayerPosition;

public class UpdatePlayerDAO extends AbstractDAO {

    private static final String STATEMENT = "UPDATE public.players " +
                                            "SET name = (?), surname = (?), team_id = (?), position = (?), " +
                                            "medical_certificate = (?), date_of_birth = (?) " +
                                            "WHERE id = (?)";

    private final Player player;

    public UpdatePlayerDAO(final Connection con, final Player player) {
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
            p.setString(5, player.getMedicalCertificate());
            p.setDate(6, player.getDateOfBirth());
            p.setInt(7, player.getId());
            int res = p.executeUpdate();
            if (res == 1) LOGGER.info("Player successfully updated");
            else LOGGER.info("Something went wrong %d", res);
        } finally {
            if (p != null) p.close();
            if (rs != null) rs.close();
        }
    }

}

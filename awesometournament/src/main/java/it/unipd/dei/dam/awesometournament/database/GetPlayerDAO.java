package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import it.unipd.dei.dam.awesometournament.resources.entities.Player;
import it.unipd.dei.dam.awesometournament.resources.enums.PlayerPosition;

public class GetPlayerDAO extends AbstractDAO {

    private static final String STATEMENT = "SELECT p.*, t.name AS team_name FROM public.players p " +
                                            "INNER JOIN public.teams t ON p.team_id = t.id " +
                                            "WHERE p.id = (?)";

    private final int id;

    public GetPlayerDAO(final Connection con, final int id) {
        super(con);
        this.id = id;
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = null;
        ResultSet rs = null;
        List<Object> playerTeam = null;

        try {
            p = con.prepareStatement(STATEMENT);
            p.setInt(1, this.id);
            rs = p.executeQuery();
            if (rs.next()) {
                playerTeam = new ArrayList<Object>();
                Player player = new Player(rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getInt("team_id"),
                    (PlayerPosition)rs.getObject("position"),
                    rs.getString("medical_certificate"),
                    rs.getDate("date_of_birth"));
                playerTeam.add(player);
                playerTeam.add(rs.getString("team_name"));

                LOGGER.info("Player with id %d found", this.id);
            } else {
                LOGGER.info("Player with id %d doesn't exist", this.id);
            }
        } finally {
            if (p != null) p.close();
            if (rs != null) rs.close();
        }
        this.outputParam = playerTeam;
    }

}

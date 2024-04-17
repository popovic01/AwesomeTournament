package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import it.unipd.dei.dam.awesometournament.resources.entities.Team;

public class GetTeamDAO extends AbstractDAO<Team> {

    private static final String STATEMENT = "SELECT * FROM public.teams WHERE id = ?";

    private final int id;

    public GetTeamDAO(final Connection con, final int id) {
        super(con);
        this.id = id;
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = null;
        ResultSet rs = null;
        Team team = null;

        try {
            p = con.prepareStatement(STATEMENT);
            p.setInt(1, this.id);
            rs = p.executeQuery();
            if (rs.next()) {
                team = new Team(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("logo"),
                    rs.getInt("creator_user_id"),
                    rs.getInt("tournament_id")
                );
                LOGGER.info("Team with id %d found", this.id);
            } else {
                LOGGER.info("Team with id %d doesn't exist", this.id);
            }
        } finally {
            if (p != null) p.close();
            if (rs != null) rs.close();
        }
        this.outputParam = team;
    }

}

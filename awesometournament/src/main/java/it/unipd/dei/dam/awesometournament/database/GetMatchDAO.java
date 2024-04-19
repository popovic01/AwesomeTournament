package it.unipd.dei.dam.awesometournament.database;

import java.sql.*;

import it.unipd.dei.dam.awesometournament.resources.entities.Match;
import it.unipd.dei.dam.awesometournament.resources.enums.MatchResult;

public class GetMatchDAO extends AbstractDAO{

    private static final String STATEMENT = "SELECT * FROM public.matches WHERE id = ?";

    private final int matchId;

    public GetMatchDAO(final Connection con, final int matchId) {
        super(con);
        this.matchId = matchId;
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = null;
        ResultSet rs = null;

        Match match = null;

        try {
            p = con.prepareStatement(STATEMENT);
            p.setInt(1, this.matchId);
            rs = p.executeQuery();

            if (rs.next()) {
                match = new Match(
                    rs.getInt("id"),
                    rs.getInt("team1_id"),
                    rs.getInt("team2_id"),
                    rs.getInt("tournament_id"),
                    rs.getInt("team1_score"),
                    rs.getInt("team2_score"),
                    rs.getString("result") != null ? MatchResult.db2enum(rs.getString("result")) : null,
                    rs.getString("referee"),
                    rs.getTimestamp("match_date"),
                    rs.getBoolean("is_finished")
                    );
                
                LOGGER.info("Player with id %d found", this.matchId);
            } else {
                LOGGER.info("Player with id %d doesn't exist", this.matchId);
            }
        } finally {
            if (p != null) p.close();
            if (rs != null) rs.close();
        }
        this.outputParam = match;
    }
}

package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import it.unipd.dei.dam.awesometournament.utils.RankingEntry;

public class GetRankingDAO extends AbstractDAO<ArrayList<RankingEntry>> {

    private static final String STATEMENT = "SELECT " +
                                            "t.name AS team_name, " +
                                            "SUM(CASE " +
                                                "WHEN m.team1_id = t.id AND m.result = 'team1' THEN 3 " +  // Team 1 won
                                                "WHEN m.team2_id = t.id AND m.result = 'team2' THEN 3 " +  // Team 2 won
                                                "WHEN m.result = 'draw' THEN 1 " +                          // Draw
                                                "ELSE 0 " +
                                            "END) AS points, " +
                                            "COUNT(*) AS matches_played " +
                                        "FROM " +
                                            "teams t " +
                                        "JOIN " +
                                            "matches m ON t.id = m.team1_id OR t.id = m.team2_id " +
                                        "WHERE " +
                                            "t.tournament_id = ? AND " +
                                            "m.is_finished = true " +  // Only consider finished matches
                                        "GROUP BY " +
                                            "t.id, t.name " +
                                        "ORDER BY " +
                                            "points DESC, matches_played DESC;";
    private final int tournamentId;

    public GetRankingDAO(final Connection con, final int tournamentId) {
        super(con);
        this.tournamentId = tournamentId;
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = null;
        ResultSet rs = null;
        ArrayList<RankingEntry> ranking = new ArrayList<>();

        try {
            p = con.prepareStatement(STATEMENT);
            p.setInt(1, this.tournamentId);
            rs = p.executeQuery();
            while (rs.next()) {
                LOGGER.info("Add team to ranking");
                ranking.add(new RankingEntry(rs.getString("team_name"), rs.getInt("points"),
                    rs.getInt("matches_played")));
            }
        } finally {
            if (p != null) p.close();
            if (rs != null) rs.close();
        }
        this.outputParam = ranking;
    }

}

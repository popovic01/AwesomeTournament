package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import it.unipd.dei.dam.awesometournament.utils.RankingEntry;

/**
 * DAO class for retrieving the ranking of teams in a tournament from the database.
 */
public class GetRankingDAO extends AbstractDAO<ArrayList<RankingEntry>> {
    /**
     * The SQL statement used to retrieve a ranking from the database.
     */
    private static final String STATEMENT = "SELECT t.id AS team_id, t.name AS team_name, t.logo AS team_logo, " +
                                        "SUM(CASE WHEN (m.team1_id = t.id AND m.result = 'team1') OR (m.team2_id = t.id AND m.result = 'team2') THEN 3 " +
                                        "WHEN m.result = 'draw' THEN 1 " +
                                        "WHEN m.is_finished = false THEN 0 ELSE 0 END) AS points, " +
                                        "SUM(CASE WHEN m.is_finished = true THEN 1 ELSE 0 END) AS matches_played, " +
                                        "SUM(CASE WHEN (m.team1_id = t.id AND m.result = 'team1') OR (m.team2_id = t.id AND m.result = 'team2') THEN 1 ELSE 0 END) AS wins, " +
                                        "SUM(CASE WHEN (m.team1_id = t.id AND m.result = 'team2') OR (m.team2_id = t.id AND m.result = 'team1') THEN 1 ELSE 0 END) AS defeats, " +
                                        "SUM(CASE WHEN m.result = 'draw' THEN 1 ELSE 0 END) AS draws, " +
                                        "SUM(CASE WHEN m.team1_id = t.id THEN m.team1_score ELSE 0 END + " +
                                        "CASE WHEN m.team2_id = t.id THEN m.team2_score ELSE 0 END) AS goals_scored, " +
                                        "SUM(CASE WHEN m.team1_id = t.id THEN m.team2_score ELSE 0 END + " +
                                        "CASE WHEN m.team2_id = t.id THEN m.team1_score ELSE 0 END) AS goals_conceded " +
                                        "FROM teams t " +
                                        "LEFT JOIN matches m ON t.id = m.team1_id OR t.id = m.team2_id " +
                                        "WHERE t.tournament_id = ? " +
                                        "GROUP BY t.id, t.name, t.logo " +
                                        "ORDER BY points DESC, matches_played DESC;";
    /**
     * Id of the tournament for which we want to retrieve the ranking.
     */
    private final int tournamentId;

    /**
     * Constructs a new GetRankingDAO object with the specified connection and tournament id.
     * @param con A connection to the database.
     * @param tournamentId An id of a tournament from which the ranking should be retrieved from the database.
     */
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
            LOGGER.info(p);
            rs = p.executeQuery();
            while (rs.next()) {
                LOGGER.info("Add team to ranking");
                ranking.add(new RankingEntry(rs.getInt("team_id"), rs.getString("team_name"),
                        rs.getInt("points"), rs.getInt("matches_played"), rs.getBytes("team_logo"),
                        rs.getInt("wins"), rs.getInt("defeats"), rs.getInt("draws"), rs.getInt("goals_scored"),
                        rs.getInt("goals_conceded")));
            }
        } finally {
            if (p != null) p.close();
            if (rs != null) rs.close();
        }
        this.outputParam = ranking;
    }

}

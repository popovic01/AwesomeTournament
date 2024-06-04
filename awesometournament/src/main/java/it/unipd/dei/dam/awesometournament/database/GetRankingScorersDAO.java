package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import it.unipd.dei.dam.awesometournament.utils.RankingScorersEntry;
/**
 * DAO class for retrieving the ranking of top scorers in a tournament from the database.
 */
public class GetRankingScorersDAO extends AbstractDAO<ArrayList<RankingScorersEntry>> {
    /**
     * The SQL statement used to retrieve a ranking of top scorers from the database.
     */
    private static final String STATEMENT = "SELECT p.id AS player_id, p.name AS player_name, p.surname AS player_surname, " +
                                            "tm.id AS team_id, tm.name AS team_name, tm.logo AS team_logo, " +
                                            "COUNT(CASE WHEN e.type = 'goal' THEN 1 END) AS goals_scored " +
                                            "FROM public.tournaments t " +
                                            "JOIN public.matches m ON m.tournament_id = t.id " +
                                            "JOIN public.events e ON e.match_id = m.id " +
                                            "JOIN public.players p ON e.player_id = p.id " +
                                            "JOIN public.teams tm ON p.team_id = tm.id " +
                                            "WHERE t.id = ? " +
                                            "GROUP BY p.id, tm.id, tm.logo " +
                                            "HAVING COUNT(CASE WHEN e.type = 'goal' THEN 1 END) > 0 " +
                                            "ORDER BY goals_scored DESC;";


    private final int tournamentId;

    /**
     * Constructs a new GetRankingScorersDAO object with the specified connection and id.
     * @param con A connection to the database.
     * @param tournamentId An id of a tournament from which the ranking should be retrieved from the database.
     */
    public GetRankingScorersDAO(final Connection con, final int tournamentId) {
        super(con);
        this.tournamentId = tournamentId;
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = null;
        ResultSet rs = null;
        ArrayList<RankingScorersEntry> ranking = new ArrayList<>();

        try {
            p = con.prepareStatement(STATEMENT);
            p.setInt(1, this.tournamentId);
            rs = p.executeQuery();
            while(rs.next()) {
                ranking.add(new RankingScorersEntry(rs.getInt("player_id"), rs.getString("player_name"), rs.getString("player_surname"),
                        rs.getInt("team_id"), rs.getString("team_name"), rs.getBytes("team_logo"),
                        rs.getInt("goals_scored")));
            }
        } finally {
            if (p != null) p.close();
            if (rs != null) rs.close();
        }
        this.outputParam = ranking;
    }
}

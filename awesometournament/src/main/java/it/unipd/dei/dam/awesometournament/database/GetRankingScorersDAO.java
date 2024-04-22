package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import it.unipd.dei.dam.awesometournament.utils.RankingScorersEntry;

public class GetRankingScorersDAO extends AbstractDAO<ArrayList<RankingScorersEntry>> {

    private static final String STATEMENT = "SELECT p.name AS player_name, p.surname AS player_surname, " +
                                            "COUNT(CASE WHEN e.type = 'goal' THEN 1 END) AS goals_scored, " +
                                            "COUNT(DISTINCT e.match_id) AS matches_played " +
                                            "FROM public.tournaments t " +
                                            "JOIN public.matches m ON m.tournament_id = t.id " +
                                            "JOIN public.events e ON e.match_id = m.id " +
                                            "JOIN public.players p ON e.player_id = p.id " +
                                            "WHERE t.id = ? " +
                                            "GROUP BY p.id " +
                                            "ORDER BY goals_scored DESC;";

    private final int tournamentId;

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
            while (rs.next()) {
                LOGGER.info("Add player to ranking");
                ranking.add(new RankingScorersEntry(rs.getString("player_name"), rs.getString("player_surname"),
                        rs.getInt("goals_scored"), rs.getInt("matches_played")));
            }
        } finally {
            if (p != null) p.close();
            if (rs != null) rs.close();
        }
        this.outputParam = ranking;
    }
}

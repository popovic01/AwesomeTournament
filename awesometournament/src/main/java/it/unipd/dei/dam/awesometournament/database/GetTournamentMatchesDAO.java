package it.unipd.dei.dam.awesometournament.database;

import it.unipd.dei.dam.awesometournament.resources.entities.Match;
import it.unipd.dei.dam.awesometournament.resources.enums.MatchResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for retrieving tournament's matches from the database.
 */
public class GetTournamentMatchesDAO extends AbstractDAO<List<Match>> {
    /**
     * The SQL statement used to retrieve tournament's matches from the database.
     */
    private static final String STATEMENT = "SELECT * FROM public.\"matches\" WHERE tournament_id = ? ORDER BY match_date";
    private final int tournamentId;

    /**
     * Constructs a new GetTournamentMatchesDAO.
     *
     * @param con The database connection.
     * @param tournamentId The ID of the tournament to retrieve.
     */
    public GetTournamentMatchesDAO(final Connection con, final int tournamentId) {
        super(con);
        this.tournamentId = tournamentId;
    }

    @Override
    protected void doAccess() throws Exception {

        PreparedStatement p = null;
        ResultSet rs = null;

        final List<Match> matches = new ArrayList<Match>();

        try {
            p = con.prepareStatement(STATEMENT);
            p.setInt(1, this.tournamentId);

            rs = p.executeQuery();

            while (rs.next()) {
                matches.add(
                        new Match(
                                rs.getInt("id"),
                                rs.getInt("team1_id"),
                                rs.getInt("team2_id"),
                                rs.getInt("tournament_id"),
                                rs.getInt("team1_score"),
                                rs.getInt("team2_score"),
                                rs.getString("result") != null ? MatchResult.db2enum(rs.getString("result")) : null,
                                rs.getString("referee"),
                                rs.getTimestamp("match_date") != null ? rs.getTimestamp("match_date").toInstant().atZone(ZoneId.systemDefault()).toOffsetDateTime() : null,
                                rs.getBoolean("is_finished")
                        )
                );
            }
        } finally {
            if(rs != null) rs.close();
            if(p != null) p.close();
        }
        this.outputParam = matches;
    }
}
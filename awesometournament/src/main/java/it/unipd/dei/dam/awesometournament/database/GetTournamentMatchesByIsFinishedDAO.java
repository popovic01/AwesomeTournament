package it.unipd.dei.dam.awesometournament.database;

import it.unipd.dei.dam.awesometournament.resources.entities.Match;
import it.unipd.dei.dam.awesometournament.resources.enums.MatchResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for retrieving matches of a tournament by their status from the database.
 */
public class GetTournamentMatchesByIsFinishedDAO extends AbstractDAO<List<Match>> {
    /**
     * The SQL statement used to retrieve a tournament by its ID and its status.
     */
    private static final String STATEMENT = "SELECT * FROM public.\"matches\" WHERE tournament_id = ? AND is_finished = ?";
    private final int tournamentId;
    private final boolean isFinished;

    /**
     * Constructs a new GetTournamentMatchesByIsFinishedDAO
     *
     * @param con The database connection.
     * @param tournamentId  The ID of the tournament to retrieve.
     * @param isFinished The status of the tournament to retrieve.
     */
    public GetTournamentMatchesByIsFinishedDAO(final Connection con, final int tournamentId, boolean isFinished) {
        super(con);
        this.tournamentId = tournamentId;
        this.isFinished = isFinished;
    }

    @Override
    protected void doAccess() throws Exception {

        PreparedStatement p = null;
        ResultSet rs = null;

        final List<Match> matches = new ArrayList<Match>();

        try {
            p = con.prepareStatement(STATEMENT);
            p.setInt(1, this.tournamentId);
            p.setBoolean(2, this.isFinished);

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
                                rs.getTimestamp("match_date"),
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
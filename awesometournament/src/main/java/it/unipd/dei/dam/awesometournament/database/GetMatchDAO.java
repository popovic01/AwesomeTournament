package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.ZoneId;

import it.unipd.dei.dam.awesometournament.resources.entities.Match;
import it.unipd.dei.dam.awesometournament.resources.enums.MatchResult;

/**
 * DAO class for retrieving a match from the database.
 */
public class GetMatchDAO extends AbstractDAO<Match> {
    /**
     * The SQL statement used to retrieve a match from the database.
     */
    private static final String STATEMENT = "SELECT * FROM public.matches WHERE id = ?";

    private final int matchId;

    /**
     * Constructs a new GetMatchDAO object with the specified connection and id.
     * @param con A connection to the database.
     * @param matchId An id of a match which should be retrieved from the database.
     */
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
                    rs.getTimestamp("match_date").toInstant().atZone(ZoneId.systemDefault()).toOffsetDateTime(),
                    rs.getBoolean("is_finished")
                    );

                LOGGER.info("Match with id %d found", this.matchId);
            } else {
                LOGGER.info("Match with id %d doesn't exist", this.matchId);
            }
        } finally {
            if (p != null) p.close();
            if (rs != null) rs.close();
        }
        this.outputParam = match;
    }
}

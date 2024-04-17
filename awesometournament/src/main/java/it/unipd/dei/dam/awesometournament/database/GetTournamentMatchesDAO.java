package it.unipd.dei.dam.awesometournament.database;

import it.unipd.dei.dam.awesometournament.resources.entities.Match;
import it.unipd.dei.dam.awesometournament.resources.enums.MatchResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to retrieve all the Matches related to
 * a given Tournament.
 *
 * @author Andrea Bruttomesso 2120933
 * @version 1.0
 * @since 1.0
 */

public class GetTournamentMatchesDAO extends AbstractDAO<List<Match>> {

    private static final String STATEMENT = "SELECT * FROM public.\"matches\" WHERE tournament_id = (SELECT id FROM public.\"tournaments\" WHERE token = ?)";
    private final String tournamentToken;

    /**
     * Creates a new DAO object.
     *
     * @param con the connection to be used for accessing the database.
     */
    public GetTournamentMatchesDAO(final Connection con, final String tournamentToken) {
        super(con);
        this.tournamentToken = tournamentToken;
    }

    @Override
    protected void doAccess() throws Exception {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        final List<Match> matches = new ArrayList<Match>();

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, this.tournamentToken);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                matches.add(
                        new Match(
                                rs.getInt("id"),
                                rs.getInt("team1_id"),
                                rs.getInt("team2_id"),
                                //Just to be consistent, I could hard code TournamentID here.
                                rs.getInt("tournament_id"),
                                rs.getInt("team1_score"),
                                rs.getInt("team2_score"),
                                rs.getString("result") != null ? MatchResult.valueOf(rs.getString("result")) : null,
                                rs.getString("referee"),
                                rs.getTimestamp("match_date"),
                                rs.getBoolean("is_finished")
                        )
                );
            }
        } finally {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
        }
        this.outputParam = matches;
    }
}
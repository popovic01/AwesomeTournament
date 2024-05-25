package it.unipd.dei.dam.awesometournament.database;

import it.unipd.dei.dam.awesometournament.resources.entities.Match;
import it.unipd.dei.dam.awesometournament.resources.enums.MatchResult;

import java.sql.*;

/**
 * DAO class for updating an event in the database.
 */
public class UpdateMatchDAO extends AbstractDAO<Integer> {
    /**
     * The SQL statement used to update a match from the database.
     */
    private String STATEMENT;
    private final Match match;

    /**
     * Constructs a new UpdateMatchDAO object with the specified connection and match.
     * @param con A connection to the database.
     * @param match Data for a match update in the database.
     */
    public UpdateMatchDAO(final Connection con, final Match match) {
        super(con);
        if (match == null) {
            LOGGER.error("Match cannot be null");
            throw new NullPointerException("Match cannot be null");
        }

        this.match = match;
    }

    //!!! NEEDS MORE LOGIC ON UPDATES
    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = null;

        if (match.getTeam1Score() == null || match.getTeam2Score() == null) {
            STATEMENT = "UPDATE public.matches SET match_date = ?, referee = ? WHERE id = ?";
            p = con.prepareStatement(STATEMENT);

            p.setTimestamp(1, Timestamp.from(match.getMatchDate().toInstant()));
            p.setString(2, match.getReferee());
            p.setInt(3, match.getId());
        } else {
            STATEMENT = "UPDATE public.matches SET team1_score = ?, team2_score = ?, result = ?, is_finished = ? WHERE id = ?";
            p = con.prepareStatement(STATEMENT);
            
            int team1Score = match.getTeam1Score();
            int team2Score = match.getTeam2Score();
            MatchResult result;
            
            if (team1Score > team2Score) result = MatchResult.TEAM1;
            else if (team1Score < team2Score) result = MatchResult.TEAM2;
            else result = MatchResult.DRAW;

            p.setInt(1, team1Score);
            p.setInt(2, team2Score);
            p.setObject(3, MatchResult.enum2db(result), Types.OTHER);
            p.setBoolean(4, true);
            p.setInt(5, match.getId());
        }

        try {
            int rs = p.executeUpdate();

            if (rs == 1) LOGGER.info("Match successfully updated");
            else LOGGER.info("Something went wrong %d", rs);

            this.outputParam = rs;
        } finally {
            if (p != null) p.close();
        }
    }
}

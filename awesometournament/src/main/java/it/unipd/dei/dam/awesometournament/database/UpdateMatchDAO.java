package it.unipd.dei.dam.awesometournament.database;

import it.unipd.dei.dam.awesometournament.resources.entities.Match;
import it.unipd.dei.dam.awesometournament.resources.enums.MatchResult;

import java.sql.*;

public class UpdateMatchDAO extends AbstractDAO<Integer> {

    private String STATEMENT;
    private final Match match;

    /**
     * Creates a new DAO object.
     *
     * @param con the connection to be used for accessing the database.
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

            p.setTimestamp(1, match.getMatchDate());
            p.setString(2, match.getReferee());
            p.setInt(3, match.getId());
        } else {
            STATEMENT = "UPDATE public.matches SET team1_score = ?, team2_score = ?, result = ?, match_date = ?, referee = ?, is_finished = ? WHERE id = ?";
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
            p.setTimestamp(4, match.getMatchDate());
            p.setString(5, match.getReferee());
            p.setBoolean(6, match.getIsFinished());
            p.setInt(7, match.getId());
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

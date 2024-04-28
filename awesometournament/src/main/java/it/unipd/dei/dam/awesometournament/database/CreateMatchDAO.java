package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unipd.dei.dam.awesometournament.resources.entities.Match;

/**
 * DAO class for creating a match in the database.
 */
public class CreateMatchDAO extends AbstractDAO<Integer>{
    /**
     * The SQL statement used to create a match from the database.
     */
    private static final String STATEMENT = "INSERT INTO public.\"matches\" (team1_id, team2_id, tournament_id, team1_score, team2_score, result, referee, match_date, is_finished) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING *";
    private final Match match;

    /**
     * Constructs a new CreateMatchDAO object with the specified connection and match.
     * @param con A connection to the database.
     * @param match A match which should be created in the database.
     */
    public CreateMatchDAO(final Connection con, final Match match) {
        super(con);

        if (match == null) {
            LOGGER.error("Match cannot be null");
            throw new NullPointerException("Match cannot be null");
        }

        this.match = match;
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = con.prepareStatement(STATEMENT);

        p.setInt(1, match.getTeam1Id());
        p.setInt(2, match.getTeam2Id());
        p.setInt(3, match.getTournamentId());
        p.setNull(4, java.sql.Types.INTEGER);
        p.setNull(5, java.sql.Types.INTEGER);
        p.setNull(6, java.sql.Types.OTHER);
        p.setString(7, match.getReferee());
        p.setNull(8, java.sql.Types.TIMESTAMP_WITH_TIMEZONE);
        p.setBoolean(9, match.getIsFinished());

        try {
            ResultSet rs = p.executeQuery();

            rs.next();
            this.outputParam = rs.getInt("id");

            LOGGER.info("Match has been successfully stored in the database");
            p.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("Error while saving match in the database: " + e.getMessage());
            this.outputParam = null;
        }
    }
}

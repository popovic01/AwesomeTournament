package it.unipd.dei.dam.awesometournament.database;

import it.unipd.dei.dam.awesometournament.resources.entities.Tournament;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * DAO class for creating a new tournament in the database.
 */
public class CreateTournamentDAO extends AbstractDAO<Integer> {
    /**
     * The SQL statement used to insert a new tournament into the database.
     */
    private static final String STATEMENT = "INSERT INTO public.tournaments " +
            "(name, token, creator_user_id, max_teams, max_players, min_players, starting_players," +
            "max_substitutions, deadline, start_date, creation_date, logo, is_finished) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING *";

    /**
     * The tournament object containing the information of the tournament to be created.
     */
    private final Tournament tournament;

    /**
     * Constructs a new CreateTournamentDAO object with the specified connection and tournament.
     *
     * @param con        the database connection
     * @param tournament the tournament object containing the information of the tournament to be created
     * @throws NullPointerException if the tournament object is null
     */
    public CreateTournamentDAO(final Connection con, final Tournament tournament) {
        super(con);
        if (tournament == null) {
            LOGGER.error("The tournament cannot be null.");
            throw new NullPointerException("The tournament cannot be null.");
        }
        this.tournament = tournament;
    }

    /**
     * Executes the create operation to create a new tournament in the database.
     *
     * @throws Exception if an error occurs during the database operation
     */
    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = null;
        ResultSet rs = null;

        try {
            p = con.prepareStatement(STATEMENT);
            p.setString(1, tournament.getName());
            p.setString(2, tournament.getToken());
            p.setInt(3, tournament.getCreatorUserId());
            p.setInt(4, tournament.getMaxTeams());
            p.setInt(5, tournament.getMaxPlayers());
            p.setInt(6, tournament.getMinPlayers());
            p.setInt(7, tournament.getStartingPlayers());
            p.setInt(8, tournament.getMaxSubstitutions());
            p.setTimestamp(9, tournament.getDeadline());
            p.setTimestamp(10, tournament.getStartDate());
            p.setTimestamp(11, tournament.getCreationDate());
            p.setBinaryStream(12, tournament.getLogo());
            p.setBoolean(13, tournament.getIsFinished());

            rs = p.executeQuery();
            if (rs.next()) {
                this.outputParam = rs.getInt("id");
                LOGGER.info("Tournament successfully created");
            }
            else {
                LOGGER.info("Something went wrong");
                this.outputParam = null;
            }
        }
        finally {
            if (p != null) p.close();
            if (rs != null) rs.close();
        }
    }
}

package it.unipd.dei.dam.awesometournament.database;

import it.unipd.dei.dam.awesometournament.resources.entities.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * DAO class for creating a team in the database.
 */
public class CreateTeamDAO extends AbstractDAO<Integer>
{
    /**
     * The SQL statement used to create a team from the database.
     */
    private static final String STATEMENT = "INSERT INTO public.teams (name, logo, creator_user_id, tournament_id) VALUES (?, ?, ?, ?) RETURNING id";
    /**
     * A team which should be created in the database.
     */
    private final Team team;

    /**
     * Constructs a new CreateTeamDAO object with the specified connection and team.
     * @param con A connection to the database.
     * @param team A team which should be created in the database.
     */
    public CreateTeamDAO(final Connection con, final Team team) {
        super(con);
        this.team = team;
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = null;
        try {
            p = con.prepareStatement(STATEMENT);

            p.setString(1, team.getName());
            p.setBinaryStream(2, team.getLogo());
            p.setInt(3, team.getCreatorUserId());
            p.setInt(4, team.getTournamentId());

            ResultSet rs = p.executeQuery();
            LOGGER.info("Team added");
            rs.next();

            this.outputParam = rs.getInt("id");
        } catch (Exception e) {
            LOGGER.error("Something went wrong: " + e.getMessage());
            throw e;
        }
        finally {
            if (p != null)
                p.close();
        }
    }
}

package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * DAO class for deleting a new Team in the database.
 */
public class DeleteTeamDAO extends AbstractDAO<Integer>  {

    /**
     * The SQL statement used to delete a team from the database.
     */
    private static final String STATEMENT = "DELETE FROM public.teams WHERE id = ?";
    /**
     * An id of a team which should be deleted from the database.
     */
    private final int id;

    /**
     * Constructs a new DeleteTeamDAO object with the specified connection and id.
     * @param con A connection to the database.
     * @param id An id of a team which should be deleted from the database.
     */
    public DeleteTeamDAO(final Connection con, final int id) {
        super(con);
        this.id = id;
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = null;

        try {
            p = con.prepareStatement(STATEMENT);
            p.setInt(1, this.id);
            int result = p.executeUpdate();
            this.outputParam = result;
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

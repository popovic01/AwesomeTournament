package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * DAO class for deleting a tournament from the database.
 */
public class DeleteTournamentDAO extends AbstractDAO<Integer> {
    /**
     * The SQL statement used to delete a tournament from the database.
     */
    private static final String STATEMENT = "DELETE FROM public.tournaments WHERE id = ?";

    /**
     * The ID of the tournament to be deleted.
     */
    private final int id;

    /**
     * Constructs a new DeleteTournamentDAO object with the specified connection and tournament ID.
     *
     * @param con the database connection
     * @param id  the ID of the tournament to be deleted
     */
    public DeleteTournamentDAO(final Connection con, final int id) {
        super(con);
        this.id = id;
    }

    /**
     * Executes the delete operation to delete the tournament from the database.
     *
     * @throws Exception if an error occurs during the database operation
     */
    @Override
    protected void doAccess() throws Exception {
        PreparedStatement t = null;

        try {
            t = con.prepareStatement(STATEMENT);
            t.setInt(1, this.id);

            int res = t.executeUpdate();
            if (res == 1) LOGGER.info("Tournament successfully deleted");
            else LOGGER.info("Something went wrong %d", res);
            this.outputParam = res;
        }
        finally {
            if (t != null) t.close();
        }
    }
}

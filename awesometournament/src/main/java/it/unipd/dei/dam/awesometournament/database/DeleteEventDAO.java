package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * DAO class for deleting an event in the database.
 */
public class DeleteEventDAO extends AbstractDAO<Integer>{
    /**
     * The SQL statement used to delete an event from the database.
     */
    private static final String STATEMENT = "DELETE FROM public.events " +
            "WHERE id = ?";
    private final int id;

    /**
     * Constructs a new DeleteEventDAO object with the specified connection and id.
     * @param con A connection to the database.
     * @param id An id of an event which should be deleted from the database.
     */
    public DeleteEventDAO(final Connection con, final int id) {
        super(con);
        this.id = id;
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = con.prepareStatement(STATEMENT);
            statement.setInt(1, this.id);
            int res = statement.executeUpdate();
            if (res == 1) LOGGER.info("Event successfully deleted");
            else LOGGER.info("Something went wrong %d", res);
            this.outputParam = res; // 1 if success, 0 otherwise
        } finally {
            if (statement != null) statement.close();
            if (rs != null) rs.close();
        }
    }

}

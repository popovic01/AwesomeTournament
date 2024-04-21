package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteTeamDAO extends AbstractDAO<Integer>  {

    private static final String STATEMENT = "DELETE FROM public.teams WHERE id = ?";
    private final int id;

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

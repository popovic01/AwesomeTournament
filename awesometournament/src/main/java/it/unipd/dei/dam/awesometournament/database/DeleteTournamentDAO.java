package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteTournamentDAO extends AbstractDAO<Integer> {
    private static final String STATEMENT = "DELETE FROM public.tournaments WHERE id = ?";
    private final int id;

    public DeleteTournamentDAO(final Connection con, final int id) {
        super(con);
        this.id = id;
    }

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

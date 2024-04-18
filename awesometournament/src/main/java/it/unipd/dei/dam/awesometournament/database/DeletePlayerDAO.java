package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DeletePlayerDAO extends AbstractDAO<Integer> {

    private static final String STATEMENT = "DELETE FROM public.players " +
                                            "WHERE id = ?";
    private final int id;

    public DeletePlayerDAO(final Connection con, final int id) {
        super(con);
        this.id = id;
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = null;
        ResultSet rs = null;

        try {
            p = con.prepareStatement(STATEMENT);
            p.setInt(1, this.id);
            int res = p.executeUpdate();
            if (res == 1) LOGGER.info("Player successfully deleted");
            else LOGGER.info("Something went wrong %d", res);
            this.outputParam = res; // 1 if success, 0 otherwise
        } finally {
            if (p != null) p.close();
            if (rs != null) rs.close();
        }
    }
}

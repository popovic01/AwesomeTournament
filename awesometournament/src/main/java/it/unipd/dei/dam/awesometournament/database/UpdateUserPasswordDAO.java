package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateUserPasswordDAO extends AbstractDAO<Integer> {
    private String STATEMENT = "UPDATE users SET password = ? WHERE id = ?";
    int id;
    String password;

    public UpdateUserPasswordDAO(final Connection con, int id, String password) {
        super(con);
        this.id = id;
        this.password = password;
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = con.prepareStatement(STATEMENT);
        p.setString(1, password);
        p.setInt(2, id);

        int res = p.executeUpdate();
        this.outputParam = res;
        p.close();
    }

    
}

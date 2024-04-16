package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unipd.dei.dam.awesometournament.resources.entities.User;

public class CreateUserDAO extends AbstractDAO {

    private static final String STATEMENT = "INSERT INTO public.\"users\" (email, password) VALUES (?, ?)";

    private final User user;

    public CreateUserDAO(final Connection con, final User user) {
        super(con);

        if(user == null) {
            LOGGER.error("user cannot be null");
            throw new NullPointerException();
        }

        this.user = user;
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = con.prepareStatement(STATEMENT);
        p.setString(1, user.getEmail());
        p.setString(2, user.getPassword());
        try {
            p.executeUpdate();
            ResultSet rs = p.getGeneratedKeys();

            LOGGER.info("reading resultset");
            while(rs.next()) {
                LOGGER.info("Resultset "+rs.getLong(1));
            }
            
            LOGGER.info("user stored in the database");
            p.close();
        }
        catch (SQLException e) {
            LOGGER.error("can't insert user");
        }
    }
    
}

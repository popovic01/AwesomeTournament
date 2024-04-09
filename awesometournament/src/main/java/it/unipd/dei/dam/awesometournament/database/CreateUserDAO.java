package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;

import it.unipd.dei.dam.awesometournament.resources.entities.User;

/**
 * This won't be part of the final project, users need
 * to be created with a sign-up procedure. It is just an example
 */
public class CreateUserDAO extends AbstractDAO {

    private static final String STATEMENT = "INSERT INTO public.\"user\" (email, password) VALUES (?, ?)";

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
        p.execute();
        LOGGER.info("user stored in the database");
        p.close();
    }
    
}

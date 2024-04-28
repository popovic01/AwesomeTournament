package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unipd.dei.dam.awesometournament.resources.entities.User;

/**
 * DAO class for creating a user in the database.
 */
public class CreateUserDAO extends AbstractDAO<Integer> {
    /**
     * The SQL statement used to create a user from the database.
     */
    private static final String STATEMENT = "INSERT INTO public.\"users\" (email, password) VALUES (?, ?) RETURNING *";

    private final User user;

    /**
     * Constructs a new CreateUserDAO object with the specified connection and user.
     * @param con A connection to the database.
     * @param user A user which should be created in the database.
     */
    public CreateUserDAO(final Connection con, final User user) {
        super(con);

        if (user == null) {
            LOGGER.error("User cannot be null");
            throw new NullPointerException("User cannot be null");
        }

        this.user = user;
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = con.prepareStatement(STATEMENT);
        p.setString(1, user.getEmail());
        p.setString(2, user.getPassword());
        try {
            ResultSet rs = p.executeQuery();

            rs.next();
            this.outputParam = rs.getInt("id");

            LOGGER.info("User has been successfully stored in the database");
            p.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("Error while saving user in the database: " + e.getMessage());
            this.outputParam = null;
        }
    }
    
}

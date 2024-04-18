package it.unipd.dei.dam.awesometournament.database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unipd.dei.dam.awesometournament.resources.entities.User;

public class LoginDAO extends AbstractDAO<User> {

    private static final String LOGIN_STATEMENT =
            "SELECT * FROM public.\"users\" WHERE email = ? AND password = ?;";

    private final String email;
    private final String password;

    public LoginDAO(final Connection con, final String email, final String password) {
        super(con);

        if (email == null) {
            LOGGER.error("Email  cannot be null");
            throw new NullPointerException("Email cannot be null");
        }

        if (password == null) {
            LOGGER.error("Password cannot be null");
            throw new NullPointerException("Password cannot be null");
        }

        this.email = email;
        this.password = password;
    }

    @Override
    protected void doAccess() throws Exception {
        try (PreparedStatement p = con.prepareStatement(LOGIN_STATEMENT)) {
            p.setString(1, email);
            p.setString(2, password);

            ResultSet rs = p.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String userEmail = rs.getString("email");
                String userPassword = rs.getString("password");

                User user = new User(id, userEmail, userPassword);
                this.outputParam = user;
                return;
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception: " + e.getMessage());
        }
        this.outputParam = null;
    }
}

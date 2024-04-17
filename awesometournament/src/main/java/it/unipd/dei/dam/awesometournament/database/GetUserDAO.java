package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unipd.dei.dam.awesometournament.resources.entities.User;

public class GetUserDAO extends AbstractDAO<User> {

    private static final String BYEMAIL_STATEMENT =
        "SELECT * FROM public.\"users\" WHERE email = ?;";

    private static final String BYID_STATEMENT =
        "SELECT * FROM public.\"users\" WHERE id = ?;";

    private final String email;
    private final Integer id;


    public GetUserDAO (final Connection con, final String email) {
        super(con);

        if (email == null) {
            LOGGER.error("name cannot be null");
            throw new NullPointerException();
        }

        this.email = email;
        this.id = null;
    }

    public GetUserDAO (final Connection con, final Integer id) {
        super(con);

        if (id == null) {
            LOGGER.error("id cannot be null");
            throw new NullPointerException();
        }

        this.id = id;
        this.email= null;
    }


    @Override
    protected void doAccess() throws Exception {
        if(this.id != null) {
            this.doAccessId();
            return;
        }
        if(this.email != null) {
            this.doAccessEmail();
            return;
        }

        throw new NullPointerException();
    }

    private void doAccessEmail() {
        LOGGER.info("accessing with email");
        try (PreparedStatement p = con.prepareStatement(BYEMAIL_STATEMENT)) {
            p.setString(1, email);
            ResultSet rs = p.executeQuery();

            if(rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String password = rs.getString("password");

                User user = new User(id, email, password);

                this.outputParam = user;
                return;
            }
        } catch (SQLException e) {
            LOGGER.error("sql exception");
        }
        this.outputParam = null;
    }

    private void doAccessId() {
        LOGGER.info("accessing with id");
        try (PreparedStatement p = con.prepareStatement(BYID_STATEMENT)) {
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();

            if(rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String password = rs.getString("password");

                User user = new User(id, email, password);

                this.outputParam = user;
                return;
            }
        } catch (SQLException e) {
            LOGGER.error("sql exception");
        }
        this.outputParam = null;
    }

}

package it.unipd.dei.dam.awesometournament.database;

import it.unipd.dei.dam.awesometournament.resources.entities.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateTeamDAO extends AbstractDAO<Integer>  {

    private String STATEMENT;
    private final Team team;

    public UpdateTeamDAO(final Connection con, final Team team) {
        super(con);
        this.team = team;
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = null;
        try {
            if (this.team.getLogo() == null) {
                STATEMENT = "UPDATE public.teams SET name = ? WHERE id = ?";
                p = con.prepareStatement(STATEMENT);

                p.setString(1, this.team.getName());
                p.setInt(2, this.team.getId());
            } else { //logo update
                STATEMENT = "UPDATE public.teams SET logo = ? WHERE id = ?";
                p = con.prepareStatement(STATEMENT);

                p.setBinaryStream(1, this.team.getLogo());
                p.setInt(2, this.team.getId());
            }

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

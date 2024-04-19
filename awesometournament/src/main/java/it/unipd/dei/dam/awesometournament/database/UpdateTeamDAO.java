package it.unipd.dei.dam.awesometournament.database;

import it.unipd.dei.dam.awesometournament.resources.entities.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateTeamDAO extends AbstractDAO<String>  {

    private String STATEMENT;
    private final Team team;

    public UpdateTeamDAO(final Connection con, final Team team) {
        super(con);
        LOGGER.error(team);
        LOGGER.error(team.getName());
        if (team == null) {
            LOGGER.error("Team cannot be null");
            throw new NullPointerException("Team cannot be null");
        }
        this.team = team;
        LOGGER.error(this.team);
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = null;

        try {
            if (this.team.getLogo().isEmpty()) {
                STATEMENT = "UPDATE public.teams SET name = ?, creator_user_id = ?, tournament_id = ? WHERE id = ?";
                p = con.prepareStatement(STATEMENT);

                p.setString(1, this.team.getName());
                p.setInt(2, this.team.getCreatorUserId());
                p.setInt(3, this.team.getTournamentId());
                p.setInt(4, this.team.getId());
            } else { //logo update
                STATEMENT = "UPDATE public.teams SET logo = ? WHERE id = ?";
                p = con.prepareStatement(STATEMENT);

                p.setString(1, this.team.getLogo());
                p.setInt(2, this.team.getId());
            }

            p.executeUpdate();
            LOGGER.info("Team successfully updated");
            this.outputParam = this.team.getName();
        } catch (Exception e) {
            LOGGER.error("Something went wrong: " + e.getMessage());
            this.outputParam = "Error";
            throw e;
        }
        finally {
            if (p != null)
                p.close();
        }
    }

}

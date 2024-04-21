package it.unipd.dei.dam.awesometournament.database;

import it.unipd.dei.dam.awesometournament.resources.entities.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CreateTeamDAO extends AbstractDAO<Integer>
{
    private static final String STATEMENT = "INSERT INTO public.teams (name, logo, creator_user_id, tournament_id) VALUES (?, ?, ?, ?) RETURNING id";
    private final Team team;

    public CreateTeamDAO(final Connection con, final Team team) {
        super(con);
        this.team = team;
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = null;
        try {
            p = con.prepareStatement(STATEMENT);

            p.setString(1, team.getName());
            p.setString(2, team.getLogo());
            p.setInt(3, team.getCreatorUserId());
            p.setInt(4, team.getTournamentId());

            ResultSet rs = p.executeQuery();
            LOGGER.info("Team added");
            rs.next();

            this.outputParam = rs.getInt("id");
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

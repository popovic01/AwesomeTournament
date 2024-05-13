package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import it.unipd.dei.dam.awesometournament.resources.entities.Team;

/**
 * DAO class for retrieving a team from the database.
 */
public class GetTeamDAO extends AbstractDAO<Team> {
    /**
     * The SQL statement used to retrieve a team from the database.
     */
    private static final String STATEMENT = "SELECT * FROM public.teams WHERE id = ?";
    /**
     * An id of a team which should be retrieved from the database.
     */
    private final int id;

    /**
     * Constructs a new GetTeamDAO object with the specified connection and id.
     * @param con A connection to the database.
     * @param id An id of a team which should be retrieved from the database.
     */
    public GetTeamDAO(final Connection con, final int id) {
        super(con);
        this.id = id;
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = null;
        ResultSet rs = null;
        Team team = null;

        try {
            p = con.prepareStatement(STATEMENT);
            p.setInt(1, this.id);
            rs = p.executeQuery();
            if (rs.next()) {
                team = new Team(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getBinaryStream("logo"),
                    rs.getInt("creator_user_id"),
                    rs.getInt("tournament_id")
                );

                team.setBase64Logo(team.getLogoAsBase64());

                LOGGER.info("Team with id %d found", this.id);
            } else {
                LOGGER.info("Team with id %d doesn't exist", this.id);
            }
        } finally {
            if (p != null) p.close();
            if (rs != null) rs.close();
        }
        this.outputParam = team;
    }

}

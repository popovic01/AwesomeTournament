package it.unipd.dei.dam.awesometournament.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import it.unipd.dei.dam.awesometournament.resources.entities.Team;
import it.unipd.dei.dam.awesometournament.utils.ImageConverter;

/**
 * DAO class for retrieving all teams belonging to a particular tournament from the database.
 */
public class GetTeamsByCreatorDAO extends AbstractDAO<List<Team>> {

    /**
     * The SQL statement used to retrieve all teams belonging to a particular tournament from the database.
     */
    private static final String STATEMENT = "SELECT * FROM public.\"teams\" WHERE creator_user_id = ? ORDER BY name";
    /**
     * An id of an user
     */
    private final int creatorId;

    /**
     * Constructs a new GetTournamentTeamsDAO object with the specified connection and tournamentId.
     * @param con A connection to the database.
     */
    public GetTeamsByCreatorDAO(final Connection con, final int creatorId) {
        super(con);
        this.creatorId = creatorId;
    }

    @Override
    protected void doAccess() throws Exception {

        PreparedStatement p = null;
        ResultSet rs = null;

        final List<Team> teams = new ArrayList<Team>();

        try {
            p = con.prepareStatement(STATEMENT);
            p.setInt(1, this.creatorId);

            rs = p.executeQuery();

            while (rs.next()) {
                teams.add(
                        new Team(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getBinaryStream("logo"),
                                rs.getInt("creator_user_id"),
                                rs.getInt("tournament_id")));
            }

            teams.forEach(team -> {
                if (team.getLogo() != null) {
                    team.setBase64Logo(ImageConverter.convertInputStreamToBase64(team.getLogo()));
                }
            });
        } finally {
            if (rs != null)
                rs.close();
            if (p != null)
                p.close();
        }
        this.outputParam = teams;
    }
}

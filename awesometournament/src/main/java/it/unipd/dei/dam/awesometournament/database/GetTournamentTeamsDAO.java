package it.unipd.dei.dam.awesometournament.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import it.unipd.dei.dam.awesometournament.resources.entities.Team;

/**
 * DAO class for retrieving all teams belonging to a particular tournament from the database.
 */
public class GetTournamentTeamsDAO extends AbstractDAO<List<Team>> {

    /**
     * The SQL statement used to retrieve all teams belonging to a particular tournament from the database.
     */
    private static final String STATEMENT = "SELECT * FROM public.\"teams\" WHERE tournament_id = ?";
    /**
     * An id of a tournament for which all the teams should be retrieved from the database.
     */
    private final int tournamentId;

    /**
     * Constructs a new GetTournamentTeamsDAO object with the specified connection and tournamentId.
     * @param con A connection to the database.
     * @param tournamentId An id of a tournament for which all the teams should be retrieved from the database.
     */
    public GetTournamentTeamsDAO(final Connection con, final int tournamentId) {
        super(con);
        this.tournamentId = tournamentId;
    }

    @Override
    protected void doAccess() throws Exception {

        PreparedStatement p = null;
        ResultSet rs = null;

        final List<Team> teams = new ArrayList<Team>();

        try {
            p = con.prepareStatement(STATEMENT);
            p.setInt(1, this.tournamentId);

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
                team.setBase64Logo(team.getLogoAsBase64());
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

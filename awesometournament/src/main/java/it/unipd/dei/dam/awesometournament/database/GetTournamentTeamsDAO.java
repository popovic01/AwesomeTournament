package it.unipd.dei.dam.awesometournament.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import it.unipd.dei.dam.awesometournament.resources.entities.Team;

public class GetTournamentTeamsDAO extends AbstractDAO<List<Team>> {
    private static final String STATEMENT = "SELECT * FROM public.\"teams\" WHERE tournament_id = ?";
    private final int tournamentId;

    /**
     * Creates a new DAO object.
     *
     * @param con the connection to be used for accessing the database.
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
                                rs.getString("logo"),
                                rs.getInt("creator_user_id"),
                                rs.getInt("tournament_id")));
            }
        } finally {
            if (rs != null)
                rs.close();
            if (p != null)
                p.close();
        }
        this.outputParam = teams;
    }
}

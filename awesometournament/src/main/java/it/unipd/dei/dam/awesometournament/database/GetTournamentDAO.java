package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import it.unipd.dei.dam.awesometournament.resources.entities.Tournament;

public class GetTournamentDAO extends AbstractDAO<ArrayList<Tournament>> {

    private static final String STATEMENT = "SELECT * FROM public.tournaments";

    public GetTournamentDAO(final Connection con) {
        super(con);
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = null;
        ResultSet rs = null;
        ArrayList<Tournament> tournaments = new ArrayList<>();

        try {
            p = con.prepareStatement(STATEMENT);
            rs = p.executeQuery();
            while (rs.next()) {
                tournaments.add(new Tournament(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("token"),
                        rs.getInt("creator_user_id"),
                        rs.getInt("max_teams"),
                        rs.getInt("max_players"),
                        rs.getInt("min_players"),
                        rs.getInt("starting_players"),
                        rs.getInt("max_substitutions"),
                        rs.getTimestamp("deadline"),
                        rs.getTimestamp("start_date"),
                        rs.getTimestamp("creation_date"),
                        rs.getString("logo"),
                        rs.getBoolean("is_finished")));
            }
        } finally {
            if (p != null) p.close();
            if (rs != null) rs.close();
        }
        this.outputParam = tournaments;
    }
}
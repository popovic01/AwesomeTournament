package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import it.unipd.dei.dam.awesometournament.resources.entities.Match;

public class GetTournamentsWithPastDeadlineDAO extends AbstractDAO<List<Integer>>{

    private static final String STATEMENT = "SELECT * FROM public.tournaments WHERE deadline BETWEEN (current_timestamp AT TIME ZONE 'UTC') - interval '24 hours' + interval '1 second' AND (current_timestamp AT TIME ZONE 'UTC')";

    public GetTournamentsWithPastDeadlineDAO(final Connection con) {
        super(con);
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = con.prepareStatement(STATEMENT);
        ResultSet rs = null;

        final List<Integer> tournamentIDs = new ArrayList<Integer>();  

        try {
            rs = p.executeQuery();

            while (rs.next()) {
                int tournamentId = rs.getInt("id");
                GetTournamentMatchesDAO getTournamentMatchesDAO = new GetTournamentMatchesDAO(con, tournamentId);
                List<Match> matches = getTournamentMatchesDAO.access().getOutputParam();
                if (matches.size() == 0) {
                    tournamentIDs.add(tournamentId);
                }
            }
        } finally {
            if(rs != null) rs.close();
            if(p != null) p.close();
        }
        this.outputParam = tournamentIDs;
    }
}
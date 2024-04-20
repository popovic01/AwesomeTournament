package it.unipd.dei.dam.awesometournament.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import it.unipd.dei.dam.awesometournament.resources.entities.Tournament;

public class GetTournamentsDAO extends AbstractDAO<List<Tournament>> {

    private static final String STATEMENT = "SELECT * FROM public.tournaments";

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement ps = con.prepareStatement(STATEMENT);
        ResultSet rs = ps.executeQuery();

        ArrayList<Tournament> res = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String token = rs.getString("token");
            int creatorUserId = rs.getInt("creator_user_id");
            int maxTeams = rs.getInt("max_teams");
            int maxPlayers = rs.getInt("max_players");
            int minPlayers = rs.getInt("min_players");
            int startingPlayers = rs.getInt("starting_players");
            int maxSubstitutions = rs.getInt("max_substitutions");
            Timestamp deadline = rs.getTimestamp("deadline");
            Timestamp startDate = rs.getTimestamp("start_date");
            Timestamp creationDate = rs.getTimestamp("creation_date");
            String logo = rs.getString("logo");
            boolean isFinished = rs.getBoolean("is_finished");

            Tournament t = new Tournament(id, name, token, creatorUserId, maxTeams, maxPlayers,
                    minPlayers, startingPlayers, maxSubstitutions, deadline,
                    startDate, creationDate, logo, isFinished);
            
            LOGGER.info(t);
            res.add(t);
        }

        outputParam = res;
    }

    public GetTournamentsDAO(final Connection con) {
        super(con);
    }

}

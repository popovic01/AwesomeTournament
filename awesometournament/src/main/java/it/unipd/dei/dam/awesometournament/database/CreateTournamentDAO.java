package it.unipd.dei.dam.awesometournament.database;

import it.unipd.dei.dam.awesometournament.resources.entities.Tournament;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CreateTournamentDAO extends AbstractDAO<Integer> {
    private static final String STATEMENT = "INSERT INTO public.tournaments " +
            "(name, token, creator_user_id, max_teams, max_players, min_players, starting_players," +
            "max_substitutions, deadline, start_date, creation_date, logo, is_finished) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING *";

    private final Tournament tournament;

    public CreateTournamentDAO(final Connection con, final Tournament tournament) {
        super(con);
        if (tournament == null) {
            LOGGER.error("The tournament cannot be null.");
            throw new NullPointerException("The tournament cannot be null.");
        }
        this.tournament = tournament;
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = null;
        ResultSet rs = null;

        try {
            p = con.prepareStatement(STATEMENT);
            p.setString(1, tournament.getName());
            p.setString(2, tournament.getToken());
            p.setInt(3, tournament.getCreatorUserId());
            p.setInt(4, tournament.getMaxTeams());
            p.setInt(5, tournament.getMaxPlayers());
            p.setInt(6, tournament.getMinPlayers());
            p.setInt(7, tournament.getStartingPlayers());
            p.setInt(8, tournament.getMaxSubstitutions());
            p.setTimestamp(9, tournament.getDeadline());
            p.setTimestamp(10, tournament.getStartDate());
            p.setTimestamp(11, tournament.getCreationDate());
            p.setString(12, tournament.getLogo());
            p.setBoolean(13, tournament.getIsFinished());

            rs = p.executeQuery();
            if (rs.next()) {
                this.outputParam = rs.getInt("id");
                LOGGER.info("Tournament successfully created");
            }
            else {
                LOGGER.info("Something went wrong");
                this.outputParam = null;
            }
        }
        finally {
            if (p != null) p.close();
            if (rs != null) rs.close();
        }
    }
}
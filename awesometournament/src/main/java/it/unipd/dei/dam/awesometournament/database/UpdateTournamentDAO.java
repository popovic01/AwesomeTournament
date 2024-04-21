package it.unipd.dei.dam.awesometournament.database;

import it.unipd.dei.dam.awesometournament.resources.entities.Tournament;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateTournamentDAO extends AbstractDAO<Integer> {
    private static String STATEMENT;

    private final Tournament tournament;

    public UpdateTournamentDAO(final Connection con, final Tournament tournament) {
        super(con);
        if (tournament == null) {
            LOGGER.error("The tournament cannot be null.");
            throw new NullPointerException("The tournament cannot be null.");
        }
        this.tournament = tournament;
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement t = null;

        try {
            if (this.tournament.getLogo().isEmpty()) {
                STATEMENT = "UPDATE public.tournaments " +
                        "SET name = ?, token = ?, creator_user_id = ?, max_teams = ?, max_players = ?, min_players = ?, " +
                        "starting_players = ?, max_substitutions = ?, deadline = ?, start_date = ?, creation_date = ?, " +
                        "is_finished = ? WHERE id = ?";

                t = con.prepareStatement(STATEMENT);
                t.setString(1, tournament.getName());
                t.setString(2, tournament.getToken());
                t.setInt(3, tournament.getCreatorUserId());
                t.setInt(4, tournament.getMaxTeams());
                t.setInt(5, tournament.getMaxPlayers());
                t.setInt(6, tournament.getMinPlayers());
                t.setInt(7, tournament.getStartingPlayers());
                t.setInt(8, tournament.getMaxSubstitutions());
                t.setTimestamp(9, tournament.getDeadline());
                t.setTimestamp(10, tournament.getStartDate());
                t.setTimestamp(11, tournament.getCreationDate());
                t.setString(12, tournament.getLogo());
                t.setBoolean(13, tournament.getIsFinished());
            }
            else {
                STATEMENT = "UPDATE tournaments.teams SET logo = ? WHERE id = ?";

                t = con.prepareStatement(STATEMENT);

                t.setString(1, this.tournament.getLogo());
                t.setInt(2, this.tournament.getId());
            }

            int res = t.executeUpdate();
            if (res == 1) LOGGER.info("Tournament successfully updated");
            else LOGGER.info("Something went wrong %d", res);
            this.outputParam = res; // 1 if success, 0 otherwise
        }
        finally {
            if (t != null) t.close();
        }
    }
}
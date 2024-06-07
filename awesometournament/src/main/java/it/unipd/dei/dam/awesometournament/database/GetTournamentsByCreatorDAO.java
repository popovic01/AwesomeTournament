package it.unipd.dei.dam.awesometournament.database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import it.unipd.dei.dam.awesometournament.resources.entities.Tournament;
import it.unipd.dei.dam.awesometournament.utils.ImageConverter;

/**
 * DAO class for retrieving tournaments from the database.
 */
public class GetTournamentsByCreatorDAO extends AbstractDAO<List<Tournament>> {
    /**
     * The SQL statement used to retrieve tournaments from the database.
     */
    private static final String STATEMENT = "SELECT * FROM public.tournaments WHERE creator_user_id = ?";

    final Integer creatorId;

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = con.prepareStatement(STATEMENT);
        p.setInt(1, this.creatorId);
        ResultSet rs = p.executeQuery();

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
            InputStream logo = rs.getBinaryStream("logo");
            boolean isFinished = rs.getBoolean("is_finished");

            Tournament t = new Tournament(id, name, token, creatorUserId, maxTeams, maxPlayers,
                    minPlayers, startingPlayers, maxSubstitutions, deadline,
                    startDate, creationDate, logo, isFinished);

            if (t.getLogo() != null) {
                t.setBase64Logo(ImageConverter.convertInputStreamToBase64(t.getLogo()));
            }
            
            LOGGER.info(t);
            res.add(t);
        }

        this.outputParam = res;
    }

    /**
     * Constructs a new GetTournamentsDAO object with the specified connection.
     * @param con A connection to the database.
     */
    public GetTournamentsByCreatorDAO(final Connection con, final Integer creatorId) {
        super(con);
        this.creatorId = creatorId;
    }

}

package it.unipd.dei.dam.awesometournament.database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import it.unipd.dei.dam.awesometournament.resources.entities.Tournament;
import it.unipd.dei.dam.awesometournament.utils.ImageConverter;

/**
 * DAO class for retrieving a tournament by its ID from the database.
 */
public class GetTournamentByIdDAO extends AbstractDAO<Tournament> {
    /**
     * The SQL statement used to retrieve a tournament by its ID.
     */
    private static final String STATEMENT = "SELECT * FROM public.tournaments where id = ?";

    private int id;

    /**
     * Constructs a new GetTournamentByIdDAO.
     *
     * @param con The database connection.
     * @param id The ID of the tournament to retrieve.
     */
    public GetTournamentByIdDAO(final Connection con, int id) {
        super(con);
        this.id = id;
    }

    @Override
    protected void doAccess() throws Exception {
        PreparedStatement p = con.prepareStatement(STATEMENT);
        p.setInt(1, id);
        ResultSet rs = p.executeQuery();

        if (rs.next()) {
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
            this.outputParam = t;
        } else {
            this.outputParam = null;
        }

    }
}

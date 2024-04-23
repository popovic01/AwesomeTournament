package it.unipd.dei.dam.awesometournament.jobs;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import it.unipd.dei.dam.awesometournament.database.CreateMatchDAO;
import it.unipd.dei.dam.awesometournament.database.GetTournamentMatchesDAO;
import it.unipd.dei.dam.awesometournament.database.GetTournamentTeamsDAO;
import it.unipd.dei.dam.awesometournament.database.GetTournamentsWithPastDeadlineDAO;
import it.unipd.dei.dam.awesometournament.resources.entities.Match;
import it.unipd.dei.dam.awesometournament.resources.entities.Team;

import jakarta.servlet.ServletException;

public class MatchCreatorJob {
    protected final static Logger LOGGER = LogManager.getLogger(MatchCreatorJob.class,
                    StringFormatterMessageFactory.INSTANCE);

    public static void execute(int tournamentId) throws Exception {
        LOGGER.info("Started Match Creator Job");

        List<Integer> tournamentIDs = new ArrayList<>();

        if (tournamentId == -1) {
            GetTournamentsWithPastDeadlineDAO getTournamentsWithPastDeadlineDAO = new GetTournamentsWithPastDeadlineDAO(
                    getConnection());
            tournamentIDs = (List<Integer>) getTournamentsWithPastDeadlineDAO.access().getOutputParam();
        } else {
            GetTournamentMatchesDAO getTournamentMatchesDAO = new GetTournamentMatchesDAO(getConnection(),
                    tournamentId);
            List<Match> matches = getTournamentMatchesDAO.access().getOutputParam();
            LOGGER.info(matches.size());
            if (matches.size() == 0) {
                tournamentIDs.add(tournamentId);
            } else {
                LOGGER.error("Matches already created");
                throw new Exception("matches already created");
            }
        }

        for (int id : tournamentIDs) {
            GetTournamentTeamsDAO getTournamentTeamsDAO = new GetTournamentTeamsDAO(getConnection(), id);
            List<Team> teams = (List<Team>) getTournamentTeamsDAO.access().getOutputParam();
            LOGGER.info("Found " + teams.size() + " teams");

            List<Match> matches = generateRoundRobinMatches(teams, id);
            LOGGER.info("Found " + matches.size() + " matches");

            for (Match match : matches) {
                CreateMatchDAO createMatchDAO = new CreateMatchDAO(getConnection(), match);
                int matchId = (int) createMatchDAO.access().getOutputParam();

                LOGGER.info("Created match: Team 1 ID: " + match.getTeam1Id() + ", Team 2 ID: " + match.getTeam2Id() + ", ID: " + matchId);
            }
        }
    }

    private static DataSource ds;

    protected final static Connection getConnection() throws SQLException, NamingException, ServletException {
        InitialContext cxt;

        try {
			cxt = new InitialContext();
			ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/awesome");

			LOGGER.info("Connection pool to the database pool successfully acquired.");
		} catch (NamingException e) {
			ds = null;

			LOGGER.error("Unable to acquire the connection pool to the database.", e);

			throw new ServletException("Unable to acquire the connection pool to the database", e);
		}
        return ds.getConnection();
    }

    public static List<Match> generateRoundRobinMatches(List<Team> teams, int tournamentId) {
        List<Match> matches = new ArrayList<>();

        int numTeams = teams.size();

        for (int i = 0; i < numTeams; i++) {
                for (int j = i + 1; j < numTeams; j++) {
                        Team teamA = teams.get(i);
                        Team teamB = teams.get(j);

                        matches.add(new Match(
                                -1,
                                teamA.getId(),
                                teamB.getId(),
                                tournamentId,
                                null,
                                null,
                                null,
                                "",
                                null,
                                false
                        ));

                        matches.add(new Match(
                                -1,
                                teamB.getId(),
                                teamA.getId(),
                                tournamentId,
                                null,
                                null,
                                null,
                                "",
                                null,
                                false
                        ));
                }
        }
        return matches;
    }
}

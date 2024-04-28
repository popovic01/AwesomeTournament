package it.unipd.dei.dam.awesometournament.utils;

/**
 * Represents an entry in a ranking table.
 */
public class RankingEntry {
    /**
     * The name of the team.
     */
    String teamName;

    /**
     * The total points accumulated by the team.
     */
    int points;

    /**
     * The total number of matches played by the team.
     */
    int matchesPlayed;

    /**
     * Constructs a ranking entry with the specified attributes.
     *
     * @param teamName      The name of the team.
     * @param points        The total points accumulated by the team.
     * @param matchesPlayed The total number of matches played by the team.
     */
    public RankingEntry (String teamName, int points, int matchesPlayed) {
        this.teamName = teamName;
        this.points = points;
        this.matchesPlayed = matchesPlayed;
    }

    /**
     * Gets the name of the team.
     *
     * @return The name of the team.
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Gets the total points accumulated by the team.
     *
     * @return The total points accumulated by the team.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Gets the total number of matches played by the team.
     *
     * @return The total number of matches played by the team.
     */
    public int getMatchesPlayed () {
        return matchesPlayed;
    }
}

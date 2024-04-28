package it.unipd.dei.dam.awesometournament.utils;

/**
 * Represents an entry in a ranking, containing information about a team's name, points, and matches played.
 */
public class RankingEntry {
    /** The name of the team. */
    String teamName;
    /** The points accumulated by the team. */
    int points;
    /** The number of matches played by the team. */
    int matchesPlayed;

    /**
     * Constructs a new ranking entry with the specified team name, points, and matches played.
     *
     * @param teamName The name of the team.
     * @param points The points accumulated by the team.
     * @param matchesPlayed The number of matches played by the team.
     */
    public RankingEntry(String teamName, int points, int matchesPlayed) {
        this.teamName = teamName;
        this.points = points;
        this.matchesPlayed = matchesPlayed;
    }

    /**
     * Gets the name of the team.
     *
     * @return The team name.
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Gets the points accumulated by the team.
     *
     * @return The points.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Gets the number of matches played by the team.
     *
     * @return The number of matches played.
     */
    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    /**
     * Sets the name of the team.
     *
     * @param teamName The name of the team.
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     * Sets the points accumulated by the team.
     *
     * @param points The points to set.
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Sets the number of matches played by the team.
     *
     * @param matchesPlayed The number of matches played.
     */
    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }
}

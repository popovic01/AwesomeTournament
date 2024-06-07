package it.unipd.dei.dam.awesometournament.utils;

import java.util.Base64;

/**
 * Represents an entry in a ranking, containing information about a team's name, points, and matches played.
 */
public class RankingEntry {
    /** The id of the team. */
    int teamId;
    /** The name of the team. */
    String teamName;
    /** The points accumulated by the team. */
    int points;
    /** The number of matches played by the team. */
    int matchesPlayed;
    /** The logo of the team. */
    byte[] logo;

    int wins;
    int defeats;
    int draws;
    int goalsScored;
    int goalsConceded;

    /**
     * Constructs a new ranking entry with the specified team name, points, and matches played.
     *
     * @param teamName The name of the team.
     * @param points The points accumulated by the team.
     * @param matchesPlayed The number of matches played by the team.
     */
    public RankingEntry(int teamId, String teamName, int points, int matchesPlayed, byte[] logo,
                        int wins, int defeats, int draws, int goalsScored, int goalsConceded) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.points = points;
        this.matchesPlayed = matchesPlayed;
        this.logo = logo;
        this.wins = wins;
        this.defeats = defeats;
        this.draws = draws;
        this.goalsScored = goalsScored;
        this.goalsConceded = goalsConceded;
    }

    /**
     * Gets the id of the team.
     *
     * @return The team id.
     */
    public int getTeamId() {
        return teamId;
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

    public int getWins() {
        return wins;
    }

    public int getDefeats() {
        return defeats;
    }

    public int getDraws() {
        return draws;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public int getGoalsConceded() {
        return goalsConceded;
    }

    /**
     * Gets the logo of the team.
     *
     * @return The logo of the team.
     */
    public String getLogo() {
        if (logo != null) return Base64.getEncoder().encodeToString(logo);
        else return "";
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

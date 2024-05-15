package it.unipd.dei.dam.awesometournament.utils;

import java.util.Base64;

/**
 * Represents an entry in the ranking of scorers.
 */
public class RankingScorersEntry {
    /**
     * The name of the player.
     */
    String playerName;

    /**
     * The surname of the player.
     */
    String playerSurname;

    String teamName;

    byte[] logo;

    /**
     * The number of goals scored by the player.
     */
    int goals;

    /**
     * Constructs a new RankingScorersEntry object with the specified player name, player surname, and number of goals.
     *
     * @param playerName    the name of the player
     * @param playerSurname the surname of the player
     * @param goals         the number of goals scored by the player
     */
    public RankingScorersEntry (String playerName, String playerSurname, String teamName, byte[] logo, int goals) {
        this.playerName = playerName;
        this.playerSurname = playerSurname;
        this.teamName = teamName;
        this.logo = logo;
        this.goals = goals;
    }

    /**
     * Retrieves the name of the player.
     *
     * @return the name of the player
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Retrieves the surname of the player.
     *
     * @return the surname of the player
     */
    public String getPlayerSurname() {
        return playerSurname;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getLogo() {
        if (logo != null) return Base64.getEncoder().encodeToString(logo);
        else return "";
    }

    /**
     * Retrieves the number of goals scored by the player.
     *
     * @return the number of goals scored by the player
     */
    public int getGoals() {
        return goals;
    }
}

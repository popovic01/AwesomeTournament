package it.unipd.dei.dam.awesometournament.utils;

public class RankingScorersEntry {
    String playerName;
    String playerSurname;
    int goals;

    public RankingScorersEntry (String playerName, String playerSurname, int goals) {
        this.playerName = playerName;
        this.playerSurname = playerSurname;
        this.goals = goals;
    }

    public String getPlayerName() {
        return playerName;
    }
    public String getPlayerSurname() {
        return playerSurname;
    }
    public int getGoals() {
        return goals;
    }

}

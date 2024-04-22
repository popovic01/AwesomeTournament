package it.unipd.dei.dam.awesometournament.utils;

public class RankingScorersEntry {
    String playerName;
    String playerSurname;
    int goals;
    int matchesPlayed;

    public RankingScorersEntry (String playerName, String playerSurname, int goals, int matchesPlayed) {
        this.playerName = playerName;
        this.playerSurname = playerSurname;
        this.goals = goals;
        this.matchesPlayed = matchesPlayed;
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
    public int getMatchesPlayed () {
        return matchesPlayed;
    }

}

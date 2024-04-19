package it.unipd.dei.dam.awesometournament.utils;

public class RankingEntry {
    String teamName;
    int points;
    int matchesPlayed;

    public RankingEntry (String teamName, int points, int matchesPlayed) {
        this.teamName = teamName;
        this.points = points;
        this.matchesPlayed = matchesPlayed;
    }
}

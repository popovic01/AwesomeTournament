package it.unipd.dei.dam.awesometournament.resources.entities;

import it.unipd.dei.dam.awesometournament.resources.enums.MatchResult;

import java.sql.Timestamp;

public class Match {
    private int id;
    private int team1Id;
    private int team2Id;
    private int tournamentId;
    private Integer team1Score; // allows null values
    private Integer team2Score; // allows null values
    private MatchResult result;
    private String referee;
    private Timestamp matchDate;
    private boolean isFinished;

    public Match(int id, int team1Id, int team2Id, int tournamentId, Integer team1Score, Integer team2Score,
                 MatchResult result, String referee, Timestamp matchDate, boolean isFinished) {
        this.id = id;
        this.team1Id = team1Id;
        this.team2Id = team2Id;
        this.tournamentId = tournamentId;
        this.team1Score = team1Score;
        this.team2Score = team2Score;
        this.result = result;
        this.referee = referee;
        this.matchDate = matchDate;
        this.isFinished = isFinished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeam1Id() {
        return team1Id;
    }

    public void setTeam1Id(int team1Id) {
        this.team1Id = team1Id;
    }

    public int getTeam2Id() {
        return team2Id;
    }

    public void setTeam2Id(int team2Id) {
        this.team2Id = team2Id;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }

    public Integer getTeam1Score() {
        return team1Score;
    }

    public void setTeam1Score(Integer team1Score) {
        this.team1Score = team1Score;
    }

    public Integer getTeam2Score() {
        return team2Score;
    }

    public void setTeam2Score(Integer team2Score) {
        this.team2Score = team2Score;
    }

    public MatchResult getResult() {
        return result;
    }

    public void setResult(MatchResult result) {
        this.result = result;
    }

    public String getReferee() {
        return referee;
    }

    public void setReferee(String referee) {
        this.referee = referee;
    }

    public Timestamp getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Timestamp matchDate) {
        this.matchDate = matchDate;
    }

    public boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean finished) {
        isFinished = finished;
    }

    @Override
    public String toString() {
        return id +
                ", " + team1Id +
                ", " + team2Id +
                ", " + tournamentId +
                ", " + team1Score +
                ", " + team2Score +
                ", " + result +
                ", " + referee +
                ", " + matchDate +
                ", " + isFinished;
    }
}


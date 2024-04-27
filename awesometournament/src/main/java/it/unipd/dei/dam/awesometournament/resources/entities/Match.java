package it.unipd.dei.dam.awesometournament.resources.entities;

import it.unipd.dei.dam.awesometournament.resources.enums.MatchResult;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a match between two teams in a tournament.
 */
public class Match {
    /**
     * The ID of the match.
     */
    @JsonProperty("id")
    private int id;

    /**
     * The ID of the first team.
     */
    @JsonProperty("team1Id")
    private int team1Id;

    /**
     * The ID of the second team.
     */
    @JsonProperty("team2Id")
    private int team2Id;

    /**
     * The ID of the tournament.
     */
    @JsonProperty("tournamentId")
    private int tournamentId;

    /**
     * The score of the first team.
     */
    @JsonProperty("team1Score")
    private Integer team1Score;

    /**
     * The score of the second team.
     */
    @JsonProperty("team2Score")
    private Integer team2Score;

    /**
     * The result of the match.
     */
    @JsonProperty("result")
    private MatchResult result;

    /**
     * The referee of the match.
     */
    @JsonProperty("referee")
    private String referee;

    /**
     * The date and time of the match.
     */
    @JsonProperty("matchDate")
    private Timestamp matchDate;

    /**
     * Indicates whether the match is finished.
     */
    @JsonProperty("isFinished")
    private boolean isFinished;

    /**
     * Default constructor for JSON deserialization.
     */
    @JsonCreator
    public Match(){}

    /**
     * Constructs a match with the specified attributes.
     *
     * @param id           The ID of the match.
     * @param team1Id      The ID of the first team.
     * @param team2Id      The ID of the second team.
     * @param tournamentId The ID of the tournament.
     * @param team1Score   The score of the first team.
     * @param team2Score   The score of the second team.
     * @param result       The result of the match.
     * @param referee      The referee of the match.
     * @param matchDate    The date and time of the match.
     * @param isFinished   Indicates whether the match is finished.
     */
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

    /**
     * Gets the ID of the match.
     *
     * @return The ID of the match.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the match.
     *
     * @param id The ID of the match.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the ID of the first team.
     *
     * @return The ID of the first team.
     */
    public int getTeam1Id() {
        return team1Id;
    }

     /**
     * Sets the ID of the first team.
     *
     * @param team1Id The ID of the first team.
     */
    public void setTeam1Id(int team1Id) {
        this.team1Id = team1Id;
    }

     /**
     * Gets the ID of the second team.
     *
     * @return The ID of the second team.
     */
    public int getTeam2Id() {
        return team2Id;
    }

    /**
     * Sets the ID of the second team.
     *
     * @param team2Id The ID of the second team.
     */
    public void setTeam2Id(int team2Id) {
        this.team2Id = team2Id;
    }

    /**
     * Gets the ID of the tournament.
     *
     * @return The ID of the tournament.
     */
    public int getTournamentId() {
        return tournamentId;
    }

    /**
     * Sets the ID of the tournament.
     *
     * @param tournamentId The ID of the tournament.
     */
    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }

     /**
     * Gets the score of the first team.
     *
     * @return The score of the first team.
     */
    public Integer getTeam1Score() {
        return team1Score;
    }

    /**
     * Sets the score of the first team.
     *
     * @param team1Score The score of the first team.
     */
    public void setTeam1Score(Integer team1Score) {
        this.team1Score = team1Score;
    }

    /**
     * Gets the score of the second team.
     *
     * @return The score of the second team.
     */
    public Integer getTeam2Score() {
        return team2Score;
    }

    /**
     * Sets the score of the second team.
     *
     * @param team2Score The score of the second team.
     */
    public void setTeam2Score(Integer team2Score) {
        this.team2Score = team2Score;
    }

    /**
     * Gets the result of the match.
     *
     * @return The result of the match.
     */
    public MatchResult getResult() {
        return result;
    }

    /**
     * Sets the result of the match.
     *
     * @param result The result of the match.
     */
    public void setResult(MatchResult result) {
        this.result = result;
    }

    /**
     * Gets the referee of the match.
     *
     * @return The referee of the match.
     */
    public String getReferee() {
        return referee;
    }

    /**
     * Sets the referee of the match.
     *
     * @param referee The referee of the match.
     */
    public void setReferee(String referee) {
        this.referee = referee;
    }

    /**
     * Gets the date and time of the match.
     *
     * @return The date and time of the match.
     */
    public Timestamp getMatchDate() {
        return matchDate;
    }

    /**
     * Sets the date and time of the match.
     *
     * @param matchDate The date and time of the match.
     */
    public void setMatchDate(Timestamp matchDate) {
        this.matchDate = matchDate;
    }

    /**
     * Indicates whether the match is finished.
     *
     * @return True if the match is finished, false otherwise.
     */
    public boolean getIsFinished() {
        return isFinished;
    }
    
    /**
     * Sets the finished status of the match.
     *
     * @param finished True if the match is finished, false otherwise.
     */
    public void setIsFinished(boolean finished) {
        isFinished = finished;
    }

    /**
     * Returns a string representation of the match.
     *
     * @return A string representation of the match.
     */
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

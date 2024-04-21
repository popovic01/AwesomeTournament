package it.unipd.dei.dam.awesometournament.resources.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class Tournament {

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("token")
    private String token;
    @JsonProperty("creatorUserId")
    private int creatorUserId;
    @JsonProperty("maxTeams")
    private int maxTeams;
    @JsonProperty("maxPlayers")
    private int maxPlayers;
    @JsonProperty("minPlayers")
    private int minPlayers;
    @JsonProperty("startingPlayers")
    private int startingPlayers;
    @JsonProperty("maxSubstitutions")
    private int maxSubstitutions;
    @JsonProperty("deadline")
    private Timestamp deadline;
    @JsonProperty("startDate")
    private Timestamp startDate;
    @JsonProperty("creationDate")
    private Timestamp creationDate;
    @JsonProperty("logo")
    private String logo;
    @JsonProperty("isFinished")
    private boolean isFinished;

    @JsonCreator
    public Tournament(){}

    public Tournament(int id, String name, String token, int creatorUserId, int maxTeams, int maxPlayers,
                      int minPlayers, int startingPlayers, int maxSubstitutions, Timestamp deadline,
                      Timestamp startDate, Timestamp creationDate, String logo, boolean isFinished) {
        this.id = id;
        this.name = name;
        this.token = token;
        this.creatorUserId = creatorUserId;
        this.maxTeams = maxTeams;
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
        this.startingPlayers = startingPlayers;
        this.maxSubstitutions = maxSubstitutions;
        this.deadline = deadline;
        this.startDate = startDate;
        this.creationDate = creationDate;
        this.logo = logo;
        this.isFinished = isFinished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(int creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public int getMaxTeams() {
        return maxTeams;
    }

    public void setMaxTeams(int maxTeams) {
        this.maxTeams = maxTeams;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getStartingPlayers() {
        return startingPlayers;
    }

    public void setStartingPlayers(int startingPlayers) {
        this.startingPlayers = startingPlayers;
    }

    public int getMaxSubstitutions() {
        return maxSubstitutions;
    }

    public void setMaxSubstitutions(int maxSubstitutions) {
        this.maxSubstitutions = maxSubstitutions;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean finished) {
        isFinished = finished;
    }

    @Override
    public String toString() {
        return "Tournament [id=" + id + ", name=" + name + ", token=" + token + ", creatorUserId=" + creatorUserId
                + ", maxTeams=" + maxTeams + ", maxPlayers=" + maxPlayers + ", minPlayers=" + minPlayers
                + ", startingPlayers=" + startingPlayers + ", maxSubstitutions=" + maxSubstitutions + ", deadline="
                + deadline + ", startDate=" + startDate + ", creationDate=" + creationDate + ", logo=" + logo
                + ", isFinished=" + isFinished + "]";
    }
}
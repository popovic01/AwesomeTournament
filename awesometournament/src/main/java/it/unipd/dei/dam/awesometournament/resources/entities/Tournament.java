package it.unipd.dei.dam.awesometournament.resources.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A class which represents a tournament.
 */
public class Tournament {

    /**
     * The ID of the tournament.
     */
    @JsonProperty("id")
    private int id;

    /**
     * The name of the tournament.
     */
    @JsonProperty("name")
    private String name;

    /**
     * The token associated with the tournament.
     */
    @JsonProperty("token")
    private String token;

    /**
     * The ID of the user who created the tournament.
     */
    @JsonProperty("creatorUserId")
    private int creatorUserId;

    /**
     * The maximum number of teams allowed in the tournament.
     */
    @JsonProperty("maxTeams")
    private int maxTeams;

    /**
     * The maximum number of players allowed in each team.
     */
    @JsonProperty("maxPlayers")
    private int maxPlayers;

    /**
     * The minimum number of players required in each team.
     */
    @JsonProperty("minPlayers")
    private int minPlayers;

    /**
     * The number of starting players in each team.
     */
    @JsonProperty("startingPlayers")
    private int startingPlayers;

    /**
     * The maximum number of substitutions allowed in each match.
     */
    @JsonProperty("maxSubstitutions")
    private int maxSubstitutions;

    /**
     * The deadline for registration or participation in the tournament.
     */
    @JsonProperty("deadline")
    private Timestamp deadline;

    /**
     * The start date of the tournament.
     */
    @JsonProperty("startDate")
    private Timestamp startDate;

    /**
     * The creation date of the tournament.
     */
    @JsonProperty("creationDate")
    private Timestamp creationDate;

    /**
     * The logo of the tournament.
     */
    @JsonProperty("logo")
    private InputStream logo;

    /**
     * The base64 representation of the tournament logo.
     */
    @JsonProperty("base64-logo")
    private String base64Logo;

    /**
     * Indicates whether the tournament has finished.
     */
    @JsonProperty("isFinished")
    private boolean isFinished;

    /**
     * Default constructor for the Tournament class.
     */
    @JsonCreator
    public Tournament(){}

    /**
     * Constructs a new Tournament object with the specified attributes.
     *
     * @param id              the ID of the tournament
     * @param name            the name of the tournament
     * @param token           the token associated with the tournament
     * @param creatorUserId   the ID of the user who created the tournament
     * @param maxTeams        the maximum number of teams allowed in the tournament
     * @param maxPlayers      the maximum number of players allowed in each team
     * @param minPlayers      the minimum number of players required in each team
     * @param startingPlayers the number of starting players in each team
     * @param maxSubstitutions the maximum number of substitutions allowed in each match
     * @param deadline        the deadline for registration or participation in the tournament
     * @param startDate       the start date of the tournament
     * @param creationDate    the creation date of the tournament
     * @param logo            the logo of the tournament
     * @param isFinished      indicates whether the tournament has finished
     */
    public Tournament(int id, String name, String token, int creatorUserId, int maxTeams, int maxPlayers,
                      int minPlayers, int startingPlayers, int maxSubstitutions, Timestamp deadline,
                      Timestamp startDate, Timestamp creationDate, InputStream logo, boolean isFinished) {
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
        this.base64Logo = logo;
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

    public LocalDate getOnlyStartDate() {
        Timestamp startDateTime = Timestamp.valueOf(getStartDate().toString());
        LocalDateTime localDateTime  = startDateTime.toLocalDateTime();
        return localDateTime.toLocalDate();
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

    public InputStream getLogo() {
        return logo;
    }

    public void setLogo(InputStream logo) {
        this.logo = logo;
    }

    public boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean finished) {
        isFinished = finished;
    }

    /**
     * Returns the base64 representation logo of the team.
     *
     * @return The base64 representation logo of the team.
     */
    public String getBase64Logo() {
        return base64Logo;
    }

    /**
     * Sets the base64 representation logo of the team.
     *
     * @param base64Logo The base64 representation logo of the team.
     */
    public void setBase64Logo(String base64Logo) {
        this.base64Logo = base64Logo;
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

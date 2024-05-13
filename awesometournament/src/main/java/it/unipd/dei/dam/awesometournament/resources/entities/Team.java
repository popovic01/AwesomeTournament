package it.unipd.dei.dam.awesometournament.resources.entities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class which represents a team in a tournament.
 */
public class Team {
    /**
     * The ID of the team.
     */
    @JsonProperty("id")
    private int id;

    /**
     * The name of the team.
     */
    @JsonProperty("name")
    private String name;

    /**
     * The logo of the team.
     */
    @JsonProperty("logo")
    private InputStream logo;

    /**
     * The base64 representation of the team logo.
     */
    @JsonProperty("base64-logo")
    private String base64Logo;

    /**
     * The ID of the user who created the team.
     */
    @JsonProperty("creatorUserId")
    private int creatorUserId;

    /**
     * The ID of the tournament the team belongs to.
     */
    @JsonProperty("tournamentId")
    private int tournamentId;

    /**
     * Default constructor for JSON deserialization.
     */
    public Team() {

    }

    /**
     * Constructs a team with the specified parameters.
     *
     * @param id            The ID of the team.
     * @param name          The name of the team.
     * @param logo          The logo of the team.
     * @param creatorUserId The ID of the user who created the team.
     * @param tournamentId  The ID of the tournament the team belongs to.
     */
    public Team(int id, String name, InputStream logo, int creatorUserId, int tournamentId) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.creatorUserId = creatorUserId;
        this.tournamentId = tournamentId;
    }

    /**
     * Constructs a team with the specified parameters.
     *
     * @param id            The ID of the team.
     * @param name          The name of the team.
     * @param logo          The base64 representation of the team logo.
     * @param creatorUserId The ID of the user who created the team.
     * @param tournamentId  The ID of the tournament the team belongs to.
     */
    public Team(int id, String name, String logo, int creatorUserId, int tournamentId) {
        this.id = id;
        this.name = name;
        this.base64Logo = logo;
        this.creatorUserId = creatorUserId;
        this.tournamentId = tournamentId;
    }
    /**
     * Returns the ID of the team.
     *
     * @return The ID of the team.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the team.
     *
     * @param id The ID of the team.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the team.
     *
     * @return The name of the team.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the team.
     *
     * @param name The name of the team.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the logo of the team.
     *
     * @return The logo of the team.
     */
    public InputStream getLogo() {
        return logo;
    }

    /**
     * Sets the logo of the team.
     *
     * @param logo The logo of the team.
     */
    public void setLogo(InputStream logo) {
        this.logo = logo;
    }

    /**
     * Returns the ID of the user who created the team.
     *
     * @return The ID of the user who created the team.
     */
    public int getCreatorUserId() {
        return creatorUserId;
    }

    /**
     * Sets the ID of the user who created the team.
     *
     * @param creatorUserId The ID of the user who created the team.
     */
    public void setCreatorUserId(int creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    /**
     * Returns the ID of the tournament the team belongs to.
     *
     * @return The ID of the tournament the team belongs to.
     */
    public int getTournamentId() {
        return tournamentId;
    }

    /**
     * Sets the ID of the tournament the team belongs to.
     *
     * @param tournamentId The ID of the tournament the team belongs to.
     */
    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
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

    /**
     * Returns a string representation of the team.
     *
     * @return A string representation of the team.
     */
    @Override
    public String toString() {
        return "Team [id=" + id + ", name=" + name + ", logo=" + logo + ", creatorUserId=" + creatorUserId
                + ", tournamentId=" + tournamentId + "]";
    }
}

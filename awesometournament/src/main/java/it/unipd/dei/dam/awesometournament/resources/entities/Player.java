package it.unipd.dei.dam.awesometournament.resources.entities;

import java.io.InputStream;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.unipd.dei.dam.awesometournament.resources.enums.PlayerPosition;

/**
 * A class which represents a player in a match.
 */
public class Player {

    /**
     * The ID of the player.
     */
    @JsonProperty("id")
    private int id;

    /**
     * The name of the player.
     */
    @JsonProperty("name")
    private String name;

    /**
     * The surname of the player.
     */
    @JsonProperty("surname")
    private String surname;

    /**
     * The ID of the team the player belongs to.
     */
    @JsonProperty("team_id")
    private int teamId;

    /**
     * The position of the player in the team.
     */
    @JsonProperty("position")
    private PlayerPosition position;

    /**
     * The medical certificate of the player.
     */
    @JsonProperty("medical_certificate")
    private InputStream medicalCertificate;

    /**
     * The date of birth of the player.
     */
    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    /**
     * Default constructor for JSON deserialization.
     */
    @JsonCreator
    public Player(){}

    /**
     * Constructs a player with the specified parameters.
     *
     * @param id                 The ID of the player.
     * @param name               The name of the player.
     * @param surname            The surname of the player.
     * @param teamId             The ID of the team the player belongs to.
     * @param position           The position of the player in the team.
     * @param medicalCertificate The medical certificate of the player.
     * @param dateOfBirth        The date of birth of the player.
     */
    public Player(int id, String name, String surname, int teamId, PlayerPosition position, InputStream medicalCertificate, Date dateOfBirth) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.teamId = teamId;
        this.position = position;
        this.medicalCertificate = medicalCertificate;
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Returns the ID of the player.
     *
     * @return The ID of the player.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the player.
     *
     * @param id The ID of the player.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the player.
     *
     * @param name The name of the player.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the surname of the player.
     *
     * @return The surname of the player.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of the player.
     *
     * @param surname The surname of the player.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Returns the ID of the team the player belongs to.
     *
     * @return The ID of the team the player belongs to.
     */
    public int getTeamId() {
        return teamId;
    }

    /**
     * Sets the ID of the team the player belongs to.
     *
     * @param teamId The ID of the team the player belongs to.
     */
    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    /**
     * Returns the position of the player in the team.
     *
     * @return The position of the player in the team.
     */
    public PlayerPosition getPosition() {
        return position;
    }

    /**
     * Sets the position of the player in the team.
     *
     * @param position The position of the player in the team.
     */
    public void setPosition(PlayerPosition position) {
        this.position = position;
    }

    /**
     * Returns the medical certificate of the player.
     *
     * @return The medical certificate of the player.
     */
    public InputStream getMedicalCertificate() {
        return medicalCertificate;
    }

    /**
     * Sets the medical certificate of the player.
     *
     * @param medicalCertificate The medical certificate of the player.
     */
    public void setMedicalCertificate(InputStream medicalCertificate) {
        this.medicalCertificate = medicalCertificate;
    }

    /**
     * Returns the date of birth of the player.
     *
     * @return The date of birth of the player.
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the date of birth of the player.
     *
     * @param dateOfBirth The date of birth of the player.
     */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Returns a string representation of the player.
     *
     * @return A string representation of the player.
     */
    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", teamId=" + teamId +
                ", position=" + position +
                ", medicalCertificate='" + medicalCertificate + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}

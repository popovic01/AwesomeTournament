package it.unipd.dei.dam.awesometournament.resources.entities;

import java.io.InputStream;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.unipd.dei.dam.awesometournament.resources.enums.PlayerPosition;

/**
 * Represents a player in the database.
 */
public class Player {

    /**
     * The unique identifier for the player.
     */
    @JsonProperty("id")
    private int id;

    /**
     * The first name of the player.
     */
    @JsonProperty("name")
    private String name;

    /**
     * The last name of the player.
     */
    @JsonProperty("surname")
    private String surname;

    /**
     * The identifier of the team the player belongs to.
     */
    @JsonProperty("team_id")
    private int teamId;

    /**
     * The position of the player within the team.
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
    public Player() {}

    /**
     * Constructs a new Player with the specified attributes.
     *
     * @param id The unique identifier for the player.
     * @param name The first name of the player.
     * @param surname The last name of the player.
     * @param teamId The identifier of the team the player belongs to.
     * @param position The position of the player within the team.
     * @param medicalCertificate The medical certificate of the player.
     * @param dateOfBirth The date of birth of the player.
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

    // Getters and Setters

    /**
     * Returns the unique identifier for the player.
     *
     * @return The unique identifier for the player.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the player.
     *
     * @param id The unique identifier for the player.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the first name of the player.
     *
     * @return The first name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the first name of the player.
     *
     * @param name The first name of the player.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the last name of the player.
     *
     * @return The last name of the player.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the last name of the player.
     *
     * @param surname The last name of the player.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Returns the identifier of the team the player belongs to.
     *
     * @return The identifier of the team the player belongs to.
     */
    public int getTeamId() {
        return teamId;
    }

    /**
     * Sets the identifier of the team the player belongs to.
     *
     * @param teamId The identifier of the team the player belongs to.
     */
    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    /**
     * Returns the position of the player within the team.
     *
     * @return The position of the player within the team.
     */
    public PlayerPosition getPosition() {
        return position;
    }

    /**
     * Sets the position of the player within the team.
     *
     * @param position The position of the player within the team.
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
     * Returns a string representation of the Player object.
     *
     * @return A string representation of the Player object.
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

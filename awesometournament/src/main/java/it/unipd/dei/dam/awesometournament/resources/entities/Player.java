package it.unipd.dei.dam.awesometournament.resources.entities;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.unipd.dei.dam.awesometournament.resources.enums.PlayerPosition;

public class Player {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("surname")
    private String surname;
    @JsonProperty("team_id")
    private int teamId;
    @JsonProperty("position")
    private PlayerPosition position;
    @JsonProperty("medical_certificate")
    private String medicalCertificate;
    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @JsonCreator
    public Player(){}

    public Player(int id, String name, String surname, int teamId, PlayerPosition position, String medicalCertificate, Date dateOfBirth) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.teamId = teamId;
        this.position = position;
        this.medicalCertificate = medicalCertificate;
        this.dateOfBirth = dateOfBirth;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public PlayerPosition getPosition() {
        return position;
    }

    public void setPosition(PlayerPosition position) {
        this.position = position;
    }

    public String getMedicalCertificate() {
        return medicalCertificate;
    }

    public void setMedicalCertificate(String medicalCertificate) {
        this.medicalCertificate = medicalCertificate;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

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

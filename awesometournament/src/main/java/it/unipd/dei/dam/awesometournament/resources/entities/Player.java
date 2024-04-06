package it.unipd.dei.dam.awesometournament.resources.entities;

import java.sql.Date;
import it.unipd.dei.dam.awesometournament.resources.enums.PlayerPosition;

public class Player {
    private int id;
    private String name;
    private String surname;
    private int teamId;
    private PlayerPosition position;
    private String medicalCertificate;
    private Date dateOfBirth;

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
}

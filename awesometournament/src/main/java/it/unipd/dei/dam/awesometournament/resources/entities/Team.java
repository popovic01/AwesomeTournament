package it.unipd.dei.dam.awesometournament.resources.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Team {
    
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("logo")
    private String logo;

    @JsonProperty("creatorUserId")
    private int creatorUserId;

    @JsonProperty("tournamentId")
    private int tournamentId;

    public Team() {

    }

    public Team(int id, String name, String logo, int creatorUserId, int tournamentId) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.creatorUserId = creatorUserId;
        this.tournamentId = tournamentId;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(int creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }

    @Override
    public String toString() {
        return "Team [id=" + id + ", name=" + name + ", logo=" + logo + ", creatorUserId=" + creatorUserId
                + ", tournamentId=" + tournamentId + "]";
    }
}

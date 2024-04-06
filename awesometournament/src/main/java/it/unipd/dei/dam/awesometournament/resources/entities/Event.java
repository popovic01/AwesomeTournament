package it.unipd.dei.dam.awesometournament.resources.entities;

import it.unipd.dei.dam.awesometournament.resources.enums.EventType;

public class Event {
    private int id;
    private int matchId;
    private int playerId;
    private EventType type;
    private int time;

    public Event(int id, int matchId, int playerId, EventType type, int time) {
        this.id = id;
        this.matchId = matchId;
        this.playerId = playerId;
        this.type = type;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}

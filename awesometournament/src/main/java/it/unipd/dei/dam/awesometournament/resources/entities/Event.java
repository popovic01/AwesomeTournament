package it.unipd.dei.dam.awesometournament.resources.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.unipd.dei.dam.awesometournament.resources.enums.EventType;

/**
 * Represents an event in a match, such as a goal or a foul.
 */
public class Event {

    /**
     * The ID of the event.
     */
    @JsonProperty("id")
    private int id;

    /**
     * The ID of the match associated with the event.
     */
    @JsonProperty("match_id")
    private int matchId;

    /**
     * The ID of the player associated with the event.
     */
    @JsonProperty("player_id")
    private int playerId;

    /**
     * The type of the event.
     */
    @JsonProperty("type")
    private EventType type;

    /**
     * The time at which the event occurred.
     */
    @JsonProperty("time")
    private int time;

    /**
     * Default constructor for JSON deserialization.
     */
    @JsonCreator
    public Event(){}

    /**
     * Constructs an event with the specified parameters.
     *
     * @param id       The ID of the event.
     * @param matchId  The ID of the match associated with the event.
     * @param playerId The ID of the player associated with the event.
     * @param type     The type of the event.
     * @param time     The time at which the event occurred.
     */
    public Event(int id, int matchId, int playerId, EventType type, int time) {
        this.id = id;
        this.matchId = matchId;
        this.playerId = playerId;
        this.type = type;
        this.time = time;
    }

    /**
     * Returns the ID of the event.
     *
     * @return The ID of the event.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the event.
     *
     * @param id The ID of the event.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the ID of the match associated with the event.
     *
     * @return The ID of the match associated with the event.
     */
    public int getMatchId() {
        return matchId;
    }

    /**
     * Sets the ID of the match associated with the event.
     *
     * @param matchId The ID of the match associated with the event.
     */
    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    /**
     * Returns the ID of the player associated with the event.
     *
     * @return The ID of the player associated with the event.
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Sets the ID of the player associated with the event.
     *
     * @param playerId The ID of the player associated with the event.
     */
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    /**
     * Returns the type of the event.
     *
     * @return The type of the event.
     */
    public EventType getType() {
        return type;
    }

    /**
     * Sets the type of the event.
     *
     * @param type The type of the event.
     */
    public void setType(EventType type) {
        this.type = type;
    }

    /**
     * Returns the time at which the event occurred.
     *
     * @return The time at which the event occurred.
     */
    public int getTime() {
        return time;
    }

    /**
     * Sets the time at which the event occurred.
     *
     * @param time The time at which the event occurred.
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Returns a string representation of the event.
     *
     * @return A string representation of the event.
     */
    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", matchID='" + matchId + '\'' +
                ", playerID='" + playerId + '\'' +
                ", type=" + type +
                ", time=" + time +
                '}';
    }
}

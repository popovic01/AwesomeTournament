package it.unipd.dei.dam.awesometournament.resources.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An enum which represents a type of event.
 */
public enum EventType {
    @JsonProperty("goal")
    GOAL,
    @JsonProperty("yellow card")
    YELLOW_CARD,
    @JsonProperty("red card")
    RED_CARD;

    public static EventType db2enum(String dbValue) {
        if (dbValue == null)
            return null;
        switch (dbValue) {
            case "goal":
                return GOAL;
            case "yellow card":
                return YELLOW_CARD;
            case "red card":
                return RED_CARD;
            default:
                throw new IllegalArgumentException("Unknown database value: " + dbValue);
        }
    }
    
    public static String enum2db(EventType enumValue) {
        if (enumValue == null) return null;
        switch(enumValue) {
            case GOAL:
                return "goal";
            case YELLOW_CARD:
                return "yellow card";
            case RED_CARD:
                return "red card";
            default:
                throw new IllegalArgumentException("Unknown enum value: " + enumValue);
        }
    }
}

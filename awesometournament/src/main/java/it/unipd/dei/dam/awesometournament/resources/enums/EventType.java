package it.unipd.dei.dam.awesometournament.resources.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An enum which represents a type of event.
 */
public enum EventType {
     /**
     * Represents a goal event.
     */
    @JsonProperty("goal")
    GOAL,

    /**
     * Represents a yellow card event.
     */
    @JsonProperty("yellow card")
    YELLOW_CARD,

    /**
     * Represents a red card event.
     */
    @JsonProperty("red card")
    RED_CARD;

    /**
     * Converts a database value to its corresponding enum value.
     *
     * @param dbValue The database value to convert.
     * @return The corresponding enum value.
     * @throws IllegalArgumentException if the database value is unknown.
     */
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
    
    /**
     * Converts an enum value to its corresponding database value.
     *
     * @param enumValue The enum value to convert.
     * @return The corresponding database value.
     * @throws IllegalArgumentException if the enum value is unknown.
     */
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
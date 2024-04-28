package it.unipd.dei.dam.awesometournament.resources.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An enum which represents a position of a player.
 */
public enum PlayerPosition {
    /**
     * Represents the goalkeeper position.
     */
    @JsonProperty("goalkeeper")
    GOALKEEPER,

    /**
     * Represents the defender position.
     */
    @JsonProperty("defender")
    DEFENDER,

    /**
     * Represents the midfielder position.
     */
    @JsonProperty("midfielder")
    MIDFIELDER,

    /**
     * Represents the striker position.
     */
    @JsonProperty("striker")
    STRIKER;

    /**
     * Converts a database value to its corresponding enum value.
     *
     * @param dbValue The database value to convert.
     * @return The corresponding enum value.
     * @throws IllegalArgumentException if the database value is unknown.
     */
    public static PlayerPosition db2enum(String dbValue) {
        if (dbValue == null) return null;
        switch (dbValue) {
            case "goalkeeper":
                return GOALKEEPER;
            case "defender":
                return DEFENDER;
            case "midfielder":
                return MIDFIELDER;
            case "striker":
                return STRIKER;
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
    public static String enum2db(PlayerPosition enumValue) {
        if (enumValue == null) return null;
        switch(enumValue) {
            case GOALKEEPER:
                return "goalkeeper";
            case DEFENDER:
                return "defender";
            case MIDFIELDER:
                return "midfielder";
            case STRIKER:
                return "striker";
            default:
                throw new IllegalArgumentException("Unknown enum value: " + enumValue);
        }
    }
}
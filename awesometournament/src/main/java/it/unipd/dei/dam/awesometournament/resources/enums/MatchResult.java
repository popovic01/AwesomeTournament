package it.unipd.dei.dam.awesometournament.resources.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the result of a match.
 */
public enum MatchResult {
    /**
     * The first team won the match.
     */
    @JsonProperty("team1")
    TEAM1,

    /**
     * The second team won the match.
     */
    @JsonProperty("team2")
    TEAM2,

    /**
     * The match ended in a draw.
     */
    @JsonProperty("draw")
    DRAW;

    /**
     * Converts a database value to the corresponding enum.
     *
     * @param dbValue The database value to convert.
     * @return The MatchResult enum corresponding to the database value.
     * @throws IllegalArgumentException If the database value is unknown.
     */
    public static MatchResult db2enum(String dbValue) {
        if (dbValue == null)
            return null;
        switch (dbValue) {
            case "team1":
                return TEAM1;
            case "team2":
                return TEAM2;
            case "draw":
                return DRAW;
            default:
                throw new IllegalArgumentException("Unknown database value: " + dbValue);
        }
    }

    /**
     * Converts an enum value to the corresponding database value.
     *
     * @param enumValue The enum value to convert.
     * @return The database value corresponding to the enum value.
     * @throws IllegalArgumentException If the enum value is unknown.
     */
    public static String enum2db(MatchResult enumValue) {
        if (enumValue == null) return null;
        switch(enumValue) {
            case TEAM1:
                return "team1";
            case TEAM2:
                return "team2";
            case DRAW:
                return "draw";
            default:
                throw new IllegalArgumentException("Unknown enum value: " + enumValue);
        }
    }
}

package it.unipd.dei.dam.awesometournament.resources.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MatchResult {
    @JsonProperty ("team1")
    TEAM1,
    @JsonProperty ("team2")
    TEAM2,
    @JsonProperty ("draw")
    DRAW;

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

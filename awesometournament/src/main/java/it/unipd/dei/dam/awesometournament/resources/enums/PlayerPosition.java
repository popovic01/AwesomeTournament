package it.unipd.dei.dam.awesometournament.resources.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PlayerPosition {
    @JsonProperty("goalkeeper")
    GOALKEEPER,
    @JsonProperty("defender")
    DEFENDER,
    @JsonProperty("midfielder")
    MIDFIELDER,
    @JsonProperty("striker")
    STRIKER;

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

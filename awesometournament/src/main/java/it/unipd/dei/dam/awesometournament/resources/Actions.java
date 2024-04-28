package it.unipd.dei.dam.awesometournament.resources;

/**
 * This class defines constants representing various actions or endpoints in the application.
 * Each constant represents a specific action that can be performed or a resource that can be accessed.
 */
public final class Actions {
    // Player actions
    public static final String GET_PLAYER = "GET_PLAYER";
    public static final String PUT_PLAYER = "PUT_PLAYER";
    public static final String POST_PLAYER = "POST_PLAYER";
    public static final String DELETE_PLAYER = "DELETE_PLAYER";

    // Team player actions
    public static final String POST_TEAM_PLAYER = "POST_TEAM_PLAYER";
    public static final String GET_TEAM_PLAYER = "GET_TEAM_PLAYER";

    // Tournament actions
    public static final String GET_TOURNAMENT = "GET_TOURNAMENT";
    public static final String PUT_TOURNAMENT = "PUT_TOURNAMENT";
    public static final String POST_TOURNAMENT = "POST_TOURNAMENT";
    public static final String DELETE_TOURNAMENT = "DELETE_TOURNAMENT";

    // Tournament matches actions
    public static final String GET_TOURNAMENT_MATCHES = "GET_TOURNAMENT_MATCHES";

    // Tournament teams actions
    public static final String GET_TOURNAMENT_TEAMS = "GET_TOURNAMENT_TEAMS";
    public static final String POST_TOURNAMENT_TEAM = "POST_TOURNAMENT_TEAM";

    // Team actions
    public static final String GET_TEAM = "GET_TEAM";
    public static final String PUT_TEAM = "PUT_TEAM";
    public static final String POST_TEAM = "POST_TEAM";
    public static final String DELETE_TEAM = "DELETE_TEAM";

    // Match actions
    public static final String GET_MATCH = "GET_MATCH";
    public static final String PUT_MATCH = "PUT_MATCH";
    public static final String POST_MATCHES = "POST_MATCHES";
    
    // Ranking actions
    public static final String GET_RANKING = "GET_RANKING";

    // Scorer ranking actions
    public static final String GET_RANKING_SCORERS = "GET_RANKING_SCORERS";
    
    // Event actions
    public static final String GET_EVENT = "GET_EVENT";
    public static final String PUT_EVENT = "PUT_EVENT";
    public static final String DELETE_EVENT = "DELETE_EVENT";

    // Match event actions
    public static final String GET_MATCH_EVENT = "GET_MATCH_EVENT";
    public static final String POST_MATCH_EVENT = "POST_MATCH_EVENT";
    
    // User actions
    public static final String GET_USER = "GET_USER";
    public static final String USER_LOGIN = "USER_LOGIN";
    public static final String USER_SIGNUP = "USER_SIGNUP";
    public static final String USER_LOGOUT = "USER_LOGOUT";
    
    // Page actions
    public static final String GET_HOMEPAGE = "GET_HOMEPAGE";
    public static final String GET_INDEX = "GET_INDEX";
    public static final String GET_LOGIN_PAGE = "GET_LOGIN_PAGE";
}

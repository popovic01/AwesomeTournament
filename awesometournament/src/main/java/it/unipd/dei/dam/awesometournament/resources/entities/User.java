package it.unipd.dei.dam.awesometournament.resources.entities;

/**
 * A class which represents a user.
 */
public class User {
    /** The ID of the user. */
    private int id;

    /** The email of the user. */
    private String email;

    /** The password of the user. */
    private String password;

    /**
     * Constructs a user with the specified parameters.
     *
     * @param id       The ID of the user.
     * @param email    The email of the user.
     * @param password The password of the user.
     */
    public User(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    /**
     * Returns the ID of the user.
     *
     * @return The ID of the user.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the user.
     *
     * @param id The ID of the user.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the email of the user.
     *
     * @return The email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email The email of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns a string representation of the user.
     *
     * @return A string representation of the user.
     */
    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", password=" + password + "]";
    }
}

package it.unipd.dei.dam.awesometournament.resources.entities;

//The class uses ThreadContext from Apache Log4j, which is a Map-based structure
//that allows you to store contextual information for logging on a per-thread basis.
import org.apache.logging.log4j.ThreadContext;

/**
 * LogContext is designed to provide a logging context for actions performed within a web application.
 * It allows developers to log contextual information related to user actions, such as the user performing
 * an action, their IP address, the action itself and the resource being processed.
 */
public final class LogContext {
    /**
     * The user who is performing an action
     */
    private static final String USER = "USER";

    /**
     * The IP address of the user who is performing an action
     */
    private static final String IP = "IP";

    /**
     * The action performed by the user
     */
    private static final String ACTION = "ACTION";

    /**
     * The resource currently processed
     */
    private static final String RESOURCE = "RESOURCE";

    /**
     * Sets the user currently performing actions
     */
    public static void setUser(final String user) {
        if (user != null && !user.isEmpty()) {
            ThreadContext.put(USER, user);
        }
    }

    /**
     * Removes the user currently performing actions
     */
    public static void removeUser() {
        ThreadContext.remove(USER);
    }

    /**
     * Sets the IP address of the user currently performing actions
     */
    public static void setIPAddress(final String ip) {
        if (ip != null && !ip.isEmpty()) {
            ThreadContext.put(IP, ip);
        }
    }

    /**
     * Removes the IP address of the user currently performing actions
     */
    public static void removeIPAddress() {
        ThreadContext.remove(IP);
    }


    /**
     *  Sets the action currently being performed
     */
    public static void setAction(final String action) {
        if (action != null) {
            ThreadContext.put(ACTION, action);
        }
    }


    /**
     * Removes the currently performed action
     */
    public static void removeAction() {
        ThreadContext.remove(ACTION);
    }

    /**
     * Sets the resource currently being processed
     */
    public static void setResource(final String resource) {
        if (resource != null && !resource.isEmpty()) {
            ThreadContext.put(RESOURCE, resource);
        }
    }

    /**
     * Removes the currently processed resource
     */
    public static void removeResource() {
        ThreadContext.remove(RESOURCE);
    }

    /**
     *
     * This class can be neither instantiated nor sub-classed.
     */
    private LogContext() {
        throw new AssertionError(String.format("No instances of %s allowed.", LogContext.class.getName()));
    }
}
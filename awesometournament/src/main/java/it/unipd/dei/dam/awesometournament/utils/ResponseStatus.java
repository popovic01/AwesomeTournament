package it.unipd.dei.dam.awesometournament.utils;

/**
 * An enum which represents possible response statuses.
 */
public enum ResponseStatus {

    /**
     * Indicates a successful response.
     */
    OK(200),

    /**
     * Indicates that a resource has been created successfully.
     */
    CREATED(201),

    /**
     * Indicates that the server successfully processed the request but is not returning any content.
     */
    NO_CONTENT(204),

    /**
     * Indicates that the requested resource has been temporarily moved to a different URL.
     */
    FOUND(302),

    /**
     * Indicates that the request cannot be fulfilled due to bad syntax.
     */
    BAD_REQUEST(400),

    /**
     * Indicates that authentication is required and has failed or has not yet been provided.
     */
    UNAUTHORIZED(401),

    /**
     * Indicates that the server understood the request but refuses to authorize it.
     */
    FORBIDDEN(403),

    /**
     * Indicates that the requested resource could not be found.
     */
    NOT_FOUND(404),

    /**
     * Indicates that the method specified in the request is not allowed for the resource identified by the request URI.
     */
    METHOD_NOT_ALLOWED(405),

    /**
     * Indicates that the server encountered an unexpected condition that prevented it from fulfilling the request.
     */
    INTERNAL_SERVER_ERROR(500),

    /**
     * Indicates that the server does not recognize the request method and is not capable of supporting it for any resource.
     */
    NOT_IMPLEMENTED(501),

    /**
     * Indicates that the server is currently unable to handle the request due to temporary overload or maintenance.
     */
    SERVICE_UNAVAILABLE(503);

    /**
     * The HTTP status code associated with the response status.
     */
    private final int code;

    /**
     * Constructs a response status with the specified status code.
     *
     * @param code The HTTP status code associated with the response status.
     */
    ResponseStatus(int code) {
        this.code = code;
    }

    /**
     * Gets the HTTP status code associated with the response status.
     *
     * @return The HTTP status code associated with the response status.
     */
    public int getCode() {
        return code;
    }
}

package it.unipd.dei.dam.awesometournament.utils;

/**
 * A class which represents response with message and status.
 */
public class ResponsePackageNoData {
    /**
     * The status of the response.
     */
    private ResponseStatus status;

    /**
     * The message associated with the response.
     */
    private String message;

    /**
     * Constructs an empty response package with default status and message.
     */
    public ResponsePackageNoData() {
        this.status = ResponseStatus.OK;
        this.message = "";
    }

    /**
     * Constructs a response package with the specified status and message.
     *
     * @param status  The status of the response.
     * @param message The message associated with the response.
     */
    public ResponsePackageNoData(ResponseStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * Gets the status of the response.
     *
     * @return The status of the response.
     */
    public ResponseStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the response.
     *
     * @param status The status to be set for the response.
     */
    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

     /**
     * Gets the message associated with the response.
     *
     * @return The message associated with the response.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message associated with the response.
     *
     * @param message The message to be associated with the response.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}

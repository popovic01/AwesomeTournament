package it.unipd.dei.dam.awesometournament.utils;

/**
 * A class which represents response with message, status and data.
 */
public class ResponsePackage<T> extends ResponsePackageNoData {
    /**
     * The data contained in the response package.
     */
    private T data;

    /**
     * Constructs an empty response package.
     */
    public ResponsePackage() {
        super();
    }

    /**
     * Constructs a response package with the specified data, status, and message.
     *
     * @param data    The data to be included in the response package.
     * @param status  The status of the response.
     * @param message The message associated with the response.
     */
    public ResponsePackage(T data, ResponseStatus status, String message) {
        super(status, message);
        this.data = data;
    }

    /**
     * Gets the data contained in the response package.
     *
     * @return The data contained in the response package.
     */
    public T getData() { return data; }

    /**
     * Sets the data contained in the response package.
     *
     * @param data The data to be set in the response package.
     */
    public void setData(T data) { this.data = data; }
}

package it.unipd.dei.dam.awesometournament.utils;

/**
 * A class which represents response with message and status.
 */
public class ResponsePackageNoData {
    private ResponseStatus status;
    private String message;

    public ResponsePackageNoData() {
        this.status = ResponseStatus.OK;
        this.message = "";
    }

    public ResponsePackageNoData(ResponseStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
